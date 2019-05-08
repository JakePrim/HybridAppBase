package demo.prim.com.rxjavademo;

import com.google.gson.Gson;

import org.junit.Test;

import java.util.List;

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
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019-05-08 - 06:26
 */
public class MapOperators {

    public class MyClass {
        public String code;

        public List<Banner> banners;

        public class Banner {
            public String imageUrl;
            public String typeTitle;
        }

        @Override
        public String toString() {
            return "MyClass{" +
                    "code='" + code + '\'' +
                    ", banners=" + banners +
                    '}';
        }
    }

    @Test
    public void map() {
        Observable.create(new ObservableOnSubscribe<Response>() {
            @Override
            public void subscribe(ObservableEmitter<Response> emitter) throws Exception {
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
        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<MyClass>() {
                    @Override
                    public void accept(MyClass myClass) throws Exception {
                        System.out.println("保存成功 myClass = [" + myClass.toString() + "]");
                    }
                })
                .subscribe(new Consumer<MyClass>() {
                    @Override
                    public void accept(MyClass myClass) throws Exception {
                        System.out.println("获取数据成功 myClass = [" + myClass.toString() + "]");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println("throwable = [" + throwable + "]");
                    }
                });

    }

    @Test
    public void flatMap() {
        final boolean[] isRegister = {false};
        //先去注册 再去登录
        Observable.just("register", "login").flatMap(new Function<String, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(String s) throws Exception {
                if (!isRegister[0]) {
                    return Observable.create(new ObservableOnSubscribe<String>() {
                        @Override
                        public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                            //调用注册接口
                            isRegister[0] = true;
//                            emitter.onNext("注册成功");
                            emitter.onError(new NullPointerException());
                            emitter.onComplete();
                        }
                    });
                } else {
                    return Observable.create(new ObservableOnSubscribe<String>() {
                        @Override
                        public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                            //调用登录接口
                            emitter.onNext("登录成功");
                        }
                    });
                }
            }
        }).subscribe(new Observer<Object>() {
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

    @Test
    public void concat() {
//        Observable.concat()*/
        //查询缓存的observable
        Observable<String> cacheObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

            }
        });

        //请求网络的observable
        Observable<Response> webObservable = Observable.create(new ObservableOnSubscribe<Response>() {
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
        });
    }

    @Test
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
        }).subscribeOn(Schedulers.io())
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
}
