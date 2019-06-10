package com.prim.gkapp.ui

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import com.prim.gkapp.R
import com.prim.gkapp.data.model.UserData
import com.prim.gkapp.ui.home.MainActivity
import com.prim.gkapp.ui.login.LoginActivity
import com.prim.lib_base.utils.doViewAvailable
import com.prim.lib_base.utils.otherwise
import com.prim.lib_base.utils.yes
import kotlinx.android.synthetic.main.activity_welcome.*
import org.jetbrains.anko.startActivity


class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        tv_app_name.doViewAvailable {
            val anim = ObjectAnimator.ofFloat(
                tv_app_name,
                "alpha", 0.1f, 1f, 0.1f
            )
            anim.duration = 3000
            anim.doOnEnd {
                doWelcome()
            }
            anim.start()
        }
    }

    private fun doWelcome() {
        UserData.firstIn.yes {
            //true 表示已经进入过了
            UserData.isLogin().yes {
                //已经登录 进入MainActivity
                startActivity<MainActivity>()
            }.otherwise {
                //没有登录 进入登录Activity
                startActivity<LoginActivity>()
            }
        }.otherwise {
            //false 表示还没有进入过
            startActivity<FristActivity>()
        }
        finish()
    }
}
