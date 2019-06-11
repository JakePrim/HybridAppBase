package com.prim.lib_base.utils

import com.prim.lib_base.AppContext
import kotlin.reflect.jvm.jvmName

/**
 * @desc 继续简化SharePreferences name 可自动获取 变量的名称
 * @author prim
 * @time 2019-06-10 - 18:45
 * @version 1.0.0
 */
inline fun <reified R, T> R.pref(default: T) = Preference(AppContext, "", default, R::class.jvmName)

inline fun <reified R, T> R.pref(default: T, name: String) = Preference(AppContext, name, default, R::class.jvmName)