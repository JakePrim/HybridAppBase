package com.prim.primweb.core.config;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.ArrayMap;
import android.util.SparseArray;

import com.prim.primweb.core.PrimWeb;
import com.prim.primweb.core.utils.PWLog;
import com.prim.primweb.core.webview.webpool.WebViewPool;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;

import java.util.HashMap;

import static com.prim.primweb.core.config.ConfigKey.API_HOST;
import static com.prim.primweb.core.config.ConfigKey.APPLICATION_CONTEXT;
import static com.prim.primweb.core.config.ConfigKey.CONFIG_READY;
import static com.prim.primweb.core.config.ConfigKey.WEB_POOL;
import static com.prim.primweb.core.config.ConfigKey.WEB_X5CORE;

/**
 * @sufulu 初始化配置
 */
public class Configurator {

    private static final SparseArray CONFIGS = new SparseArray();

    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    private Configurator() {
        CONFIGS.put(CONFIG_READY, false);
    }

    public final SparseArray getConfigurator() {
        return CONFIGS;
    }

    /**
     * 设置Web加载的base URL，或者本地的模板URL
     *
     * @param host URL
     * @return Configurator
     */
    @NonNull
    public final Configurator withWebHost(@NonNull String host) {
        CONFIGS.put(API_HOST, host);
        return this;
    }

    /**
     * 是否开启WebPool 的复用池，从复用池中获取webView
     *
     * @param webPool false 关闭复用池，true 开启复用池
     * @return Configurator
     */
    public final Configurator enableWebPool(boolean webPool) {
        CONFIGS.put(WEB_POOL, webPool);
        return this;
    }

    /**
     * 设置是否为开发模式
     *
     * @param isDebug
     * @return
     */
    public final Configurator setDebugLog(boolean isDebug) {
        PWLog.LOG = isDebug;
        return this;
    }

    public final void configure() {
        configure(null);
    }

    public final void configure(final OnX5CoreCallback callback) {
        CONFIGS.put(CONFIG_READY, true);//配置参数完成后 判断x5是否初始化完毕
        if (getConfiguration(WEB_X5CORE)) {
            Context ctx = getConfiguration(APPLICATION_CONTEXT);
            if (!QbSdk.isTbsCoreInited()) {//x5没有初始化
                PWLog.e("WebViewPool preInit 预加载中......");
                QbSdk.setDownloadWithoutWifi(true);
                //https://x5.tencent.com/tbs/technical.html#/detail/sdk/1/edb47a0f-6923-4bd4-af1e-83a07fb1c6e9
                //TBS内核首次使用时加载卡顿ANR如何解决？
                HashMap map = new HashMap();
                map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
                map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, true);
                QbSdk.initTbsSettings(map);
                // X5浏览器实列化
                QbSdk.initX5Environment(ctx.getApplicationContext(), new QbSdk.PreInitCallback() {
                    @Override
                    public void onCoreInitFinished() {
                        if (callback != null) {
                            callback.onCoreInitFinished();
                        }
                    }

                    @Override
                    public void onViewInitFinished(boolean b) {
                        PrimWeb.x5CoreInited = b;//记录x5是否初始化成功
                        if (callback != null) {
                            callback.onInitFinished(b);
                        }
                        PWLog.e("x5Core preInit onViewInitFinished：" + b);
                    }
                });
            } else {//x5内核已经初始化完毕
                PWLog.e("x5Core isTbsCoreInited = true onViewInitFinished：" + PrimWeb.x5CoreInited);
                if (callback != null) {
                    callback.onInitFinished(PrimWeb.x5CoreInited);
                }
            }
        } else {
            if (callback != null) {
                callback.onInitFinished(true);
            }
        }
    }


    public interface OnX5CoreCallback {
        void onCoreInitFinished();

        void onInitFinished(boolean b);
    }

//    private void initWebPool(Context ctx) {
//        boolean enableWebPool = getConfiguration(ConfigKey.WEB_POOL);
//        if (enableWebPool) {
//            WebViewPool.getInstance().initPool(ctx);
//        }
//    }

    private void checkConfiguration() {
        final boolean isReady = (boolean) CONFIGS.get(CONFIG_READY);
        if (!isReady) {
            throw new RuntimeException("Configuration is not ready,call configure");
        }
    }

    @SuppressWarnings("unchecked")
    public final <T> T getConfiguration(@ConfigAnnotation int key) {
        checkConfiguration();
        final Object value = CONFIGS.get(key);
        if (value == null) {
            throw new NullPointerException(key + " IS NULL");
        }
        return (T) CONFIGS.get(key);
    }
}
