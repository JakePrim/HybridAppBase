package demo.prim.com.rxjavademo;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.Functions;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        interval();
    }

    public void zip1(View view) {
        zip();
    }

    public void refush(View view) {
        concat();
    }

    public void map1(View view) {
        map();
    }

    public void interval1(View view) {
        interval();
    }

    public void retryWhen1(View view) {
        retryWhen();
    }

    final int count = 3;//重试次数为3次
    final int delay = 5000;//等待时长 重新链接的时间间隔
    String cacheToken = null;

    @SuppressLint("CheckResult")
    private void retryWhen() {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                Log.e(TAG, "subscribe: 1 - 请求网络");
                //模拟网络请求
                if (cacheToken == null) {//模拟网络请求失败
                    emitter.onError(new NullPointerException());
                } else {
                    //模拟网络请求成功
                    emitter.onNext("网络请求成功");//当调用next 时不会走retryWhen
                }
            }
        }).retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Observable<Throwable> observable) throws Exception {
                return observable.zipWith(Observable.range(1, 1 + count), new BiFunction<Throwable, Integer, Warrp>() {
                    @Override
                    public Warrp apply(Throwable throwable, Integer integer) throws Exception {
                        Log.e(TAG, "apply: 合并重试次数和异常信息：" + integer);
                        return new Warrp(integer, throwable);
                    }
                }).flatMap(new Function<Warrp, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Warrp warrp) throws Exception {
                        //收到异常信息 且重试次数小于设置的次数
                        if (warrp.throwable instanceof NullPointerException && (warrp.count < count + 1)) {
                            Log.e(TAG, "apply: 重试次数：" + warrp.count);
                            Log.e(TAG, "apply: 收到错误准备重试：" + delay + " ms后开始重试");
                            if (warrp.count == 2) {//模拟 第二次重试 网络请求正常了
                                cacheToken = "1212213";
                            }
                            //重新链接 请求网络
                            return Observable.timer(delay, TimeUnit.MILLISECONDS);
                        }
                        return Observable.error(warrp.throwable);
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Log.e(TAG, "accept: next - " + o);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "accept: error - " + throwable);
                    }
                });
    }


    public class Warrp {
        public int count;
        public Throwable throwable;

        public Warrp(int count, Throwable throwable) {
            this.count = count;
            this.throwable = throwable;
        }
    }

    private void interval() {
        Observable.interval(1, TimeUnit.SECONDS, Schedulers.newThread())
                .take(5)//限制 拦截产生事件的数量
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("d = [" + d + "]");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        System.out.println("aLong = [" + aLong + "]" + Thread.currentThread().getName());
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

    public void zip() {
        Observable<String> zip1 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                System.out.println("emitter = [" + Thread.currentThread().getName() + "]");
                emitter.onNext("test");
            }
        }).map(new Function<String, String>() {
            @Override
            public String apply(String s) throws Exception {
                System.out.println("emitter = [" + Thread.currentThread().getName() + "]");
                //解析json数据 返回bean
                return "json:" + s;
            }
        });

        Observable<Integer> zip2 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                System.out.println("emitter = [" + Thread.currentThread().getName() + "]");
                emitter.onNext(111);
            }
        }).map(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer o) throws Exception {
                System.out.println("emitter = [" + Thread.currentThread().getName() + "]");
                //解析json数据 返回bean
                return 111 + o;
            }
        });

        Observable.zip(zip1, zip2, new BiFunction<String, Integer, Object>() {
            @Override
            public Object apply(String s, Integer integer) throws Exception {
                return "合并后的数据为：" + s + integer;
            }
        })
                .subscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
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

    private static final String TAG = "MainActivity";

    public void concat() {
        //查询缓存的observable
        Observable<String> cacheObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.e(TAG, "subscribe 读取缓存线程: " + Thread.currentThread().getName());
                String cache = CacheManage.getInstance().get("cache");
                if (!TextUtils.isEmpty(cache)) {
                    Log.e(TAG, "subscribe: 读取缓存数据 -》" + cache);
                    emitter.onNext(cache);
                    //请求网络 更新缓存数据
                    emitter.onComplete();
                } else {
                    Log.e(TAG, "subscribe: 没有缓存请求网络");
                    emitter.onComplete();
                }
            }
        });

        //请求网络的observable
        Observable<String> webObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.e(TAG, "subscribe 网络请求线程: " + Thread.currentThread().getName());
                Log.e(TAG, "subscribe: 请求网络数据");
                //请求网络
                Request.Builder builder = new Request.Builder().url("https://music.aityp.com/banner?type=1").get();
                Request request = builder.build();
                Call newCall = new OkHttpClient().newCall(request);
                Response response = newCall.execute();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        String string = response.body().string();
                        CacheManage.getInstance().put("cache", string);
                        emitter.onNext(string);
                    }
                }
            }
        });

        Observable.concat(cacheObservable, webObservable)
                .map(new ParaseFunction())//解析json 字符串
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyClass>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(MyClass myClass) {
                        Log.e(TAG, "subscribe UI更新线程: " + Thread.currentThread().getName());
                        Log.e(TAG, "onNext: " + myClass.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: ");
                    }
                });
    }


    private class ParaseFunction implements Function<String, MyClass> {

        @Override
        public MyClass apply(String response) throws Exception {
            Log.e(TAG, "subscribe 解析json线程: " + Thread.currentThread().getName());
            Log.e(TAG, "apply: 解析json字符串");
            return new Gson().fromJson(response, MyClass.class);
        }
    }

    public void map() {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<Response>() {
            @Override
            public void subscribe(ObservableEmitter<Response> emitter) throws Exception {
                System.out.println("emitter = [" + Thread.currentThread().getName() + "]");
                //请求网络
                Request.Builder builder = new Request.Builder().url("https://music.aityp.com/banner?type=1").get();
                Request request = builder.build();
                Call newCall = new OkHttpClient().newCall(request);
                Response response = newCall.execute();
                emitter.onNext(response);
            }
        }).map(new Function<Response, MyClass>() {
            @Override
            public MyClass apply(Response response) throws Exception {
                //转换数据
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    if (responseBody != null) {
                        return new Gson().fromJson(responseBody.string(), MyClass.class);
                    }
                }
                return null;
            }
        })
                .doOnNext(new Consumer<MyClass>() {
                    @Override
                    public void accept(MyClass myClass) throws Exception {
                        System.out.println("将数据保存到数据库，保存成功 myClass = [" + Thread.currentThread().getName() + ":" + myClass.toString() + "]");
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MyClass>() {
                    @Override
                    public void accept(MyClass myClass) throws Exception {
                        System.out.println("获取数据成功 myClass = [" + Thread.currentThread().getName() + ":" + myClass.toString() + "]");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println("throwable = [" + throwable + "]");
                    }
                });

    }

    public class MyClass {
        public String code;

        public List<Banner> banners;

        public class Banner {
            public String imageUrl;
            public String typeTitle;

            @Override
            public String toString() {
                return "Banner{" +
                        "imageUrl='" + imageUrl + '\'' +
                        ", typeTitle='" + typeTitle + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "MyClass{" +
                    "code='" + code + '\'' +
                    ", banners=" + banners +
                    '}';
        }
    }
}
