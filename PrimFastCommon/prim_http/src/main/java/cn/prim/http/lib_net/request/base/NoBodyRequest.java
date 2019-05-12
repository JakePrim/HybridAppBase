package cn.prim.http.lib_net.request.base;

import android.annotation.SuppressLint;
import cn.prim.http.lib_net.callback.CallBackProxy;
import cn.prim.http.lib_net.callback.Callback;
import cn.prim.http.lib_net.model.Response;
import cn.prim.http.lib_net.request.function.ParseResponseFunction;
import cn.prim.http.lib_net.request.function.RepeatFunction;
import cn.prim.http.lib_net.request.observer.CallbackObserver;
import cn.prim.http.lib_net.utils.SchedulersUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import java.lang.reflect.Type;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019-05-10 - 16:04
 */
public abstract class NoBodyRequest<T, R extends NoBodyRequest> extends BaseRequest<T, R> {
    public NoBodyRequest(String url) {
        super(url);
    }

    /**
     * 生成异步请求
     *
     * @param callback
     */
    protected void generateEnqueue(Callback<T> callback) {
        //先查询看是否缓存了数据
        generateEnqueue(new CallBackProxy<Response<T>, T>(callback) {
            @Override
            public Type getRawType() {
                return null;
            }
        });
    }

    @SuppressLint("CheckResult")
    private void generateEnqueue(CallBackProxy<? extends Response<T>, T> callback) {
        if (cache) {//如果开启了缓存 则先查找是否有缓存数据
            Observable.concat(primCache.getCache(), generateRequest())//先走缓存 如果缓存不存在，在请求网络，如果缓存存在则不请求网络
                    .map(new ParseResponseFunction(callback == null ? null : callback.getType(), parse))//转换json数据
                    .compose(SchedulersUtils.taskIo_main())//子线程请求网络 主线程回调
                    .retryWhen(new RepeatFunction(getRepeatCount(), getRepeatDuration()))//网络请求失败 重试的判断
                    .subscribeWith(new CallbackObserver<>(callback.getCallBack()));// 订阅观察者 CallbackObserver 观察者后续添加缓存 重试等
        } else {//如果没有开启缓存 则直接请求网络
            //当创建Observable流的时候，compose()会立即执行
            generateRequest()
                    .map(new ParseResponseFunction(callback == null ? null : callback.getType(), parse))//转换json数据
                    .compose(SchedulersUtils.taskIo_main())//子线程请求网络 主线程回调
                    .retryWhen(new RepeatFunction(getRepeatCount(), getRepeatDuration()))//网络请求失败 重试的判断
                    .subscribeWith(new CallbackObserver<>(callback.getCallBack()));// 订阅观察者 CallbackObserver 观察者后续添加缓存 重试等
        }
    }

    /**
     * 生成同步请求
     *
     * @return
     */
    protected T generateExecute() {
        final T[] result = (T[]) new Object[]{null};
        generateRequest()
                .map(new ParseResponseFunction(callback == null ? null : callback.getType(), parse))//转换json数据
                .compose(SchedulersUtils.taskMain())//子线程请求网络 主线程回调
                .retryWhen(new RepeatFunction(getRepeatCount(), getRepeatDuration()))//网络请求失败 重试的判断
                .doOnNext(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        result[0] = (T) o;
                    }
                });
        return result[0];
    }
}
