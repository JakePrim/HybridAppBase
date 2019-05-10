package cn.prim.http.lib_net.request;

import cn.prim.http.lib_net.callback.Callback;
import cn.prim.http.lib_net.model.HttpMethod;
import cn.prim.http.lib_net.request.function.ParseResponseFunction;
import cn.prim.http.lib_net.request.observer.CallbackObserver;
import cn.prim.http.lib_net.utils.SchedulersUtils;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * @author prim
 * @version 1.0.0
 * @desc put方式请求网络
 * @time 2019-05-06 - 17:45
 */
public class PutRequest<T> extends BodyRequest<T, PutRequest<T>> {
    public PutRequest(String url) {
        super(url);
    }

    @Override
    public T execute() {
        return generateExecute();
    }

    @Override
    public void enqueue(Callback<T> callback) {
        generateEnqueue(callback);
    }

    @Override
    public void enqueue() {
        generateEnqueue(callback);
    }

    @Override
    protected Observable<ResponseBody> generateRequest() {
        return generateService().put(url, mParams.getCommonParams());
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.PUT;
    }
}
