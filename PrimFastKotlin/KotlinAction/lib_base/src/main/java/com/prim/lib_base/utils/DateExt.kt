package com.prim.lib_base.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * this is date ext function
 **/

fun Date.format(pattern: String) = SimpleDateFormat(pattern, Locale.CHINA).format(this)
