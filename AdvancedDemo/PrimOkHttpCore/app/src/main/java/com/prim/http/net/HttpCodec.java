package com.prim.http.net;

import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author prim
 * @version 1.0.0
 * @desc 专门处理Http的拼接和响应Http
 * @time 2019-08-15 - 18:48
 */
public class HttpCodec {
    //定义拼接常用的常量
    static final String CRLF = "\r\n";
    static final int CR = 13;
    static final int LF = 10;
    static final String SPACE = " ";
    static final String VERSION = "HTTP/1.1";
    static final String COLON = ":";

    public static final String HEAD_HOST = "Host";
    public static final String HEAD_CONNECTION = "Connection";
    public static final String HEAD_CONTENT_TYPE = "Content-Type";
    public static final String HEAD_CONTENT_LENGTH = "Content-Length";
    public static final String HEAD_TRANSFER_ENCODING = "Transfer-Encoding";

    public static final String HEAD_VALUE_KEEP_ALIVE = "Keep-Alive";
    public static final String HEAD_VALUE_CHUNKED = "chunked";

    //将读出来的字节添加到缓存中
    private final ByteBuffer byteBuffer;

    public HttpCodec() {
        byteBuffer = ByteBuffer.allocate(10 * 1024);
    }

    /**
     * 拼接http协议的信息,并将这些信息写入OutputStream
     *
     * @param out     OutputStream 写入http的协议信息
     * @param request 请求的包装类
     * @throws IOException
     */
    public void writeRequest(OutputStream out, Request request) throws IOException {
        StringBuffer sb = new StringBuffer();
        //请求行 GET / 。。。。。/ HTTP/1.1\r\n
        sb.append(request.getMethod());
        sb.append(SPACE);
        sb.append(request.getUrl().getFile());
        sb.append(SPACE);
        sb.append(VERSION);
        sb.append(CRLF);

        //请求头
        Map<String, String> headers = request.getHeaders();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            sb.append(entry.getKey());
            sb.append(COLON);
            sb.append(SPACE);
            sb.append(entry.getValue());
            sb.append(CRLF);
        }
        sb.append(CRLF);

        //请求体 POST 请求会用到
        RequestBody body = request.getBody();
        if (null != body) {
            sb.append(body.body());
        }
        out.write(sb.toString().getBytes());
        out.flush();
    }

    /**
     * 读取一行
     *
     * @param in 服务器返回的数据
     * @return
     * @throws IOException
     */
    public String readLine(InputStream in) throws IOException {
        //清理
        byteBuffer.clear();
        //标记
        byteBuffer.mark();
        boolean isMabeEofLine = false;
        byte b;
        while ((b = (byte) in.read()) != -1) {
            byteBuffer.put(b);
            //如果读到一个\r
            if (b == CR) {
                isMabeEofLine = true;
            } else if (isMabeEofLine) {
                //读到\n一行结束
                if (b == LF) {
                    //一行数据
                    byte[] lineBytes = new byte[byteBuffer.position()];
                    //将标记设置为0
                    byteBuffer.reset();
                    //从allocate获得数据
                    byteBuffer.get(lineBytes);
                    byteBuffer.clear();
                    byteBuffer.mark();
                    //将一行数据返回
                    return new String(lineBytes);
                }
                //如果下一个不是\n 置为false
                isMabeEofLine = false;
            }
        }
        //如果读完了都没有读到 则服务器出现问题
        throw new IOException("Response read line");
    }

    /**
     * 读取响应头
     *
     * @param in
     * @return
     */
    public Map<String, String> readHeader(InputStream in) throws IOException {
        HashMap<String, String> headers = new HashMap<>();
        while (true) {
            //读取一行
            String line = readLine(in);
            //如果读到空行\r\n 表示响应头读取完毕了
            if (isEmptyLine(line)) {
                break;
            }
            //读取响应头的key value
            int index = line.indexOf(":");
            if (index > 0) {
                String key = line.substring(0, index);
                //key与value还有空格
                String value = line.substring(index + 2, line.length() - 2);
                headers.put(key, value);
            }
        }
        return headers;
    }

    /**
     * 读取的是否为空行
     *
     * @param line
     * @return
     */
    private boolean isEmptyLine(String line) {
        return TextUtils.equals(line, CRLF);
    }

    /**
     * 读取一定长度的字节
     *
     * @param in
     * @param len
     * @return
     */
    public byte[] readBytes(InputStream in, int len) throws IOException {
        byte[] bytes = new byte[len];
        int readNum = 0;
        while (true) {
            readNum += in.read(bytes, readNum, len - readNum);
            if (readNum == len) {
                return bytes;
            }
        }
    }

    public String readChunked(InputStream in) throws IOException {
        int len = -1;
        boolean isEmptyData = false;
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            if (len < 0) {
                String line = readLine(in);
                line = line.substring(0, line.length() - 2);
                //获取长度 16进制 将16进制的字符串转成10进制的整形
                len = Integer.valueOf(line, 16);
                isEmptyData = len == 0;
            } else {
                //读取内容
                byte[] bytes = readBytes(in, len + 2);//把\r\n也读了
                stringBuffer.append(new String(bytes));
                len = -1;
                if (isEmptyData) {
                    return stringBuffer.toString();
                }
            }
        }
    }
}
