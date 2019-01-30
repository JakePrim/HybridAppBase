package cn.prim.http.lib_net.model;

import okhttp3.ResponseBody;

import java.io.Serializable;

/**
 * @author prim
 * @version 1.0.0
 * @desc 返回结果类
 * @time 2019/1/3 - 4:20 PM
 */
public class Response<T> implements Serializable {
    private static final long serialVersionUID = -2173983579010786233L;
    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
