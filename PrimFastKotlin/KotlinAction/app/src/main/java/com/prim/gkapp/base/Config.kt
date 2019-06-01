package com.prim.gkapp.base

import com.prim.gkapp.AppContext
import com.prim.gkapp.BuildConfig
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
        const val CLIENT_ID = BuildConfig.client_id
        const val CLIENT_SECRET = BuildConfig.client_secret
        //https://jakeprim.cn
        const val NOTE_URL = BuildConfig.note_url
        const val NOTE = BuildConfig.note
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