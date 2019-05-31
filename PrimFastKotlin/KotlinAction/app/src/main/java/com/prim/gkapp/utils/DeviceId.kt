package com.prim.gkapp.utils

import android.content.Context
import android.provider.Settings.Secure.ANDROID_ID
import android.provider.Settings.Secure.getString

/**
 * @desc 获取deviceID 给context一个扩展的属性
 * @author prim
 * @time 2019-05-29 - 09:57
 * @version 1.0.0
 */
val Context.deviceID: String
    get() = getString(contentResolver, ANDROID_ID)