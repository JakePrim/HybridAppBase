package com.prim.hybrid.entry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author prim
 * @version 1.0.0
 * @desc 存储配置信息
 * @time 2/20/21 - 2:40 PM
 * @contact https://jakeprim.cn
 * @name PHybrid
 */
public class Configuration implements Serializable {
    /**
     * 当前的环境id
     */
    private String environmentId;

    private List<Environment> environments = new ArrayList<>();

    private List<Setting> settings = new ArrayList<>();

    private Map<String, WebEntry> webviews = new LinkedHashMap<>();

    public String getEnvironmentId() {
        return environmentId;
    }

    public void setEnvironmentId(String environmentId) {
        this.environmentId = environmentId;
    }

    public List<Environment> getEnvironments() {
        return environments;
    }

    public void setEnvironments(List<Environment> environments) {
        this.environments = environments;
    }

    public List<Setting> getSettings() {
        return settings;
    }

    public void setSettings(List<Setting> settings) {
        this.settings = settings;
    }

    public Map<String, WebEntry> getWebviews() {
        return webviews;
    }

    public void setWebviews(Map<String, WebEntry> webviews) {
        this.webviews = webviews;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "environmentId='" + environmentId + '\'' +
                ", environments=" + environments +
                ", settings=" + settings +
                ", webviews=" + webviews +
                '}';
    }
}
