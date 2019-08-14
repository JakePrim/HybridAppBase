package com.prim.http.net;


import java.io.IOException;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019-08-14 - 18:01
 */
public interface Interceptor {
    Response interceptor(InterceptorChain chain) throws IOException;
}
