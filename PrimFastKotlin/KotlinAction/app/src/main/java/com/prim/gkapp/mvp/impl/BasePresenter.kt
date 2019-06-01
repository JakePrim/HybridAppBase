package com.prim.gkapp.mvp.impl

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
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
    override fun onCreate(savedInstanceState: Bundle?) = Unit
    override fun onSaveInstanceState(@NonNull outState: Bundle) = Unit
    override fun onConfigurationChanged(newConfig: Configuration) = Unit
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = Unit
    override fun onViewStateRestored(savedInstanceState: Bundle?) = Unit
    override fun onRestart() = Unit
    override fun onStart() = Unit
    override fun onResume() = Unit
    override fun onPause() = Unit
    override fun onStop() = Unit
    override fun onDestroy() = Unit
}