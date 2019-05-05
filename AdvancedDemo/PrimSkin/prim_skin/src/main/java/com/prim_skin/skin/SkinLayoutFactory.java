package com.prim_skin.skin;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/12/12 - 11:16 PM
 */
public class SkinLayoutFactory implements LayoutInflater.Factory2,Observer {

    private static final String[] mClassAndroidViewList = {
            "android.widget.",
            "android.view.",
            "android.webkit."
    };

    static final Class<?>[] mConstructorSignature = new Class[]{
            Context.class, AttributeSet.class};

    private static final HashMap<String, Constructor<? extends View>> sConstructorMap =
            new HashMap<String, Constructor<? extends View>>();

    //属性处理类
    private SkinAttribute skinAttribute;

    public SkinLayoutFactory() {
        skinAttribute = new SkinAttribute();
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        //得到了所有的view
        View view = createViewFromTag(name, context, attrs);
        if (view == null) {//自定义控件
            view = createView(name, context, attrs);
        }
        //筛选符合属性的View
        skinAttribute.load(view,attrs);
        return view;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = createViewFromTag(name, context, attrs);
        if (view == null) {//自定义控件
            view = createView(name, context, attrs);
        }
        return view;
    }

    private View createViewFromTag(String name, Context context, AttributeSet attrs) {
        //包含了自定义控件
        if (-1 == name.indexOf('.')) {//自定义View
            return null;
        }
        View view = null;
        //系统控件
        for (int i = 0; i < mClassAndroidViewList.length; i++) {
            view = createView(mClassAndroidViewList[i] + name, context, attrs);
            //如果view创建则停止循环，返回
            if (view != null) {
                break;
            }
        }
        return view;
    }

    /**
     * 创建一个view
     *
     * @param name
     * @param context
     * @param attrs
     * @return
     */
    private View createView(String name, Context context, AttributeSet attrs) {
        Class<? extends View> clazz = null;
        Constructor<? extends View> constructor = sConstructorMap.get(name);
        try {
            if (constructor == null) {
                //获得具体的类
                clazz = context.getClassLoader().loadClass(name).asSubclass(View.class);
                //获得类的构造函数
                constructor = clazz.getConstructor(mConstructorSignature);
                constructor.setAccessible(true);
                sConstructorMap.put(name, constructor);
            }
            //调用构造函数 创建出view对象
            return constructor.newInstance(context, attrs);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Observable o, Object arg) {
       //更换皮肤了
        skinAttribute.applySkin();
    }
}
