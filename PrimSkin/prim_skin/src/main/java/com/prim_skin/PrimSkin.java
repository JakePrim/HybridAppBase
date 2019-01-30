package com.prim_skin;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.text.TextUtils;

import com.prim_skin.skin.SkinActivityLifecycle;
import com.prim_skin.utils.SkinPreferenceTools;
import com.prim_skin.utils.SkinResourceTools;
import com.prim_skin.utils.SkinTools;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Observable;
import java.util.Observer;

/**
 * @author prim
 * @version 1.0.0
 * @desc PrimSkin 动态换肤总入口
 * @time 2018/12/3 - 10:37 PM
 */
public class PrimSkin extends Observable {

    private static PrimSkin instance;

    private Application mAppContext;

    public static void init(Application application) {
        if (null == instance) {
            synchronized (PrimSkin.class) {
                if (instance == null) {
                    instance = new PrimSkin(application);
                }
            }
        }
    }

    public static PrimSkin getInstance() {
        return instance;
    }

    private PrimSkin(Application application) {
        this.mAppContext = application;
        SkinPreferenceTools.init(application);
        SkinResourceTools.init(application);
        //application 绑定生命周期
        application.registerActivityLifecycleCallbacks(new SkinActivityLifecycle());
        //加载皮肤包
        loadSkin(SkinPreferenceTools.getInstance().getSkinPath());
    }

    /**
     * 加载皮肤包，并更新
     *
     * @param skinPath
     */
    public void loadSkin(String skinPath) {
        //可能为null 或者还原默认皮肤包
        if (TextUtils.isEmpty(skinPath) || "-1".equals(skinPath)) {
            //清空皮肤包资源
            SkinPreferenceTools.getInstance().clearSkin();
            SkinResourceTools.getInstance().reset();
        } else {
            //将皮肤包添加到资源管理器中
            AssetManager assetManager = SkinTools.addAssetPath(skinPath);
            if (assetManager != null) {
                Resources resources = mAppContext.getResources();
                //当前app的横竖屏 语言等等 新建一个皮肤包的resource
                Resources newResources =
                        new Resources(assetManager, resources.getDisplayMetrics(), resources.getConfiguration());
                //获取外部apk的包名
                PackageManager packageManager = mAppContext.getPackageManager();
                PackageInfo packageArchiveInfo = packageManager.getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES);
                String packageName = "skin";
                if (packageArchiveInfo != null){
                    packageName = packageArchiveInfo.packageName;
                }
                //申请加载皮肤包
                SkinResourceTools.getInstance().applySkin(newResources, packageName);
                //保存当前使用到皮肤包
                SkinPreferenceTools.getInstance().saveSkin(skinPath);
            }
        }
        setChanged();
        //通知所有观察者
        notifyObservers();
    }

    /**
     * 清除皮肤包，回复默认皮肤
     */
    public void clearSkin() {
        loadSkin(null);
    }
}


