package com.prim.fast;

import android.app.Application;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.prim.config.PrimFast;
import com.prim.icons.ExMoudle;
//import com.joanzapata.iconify.fonts.FontAwesomeModule;
//import com.prim.core.common.app.PrimFast;
//import com.prim.icons.ExMoudle;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019/1/4 - 12:15 PM
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //网络框架初始化
//        PrimHttp.init(this)
//                .withHost("https://api.weibo.com/")
//                .readTimeout(1000)
//                .connectionTimeout(5000)
//                .build();
        PrimFast.init(this)
                .withIcons(new ExMoudle())//自定义字体图标库
                .withIcons(new FontAwesomeModule())
                .withApiHost("https://music.aityp.com")
                .configure();

    }
}
