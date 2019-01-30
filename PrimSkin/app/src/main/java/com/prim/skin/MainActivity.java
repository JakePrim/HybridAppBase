package com.prim.skin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.prim_skin.PrimSkin;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void changeSkin(View view) {
        PrimSkin.getInstance().loadSkin("file:///android_asset/test.skin");
    }

    public void resumeSkin(View view) {
        PrimSkin.getInstance().clearSkin();
    }
}
