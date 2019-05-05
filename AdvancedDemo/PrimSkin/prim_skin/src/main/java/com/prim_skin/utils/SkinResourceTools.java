package com.prim_skin.utils;

import android.app.Application;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

/**
 * @author prim
 * @version 1.0.0
 * @desc 资源查找的一个工具类
 * @time 2018/12/13 - 2:03 PM
 */
public class SkinResourceTools {
    private static SkinResourceTools ourInstance;

    private Application mCxtApplication;

    //皮肤包的资源
    private Resources mSkinResource;

    //app资源
    private Resources mAppResource;

    private boolean isDefaultSkin;

    private String mSkinPkgName;

    public SkinResourceTools(Application application) {
        this.mCxtApplication = application;
        mAppResource = application.getResources();
    }

    public static void init(Application application) {
        if (null == ourInstance) {
            synchronized (SkinResourceTools.class) {
                if (null == ourInstance) {
                    ourInstance = new SkinResourceTools(application);
                }
            }
        }
    }

    public static SkinResourceTools getInstance() {
        return ourInstance;
    }

    public ColorStateList getColorStateList(int resId) {
        if (isDefaultSkin) {
            return mAppResource.getColorStateList(resId);
        }
        int skinId = getIdntifier(resId);
        if (skinId == 0) {
            return mAppResource.getColorStateList(resId);
        }
        return mSkinResource.getColorStateList(skinId);
    }

    public void applySkin(Resources skinResource, String skinPkgName) {
        this.mSkinResource = skinResource;
        this.mSkinPkgName = skinPkgName;
        //是否使用默认皮肤包
        this.isDefaultSkin = skinPkgName == null || TextUtils.isEmpty(skinPkgName);
    }

    public void reset() {
        mSkinResource = null;
        mSkinPkgName = "";
        isDefaultSkin = true;
    }

    public int getIdntifier(int resId) {
        if (isDefaultSkin) {
            return resId;
        }
        //在皮肤包中不一定就是当前程序app 的ID
        String resourceName = mAppResource.getResourceName(resId);
        String resourceTypeName = mAppResource.getResourceTypeName(resId);
        return mSkinResource.getIdentifier(resourceName, resourceTypeName, mSkinPkgName);
    }

    public int getColor(int resId) {
        if (isDefaultSkin) {//默认app的资源属性
            return mAppResource.getColor(resId);
        }
        //获取皮肤包的资源ID
        int skinId = getIdntifier(resId);
        if (skinId == 0) {
            return mAppResource.getColor(resId);
        }
        return mSkinResource.getColor(skinId);
    }

    public Drawable getDrawable(int resId) {
        if (isDefaultSkin) {
            return mAppResource.getDrawable(resId);
        }
        int skinId = getIdntifier(resId);
        if (skinId == 0) {
            return mAppResource.getDrawable(resId);
        }
        return mSkinResource.getDrawable(skinId);
    }

    public Object getBackground(int resId) {
        String resourceTypeName = mAppResource.getResourceTypeName(resId);
        if (resourceTypeName.equals("color")) {
            return getColor(resId);
        } else {
            return getDrawable(resId);
        }
    }

    public String getString(int resId) {
        try {
            if (isDefaultSkin) {
                return mAppResource.getString(resId);
            }
            int skinId = getIdntifier(resId);
            if (skinId == 0) {
                return mAppResource.getString(resId);
            }
            return mSkinResource.getString(skinId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Typeface getTypeface(int resId) {
        try {
            String skinTypefacePath = getString(resId);
            if (TextUtils.isEmpty(skinTypefacePath)) {
                return Typeface.DEFAULT;
            }
            if (isDefaultSkin) {
                return Typeface.createFromAsset(mAppResource.getAssets(), skinTypefacePath);
            }
            return Typeface.createFromAsset(mSkinResource.getAssets(), skinTypefacePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Typeface.DEFAULT;
    }
}
