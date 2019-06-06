package com.prim.lib_base.utils

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

/**
 * @desc
 * @author prim
 * @time 2019-06-03 - 09:44
 * @version 1.0.0
 */
fun AppCompatActivity.showFragment(contentId: Int, clazz: Class<out Fragment>, args: Bundle) {
    supportFragmentManager.beginTransaction().replace(contentId, clazz.newInstance().apply {
        arguments = args
    }, clazz.name)
        .commitAllowingStateLoss()
}

fun AppCompatActivity.showFragment(contentId: Int, clazz: Class<out Fragment>, vararg args: Pair<String, String>) {
    supportFragmentManager.beginTransaction().replace(contentId, clazz.newInstance().apply {
        arguments = Bundle().apply {
            args.forEach {
                putString(it.first, it.second)
            }
        }
    }, clazz.name)
        .commitAllowingStateLoss()
}

