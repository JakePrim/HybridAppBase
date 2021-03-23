package com.prim.primweb.core.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.prim.primweb.core.utils.PWLog;
import com.tencent.smtt.sdk.QbSdk;

/**
 * @author prim
 * @version 1.0.0
 * @desc X5 内核初始化 预加载处理
 * @time 2019/1/11 - 11:11 AM
 */
public class X5InitService extends IntentService {

    private static final String TAG = "X5InitService";

    public X5InitService() {
        super(TAG);
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public X5InitService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        PWLog.e("onHandleIntent");
        initX5();
    }

    private void initX5() {
        if (!QbSdk.isTbsCoreInited()) {
            PWLog.e("preInit 预加载中......");
            QbSdk.preInit(getApplicationContext(), null);// 设置X5初始化完成的回调接口
        }
        QbSdk.initX5Environment(getApplicationContext(), preInitCallback);
    }

    QbSdk.PreInitCallback preInitCallback = new QbSdk.PreInitCallback() {
        @Override
        public void onCoreInitFinished() {
            PWLog.e("onCoreInitFinished");
        }

        @Override
        public void onViewInitFinished(boolean b) {
            PWLog.e("onViewInitFinished:" + b);
        }
    };
}
