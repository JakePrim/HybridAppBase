package com.prim.gkapp.ui.home

import android.content.Context
import com.prim.gkapp.data.model.UserInfo
import com.prim.gkapp.ext.toastKx
import com.prim.gkapp.mvp.impl.BasePresenter

/**
 * @desc
 * @author prim
 * @time 2019-06-02 - 00:01
 * @version 1.0.0
 */
class MainPresenter : BasePresenter<MainActivity>() {
    var context: Context? = null

    override fun initContext(context: Context?) {
        this.context = context
    }

    fun logout() {
        UserInfo.logout().subscribe({
            context?.toastKx("已退出登录")
//            view.toLogin()
        }, {
            context?.toastKx("退出失败：${it.message}")
        })
    }


}