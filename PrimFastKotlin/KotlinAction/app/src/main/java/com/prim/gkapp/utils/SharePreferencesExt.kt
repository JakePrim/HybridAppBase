package com.prim.gkapp.utils

import android.content.Context
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @desc sharePreferences 的扩展类 可作为代理类来简化sharePreferences
 * @author prim
 * @time 2019-05-26 - 10:28
 * @version 1.0.0
 */
class Preference<T>(val context: Context, val name: String, val default: T, val prefName: String = "default") :
    ReadWriteProperty<Any?, T> {
    private val prefs by lazy {
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    }

    //相当于直接获取值
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findPreference(name)
    }

    //相当于 = 可以直接设置值，设置进入Preference
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(name, value)
    }

    private fun findPreference(key: String): T {
        return when (default) {
            is Long -> prefs.getLong(key, default)
            is Int -> prefs.getInt(key, default)
            is String -> prefs.getString(key, default)
            is Boolean -> prefs.getBoolean(key, default)
            else -> throw IllegalArgumentException("Unsupport type")
        } as T
    }

    private fun putPreference(key: String, value: T) {
        with(prefs.edit()) {
            when (value) {
                is Long -> putLong(key, value)
                is Int -> putInt(key, value)
                is String -> putString(key, value)
                is Boolean -> putBoolean(key, value)
                else -> throw IllegalArgumentException("Unsupport type")
            }
        }.apply()
    }

}