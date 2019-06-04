package com.prim.gkapp.ui

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import com.prim.gkapp.R
import com.prim.gkapp.data.model.UserData
import com.prim.gkapp.ext.otherwise
import com.prim.gkapp.ext.yes
import com.prim.gkapp.ui.home.MainActivity
import com.prim.gkapp.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_welcome.*
import org.jetbrains.anko.startActivity


class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        val anim = ObjectAnimator.ofFloat(
            tv_app_name,
            "alpha", 0.1f, 1f
        )
        anim.duration = 3000
        anim.doOnEnd {
            doWelcome()
        }
        anim.start()
    }


    private fun doWelcome() {
        Log.e("welcome", "doWelcome")
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
