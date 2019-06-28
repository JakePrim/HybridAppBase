package com.prim.lib_base.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * @desc
 * @author prim
 * @time 2019-06-28 - 10:16
 * @version 1.0.0
 */

fun AppCompatActivity.go(clazz: Class<out Activity>, bundle: Bundle = Bundle(), resultCode: Int = 0x1) {
    val intent = Intent(this, clazz)
    startActivityForResult(intent, resultCode, bundle)
}