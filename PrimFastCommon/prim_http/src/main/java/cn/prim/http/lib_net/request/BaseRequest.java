package cn.prim.http.lib_net.request;

import android.text.TextUtils;
import cn.prim.http.lib_net.PrimHttp;
import cn.prim.http.lib_net.cache.PrimCache;
import cn.prim.http.lib_net.callback.Callback;
import cn.prim.http.lib_net.client.retrofit.ApiService;
import cn.prim.http.lib_net.model.HttpHeaders;
import cn.prim.http.lib_net.model.HttpMethod;
import cn.prim.http.lib_net.model.HttpParams;
import cn.prim.http.lib_net.utils.Utils;
import io.reactivex.Observable;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author prim
 * @version 1.0.0
 * @desc 请求的基类
 * 所有请求的基类，其中泛型 R 主要用于属性设置方法后，返回对应的子类型，以便于实现链式调用
 * @time 2019/1/2 - 5:46 PM
 */
public abstract class BaseRequest<T, R extends BaseRequest> implements Serializable {
    //获取全局的请求头
    protected HttpHeaders mHeaders;
    //获取全局的请求参数
    protected HttpParams mParams;
    //url host
    protected String baseUrl;

    protected String url;

    protected HttpUrl httpUrl;

    protected PrimHttp primHttp;

    //当前请求网络的tag，可以作为某个请求的标签
    protected Object tag;

    //当前请求网络的唯一ID，缓存数据时必须要传递
    protected int id;

    //拦截器集合
    protected List<Interceptor> interceptors;

    protected Interceptor networkInterceptor;

    //不需要序列化的字段
    protected transient ApiService apiService;

    //不需要序列化的字段
    protected transient Callback<T> callback;

    //是否需要缓存数据到数据库
    protected boolean cache;

    //链接超时
    private long connectTimeout;

    //读取超时
    private long readTimeout;

    //写入超时
    private long writeTimeout;

    //重试的次数
    private int repeatCount;

    //重试间隔的时长 ms
    private long repeatDuration = 500;

    protected PrimCache primCache;

    public BaseRequest(String url) {
        this.url = url;
        //获取网络请求配置
        primHttp = PrimHttp.getInstance();

        primCache = new PrimCache();

        baseUrl = primHttp.getBaseUrl();

        init(url);
    }

    private void init(String url) {
        connectTimeout = primHttp.getConnectionTimeout();
        readTimeout = primHttp.getReadTimeout();
        writeTimeout = primHttp.getWriteTimeout();

        if (primHttp.getCommonHeaders() != null) {
            mHeaders = primHttp.getCommonHeaders();
        } else {
            mHeaders = new HttpHeaders();
        }

        if (primHttp.getCommonParams() != null) {
            mParams = primHttp.getCommonParams();
        } else {
            mParams = new HttpParams();
        }

        if (primHttp.getCommonInterceptor() != null) {
            interceptors = primHttp.getCommonInterceptor();
        } else {
            interceptors = new ArrayList<>();
        }

        if (baseUrl == null && url != null && (url.startsWith("http://") || url.startsWith("https://"))) {
            httpUrl = HttpUrl.parse(url);
            if (httpUrl != null) {
                baseUrl = httpUrl.url().getProtocol() + "://" + httpUrl.url().getHost() + "/";
            }
        } else if (baseUrl != null && url != null && (url.startsWith("http://") || url.startsWith("https://"))) {
            httpUrl = HttpUrl.parse(url);
            if (httpUrl != null) {
                baseUrl = httpUrl.url().getProtocol() + "://" + httpUrl.url().getHost() + "/";
            }
        } else {
            if (!TextUtils.isEmpty(baseUrl)) {
                httpUrl = HttpUrl.parse(baseUrl);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public R baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        if (!TextUtils.isEmpty(baseUrl)) {
            httpUrl = HttpUrl.parse(baseUrl);
        }
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R tag(Object tag) {
        this.tag = tag;
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R url(String url) {
        this.url = url;
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R id(int id) {
        this.id = id;
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R cacheEnable(boolean enable) {
        this.cache = enable;
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R repeatCount(int count) {
        this.repeatCount = count;
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R repeatDuration(int duration) {
        this.repeatDuration = duration;
        return (R) this;
    }

    /**
     * 添加参数
     */
    @SuppressWarnings("unchecked")
    public R params(HttpParams params) {
        Utils.checkForceNotNull(params, "params == null");
        this.mParams.put(params);
        return (R) this;
    }

    /**
     * 添加参数
     */
    @SuppressWarnings("unchecked")
    public R params(String key, String value) {
        mParams.put(key, value);
        return (R) this;
    }

    /**
     * 删除某个参数
     */
    @SuppressWarnings("unchecked")
    public R removeParams(String key) {
        mParams.remove(key);
        return (R) this;
    }

    /**
     * 删除所有参数
     */
    @SuppressWarnings("unchecked")
    public R removeAllParams() {
        mParams.clear();
        return (R) this;
    }

    /**
     * 添加全局的请求头
     */
    public R headers(HttpHeaders headers) {
        this.mHeaders.put(headers);
        return (R) this;
    }

    /**
     * 添加全局公共参数
     */
    public R headers(String key, String value) {
        mHeaders.put(key, value);
        return (R) this;
    }

    /**
     * 删除某个请求头
     */
    @SuppressWarnings("unchecked")
    public R removeHeaders(String key) {
        mHeaders.remove(key);
        return (R) this;
    }

    /**
     * 删除所有请求头
     */
    @SuppressWarnings("unchecked")
    public R removeAllHeaders() {
        mHeaders.clear();
        return (R) this;
    }

    /**
     * 添加网络拦截器
     *
     * @param interceptor
     * @return
     */
    @SuppressWarnings("unchecked")
    public R addNetworkInterceptor(Interceptor interceptor) {
        if (interceptor == null) return (R) this;
        networkInterceptor = interceptor;
        return (R) this;
    }

    /**
     * 添加其他可一个或者多个拦截器
     *
     * @param interceptor
     * @return
     */
    @SuppressWarnings("unchecked")
    public R addInterceptor(Interceptor interceptor) {
        if (interceptor == null) return (R) this;
        interceptors.add(interceptor);
        return (R) this;
    }

    /**
     * 监听网络请求回调
     * 可自定义回调接口
     * 设置回调
     *
     * @param callback
     * @return
     */
    @SuppressWarnings("unchecked")
    public R setCallback(Callback<T> callback) {
        this.callback = callback;
        return (R) this;
    }

    /**
     * 获取参数
     */
    public HttpParams getParams() {
        return mParams;
    }

    /**
     * 获取请求头
     */
    public HttpHeaders getHeaders() {
        return mHeaders;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public long getRepeatDuration() {
        return repeatDuration;
    }

    public String getUrl() {
        return baseUrl + url;
    }

    public Object getTag() {
        return tag;
    }

    public int getId() {
        return id;
    }

    /**
     * 同步请求执行 阻塞方法 同步调用 在主线程中执行 一般不用此方法
     *
     * @return ResponseBody
     */
    public abstract T execute();

    /**
     * 异步调用 非阻塞方法 请求网络在子线程中执行，请求的回调在主线程中执行
     *
     * @param callback {@link Callback} 可自定义callback 回调，如：加载中弹窗等
     */
    public abstract void enqueue(Callback<T> callback);

    /**
     * 异步调用 非阻塞方法 请求网络在子线程中执行，请求的回调在主线程中执行
     * 没有回调
     */
    public abstract void enqueue();

    /**
     * 生成网络请求
     *
     * @return Observable 通过RxJava来处理网络请求
     */
    protected abstract Observable<ResponseBody> generateRequest();

    /**
     * 当前的请求类型
     *
     * @return {@link HttpMethod}
     */
    public abstract HttpMethod getHttpMethod();

    /**
     * 生成ApiService
     *
     * @return {@link ApiService}
     */
    protected ApiService generateService() {
        OkHttpClient.Builder okBuilder = generateOkHttpClient();
        Retrofit.Builder retrofitBuilder = generateRetrofit();
        OkHttpClient okHttpClient = okBuilder.build();
        retrofitBuilder.client(okHttpClient);
        Retrofit retrofit = retrofitBuilder.build();
        this.apiService = retrofit.create(ApiService.class);
        return apiService;
    }

    /**
     * 生成okHttp，将配置的参数：拦截器、超时设置进行设置
     *
     * @return OkHttpClient.Builder
     */
    private OkHttpClient.Builder generateOkHttpClient() {
        OkHttpClient.Builder okHttpBuilder = PrimHttp.getInstance().getOkHttpBuilder();
        if (null == okHttpBuilder) {
            okHttpBuilder = new OkHttpClient.Builder();
        }

        if (null != networkInterceptor) {
            okHttpBuilder.addNetworkInterceptor(networkInterceptor);
        }

        for (int i = 0; i < interceptors.size(); i++) {
            okHttpBuilder.addInterceptor(interceptors.get(i));
        }
        if (connectTimeout > 0) {
            okHttpBuilder.connectTimeout(connectTimeout, TimeUnit.MILLISECONDS);
        }

        if (readTimeout > 0) {
            okHttpBuilder.readTimeout(readTimeout, TimeUnit.MILLISECONDS);
        }

        if (writeTimeout > 0) {
            okHttpBuilder.writeTimeout(writeTimeout, TimeUnit.MILLISECONDS);
        }

        return okHttpBuilder;
    }

    /**
     * 生成Retrofit，设置baseURL
     *
     * @return Retrofit.Builder
     */
    private Retrofit.Builder generateRetrofit() {
        Retrofit.Builder retrofitBuilder = PrimHttp.getInstance().getRetrofitBuilder();
        if (!TextUtils.isEmpty(baseUrl)) {
            retrofitBuilder.baseUrl(baseUrl);
        }
        return retrofitBuilder;
    }

}
