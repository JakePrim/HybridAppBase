package com.prim.config;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author prim
 * @version 1.0.0
 * @desc 全局配置项
 * @time 2019/2/26 - 6:48 AM
 */
public class Configurator {
    //配置集合
    private static final HashMap<String, Object> PRIM_CONFIGS = new HashMap<>();

    //字体图标库
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();

    private Configurator() {
        //初始化配置
        PRIM_CONFIGS.put(ConfigType.CONFIG_READ.name(), false);
    }

    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    //获取配置集合
    public final HashMap<String, Object> getPrimConfigs() {
        return PRIM_CONFIGS;
    }

    /**
     * 设置字体和图标库
     *
     * @param descriptor
     * @return
     */
    public final Configurator withIcons(IconFontDescriptor descriptor) {
        ICONS.add(descriptor);
        return this;
    }

    //final 尽量保证不可变性

    /**
     * 配置API host
     *
     * @param apiHost
     * @return
     */
    public final Configurator withApiHost(String apiHost) {
        PRIM_CONFIGS.put(ConfigType.API_HOST.name(), apiHost);
        return this;
    }

    /**
     * 调用此方法表示配置完成
     */
    public final void configure() {
        initIcons();
        PRIM_CONFIGS.put(ConfigType.CONFIG_READ.name(), true);
    }

    private void initIcons() {
        if (ICONS.size() > 0) {
            Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for (int i = 1; i < ICONS.size(); i++) {
                initializer.with(ICONS.get(i));
            }
        }
    }

    /**
     * 检查是否配置完毕
     */
    private void checkConfiguration() {
        final boolean isReady = (boolean) PRIM_CONFIGS.get(ConfigType.CONFIG_READ.name());
        if (!isReady) {
            throw new RuntimeException("Configuration is not ready,call configure()");
        }
    }

    /**
     * 获取某一个配置项
     *
     * @param <T type>
     * @return <T>
     */
    @SuppressWarnings("unchecked")
    public final <T> T getConfiguration(Enum<ConfigType> type) {
        checkConfiguration();
        return (T) PRIM_CONFIGS.get(type.name());

    }
}
