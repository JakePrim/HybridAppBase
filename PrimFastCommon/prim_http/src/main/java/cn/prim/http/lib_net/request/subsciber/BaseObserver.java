package cn.prim.http.lib_net.request.subsciber;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * @author prim
 * @version 1.0.0
 * @desc 订阅等基类
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
