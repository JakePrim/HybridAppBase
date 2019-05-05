package com.prim.skin;

import android.app.Application;

import com.prim_skin.PrimSkin;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/12/13 - 4:58 PM
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PrimSkin.init(this);
    }
}
