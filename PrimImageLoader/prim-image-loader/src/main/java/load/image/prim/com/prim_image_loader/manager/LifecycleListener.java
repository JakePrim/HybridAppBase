package load.image.prim.com.prim_image_loader.manager;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/8/28 - 下午11:04
 */
public interface LifecycleListener {
    void onStart();

    void onStop();

    void onDestory();
}
