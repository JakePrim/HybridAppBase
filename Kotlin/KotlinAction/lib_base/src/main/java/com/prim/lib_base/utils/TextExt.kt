package com.prim.lib_base.utils

import android.widget.TextView
import androidx.annotation.StringRes

/**
 * @desc 扩展TextView
 * @author prim
 * @time 2019-07-02 - 10:44
 * @version 1.0.0
 */
fun TextView.textRes(@StringRes res: Int) {
    val string = resources.getString(res)
    text = string
}