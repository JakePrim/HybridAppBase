package com.prim.phybrid;

import android.app.Application;

import com.prim.hybrid.PHybrid;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2/22/21 - 3:13 PM
 * @contact https://jakeprim.cn
 * @name PHybrid
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            PHybrid.init(this, "hybrid-config.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
