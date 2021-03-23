package com.prim.phybrid;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.FrameLayout;

import com.prim.hybrid.PHybrid;
import com.prim.hybrid.webview.IWebView;

public class ArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        FrameLayout webContainer = findViewById(R.id.webContainer);
        PHybrid.loadTemplate(this, "article", webContainer);
    }
}