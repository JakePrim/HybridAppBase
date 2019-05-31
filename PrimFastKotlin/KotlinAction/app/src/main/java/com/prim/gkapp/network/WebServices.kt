package com.prim.gkapp.network

import com.prim.gkapp.AppContext
import com.prim.gkapp.ext.ensureDir
import com.prim.gkapp.network.interceptors.AcceptInterceptor
import com.prim.gkapp.network.interceptors.AuthInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @desc 请求网络
 * @author prim
 * @time 2019-05-29 - 07:30
 * @version 1.0.0
 */

private const val BASE_URL = "https://api.github.com"

//lazy 延迟初始化 再用到到时候才会初始化

//缓存
private val cacheFile by lazy {
    File(AppContext.cacheDir, "webApi").apply { ensureDir() }
}

val retrofit by lazy {
    Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(
            OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(AuthInterceptor())
                .addInterceptor(AcceptInterceptor())
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .cache(Cache(cacheFile, 1024 * 1024 * 1024))
                .build()
        )
        .baseUrl(BASE_URL)
        .build()
}