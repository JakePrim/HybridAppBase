package com.prim.gkapp.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.prim.gkapp.mvp.IMvpView
import com.prim.gkapp.mvp.IPresenter
import com.prim.gkapp.mvp.impl.BasePresenter
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @desc Fragment的基类
 * @author prim
 * @time 2019-05-28 - 06:11
 * @version 1.0.0
 */
abstract class BaseFragment<out P : BasePresenter<BaseFragment<P>>> : IMvpView<P>, Fragment() {
    override val presenter: P

    //初始化 Presenter
    init {
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
            var thisClass: Class<*> = this@BaseFragment.javaClass
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
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}