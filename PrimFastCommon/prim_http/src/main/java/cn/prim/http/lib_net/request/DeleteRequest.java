package cn.prim.http.lib_net.request;

import cn.prim.http.lib_net.callback.Callback;
import cn.prim.http.lib_net.request.func.ParseResponseFunc;
import cn.prim.http.lib_net.request.subsciber.CallbackObserver;
import cn.prim.http.lib_net.utils.SchedulersUtils;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * @author prim
 * @version 1.0.0
 * @desc delete方式请求网络
 * @time 2019-05-06 - 17:45
 */
public class DeleteRequest<T> extends BaseRequest<T, DeleteRequest<T>> {
    public DeleteRequest(String url) {
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
                .compose(SchedulersUtils.<T>taskIo_main())//子线程请求网络 主线程回调
                .subscribeWith(new CallbackObserver<>(callback));//设置线程调度 后续添加缓存 重试等
    }

    @Override
    protected Observable<ResponseBody> generateRequest() {
        return generateService().delete(url, mParams.getCommonParams());
    }
}
