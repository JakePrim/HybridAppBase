package com.prim.phybrid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.prim.hybrid.PHybrid;
import com.prim.primweb.core.PrimWeb;

public class ActiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active);
        FrameLayout frameLayout = findViewById(R.id.frameLayout);
        PHybrid.loadTemplate(this, "active", frameLayout);
    }
}