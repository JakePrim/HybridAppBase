package cn.prim.http.lib_net.parse;

import cn.prim.http.lib_net.model.Response;
import okhttp3.ResponseBody;

import java.lang.reflect.Type;

/**
 * @author prim
 * @version 1.0.0
 * @desc 解析ResponseBody接口类，可自己实现此接口
 * @time 2019-05-12 - 14:00
 */
public interface IParse<T> {

    /**
     * 返回传递的类型
     * @param responseBody 网络请求返回的ResponseBody
     * @param type 解析的类型
     * @return Response
     */
    T parseResponse(ResponseBody responseBody, Type type);
}
