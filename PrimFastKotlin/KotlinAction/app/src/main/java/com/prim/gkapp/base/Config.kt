package com.prim.gkapp.base

import com.prim.gkapp.AppContext
import com.prim.gkapp.log.logger
import com.prim.gkapp.utils.deviceID

/**
 * @desc
 * @author prim
 * @time 2019-05-29 - 07:27
 * @version 1.0.0
 */

object Config {

    object Auth {
        //dff9db535c8e05b0467a
        const val CLIENT_ID = "f436bf9f6125615d669b"//cccb7d70ba4fe6d4f62d
        //c1c4908ce62bcb866971edc962a6bed24bf99300
        const val CLIENT_SECRET = "c0eedfb1c9ade985ff59227e384a6dcdac0dde12"//30bea5fc021ed503bef21e23ce8e2b02d588ab6c
        //https://jakeprim.cn
        const val NOTE_URL = "https://jakeprim.cn"
        const val NOTE = "码乎.GitHub"//http://www.kotliner.cn
        val SCOPES = listOf("repo", "user", "notifications", "gist", "admin:org")
        //lazy 延迟初始化 只有在使用的是否才会进行赋值
        val fingerPrint by lazy {
            //also 在函数块内可以通过it指代该对象，返回值为该对象自己
            (AppContext.deviceID + CLIENT_ID).also {
                logger.info("fingerPrint: " + it)
            }
        }
    }

}