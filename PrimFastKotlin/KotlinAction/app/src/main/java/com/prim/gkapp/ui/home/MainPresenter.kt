package com.prim.gkapp.ui.home

import androidx.fragment.app.FragmentActivity
import com.prim.gkapp.data.model.UserInfo
import com.prim.gkapp.mvp.impl.BasePresenter

/**
 * @desc
 * @author prim
 * @time 2019-06-02 - 00:01
 * @version 1.0.0
 */
class MainPresenter : BasePresenter<MainActivity>() {
    override fun initContext(context: FragmentActivity?) {

    }

    fun logout() {
        UserInfo.logout().subscribe({
            view.toLogin()
        }, {

        })
    }


}