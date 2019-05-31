package com.prim.gkapp.ui.login

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import com.prim.gkapp.R
import com.prim.gkapp.base.BaseActivity

class LoginActivity : BaseActivity<LoginPresenter>(), LifecycleOwner {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}
