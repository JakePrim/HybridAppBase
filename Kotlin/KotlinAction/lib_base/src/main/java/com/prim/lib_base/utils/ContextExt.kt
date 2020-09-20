package com.prim.lib_base.utils

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi

/**
 * @desc 对Context对扩展
 * @author prim
 * @time 2019-06-02 - 00:37
 * @version 1.0.0
 */

fun Context.toastKx(value: String): Toast =
    Toast.makeText(this, value, Toast.LENGTH_SHORT).apply { show() }

fun Context.getColorEx(color: Int): Int = resources.getColor(color)

@RequiresApi(Build.VERSION_CODES.M)
fun Context.getColorEx(color: Int, theme: Resources.Theme): Int = resources.getColor(color, theme)
