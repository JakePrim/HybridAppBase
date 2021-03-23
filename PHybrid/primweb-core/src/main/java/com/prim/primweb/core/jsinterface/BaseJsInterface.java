package com.prim.primweb.core.jsinterface;

import android.os.Build;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.prim.primweb.core.PrimWeb;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/15 0015
 * 描    述：注入js代理基类
 * 修订历史：
 * ================================================
 */
public abstract class BaseJsInterface implements IJsInterface {

    private static final String TAG = "BaseJsInterface";
    private PrimWeb.ModeType modeType;

    public BaseJsInterface(PrimWeb.ModeType modeType) {
        this.modeType = modeType;
    }

    @Override
    public boolean checkJsInterface(Object o) {
        boolean tag = false;
        Class<?> aClass = o.getClass();
        Method[] methods = aClass.getDeclaredMethods();
        for (Method method : methods) {
            method.setAccessible(true);
            Annotation[] annotations = method.getAnnotations();
            if (annotations.length == 0) {
                tag = false;
            }
            for (Annotation annotation : annotations) {
                if (annotation instanceof JavascriptInterface) {
                    tag = true;
                    break;
                } else {
                    tag = false;
                }
            }
            if (modeType == PrimWeb.ModeType.Strict) {
                if (!tag) {//只要有一个方法没有加入 JavascriptInterface 返回false
                    break;
                }
            } else {
                if (tag) {
                    break;
                }
            }
        }
        return tag;
    }
}
