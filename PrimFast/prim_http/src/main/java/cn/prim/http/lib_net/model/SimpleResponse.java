package cn.prim.http.lib_net.model;

import java.io.Serializable;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019/1/3 - 7:04 PM
 */
public class SimpleResponse implements Serializable {

    private static final long serialVersionUID = -3133370400211434125L;

    public int code;
    public String msg;

    public Response toResponse() {
        Response response = new Response();
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }

}
