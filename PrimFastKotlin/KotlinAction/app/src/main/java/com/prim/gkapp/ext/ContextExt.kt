package com.prim.gkapp.ext

import android.content.Context
import android.widget.Toast

/**
 * @desc 对Context对扩展
 * @author prim
 * @time 2019-06-02 - 00:37
 * @version 1.0.0
 */

fun Context.toastKx(value: String): Toast =
    Toast.makeText(this, value, Toast.LENGTH_SHORT).apply { show() }
