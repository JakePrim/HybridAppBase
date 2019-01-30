package cn.prim.http.lib_net.request;

import android.text.TextUtils;
import cn.prim.http.lib_net.PrimHttp;
import cn.prim.http.lib_net.callback.Callback;
import cn.prim.http.lib_net.client.retrofit.ApiService;
import cn.prim.http.lib_net.model.HttpHeaders;
import cn.prim.http.lib_net.model.HttpParams;
import cn.prim.http.lib_net.utils.Utils;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
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

    //当前请求网络的tag
    protected Object tag;
    //当前请求网络的唯一ID
    protected int id;

    protected List<Interceptor> interceptors;

    protected Interceptor networkInterceptor;

    protected transient ApiService apiService;

    protected transient Callback<T> callback;

    private long connectTimeout;

    private long readTimeout;

    private long writeTimeout;

    public BaseRequest(String url) {
        this.url = url;
        primHttp = PrimHttp.getInstance();

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

    /**
     * 添加参数
     */
    @SuppressWarnings("unchecked")
    public R params(HttpParams params) {
        Utils.checkNotNull(params, "params == null");
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

    @SuppressWarnings("unchecked")
    public R addNetworkInterceptor(Interceptor interceptor) {
        networkInterceptor = interceptor;
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R setCallback(Callback<T> callback) {
        this.callback = callback;
        return (R) this;
    }

    public String getUrl() {
        return url;
    }

    public Object getTag() {
        return tag;
    }

    /**
     * 同步请求执行 阻塞方法 同步调用
     *
     * @return
     */
    public abstract ResponseBody execute();

    /**
     * 异步调用 非阻塞方法 回调会在子线程中执行
     *
     * @param callback
     */
    public abstract void enqueue(Callback<T> callback);

    protected abstract Observable<ResponseBody> generateRequest();

    protected ApiService generateService() {
        OkHttpClient.Builder okBuilder = generateOkHttpClient();
        Retrofit.Builder retrofitBuilder = generateRetrofit();
        OkHttpClient okHttpClient = okBuilder.build();
        retrofitBuilder.client(okHttpClient);
        Retrofit retrofit = retrofitBuilder.build();
        this.apiService = retrofit.create(ApiService.class);
        return apiService;
    }


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

    private Retrofit.Builder generateRetrofit() {
        Retrofit.Builder retrofitBuilder = PrimHttp.getInstance().getRetrofitBuilder();
        if (!TextUtils.isEmpty(baseUrl)) {
            retrofitBuilder.baseUrl(baseUrl);
        }
        return retrofitBuilder;
    }

}
