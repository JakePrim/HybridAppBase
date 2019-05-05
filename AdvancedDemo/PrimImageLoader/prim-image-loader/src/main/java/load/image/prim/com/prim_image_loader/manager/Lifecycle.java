package load.image.prim.com.prim_image_loader.manager;

/**
 * @author prim
 * @version 1.0.0
 * @desc 管理生命周期接口
 * @time 2018/8/28 - 下午11:04
 */
public interface Lifecycle {
    void addListener(LifecycleListener lifecycleListener);

    void removeListener(LifecycleListener lifecycleListener);
}
