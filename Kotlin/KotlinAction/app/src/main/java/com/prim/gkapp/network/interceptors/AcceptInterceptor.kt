package com.prim.gkapp.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

/**
 * @desc
 * @author prim
 * @time 2019-05-30 - 23:01
 * @version 1.0.0
 */
class AcceptInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return chain.proceed(request.newBuilder().apply {
            header("Accept", "application/vnd.github.v3.full+json,${request.header("accept") ?: ""}")
        }.build())
    }
}