package com.prim.lib_base.base

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.prim.lib_base.mvp.IMvpView
import com.prim.lib_base.mvp.IPresenter
import com.prim.lib_base.mvp.impl.BasePresenter
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @desc
 * @author prim
 * @time 2019-05-28 - 07:10
 * @version 1.0.0
 */
abstract class BaseActivity<out P : BasePresenter<BaseActivity<P>>> : IMvpView<P>, AppCompatActivity() {

    final override val presenter: P

    init {
        //自动实例化presenter
        presenter = createPresenterKt()
        presenter.view = this
    }

    /**
     * 创建presenter 通过java的反射
     * 这样有一个坏处 不能去写其他的构造方法了，好处就是自动创建Presenter的实例
     */
    fun createPresenterKt(): P {
        //kotlin 1.3 之前的版本 buildSequence
        //sequence
        sequence<Type> {
            var thisClass: Class<*> = this@BaseActivity.javaClass
            while (true) {
                yield(thisClass.genericSuperclass)
                thisClass = thisClass.superclass ?: break
            }
        }.filter {
            it is ParameterizedType//过滤type 找到ParameterizedType
        }.flatMap {
            (it as ParameterizedType).actualTypeArguments.asSequence()//获取泛型的第一个
        }.first {
            it is Class<*> && IPresenter::class.java.isAssignableFrom(it)
        }.let {
            return (it as Class<P>).newInstance()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.initContext(this)
        presenter.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(@NonNull outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onRestart() {
        super.onRestart()
        presenter.onRestart()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        presenter.onConfigurationChanged(newConfig)
    }
}