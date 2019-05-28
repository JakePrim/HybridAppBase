package com.prim.gkapp.ext

import android.util.Log
import java.io.File

/**
 * @desc file的扩展类
 * @author prim
 * @time 2019-05-28 - 22:57
 * @version 1.0.0
 */
private const val TAG = "FileExt"

fun File.ensureDir(): Boolean {
    try {
        isDirectory.no {
            isFile.yes {
                delete()
            }
            return mkdirs()
        }
    } catch (e: Exception) {
        Log.w(TAG, e.message)
    }
    return false
}