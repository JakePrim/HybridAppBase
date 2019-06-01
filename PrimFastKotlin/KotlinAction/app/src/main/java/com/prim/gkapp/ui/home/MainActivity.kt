package com.prim.gkapp.ui.home

import android.os.Bundle
import com.prim.gkapp.base.BaseActivity
import com.prim.gkapp.ui.login.LoginActivity
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * 项目主页面
 * {@see MainPresenter}
 */
class MainActivity : BaseActivity<MainPresenter>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.prim.gkapp.R.layout.activity_main)
    }

    fun toLogin() {
        toast("已退出登录")
        startActivity<LoginActivity>()
        finish()
    }

}
