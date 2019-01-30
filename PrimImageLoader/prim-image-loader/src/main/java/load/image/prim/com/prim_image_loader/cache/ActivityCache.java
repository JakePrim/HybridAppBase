package load.image.prim.com.prim_image_loader.cache;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import load.image.prim.com.prim_image_loader.cache.recycle.Resource;

/**
 * @author prim
 * @version 1.0.0
 * @desc 活动缓存 正在使用的图片资源
 * @time 2018/8/18 - 下午11:00
 */
public class ActivityCache {
    /**
     * 将正在使用的图片加入映射集合里面
     */
    private Map<Key, ResourceWeakReference> activityResource = new HashMap<>();

    private Resource.ResourceListener listener;

    private ReferenceQueue<Resource> queue;

    private Thread cleanReferenceQueueThread;

    private boolean isShutDown;

    private ReferenceQueue<Resource> getRefrenceQueue() {
        if (null == queue) {
            //当弱引用被回收时 会将回收掉的弱引用放到引用队列中去
            queue = new ReferenceQueue<>();//引用队列 只是起到一个通知的作用，让我们知道弱引用被回收了
            cleanReferenceQueueThread = new Thread() {
                @Override
                public void run() {
                    while (!isShutDown) {
                        try {
                            //获得被回收掉的引用
                            ResourceWeakReference remove = (ResourceWeakReference) queue.remove();
                            //因为对应的映射集合存储的value 是该弱引用，所以此时映射集合也要remove掉
                            activityResource.remove(remove.key);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            };
            cleanReferenceQueueThread.start();

        }
        return queue;
    }

    public ActivityCache(Resource.ResourceListener listener) {
        this.listener = listener;
    }

    /**
     * 加入活动缓存
     *
     * @param key
     * @param resource
     */
    public void activite(Key key, Resource resource) {
        resource.setResourceListener(key, listener);
        activityResource.put(key, new ResourceWeakReference(key, resource, getRefrenceQueue()));
    }

    /**
     * 移除活动缓存
     */
    public Resource deactivite(Key key) {
        ResourceWeakReference remove = activityResource.remove(key);
        if (remove != null) {
            return remove.get();
        }
        return null;
    }

    public Resource get(Key key){
        ResourceWeakReference reference = activityResource.get(key);
        if (reference != null) {
            return reference.get();
        }
        return null;
    }

    void shutDown(){
        isShutDown = true;
        if (cleanReferenceQueueThread != null){
            cleanReferenceQueueThread.interrupt();//强制关掉线程
            try {
                //保证线程关闭
                cleanReferenceQueueThread.join(TimeUnit.SECONDS.toMillis(5));
                if (cleanReferenceQueueThread.isAlive()){//如果5s还没哟结束，抛出异常
                    throw new RuntimeException("Failed to join in time");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建一个弱引用资源
     */
    static final class ResourceWeakReference extends WeakReference<Resource> {

        private final Key key;

        public ResourceWeakReference(Key key, Resource referent, ReferenceQueue<? super Resource> q) {
            super(referent, q);
            this.key = key;
        }
    }
}
