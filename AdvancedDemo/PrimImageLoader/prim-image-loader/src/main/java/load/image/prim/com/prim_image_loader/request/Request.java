package load.image.prim.com.prim_image_loader.request;

import load.image.prim.com.prim_image_loader.manager.Traget;

/**
 * @author prim
 * @version 1.0.0
 * @desc 图片加载请求到开始
 * @time 2018/8/28 - 下午11:34
 */
public class Request {
    private final Object model;
    private final Traget traget;

    //将请求到地址给它
    public Request(Object model, Traget traget) {
        //通过traget 将bitmap 设置到imageview中去
        this.model = model;
        this.traget = traget;
    }
}
