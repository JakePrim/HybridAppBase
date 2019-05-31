package com.prim.gkapp.network.interceptors

import android.util.Base64
import com.prim.gkapp.data.model.UserInfo
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @desc 鉴权拦截器
 * @author prim
 * @time 2019-05-30 - 22:30
 * @version 1.0.0
 */
class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()//拿到原来的请求

        return chain.proceed(request.newBuilder()
                //apply 可以调用对象的任意方法 并返回对象
            .apply {
                when {
                    //当请求接口是authorizations
                    request.url().pathSegments().contains("authorizations") -> {
                        val userCreator = UserInfo.username + ":" + UserInfo.password
                        val auth = "Basic " + String(Base64.encode(userCreator.toByteArray(), Base64.DEFAULT)).trim()
                        header("Authorization", auth)//添加header
                    }
                    //如果用户已经登录传递token
                    UserInfo.isLogin() -> {
                        val auth = "Token " + UserInfo.token
                        header("Authorization", auth)
                    }
                    //否则就删掉head
                    else -> {
                        removeHeader("Authorization")
                    }
                }
            }
            .build())
    }
}