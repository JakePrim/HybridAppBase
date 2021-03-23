package com.prim.hybrid.data;

import java.io.Serializable;

/**
 * @author prim
 * @version 1.0.0
 * @desc 模板版本的历史记录
 * @time 2/20/21 - 3:39 PM
 * @contact https://jakeprim.cn
 * @name PHybrid
 */
public class TemplateVersion implements Serializable {
    private String version;
    private String path;

    public TemplateVersion(String version, String path) {
        this.version = version;
        this.path = path;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "TemplateVersion{" +
                "version='" + version + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
