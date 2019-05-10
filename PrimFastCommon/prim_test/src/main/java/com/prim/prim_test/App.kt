package com.prim.prim_test

import android.app.Application
import cn.prim.http.lib_net.PrimHttp

/**
 * @desc
 * @author prim
 * @time 2019-05-10 - 14:24
 * @version 1.0.0
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        //网络请求配置
        PrimHttp
            .init(this)
            .withHost("https://music.aityp.com/")
            .connectionTimeout(5000)
            .readTimeout(5000)
            .writeTimeout(5000)
            .build()
    }
}