package com.prim.lib_base.utils

import android.content.Context
import android.os.Parcelable
import android.text.TextUtils
import java.io.*
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
        return findPreference(findName(property))
    }

    //相当于 = 可以直接设置值，设置进入Preference
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(findName(property), value)
    }

    private fun findName(property: KProperty<*>) = if (name.isEmpty()) property.name else name

    private fun findPreference(key: String): T {
        return when (default) {
            is Long -> prefs.getLong(key, default)
            is Int -> prefs.getInt(key, default)
            is String -> prefs.getString(key, default)
            is Boolean -> prefs.getBoolean(key, default)
            is Parcelable -> deSerialization(prefs.getString(key, default.toString()))
            is Serializable -> deSerialization(prefs.getString(key, default.toString()))
            else -> throw IllegalArgumentException("Unsupport type")
        } as T
    }

    private fun putPreference(key: String, value: T) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(key, value)
            is Int -> putInt(key, value)
            is String -> putString(key, value)
            is Boolean -> putBoolean(key, value)
            is Parcelable -> putString(key, serialize(value))
            is Serializable -> putString(key, serialize(value))
            else -> throw IllegalArgumentException("Unsupport type")
        }
    }.apply()

    @Throws(IOException::class)
    private fun serialize(obj: Any): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val objectOutputStream = ObjectOutputStream(
            byteArrayOutputStream
        )
        objectOutputStream.writeObject(obj)
        var serStr = byteArrayOutputStream.toString("ISO-8859-1")
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8")
        objectOutputStream.close()
        byteArrayOutputStream.close()
        return serStr
    }

    @Throws(IOException::class, ClassNotFoundException::class)
    private fun deSerialization(str: String): Any? {
        if (TextUtils.isEmpty(str)) {
            return null
        }

        val redStr = java.net.URLDecoder.decode(str, "UTF-8")
        val byteArrayInputStream = ByteArrayInputStream(
            redStr.toByteArray(charset("ISO-8859-1"))
        )
        val objectInputStream = ObjectInputStream(
            byteArrayInputStream
        )
        val obj = objectInputStream.readObject() as Any
        objectInputStream.close()
        byteArrayInputStream.close()
        return obj
    }

}