package cn.prim.http.lib_net.request.function;

import cn.prim.http.lib_net.utils.PrimHttpLog;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author prim
 * @version 1.0.0
 * @desc 网络请求失败 重试的处理
 * 只有Observable 发射onError 的情况下 - 才会走此类
 * 其他的情况不会走此类 可以完美的对错误进行处理
 * @time 2019-05-10 - 15:05
 */
public class RepeatFunction implements Function<Observable<? extends Throwable>, Observable<?>> {

    //重试的次数
    private int count;
    //一次重试间隔的时长
    private long duration;

    private static final String TAG = "RepeatFunction";

    public RepeatFunction(int count, long duration) {
        this.count = count;
        this.duration = duration;
    }

    @Override
    public Observable<?> apply(Observable<? extends Throwable> observable) throws Exception {
        //range
        return observable.zipWith(Observable.range(1, count + 1), new BiFunction<Throwable, Integer, RepeatWarrp>() {
            @Override
            public RepeatWarrp apply(Throwable throwable, Integer integer) throws Exception {// 拿到请求次数和异常
                return new RepeatWarrp(integer, throwable);
            }
        }).flatMap(new Function<RepeatWarrp, ObservableSource<?>>() {//flatMap 拿到上一个发射的结果 进行处理
            @Override
            public ObservableSource<?> apply(RepeatWarrp repeatWarrp) throws Exception {
                Throwable throwable = repeatWarrp.throwable;
                int counts = repeatWarrp.count;
                if (counts > 1) {
                    PrimHttpLog.d(TAG, "重试的次数:" + repeatWarrp.count);
                }

                if (throwable instanceof ConnectException
                        || throwable instanceof SocketTimeoutException
                        || throwable instanceof TimeoutException
                        || throwable instanceof NullPointerException
                        && (counts < count + 1)) {
                    return Observable.timer(duration, TimeUnit.MILLISECONDS);
                }

                //超过请求次数
                return Observable.error(repeatWarrp.throwable);
            }
        });
    }

    public class RepeatWarrp {
        public int count;//记录重试的次数
        public Throwable throwable;//记录出现的异常

        public RepeatWarrp(int count, Throwable throwable) {
            this.count = count;
            this.throwable = throwable;
        }
    }
}
