package com.prim.gkapp.ui.login

import android.content.Context
import com.prim.gkapp.BuildConfig
import com.prim.gkapp.data.model.UserData
import com.prim.lib_base.mvp.impl.BasePresenter

/**
 * @desc
 * @author prim
 * @time 2019-05-29 - 07:32
 * @version 1.0.0
 */
class LoginPresenter : BasePresenter<LoginActivity>() {
    override fun initContext(context: Context?) {

    }

    fun login(userName: String, password: String) {
        UserData.username = userName
        UserData.password = password
        UserData.login().subscribe({
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
        if (BuildConfig.DEBUG) {
            view.onDataInit(BuildConfig.testUserName, BuildConfig.testPassword)
        } else {
            view.onDataInit(UserData.username, UserData.password)
        }
    }
}