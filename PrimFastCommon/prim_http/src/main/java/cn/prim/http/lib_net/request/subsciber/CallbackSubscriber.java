package cn.prim.http.lib_net.request.subsciber;

import cn.prim.http.lib_net.callback.Callback;
import io.reactivex.disposables.Disposable;

/**
 * @author prim
 * @version 1.0.0
 * @desc 订阅网络回调
 * @time 2019/1/3 - 7:38 PM
 */
public class CallbackSubscriber<T> extends BaseSubscriber<T> {
    public Callback<T> callback;

    public CallbackSubscriber(Callback<T> callback) {
        this.callback = callback;
    }

    @Override
    public void onStart() {
        if (callback != null) {
            callback.onStart();
        }
    }

    @Override
    public void onNext(T t) {
        super.onNext(t);
        if (callback != null) {
            callback.onSuccess(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        if (callback != null) {
            callback.onError();
        }
    }

    @Override
    public void onComplete() {
        super.onComplete();
        if (callback != null) {
            callback.onFinish();
        }
    }
}
