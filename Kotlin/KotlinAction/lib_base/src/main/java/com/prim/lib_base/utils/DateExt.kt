package com.prim.lib_base.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * this is date ext function
 **/

fun Date.format(pattern: String) = SimpleDateFormat(pattern, Locale.CHINA).format(this)

val outputDateFormatter by lazy {
    SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
}

val githubDateFormatter by lazy {
    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.CHINA)
            .apply {
                timeZone = TimeZone.getTimeZone("GMT")
            }
}

fun timeToData(time: String) = githubDateFormatter.parse(time)

fun Date.rule1(): String {
    val currentTimeMillis = System.currentTimeMillis()
    val senconds = (currentTimeMillis - this.time) / 1000
    val minutes = senconds / 60
    return if (minutes >= 60) {
        val hours = minutes / 60
        if (hours in 24..47) {
            "Yesterday"
        } else if (hours >= 48) {
            outputDateFormatter.format(this)
        } else {
            "$hours hours ago"
        }
    } else if (minutes < 1) {
        "$senconds seconds ago"
    } else {
        "$minutes minutes ago"
    }
}