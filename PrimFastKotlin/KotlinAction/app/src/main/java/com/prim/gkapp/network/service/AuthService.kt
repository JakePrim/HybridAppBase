package com.prim.gkapp.network.service

import com.prim.gkapp.base.Config
import com.prim.gkapp.data.model.AuthBody
import com.prim.gkapp.data.model.AuthResponse
import com.prim.gkapp.network.retrofit
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * @desc auth request API
 * @author prim
 * @time 2019-05-29 - 10:26
 * @version 1.0.0
 */

interface AuthApi {
    //请求授权
    @PUT("/authorizations/clients/${Config.Auth.CLIENT_ID}/{fingerprint}")
    fun createAuth(
        @Body req: AuthBody,
        @Path("fingerprint") fingerprint: String = Config.Auth.fingerPrint
    ): Observable<AuthResponse>


    //删除授权
    @DELETE("/authorizations/{authorization_id}")
    fun deleteAuth(@Path("authorization_id") id: Int): Observable<Response<Any>>
}

//object 实现单例 代理retrofit
object AuthService : AuthApi by retrofit.create(AuthApi::class.java)
