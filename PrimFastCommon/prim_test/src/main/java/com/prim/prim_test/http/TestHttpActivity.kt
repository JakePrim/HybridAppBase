package com.prim.prim_test.http

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.prim.prim_test.R
import kotlinx.android.synthetic.main.activity_test_http.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity

/**
 * 测试 prim_http 网络请求库
 */
class TestHttpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_http)
        tv_prim_http_base.onClick {
            startActivity<BaseHttpActivity>()
        }
    }
}
