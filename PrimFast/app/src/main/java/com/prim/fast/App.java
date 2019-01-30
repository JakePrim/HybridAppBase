package com.prim.fast;

import android.app.Application;
import cn.prim.http.lib_net.PrimHttp;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;

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
        PrimHttp.init(this)
                .withHost("https://api.weibo.com/")
                .readTimeout(1000)
                .connectionTimeout(5000)
                .build();

        WbSdk.install(this,
                new AuthInfo(this,
                        Constants.APP_KEY,
                        Constants.REDIRECT_URL,
                        Constants.SCOPE));
    }
}
