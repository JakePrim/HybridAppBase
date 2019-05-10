package cn.prim.http.lib_net.utils;

import cn.prim.http.lib_net.model.Response;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author prim
 * @version 1.0.0
 * @desc 任务调度切换：子线程和主线程切换
 * @time 2019/1/3 - 3:56 PM
 */
public class SchedulersUtils {
    private static final String TAG = "SchedulersUtils";

    /**
     * 被观察者在IO 线程中运行
     * 观察者在Android 主线程中运行
     *
     * @param <T>
     * @return
     */
    @NonNull
    public static <T> ObservableTransformer<T, T> taskIo_main() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                //当创建Observable流的时候，compose()会立即执行 用于线程切换操作 请求网络包括转换数据类型在子线程中执行
                //完毕后在主线程中回调返回
                return upstream
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .doFinally(new Action() {
                            @Override
                            public void run() throws Exception {
                                PrimHttpLog.d(TAG, "taskIo_main doFinally");
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    @NonNull
    public static <T> ObservableTransformer<T, T> taskMain() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                //当创建Observable流的时候，compose()会立即执行 用于线程切换操作 请求网络包括转换数据类型在子线程中执行
                //完毕后在主线程中回调返回
                return upstream
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {

                            }
                        })
                        .doFinally(new Action() {
                            @Override
                            public void run() throws Exception {
                                PrimHttpLog.d(TAG, "taskMain doFinally");
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
