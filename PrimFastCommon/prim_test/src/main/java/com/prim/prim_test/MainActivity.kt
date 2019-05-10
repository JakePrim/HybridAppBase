package com.prim.prim_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.prim.prim_test.http.TestHttpActivity
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun testPrimHttp(view: View) {
//        var intent = Intent(this,TestHttpActivity::class.java)
        //anko DSL
        startActivity<TestHttpActivity>()
    }
}
