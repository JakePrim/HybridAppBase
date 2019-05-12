package cn.prim.http.lib_net.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author prim
 * @version 1.0.0
 * @desc 返回结果类 统一标准的结果类
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

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response<?> response = (Response<?>) o;
        return code == response.code &&
                Objects.equals(msg, response.msg) &&
                Objects.equals(data, response.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, msg, data);
    }
}
