package cn.prim.http.lib_net;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import cn.prim.http.lib_net.config.ApplicationAttach;
import cn.prim.http.lib_net.config.ConfigKeys;
import cn.prim.http.lib_net.config.Configurator;
import cn.prim.http.lib_net.model.*;
import cn.prim.http.lib_net.request.DeleteRequest;
import cn.prim.http.lib_net.request.GetRequest;
import cn.prim.http.lib_net.request.PostRequest;
import cn.prim.http.lib_net.request.PutRequest;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author prim
 * @version 1.0.0
 * @desc PrimHttp 网络请求总入口
 * @time 2018/12/29 - 11:52 AM
 */
public class PrimHttp {
    private Handler mHandler;
    private WeakReference<Activity> weakActivity;

    private static PrimHttp mPrimHttp = null;

    private OkHttpClient.Builder okHttpBuilder;
    private Retrofit.Builder retrofitBuilder;

    public static PrimHttp getInstance() {
        if (mPrimHttp == null) {
            synchronized (PrimHttp.class) {
                if (mPrimHttp == null) {
                    mPrimHttp = new PrimHttp();
                }
            }
        }
        return mPrimHttp;
    }

    public PrimHttp() {
        mHandler = new Handler(Looper.getMainLooper());
        //初始化okHttpBuilder
        okHttpBuilder = new OkHttpClient.Builder();
        //设置连接池
        okHttpBuilder.connectionPool(new ConnectionPool(8, 15, TimeUnit.MILLISECONDS));
        //初始化retrofitBuilder
        retrofitBuilder = new Retrofit.Builder();
        retrofitBuilder
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    /**
     * 初始化 PrimHttp 网络请求对请求进行配置
     * 建议在Application 中进行初始化
     *
     * @param context 上下文
     * @return {@link Configurator} 网络请求配置类
     */
    public static Configurator init(Application context) {
        ApplicationAttach.attach(context);
        return Configurator.getInstance();
    }

    /**
     * 请求网络的第一步
     *
     * @param activity activity
     * @return
     */
    public PrimHttp with(Activity activity) {
        weakActivity = new WeakReference<>(activity);
        return this;
    }

    /**
     * GET 请求网络
     *
     * @param url 请求的地址
     * @param <T> 返回的实体bean类
     * @return {@link GetRequest}
     */
    public <T> GetRequest<T> get(String url) {
        return new GetRequest<>(url);
    }

    /**
     * POST 请求网络
     *
     * @param url 请求的地址
     * @param <T> 返回的实体bean类
     * @return {@link PostRequest}
     */
    public <T> PostRequest<T> post(String url) {
        return new PostRequest<>(url);
    }

    /**
     * PUT 请求网络
     *
     * @param url 请求的地址
     * @param <T> 返回的实体bean类
     * @return {@link PutRequest}
     */
    public <T> PutRequest<T> put(String url) {
        return new PutRequest<>(url);
    }

    /**
     * DELETE 请求网络
     *
     * @param url 请求的地址
     * @param <T> 返回的实体bean类
     * @return {@link DeleteRequest}
     */
    public <T> DeleteRequest<T> delete(String url) {
        return new DeleteRequest<>(url);
    }

    public OkHttpClient.Builder getOkHttpBuilder() {
        return okHttpBuilder;
    }

    public Retrofit.Builder getRetrofitBuilder() {
        return retrofitBuilder;
    }

    public Activity getActivity() {
        return weakActivity.get();
    }

    public Context getApplicationContext() {
        return ApplicationAttach.getApplicationContext();
    }

    public HttpHeaders getCommonHeaders() {
        return Configurator.getInstance().getConfiguration(ConfigKeys.HEADERS);
    }

    public HttpParams getCommonParams() {
        return Configurator.getInstance().getConfiguration(ConfigKeys.PARAMS);
    }

    public long getConnectionTimeout() {
        return Configurator.getInstance().getConfiguration(ConfigKeys.CONNECT_TIME_OUT);
    }

    public long getReadTimeout() {
        return Configurator.getInstance().getConfiguration(ConfigKeys.READ_TIME_OUT);
    }

    public long getWriteTimeout() {
        return Configurator.getInstance().getConfiguration(ConfigKeys.WRITE_TIME_OUT);
    }

    public String getBaseUrl() {
        return Configurator.getInstance().getConfiguration(ConfigKeys.API_HOST);
    }

    public List<Interceptor> getCommonInterceptor() {
        return Configurator.getInstance().getConfiguration(ConfigKeys.INTERCEPTOR);
    }

    public Handler getMainHandler() {
        return mHandler;
    }
}
