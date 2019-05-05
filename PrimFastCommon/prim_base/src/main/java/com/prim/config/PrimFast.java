package com.prim.config;

import android.content.Context;

import java.util.HashMap;

/**
 * @author prim
 * @version 1.0.0
 * @desc PrimFast框架初始化
 * @time 2019/2/26 - 6:47 AM
 */
public final class PrimFast {
    /**
     * 项目框架的初始化
     *
     * @param context 上下文
     * @return Configurator
     */
    public static Configurator init(Context context) {
        getConfigures().put(ConfigType.APPLICATION_CONTEXT.name(), context.getApplicationContext());
        return Configurator.getInstance();
    }

    //获取配置集合
    public static HashMap<String, Object> getConfigures() {
        return Configurator.getInstance().getPrimConfigs();
    }

    /**
     * 获取application
     *
     * @return context
     */
    public static Context getApplication() {
        return Configurator.getInstance().getConfiguration(ConfigType.APPLICATION_CONTEXT);
    }


}
