package com.prim.lib_base.utils

/**
 * @desc 数字的扩展类
 * @author prim
 * @time 2019-06-24 - 13:52
 * @version 1.0.0
 */

fun Int.toKilo(): String {
    return if (this > 1000) {
        "${(Math.round(this / 100f) / 10f)}k"
    } else {
        "$this"
    }
}
