package com.prim.hybrid.config;

import android.content.Context;

import com.prim.hybrid.entry.Setting;
import com.prim.hybrid.entry.WebEntry;
import com.prim.hybrid.template.DownloadListener;
import com.prim.primweb.core.websetting.DefaultWebSetting;

import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc 加载setting的配置
 * @time 2/23/21 - 4:32 PM
 * @contact https://jakeprim.cn
 * @name PHybrid
 */
public class LoadSettingConfig {
    private List<Setting> settings;

    private DownloadListener downloadListener;

    public LoadSettingConfig(List<Setting> settings) {
        this.settings = settings;
    }

    public LoadSettingConfig parseSetting() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        for (Setting setting : settings) {
            String name = setting.getName();
            String value = setting.getValue();
            if ("downloadTemplate".equals(name)) {
                Class<?> downloadListenerClass = Class.forName(value);
                if (DownloadListener.class.isAssignableFrom(downloadListenerClass)) {
                    downloadListener = (DownloadListener) downloadListenerClass.newInstance();
                }
            }
        }
        return this;
    }

    public DownloadListener getDownloadListener() {
        return downloadListener;
    }

    public void setDownloadListener(DownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }
}
