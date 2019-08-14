package com.prim.http.net;


/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019-08-14 - 17:20
 */
public interface CallBack {
    void onFailure(Call call,Throwable throwable);
    void onResponse(Call call, Response response);
}
