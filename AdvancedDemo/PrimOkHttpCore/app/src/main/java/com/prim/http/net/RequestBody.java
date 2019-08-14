package com.prim.http.net;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author prim
 * @version 1.0.0
 * @desc 请求体的封装类
 * @time 2019-06-24 - 07:19
 */
public class RequestBody {
    /**
     * 表单提交 使用urlencoded编码
     */
    private final static String CONTENT_TYPE = "application/x-www-form-urlencoded";

    private final static String CHARSET = "utf-8";

    private Map<String,String> encodedBodys = new HashMap<>();

    public String contentType(){
        return CONTENT_TYPE;
    }

    public long contentLength(){
        return body().getBytes().length;
    }

    /**
     * 真正的请求体 就是一个key:value & ...字符串
     * @return
     */
    public String body(){
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : encodedBodys.entrySet()) {
            sb.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("&");
        }
        if (sb.length() != 0){
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }

    public RequestBody add(String name,String value){
        try {
            encodedBodys.put(URLEncoder.encode(name,CHARSET),URLEncoder.encode(value,CHARSET));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return this;
    }
}
