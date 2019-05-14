package cn.prim.http.lib_net.request.observer;

import cn.prim.http.lib_net.callback.Callback;
import cn.prim.http.lib_net.utils.PrimHttpLog;

/**
 * @author prim
 * @version 1.0.0
 * @desc 观察者Observer 观察网络对回调
 * @time 2019/1/3 - 7:38 PM
 */
public class CallbackObserver<T> extends BaseObserver<T> {
    public Callback<T> callback;

    private static final String TAG = "CallbackObserver";

    public CallbackObserver(Callback<T> callback) {
        this.callback = callback;
    }

    @Override
    public void onStart() {
        if (callback != null) {
            callback.onStart();
        }
        PrimHttpLog.e(TAG, "onStart");
    }

    @Override
    public void onNext(T tResponse) {
        super.onNext(tResponse);
//        PrimHttpLog.e(TAG, "Response:" + tResponse.toString());
        if (callback != null) {
//            callback.onSuccess(tResponse);
        }
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        PrimHttpLog.e(TAG, "onError:" + e);
        if (callback != null) {
            callback.onError();
        }
    }

    @Override
    public void onComplete() {
        super.onComplete();
        if (callback != null) {
            callback.onComplete();
        }
        PrimHttpLog.e(TAG, "onComplete");
    }
}
