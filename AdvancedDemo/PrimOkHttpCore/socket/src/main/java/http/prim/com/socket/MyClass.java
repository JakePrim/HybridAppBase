package http.prim.com.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.net.ssl.SSLSocketFactory;

public class MyClass {

    public static void main(String[] args) {
        doHttps();//SSL 的过程？？？ 如何使用证书？？？
    }

    public static void doHttp() {

    }

    public static void doHttps() {
        //导入提取的cer证书 如果服务器需要证书的话  这里需要导入证书


        //ssl socket工厂创建ssl
        try {
            //Socket socket = new Socket("www.baidu.com", 80);//http 默认端口80 https 默认端口443
            Socket socket = SSLSocketFactory.getDefault().createSocket("www.baidu.com", 443);
            doSocket(socket, "www.baidu.com");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void doSocket(Socket socket, String host) {
        try {
            //输入流 接收数据
            InputStream inputStream = socket.getInputStream();

            //转成BufferedReader 一次读取一行
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            //输出流 发送数据
            OutputStream outputStream = socket.getOutputStream();

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //读取数据
                        String readLine = null;
                        while ((readLine = bufferedReader.readLine()) != null) {
                            System.out.println("读取：" + readLine);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            //发送数据
            bufferedWriter.write("GET / HTTP/1.1\r\n");
            bufferedWriter.write("Host: " + host + "\r\n\r\n");
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
