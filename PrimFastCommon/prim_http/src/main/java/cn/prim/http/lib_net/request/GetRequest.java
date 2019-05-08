package cn.prim.http.lib_net.request;

import cn.prim.http.lib_net.callback.Callback;
import cn.prim.http.lib_net.request.function.ParseResponseFunction;
import cn.prim.http.lib_net.request.observer.CallbackObserver;
import cn.prim.http.lib_net.utils.SchedulersUtils;
import io.reactivex.*;
import okhttp3.ResponseBody;

/**
 * @author prim
 * @version 1.0.0
 * @desc get方式请求网络
 * @time 2019/1/3 - 2:39 PM
 */
public class GetRequest<T> extends BaseRequest<T, GetRequest<T>> {
    private static final long serialVersionUID = 1284051499511650147L;

    public GetRequest(String url) {
        super(url);
    }

    //同步请求
    @Override
    public ResponseBody execute() {
        return null;
    }

    //异步请求
    @Override
    public void enqueue(final Callback<T> callback) {
        //先查询看是否缓存了数据


        //当创建Observable流的时候，compose()会立即执行
        generateRequest()
                .map(new ParseResponseFunction<T>(callback == null ? null : callback.getType()))//转换json数据
                .compose(SchedulersUtils.<T>taskIo_main())//子线程请求网络 主线程回调
                .subscribe(new CallbackObserver<>(callback));// 订阅观察者 CallbackObserver 观察者后续添加缓存 重试等
    }

    @Override
    public void enqueue() {
        //当创建Observable流的时候，compose()会立即执行
        generateRequest()
                .map(new ParseResponseFunction<T>(callback == null ? null : callback.getType()))//转换json数据
                .compose(SchedulersUtils.<T>taskIo_main())//子线程请求网络 主线程回调
                .subscribe(new CallbackObserver<>(callback));// 订阅观察者 CallbackObserver 观察者后续添加缓存 重试等
    }

    @Override
    protected Observable<ResponseBody> generateRequest() {
        //调用.get 就相当与调用了 Observable.create() 在create中请求网络
        return generateService().get(url, mParams.getCommonParams());
    }
}
