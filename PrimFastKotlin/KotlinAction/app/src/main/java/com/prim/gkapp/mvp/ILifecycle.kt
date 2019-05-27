package com.prim.gkapp.mvp

import android.content.res.Configuration
import android.os.Bundle
import androidx.annotation.NonNull

/**
 * @desc 生命周期的接口
 * @author prim
 * @time 2019-05-27 - 23:12
 * @version 1.0.0
 */
interface ILifecycle {
    fun onCreate(savedInstanceState: Bundle?)
    fun onSaveInstanceState(@NonNull outState: Bundle)
    fun onConfigurationChanged(newConfig: Configuration?)
    fun onStart()
    fun onResume()
    fun onPause()
    fun onStop()
    fun onDestroy()
}