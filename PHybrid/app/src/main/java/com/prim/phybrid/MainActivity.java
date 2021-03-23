package com.prim.phybrid;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.prim.hybrid.PHybrid;
import com.prim.hybrid.entry.DownloadEntry;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onArticle(View view) {
        Intent intent = new Intent(this, ArticleActivity.class);
        startActivity(intent);//查看当前模板
    }

    public void onItem(View view) {
    }

    public void onChannel(View view) {
        Intent intent = new Intent(this, TabActivity.class);
        startActivity(intent);//查看当前模板
    }

    public void onArticle2(View view) {
        DownloadEntry downloadEntry = new DownloadEntry();
        downloadEntry.setDownloadUrl("article");
        downloadEntry.setKey("article");
        downloadEntry.setVersion("2.0");
        PHybrid.download(downloadEntry);//升级模板，升级成功后的回调
    }

    public void onArticle3(View view) {
        boolean change = PHybrid.changeTemplateVersion("article", "1.0");//切换模板
        if (change) {
            Toast.makeText(this, "切换版本为：1.0", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "切换版本失败", Toast.LENGTH_LONG).show();
        }
    }

    public void onArticle4(View view) {
        boolean change = PHybrid.changeTemplateVersion("article", "2.0");//切换模板
        if (change) {
            Toast.makeText(this, "切换版本为：2.0", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "切换版本失败", Toast.LENGTH_LONG).show();
        }
    }

    public void onActive(View view) {
        DownloadEntry downloadEntry = new DownloadEntry();
        downloadEntry.setDownloadUrl("active");
        downloadEntry.setKey("active");
        downloadEntry.setVersion("1.0");
        PHybrid.download(downloadEntry);//升级模板，升级成功后的回调
    }

    public void onLoadActive(View view) {
        Intent intent = new Intent(this, ActiveActivity.class);
        startActivity(intent);
    }

    public void onChannelV(View view) {
        DownloadEntry downloadEntry = new DownloadEntry();
        downloadEntry.setDownloadUrl("channel");
        downloadEntry.setKey("channel");
        downloadEntry.setVersion("2.0");
        PHybrid.download(downloadEntry);//升级模板，升级成功后的回调
    }
}