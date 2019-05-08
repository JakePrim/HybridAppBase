package cn.prim.http.lib_net.request.observer;

import io.reactivex.observers.DisposableObserver;

/**
 * @author prim
 * @version 1.0.0
 * @desc 订阅者基类
 * @time 2019/1/3 - 7:33 PM
 */
public abstract class BaseObserver<T> extends DisposableObserver<T> {
    @Override
    protected void onStart() {
    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
