package cn.prim.http.lib_net.config;

import android.app.Application;
import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019/1/2 - 2:32 PM
 */
public class ApplicationAttach {
    private static WeakReference<Context> mContext;

    public static void attach(Application application) {
        if (application == null) {
            throw new RuntimeException("PrimHttp need use context");
        }
        mContext = new WeakReference<>(application.getApplicationContext());
    }

    public static Context getApplicationContext() {
        if (mContext == null) {
            throw new RuntimeException("PrimHttp need use context");
        }
        return mContext.get();
    }
}
