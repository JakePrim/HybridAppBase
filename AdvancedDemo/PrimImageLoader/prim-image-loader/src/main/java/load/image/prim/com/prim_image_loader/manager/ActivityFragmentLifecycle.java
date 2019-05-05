package load.image.prim.com.prim_image_loader.manager;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/8/28 - 下午11:05
 */
public class ActivityFragmentLifecycle implements Lifecycle {

    //这里为什么用set??
    private final Set<LifecycleListener> lifecycleListeners = Collections.newSetFromMap(new WeakHashMap<LifecycleListener, Boolean>());

    //已启动
    private boolean isStarted;

    //已销毁
    private boolean isDestoryed;

    @Override
    public void addListener(LifecycleListener lifecycleListener) {
        lifecycleListeners.add(lifecycleListener);

        if (isDestoryed) {
            lifecycleListener.onDestory();
        } else if (isStarted) {
            lifecycleListener.onStart();
        } else {
            lifecycleListener.onStop();
        }

    }

    @Override
    public void removeListener(LifecycleListener lifecycleListener) {
        lifecycleListeners.remove(lifecycleListener);
    }

    void onStart() {
        isStarted = true;
        for (LifecycleListener lifecycleListener : lifecycleListeners) {
            lifecycleListener.onStart();
        }
    }

    void onStop() {
        isStarted = false;
        for (LifecycleListener lifecycleListener : lifecycleListeners) {
            lifecycleListener.onStop();
        }
    }

    void onDestory() {
        isDestoryed = true;
        for (LifecycleListener lifecycleListener : lifecycleListeners) {
            lifecycleListener.onDestory();
        }
    }
}
