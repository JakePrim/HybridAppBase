package com.prim_skin.skin;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.view.LayoutInflater;

import com.prim_skin.PrimSkin;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author prim
 * @version 1.0.0
 * @desc Activity的生命周期
 * @time 2018/12/3 - 10:46 PM
 */
public class SkinActivityLifecycle implements Application.ActivityLifecycleCallbacks {

    private Map<Activity, SkinLayoutFactory> mSkinLayoutFactoryMap = new HashMap<>();

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        //拿到Activity的布局加载器 更新视图布局
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        //mFactorySet 如果为true 会报出异常，在每次setFactory 的时候置为false
        try {
            //利用反射设置mFactorySet 为false
            Field mFactorySet = LayoutInflater.class.getDeclaredField("mFactorySet");
            mFactorySet.setAccessible(true);
            mFactorySet.setBoolean(layoutInflater, false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //HOOK Native
        SkinLayoutFactory skinLayoutFactory = new SkinLayoutFactory();
        LayoutInflaterCompat.setFactory2(layoutInflater, skinLayoutFactory);
        PrimSkin.getInstance().addObserver(skinLayoutFactory);
        mSkinLayoutFactoryMap.put(activity, skinLayoutFactory);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        SkinLayoutFactory skinLayoutFactory = mSkinLayoutFactoryMap.remove(activity);
        PrimSkin.getInstance().deleteObserver(skinLayoutFactory);
    }
}
