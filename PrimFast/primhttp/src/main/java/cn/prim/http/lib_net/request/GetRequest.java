package cn.prim.http.lib_net.request;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import cn.prim.http.lib_net.callback.Callback;
import cn.prim.http.lib_net.model.Response;
import cn.prim.http.lib_net.request.func.ParseResponseFunc;
import cn.prim.http.lib_net.request.subsciber.CallbackSubscriber;
import cn.prim.http.lib_net.utils.Task;
import io.reactivex.*;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
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

    @Override
    public ResponseBody execute() {
        return null;
    }

    @Override
    public void enqueue(final Callback<T> callback) {
        //当创建Observable流的时候，compose()会立即执行
         generateRequest()
                .map(new ParseResponseFunc<T>(callback == null ? null : callback.getType()))//转换json数据
                .compose(Task.<T>taskIo_main())
                .subscribeWith(new CallbackSubscriber<>(callback));//设置线程调度 后续添加缓存 重试等
    }

    @Override
    protected Observable<ResponseBody> generateRequest() {
        return generateService().get(url, mParams.commonParams);
    }
}
