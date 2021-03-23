package com.prim.hybrid.entry;

import java.io.Serializable;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2/20/21 - 2:43 PM
 * @contact https://jakeprim.cn
 * @name PHybrid
 */
public class TemplateEntry implements Serializable {
    private String key;
    private String value;
    private String version;

    public TemplateEntry(String key, String value, String version) {
        this.key = key;
        this.value = value;
        this.version = version;
    }

    public TemplateEntry(String value, String version) {
        this.value = value;
        this.version = version;
    }

    public TemplateEntry() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "TemplateEntry{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
