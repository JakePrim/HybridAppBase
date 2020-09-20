package com.prim.gkapp.utils

import com.google.gson.Gson

/**
 * @desc
 * @author prim
 * @time 2019-05-30 - 23:24
 * @version 1.0.0
 */
//reified??
inline fun <reified T> Gson.fromJson(json:String) = fromJson(json,T::class.java)