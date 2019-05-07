package demo.prim.com.rxjavademo;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;

/**
 * @author prim
 * @version 1.0.0
 * @desc 创建操作符
 * @time 2019-05-07 - 14:00
 */
public class CreateOperators {
    //创建操作符
    @Test
    public void create() {
        Observable.create(new ObservableOnSubscribe<Integer>() {//被观察者
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                //检查观察者的isUnsubscribe 观察者observer是否订阅了本观察者Observable 在RxJava 2.x 已经没有了，直接是ObservableEmitter
                try {
                    for (int i = 0; i < 5; i++) {
                        //被观察者发射数据
                        emitter.onNext(i);
                    }
                    //调用观察者的onCompleted正好一次或者它的onError正好一次，而且此后不能再调用观察者的任何其它方法。
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }).subscribe(new Observer<Integer>() {//观察者
            @Override
            public void onSubscribe(Disposable d) {
                if (d.isDisposed()) {//判断是否解除订阅
                    System.out.println("d = [" + d + "]");
                }
                d.dispose();//解除订阅
            }

            @Override
            public void onNext(Integer integer) {
                //观察者 观察到被观察者发射了数据
                System.out.println("integer = [" + integer + "]");
            }

            @Override
            public void onError(Throwable e) {
                //错误
                System.out.println("e = [" + e + "]");
            }

            @Override
            public void onComplete() {
                //完成
                System.out.println("onComplete");
            }
        });
    }

    @Test
    public void just() {
        Observable.just("1", "2").subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext:" + s);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError:" + e);
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });
    }

    @Test
    public void fromArray() {
        Integer[] a = new Integer[]{1, 2, 3, 4, 5};
        Observable.fromArray(a).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("onNext:" + integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Test
    public void empty() {
        Observable.empty().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                System.out.println("o = [" + o + "]");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });

        Observable.never().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("d = [" + d + "]");
            }

            @Override
            public void onNext(Object o) {
                System.out.println("o = [" + o + "]");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("e = [" + e + "]");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });
    }

    /**
     * 创建一个固定时间间隔发送整数序列的Observable
     */
    @Test
    public void interval() {
        Observable.interval(1, TimeUnit.SECONDS)
                .take(5)//限制产生事件的数量
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        System.out.println("aLong = [" + aLong + "]");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("e = [" + e + "]");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("");
                    }
                });
    }

    /**
     * range 创建一个发射特定整数序列的Observable
     */
    @Test
    public void range() {
        Observable.range(1, 10)
                .scan(new BiFunction<Integer, Integer, Integer>() {//处理数据 改变发射的数据
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        return integer + integer2;
                    }
                }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("integer = [" + integer + "]");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
