package cn.prim.http.lib_net.request;

import cn.prim.http.lib_net.callback.Callback;
import cn.prim.http.lib_net.model.HttpMethod;
import cn.prim.http.lib_net.request.base.NoBodyRequest;
import cn.prim.http.lib_net.utils.PrimHttpLog;
import io.reactivex.*;
import okhttp3.ResponseBody;

/**
 * @author prim
 * @version 1.0.0
 * @desc get方式请求网络
 * @time 2019/1/3 - 2:39 PM
 */
public class GetRequest<T> extends NoBodyRequest<T, GetRequest<T>> {
    private static final long serialVersionUID = 1284051499511650147L;

    public GetRequest(String url) {
        super(url);
    }

    private static final String TAG = "GetRequest";

    //同步请求
    @Override
    public T execute() {
        return generateExecute();
    }

    //异步请求 需要回调
    @Override
    public void enqueue(final Callback<T> callback) {
        PrimHttpLog.e(TAG, "enqueue:" + getUrl());
        generateEnqueue(callback);
    }

    //不需要回调
    @Override
    public void enqueue() {
        generateEnqueue(callback);
    }

    @Override
    protected Observable<ResponseBody> generateRequest() {
        //调用.get 就相当与调用了 Observable.create() 在create中请求网络
        return generateService().get(url, mParams.getCommonParams());
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.GET;
    }
}
