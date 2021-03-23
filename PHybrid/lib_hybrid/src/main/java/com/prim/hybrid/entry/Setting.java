package com.prim.hybrid.entry;

import java.io.Serializable;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2/20/21 - 2:46 PM
 * @contact https://jakeprim.cn
 * @name PHybrid
 */
public class Setting implements Serializable {
    private String name;
    private String value;

    public Setting(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Setting{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
