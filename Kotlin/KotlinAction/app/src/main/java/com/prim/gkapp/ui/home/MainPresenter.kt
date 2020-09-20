package com.prim.gkapp.ui.home

import android.content.Context
import com.prim.gkapp.data.UserData
import com.prim.lib_base.utils.toastKx
import com.prim.lib_base.mvp.impl.BasePresenter

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
        UserData.logout().subscribe({
            context?.toastKx("已退出登录")
//            view.toLogin()
        }, {
            context?.toastKx("退出失败：${it.message}")
        })
    }


}