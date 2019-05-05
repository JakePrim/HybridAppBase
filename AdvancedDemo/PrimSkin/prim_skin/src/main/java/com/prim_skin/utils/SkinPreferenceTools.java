package com.prim_skin.utils;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * @author prim
 * @version 1.0.0
 * @desc 存储皮肤包路径
 * @time 2018/12/13 - 1:43 PM
 */
public class SkinPreferenceTools {
    private static SkinPreferenceTools ourInstance;

    private static final String SKIN_PREFERENCE_NAME = "com.prim.skin";

    private static final String SKIN_PATH = "_com.prim.skin.path";
    private SharedPreferences sharedPreferences;

    public static void init(Application application) {
        if (null == ourInstance) {
            synchronized (SkinPreferenceTools.class) {
                if (null == ourInstance) {
                    ourInstance = new SkinPreferenceTools(application);
                }
            }
        }

    }

    private Application mCxtApplication;

    private SkinPreferenceTools(Application application) {
        this.mCxtApplication = application;
        sharedPreferences = mCxtApplication.getSharedPreferences(SKIN_PREFERENCE_NAME, 0);
    }

    public static SkinPreferenceTools getInstance() {
        return ourInstance;
    }


    public void saveSkin(String path) {
        sharedPreferences.edit().putString(SKIN_PATH, path).apply();
    }

    public String getSkinPath() {
        return sharedPreferences.getString(SKIN_PATH, "-1");
    }

    public void clearSkin() {
        sharedPreferences.edit().remove(SKIN_PATH).apply();
    }
}
