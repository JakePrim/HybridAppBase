package demo.prim.com.rxjavademo;

import android.graphics.Bitmap;
import android.util.Log;

import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observables.GroupedObservable;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private static final String TAG = "ExampleUnitTest";

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    /**
     * 基本写法
     */
    @Test
    public void testCreate() {

        // 1 建立被观察者角色 产生需求
        Observable observable = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) {
                //产生一个事件
                emitter.onNext("123");
            }
        });

        // 2 创建观察者  观察是否有需求
        Observer observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe = [" + d + "]");
                d.dispose();//中断
            }

            @Override
            public void onNext(String o) {
                System.out.println("onNext:" + o);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError");
            }

            @Override
            public void onComplete() {
                //？？什么时候调用完成呢
                System.out.println("onComplete");
            }
        };

        //3 订阅
        observable.subscribe(observer);
    }


    /**
     * 链式写法
     */
    @Test
    public void testChainCreate() {
        //被观察者
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("123456");

            }
        }).subscribe(new Observer<String>() {//观察者
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                System.out.println("s = [" + s + "]");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });
    }

    /**
     * 更加简单的方法
     * 操作符 - 创建操作符
     * <p>
     * 过滤操作符
     * 合并聚合操作符
     * 条件操作符
     * 变换操作符
     * 异常操作符
     */
    @Test
    public void simpleCreate() {
        // just 两个参数  fromArray 多个参数 遍历文件 just 是fromArray的一个封装
        Observable.just("123232", "2323").subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                System.out.println("s = [" + s + "]");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });

        //遍历数组 T...
        Observable.fromArray(new Integer[]{1, 2, 3, 4}).subscribe(new Observer<Integer>() {
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
                System.out.println("onComplete");
            }
        });

        // empty 创建型操作符 只调用onComplete 适用于重新渲染UI   加载网络  重新刷新
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

    }


    /**
     * 背压 ?? 什么时候会用到背压呢 背压用于并发处理
     * 生产速度 与 消费速度的比值
     */
    @Test
    public void testFlowable() {
        // 背压策略
        Flowable.create(new FlowableOnSubscribe<Object>() {
            @Override
            public void subscribe(FlowableEmitter<Object> emitter) throws Exception {
                for (int i = 0; i < 10000000; i++) {
                    emitter.onNext(i);
                }
            }
        }, BackpressureStrategy.BUFFER)
                .subscribe(new Subscriber<Object>() {
            @Override
            public void onSubscribe(Subscription s) {
                System.out.println("onComplete");
                s.request(Integer.MAX_VALUE);//最大的处理能力 最大处理数
            }

            @Override
            public void onNext(Object o) {
                System.out.println("o = [" + o + "]");

            }

            @Override
            public void onError(Throwable t) {
                System.out.println("onComplete");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });
    }

    /**
     * 转换操作符
     */
    @Test
    public void testChange() {
        Observable.just("head", "bit").map(new Function<String, String>() {
            @Override
            public String apply(String s) throws Exception {//对每一项数据进行 数据变换
                // 进行网络请求
                System.out.println("s = [" + s + "]");
                return s+".png";
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String bitmap) {
                System.out.println("bitmap = [" + bitmap + "]");

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
    public void testFlatMap() {
        //先拿到 app的配置  再去登陆   just 直接触发事件

        Observable.just("config", "login").flatMap(new Function<String, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(String s) throws Exception {

                return createResponce(s);
            }
        }).subscribe(new Observer<Object>() {
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

            }
        });
    }

    private ObservableSource<?> createResponce(final String s) {
        // 回调地狱  观察上一个请求是否成功了
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("login:" + s);
            }
        });
    }

    @Test
    public void testGroupBy() {//将事件类型 转换为 结果类型
        Observable.just(1, 2, 3, 4, 5).groupBy(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                System.out.println("integer = [" + integer + "]");

                //根据参数进行分类

                return integer > 2 ? "A" : "B";
            }
        }).subscribe(new Consumer<GroupedObservable<String, Integer>>() {
            @Override
            public void accept(final GroupedObservable<String, Integer> stringIntegerGroupedObservable) throws Exception {
                //stringIntegerGroupedObservable 是被观察者 需要通过观察者拿到数据
                stringIntegerGroupedObservable.subscribe(new Consumer<Integer>() {// 通过观察者拿到数据
                    @Override
                    public void accept(Integer integer) throws Exception {
                        String key = stringIntegerGroupedObservable.getKey();
                        System.out.println("key--" + key + " | " + integer);

                    }
                });
            }
        });

    }

    /**
     * 使用场景
     * 100000条数据插入到数据库
     * 每一条数据产生都需要时间
     * 如果产生一条 插入一条比较浪费时间 全部一次性插入用户等太久
     * 采取buffer等形式 将100000 条 分成 一小段执行
     */
    @Test
    public void testBuffer() {
        Observable.just(1, 2, 3, 4, 5, 6).buffer(3).subscribe(new Observer<List<Integer>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Integer> integers) {
                System.out.println("integers = [" + integers + "]");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 适合 上一个结果 作为 下一个参数
     */
    @Test
    public void testScan() {
        // 10 个子文件 ---》 大文件
        Observable.range(1, 5)
                .scan(new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) throws Exception {
                //
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

    // ----------------------- 过滤操作符 对多个事件过滤 ------------------------ //


    @Test
    public void testFilter() {
        Observable.just(1, 2, 3, 4, 5, 6).filter(new Predicate<Integer>() {//过滤器
            @Override
            public boolean test(Integer integer) throws Exception {// false 当前等事件不被处理  true 当前等事件处理
                return integer > 2;
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

    /**
     * 定时器 心跳
     */
    @Test
    public void testTake() {
        Observable.interval(1, TimeUnit.SECONDS)
                .take(5)//限制 拦截产生事件的数量
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("d = [" + d + "]");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        System.out.println("aLong = [" + aLong + "]");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });
    }

    //过滤重复的元素
    @Test
    public void testDistinct() {
        Observable.just(1, 2, 3, 3, 3, 4, 45, 6, 6).distinct().subscribe(new Observer<Integer>() {
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

    /**
     * 获取指定的元素
     */
    @Test
    public void testElementAt() {
        Observable.just(1, 2, 3, 4).elementAt(2).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println("integer = [" + integer + "]");
            }
        });
    }

    // ------------------------- 条件操作符 ----------------------- //

    /**
     * 判断事件中是否满足 得到一个结果
     */
    @Test
    public void testAll() {
        Observable.just(4, 3, 4, 5).all(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Exception {
                return integer > 2;//是否全部大于 2
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                System.out.println("aBoolean = [" + aBoolean + "]");
            }
        });
    }

    /**
     * 判断事件是否包含
     */
    @Test
    public void testContains() {
        Observable.just("abc", "a", "er").contains("a").subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                System.out.println("aBoolean = [" + aBoolean + "]");
            }
        });
    }
}