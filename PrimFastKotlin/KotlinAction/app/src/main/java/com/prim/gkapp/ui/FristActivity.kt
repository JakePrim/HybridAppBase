package com.prim.gkapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.prim.gkapp.R
import com.prim.gkapp.data.model.UserData

class FristActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frist)
        UserData.firstIn = true
    }
}
