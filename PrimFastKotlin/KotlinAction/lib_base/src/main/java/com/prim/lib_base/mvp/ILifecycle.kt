package com.prim.lib_base.mvp

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
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
    fun onViewCreated(view: View, savedInstanceState: Bundle?)
    fun onViewStateRestored(savedInstanceState: Bundle?)
    fun onConfigurationChanged(newConfig: Configuration)
    fun onStart()
    fun onResume()
    fun onRestart()
    fun onPause()
    fun onStop()
    fun onDestroy()
}