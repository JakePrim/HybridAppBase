package com.prim.gkapp.ui.login

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.LifecycleOwner
import com.gyf.immersionbar.ImmersionBar
import com.prim.gkapp.R
import com.prim.gkapp.ui.home.MainActivity
import com.prim.lib_base.base.BaseActivity
import com.prim.lib_base.utils.otherwise
import com.prim.lib_base.utils.yes
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * login activity
 * @author JakePrim
 *
 */
class LoginActivity : BaseActivity<LoginPresenter>(), LifecycleOwner {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ImmersionBar.with(this).statusBarDarkFont(true).init()
        setContentView(R.layout.activity_login)
        username.doOnTextChanged { _, _, _, _ ->
            login.isEnabled = username.text.isNotEmpty() && password.text.isNotEmpty()
        }

        password.doOnTextChanged { _, _, _, _ ->
            login.isEnabled = username.text.isNotEmpty() && password.text.isNotEmpty()
        }

        login.onClick {
            presenter.checkUserName(username.text.toString()).yes {
                presenter.checkPassword(password.text.toString()).yes {
                    presenter.login(username.text.toString(), password.text.toString())
                }.otherwise {
                    toast("密码输入不合法")
                }
            }.otherwise {
                toast("账号输入不合法")
            }
        }
    }

    fun onStartLogin() {
        loading.visibility = VISIBLE
    }

    fun onErrorLogin(e: Throwable) {
        toast("登录失败:${e.message}")
        loading.visibility = GONE
    }

    fun onSuccessLogin() {
        toast("登录成功")
        loading.visibility = GONE
    }

    fun onIntent() {
        startActivity<MainActivity>()
        finish()
    }

    fun onDataInit(name: String, pass: String) {
        username.setText(name)
        password.setText(pass)
    }
}
