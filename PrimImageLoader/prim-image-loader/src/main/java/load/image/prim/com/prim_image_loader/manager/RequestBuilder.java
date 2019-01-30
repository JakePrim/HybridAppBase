package load.image.prim.com.prim_image_loader.manager;

import android.widget.ImageView;

import load.image.prim.com.prim_image_loader.request.Request;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/8/28 - 下午11:29
 */
public class RequestBuilder {

    private final RequestManager requestManager;
    //记录model
    private Object model;

    public RequestBuilder(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    public RequestBuilder load(String string) {
        model = string;
        return this;
    }

    /**
     * 开始加载图片并设置到ImageView当中
     * @param view
     */
    public void into(ImageView view){
        Traget traget = new Traget(view);//将view 交给traget管理 traget 将imagetview
        //图片加载与设置
        Request request = new Request(model, traget);
        //交给
        requestManager.track(request);
    }
}
