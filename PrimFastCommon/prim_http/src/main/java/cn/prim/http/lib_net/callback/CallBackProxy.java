package cn.prim.http.lib_net.callback;

import cn.prim.http.lib_net.model.Response;
import cn.prim.http.lib_net.utils.Utils;
import com.google.gson.internal.$Gson$Types;
import okhttp3.ResponseBody;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @author prim
 * @version 1.0.0
 * @desc callback 代理类 用于自定义的Response 参考了RxEasyHttp
 * @time 2019-05-12 - 22:43
 */
public abstract class CallBackProxy<T extends Response<R>, R> implements Converter<T> {
    private Callback<R> mCallBack;

    public CallBackProxy(Callback<R> callback) {
        this.mCallBack = callback;
    }

    public Callback<R> getCallBack() {
        return mCallBack;
    }

    @Override
    public Type getType() {
        Type typeArguments = null;
        if (mCallBack != null) {
            Type rawType = mCallBack.getRawType();//如果用户的信息是返回List需单独处理
            if (List.class.isAssignableFrom(Utils.getClass(rawType, 0)) || Map.class.isAssignableFrom(Utils.getClass(rawType, 0))) {
                typeArguments = mCallBack.getType();
            }
//            else if (CacheResult.class.isAssignableFrom(Utils.getClass(rawType, 0))) {
//                Type type = mCallBack.getType();
//                typeArguments = Utils.getParameterizedType(type, 0);
//            }
            else {
                Type type = mCallBack.getType();
                typeArguments = Utils.getClass(type, 0);
            }
        }
        if (typeArguments == null) {
            typeArguments = ResponseBody.class;
        }
        Type rawType = Utils.findNeedType(getClass());
        if (rawType instanceof ParameterizedType) {
            rawType = ((ParameterizedType) rawType).getRawType();
        }
        return $Gson$Types.newParameterizedTypeWithOwner(null, rawType, typeArguments);
    }


}
