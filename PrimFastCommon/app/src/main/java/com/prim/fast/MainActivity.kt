package com.prim.fast

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.prim.http.lib_net.PrimHttp
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sample_text.setOnClickListener {
//            var intent = Intent()
//            intent.setClass(this, TestHttpActivity::class.java)
//            startActivity(intent)
        }
    }
}
