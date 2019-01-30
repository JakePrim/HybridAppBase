package load.image.prim.com.prim_image_loader.cache.recycle;

import android.graphics.Bitmap;

import load.image.prim.com.prim_image_loader.cache.Key;

/**
 * @author prim
 * @version 1.0.0
 * @desc 资源类
 * @time 2018/8/18 - 下午10:27
 */
public class Resource {

    private Bitmap bitmap;

    //引用计数 对象使用的次数
    private int acquired;

    private ResourceListener resourceListener;

    private Key key;

    public Resource(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    /**
     * 设置一个引用监听
     * @param resourceListener
     */
    public void setResourceListener(Key key ,ResourceListener resourceListener){
        this.key = key;
        this.resourceListener = resourceListener;
    }

    /**
     * 当 acquired 为0 的时候调用 resourceReleased() 将对应的bitmap从活动缓存放到内存缓存中去
     */
    public interface  ResourceListener{
        void resourceReleased(Key key,Resource resource);
    }

    /**
     * 引用计数-1
     */
    public void released(){
        if (--acquired == 0){
            if (resourceListener != null){
                resourceListener.resourceReleased(key,this);
            }
        }
    }

    /**
     * 引用计数+1
     */
    public void acquired(){
        if (bitmap.isRecycled()){
            throw new RuntimeException("Bitmap is Recycled");
        }
        ++acquired;
    }

    /**
     * 释放资源
     */
    public void recycle(){
        if (acquired>0){
            return;
        }
        if (!bitmap.isRecycled()){
            bitmap.recycle();
        }
    }
}
