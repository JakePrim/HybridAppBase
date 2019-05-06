package cn.prim.http.lib_net.request;

import cn.prim.http.lib_net.callback.Callback;
import cn.prim.http.lib_net.request.func.ParseResponseFunc;
import cn.prim.http.lib_net.request.subsciber.CallbackSubscriber;
import cn.prim.http.lib_net.utils.TaskUtils;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * @author prim
 * @version 1.0.0
 * @desc post方式请求网络
 * @time 2019-05-06 - 17:45
 */
public class PostRequest<T> extends BaseRequest<T, PostRequest<T>> {
    public PostRequest(String url) {
        super(url);
    }

    @Override
    public ResponseBody execute() {
        return null;
    }

    @Override
    public void enqueue(Callback<T> callback) {
        //当创建Observable流的时候，compose()会立即执行
        generateRequest()
                .map(new ParseResponseFunc<T>(callback == null ? null : callback.getType()))//转换json数据
                .compose(TaskUtils.<T>taskIo_main())//子线程请求网络 主线程回调
                .subscribeWith(new CallbackSubscriber<>(callback));//设置线程调度 后续添加缓存 重试等
    }

    @Override
    protected Observable<ResponseBody> generateRequest() {
        return generateService().post(url, mParams.getCommonParams());
    }
}
