package com.prim.gkapp.mvp

/**
 * @desc mvp框架接口
 * @author prim
 * @time 2019-05-27 - 23:04
 * @version 1.0.0
 */

//out 泛型的协变
interface IPresenter<out View : IMvpView<IPresenter<View>>> : ILifecycle {
    val view: View
}

interface IMvpView<out Presenter : IPresenter<IMvpView<Presenter>>> : ILifecycle {
    val presenter: Presenter
}
