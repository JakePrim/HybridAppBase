package demo.prim.com.rxjavademo;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.google.gson.Gson;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("demo.prim.com.rxjavademo", appContext.getPackageName());
    }

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
        Disposable disposable = Observable.create(new ObservableOnSubscribe<Response>() {
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
                .observeOn(AndroidSchedulers.mainThread())
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
}
