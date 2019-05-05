package load.image.prim.com.prim_image_loader.manager;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/8/28 - 下午11:05
 */
public class ApplicationLifecycle implements Lifecycle {
    @Override
    public void addListener(LifecycleListener lifecycleListener) {
        lifecycleListener.onStart();
    }

    @Override
    public void removeListener(LifecycleListener lifecycleListener) {

    }
}
