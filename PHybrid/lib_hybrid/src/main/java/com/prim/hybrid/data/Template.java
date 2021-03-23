package com.prim.hybrid.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author prim
 * @version 1.0.0
 * @desc 模板相关信息
 * @time 2/20/21 - 3:38 PM
 * @contact https://jakeprim.cn
 * @name PHybrid
 */
public class Template implements Serializable {

    private String key;
    /**
     * 当前的版本
     */
    private String currentVersion = "1.0";

    /**
     * 当前版本模板路径
     */
    private String currentPath;

    /**
     * 历史版本
     */
    private Map<String,String> templateVersions = new LinkedHashMap<>();

    public Template() {
    }

    public Template(String currentVersion, String currentPath) {
        this.currentVersion = currentVersion;
        this.currentPath = currentPath;
    }

    public Template(String key, String currentVersion, String currentPath) {
        this.key = key;
        this.currentVersion = currentVersion;
        this.currentPath = currentPath;
    }

    /**
     * 有新版本了，之前的版本添加到历史数据中去
     *
     * @param version
     * @param path
     */
    public void addTemplateVersion(String version, String path) {
        if (templateVersions != null) {
            templateVersions.put(version,path);
        }
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }

    public String getCurrentPath() {
        return currentPath;
    }

    public void setCurrentPath(String currentPath) {
        this.currentPath = currentPath;
    }

    public Map<String,String> getTemplateVersions() {
        return templateVersions;
    }

    public void setTemplateVersions(Map<String,String> templateVersions) {
        this.templateVersions = templateVersions;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Template{" +
                "currentVersion='" + currentVersion + '\'' +
                ", currentPath='" + currentPath + '\'' +
                ", templateVersions=" + templateVersions +
                '}';
    }
}
