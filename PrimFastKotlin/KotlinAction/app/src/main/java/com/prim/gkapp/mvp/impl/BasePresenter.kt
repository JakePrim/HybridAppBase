package com.prim.gkapp.mvp.impl

import android.content.res.Configuration
import android.os.Bundle
import androidx.annotation.NonNull
import com.prim.gkapp.mvp.IMvpView
import com.prim.gkapp.mvp.IPresenter

/**
 * @desc Presenter 的基类
 * @author prim
 * @time 2019-05-27 - 23:16
 * @version 1.0.0
 */
abstract class BasePresenter<out V : IMvpView<BasePresenter<V>>> : IPresenter<V> {
    override lateinit var view: @UnsafeVariance V
    override fun onCreate(savedInstanceState: Bundle?){}
    override fun onSaveInstanceState(@NonNull outState: Bundle){}
    override fun onConfigurationChanged(newConfig: Configuration?){}
    override fun onStart(){}
    override fun onResume(){}
    override fun onPause(){}
    override fun onStop(){}
    override fun onDestroy(){}
}