package load.image.prim.com.prim_image_loader;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import load.image.prim.com.prim_image_loader.cache.MemoryCache;
import load.image.prim.com.prim_image_loader.cache.disk.IDiskCache;
import load.image.prim.com.prim_image_loader.cache.recycle.BitmapPool;
import load.image.prim.com.prim_image_loader.manager.RequestManager;
import load.image.prim.com.prim_image_loader.manager.RequestManagerFactory;

/**
 * @author prim
 * @version 1.0.0
 * @desc PrimM 的总入口
 * @time 2018/8/23 - 下午11:21
 */
public class PrimM {

    private final MemoryCache memoryCache;
    private final BitmapPool bitmapPool;
    private final IDiskCache diskCache;

    private final RequestManagerFactory requestManagerFactory;

    private static PrimM primM;

    protected PrimM(Context context, PrimMBuilder primMBuilder) {
        requestManagerFactory = new RequestManagerFactory();
        memoryCache = primMBuilder.memoryCache;
        bitmapPool = primMBuilder.bitmapPool;
        diskCache = primMBuilder.diskCache;
    }

    public static RequestManager with(Context context) {
        return null;
    }

    public static RequestManager with(Activity activity) {
        return null;
    }

    public static RequestManager with(Fragment fragment) {
        return null;
    }

    public static RequestManager with(android.app.Fragment fragment) {
        return null;
    }

    public static RequestManager with(View view) {
        return null;
    }

    public static RequestManager with(FragmentActivity fragmentActivity) {
        return PrimM.get(fragmentActivity).requestManagerFactory.get(fragmentActivity);
    }

    /**
     * 默认实现 一个单利模式
     *
     * @param context
     * @return
     */
    private static PrimM get(Context context) {
        if (primM == null) {
            synchronized (PrimM.class) {
                if (primM == null) {
                    init(context, new PrimMBuilder());
                }
            }
        }
        return primM;
    }

    /**
     * 使用者可以定制自己的GilderBuilder
     *
     * @param context
     * @param primMBuilder
     */
    public static void init(Context context, PrimMBuilder primMBuilder) {
        Context applicationContext = context.getApplicationContext();
        primM = primMBuilder.build(applicationContext);
    }
}
