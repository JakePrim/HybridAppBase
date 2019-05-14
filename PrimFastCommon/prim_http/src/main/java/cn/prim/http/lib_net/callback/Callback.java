package cn.prim.http.lib_net.callback;

import cn.prim.http.lib_net.utils.Utils;
import okhttp3.RequestBody;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc 网络请求回调
 * @time 2019/1/2 - 4:28 PM
 */
public abstract class Callback<T> implements Converter<T> {
    public abstract void onStart();

    public abstract void onSuccess(T t);

    public abstract void onError();

    public abstract void onComplete();

    @Override
    public Type getType() {
        return Utils.findNeedClass(getClass());
    }

    public Type getRawType() {
        return Utils.findRawType(getClass());
    }
}
