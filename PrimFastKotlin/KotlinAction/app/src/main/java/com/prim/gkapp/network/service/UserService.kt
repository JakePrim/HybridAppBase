package com.prim.gkapp.network.service

import com.prim.gkapp.data.model.User
import com.prim.gkapp.network.retrofit
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * @desc
 * @author prim
 * @time 2019-05-30 - 09:55
 * @version 1.0.0
 */
interface UserApi {
    @GET("/user")
    fun authUser():Observable<User>
}

//生成单例 代理retrofit
object UserService:UserApi by retrofit.create(UserApi::class.java)