package cn.prim.http.lib_net.interceptor;

import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;

/**
 * @author prim
 * @version 1.0.0
 * @desc 网络请求日志拦截器
 * @time 2019-05-06 - 18:56
 */
public class LogInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        return null;
    }
}
