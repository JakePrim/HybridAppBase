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
        const val CLIENT_ID = "dff9db535c8e05b0467a"
        const val CLIENT_SECRET = "c1c4908ce62bcb866971edc962a6bed24bf99300"
        const val NOTE_URL = "https://jakeprim.cn"
        const val NOTE = "码乎"
        val SCOPES = listOf("repo", "user", "notifications", "gist", "admin:org")
        //lazy 延迟初始化 只有在使用的是否才会进行赋值
        val fingerPrint by lazy {
            //also 在函数块内可以通过it指代该对象，返回值为该对象自己
            (AppContext.deviceID + CLIENT_ID).also {
                logger.info("fingerPrint:" + it)
            }
        }
    }

}