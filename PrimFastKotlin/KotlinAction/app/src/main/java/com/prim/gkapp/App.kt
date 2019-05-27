package com.prim.gkapp

import android.app.Application
import android.content.ContextWrapper

/**
 * @desc
 * @author prim
 * @time 2019-05-26 - 10:45
 * @version 1.0.0
 */
//lateinit 延迟初始化
private lateinit var INSTANCE:Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}

object AppContext:ContextWrapper(INSTANCE)