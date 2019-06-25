package com.prim.gkapp.config

import com.prim.gkapp.BuildConfig
import com.prim.gkapp.R
import com.prim.lib_base.AppContext
import com.prim.lib_base.log.logger
import com.prim.lib_base.utils.deviceID
import com.prim.lib_base.utils.pref

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

    //是否开启item点击动画
    var itemClickAnimator: Boolean by pref(false)
    //是否开启list滑动动画
    var listScrollAnimator: Boolean by pref(false)

    val languageColor = mapOf<String, Int>(
        "Java" to R.drawable.shape_java_dot,
        "Dart" to R.drawable.shape_dart_dot,
        "Html" to R.drawable.shape_html_dot,
        "JavaScript" to R.drawable.shape_javascript_dot,
        "Kotlin" to R.drawable.shape_kotlin_dot,
        "Lua" to R.drawable.shape_lua_dot,
        "" to R.color.colorPrimary
    )

}