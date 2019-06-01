package com.prim.gkapp.ui.home

import android.os.Bundle
import com.prim.gkapp.base.BaseActivity
import com.prim.gkapp.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity

/**
 * 项目主页面
 * {@see MainPresenter}
 */
class MainActivity : BaseActivity<MainPresenter>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.prim.gkapp.R.layout.activity_main)
        btn_logout.onClick {
            presenter.logout()
        }
    }

    fun toLogin() {
        startActivity<LoginActivity>()
        finish()
    }

}
