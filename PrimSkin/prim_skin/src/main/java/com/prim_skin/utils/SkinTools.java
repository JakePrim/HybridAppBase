package com.prim_skin.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.TypedArray;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/12/13 - 12:12 AM
 */
public class SkinTools {
    public static int[] getResId(Context context, int... attrIds) {
        int[] resIds = new int[attrIds.length];
        TypedArray typedArray = context.obtainStyledAttributes(attrIds);
        for (int i = 0; i < typedArray.length(); i++) {
            resIds[i] = typedArray.getResourceId(i, 0);
        }
        typedArray.recycle();
        return resIds;
    }

    public static AssetManager addAssetPath(String skinPath) {
        AssetManager assetManager = null;
        try {
            assetManager = AssetManager.class.newInstance();
            Method addAssetPathMethod = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPathMethod.setAccessible(true);
            //添加资源进入资源管理器
            addAssetPathMethod.invoke(assetManager, skinPath);
            return assetManager;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
