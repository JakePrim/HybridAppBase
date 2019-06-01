package com.prim.gkapp.ui.login

import com.prim.gkapp.data.model.UserInfo
import com.prim.gkapp.mvp.impl.BasePresenter

/**
 * @desc
 * @author prim
 * @time 2019-05-29 - 07:32
 * @version 1.0.0
 */
class LoginPresenter : BasePresenter<LoginActivity>() {

    fun login(userName: String, password: String) {
        UserInfo.username = userName
        UserInfo.password = password
        val subscribe = UserInfo.login().subscribe({
            view.onSuccessLogin()
        }, {
            view.onErrorLogin(it)
        }, {
            view.onIntent()
        }, {
            view.onStartLogin()
        })
    }

    fun checkUserName(userName: String): Boolean {
        return true
    }

    fun checkPassword(password: String): Boolean {
        return true
    }

    override fun onResume() {
        super.onResume()
        view.onDataInit(UserInfo.username, UserInfo.password)
    }
}