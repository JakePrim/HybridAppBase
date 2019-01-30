package com.prim_skin.bean;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/12/13 - 1:32 PM
 */
public class SkinBean {
    private String attributeName;

    private int resId;

    public SkinBean(String attributeName, int resId) {
        this.attributeName = attributeName;
        this.resId = resId;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
