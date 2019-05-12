package cn.prim.http.lib_net.request.function;

import android.util.Log;
import cn.prim.http.lib_net.model.Response;
import cn.prim.http.lib_net.model.SimpleResponse;
import cn.prim.http.lib_net.parse.IParse;
import cn.prim.http.lib_net.utils.PrimHttpLog;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static android.content.ContentValues.TAG;

/**
 * @author prim
 * @version 1.0.0
 * @desc 解析网络请求的返回结果
 * @time 2019/1/3 - 4:17 PM
 */
public class ParseResponseFunction<T> implements Function<ResponseBody, Response<T>> {
    private Type type;

    private IParse<T> parse;

    public ParseResponseFunction(Type type, IParse<T> parse) {
        this.type = type;
        this.parse = parse;
    }

    private static final String TAG = "ParseResponseFunction";

    @Override
    public Response<T> apply(ResponseBody input) {
//        PrimHttpLog.e(TAG, "获取到的json数据：" + input.string());
        Response<T> response = parse.parseResponse(input, type);
        PrimHttpLog.e(TAG, "转换的json数据：" + response.toString());
        return response;
    }

//    private T parseClass(ResponseBody body, Class<?> rawType) throws Exception {
//        if (rawType == null) return null;
//        if (body == null) return null;
//        JsonReader jsonReader = new JsonReader(body.charStream());
//        if (rawType == String.class) {
//            //noinspection unchecked
//            return (T) body.string();
//        } else if (rawType == JSONObject.class) {
//            //noinspection unchecked
//            return (T) new JSONObject(body.string());
//        } else if (rawType == JSONArray.class) {
//            //noinspection unchecked
//            return (T) new JSONArray(body.string());
//        } else {
//            T t = fromJson(jsonReader, rawType);
//            body.close();
//            return t;
//        }
//    }
//
//    private T parseType(ResponseBody body, Type type) throws Exception {
//        if (type == null) return null;
//        if (body == null) return null;
//        JsonReader jsonReader = new JsonReader(body.charStream());
//        // 泛型格式如下： new JsonCallback<任意JavaBean>(this)
//        T t = fromJson(jsonReader, type);
//        body.close();
//        return t;
//    }


//    private T parseParameterizedType(ResponseBody input, ParameterizedType type) {
//        if (input == null) return null;
//        JsonReader jsonReader = new JsonReader(input.charStream());
//        Type rawType = type.getRawType();                     // 泛型的实际类型
//        Type typeArgument = type.getActualTypeArguments()[0]; // 泛型的参数
//        if (rawType != Response.class) {//不等于默认的返回类型
//            // 泛型格式如下： new JsonCallback<外层BaseBean<内层JavaBean>>(this)
//            T t = fromJson(jsonReader, type);
//            input.close();
//            PrimHttpLog.e(TAG, "parseParametrizedType: 0 -> " + t.toString());
//            return t;
//        } else {//等于默认的返回类型
//            if (typeArgument == Void.class) {
//                PrimHttpLog.e(TAG, "parseParametrizedType: 1");
//                // 泛型格式如下： new JsonCallback<LzyResponse<Void>>(this)
//                SimpleResponse simpleResponse = fromJson(jsonReader, SimpleResponse.class);
//                input.close();
//                //noinspection unchecked
//                return (T) simpleResponse.toResponse();
//            } else {
//                PrimHttpLog.e(TAG, "parseParametrizedType: 2");
//                // 泛型格式如下： new JsonCallback<LzyResponse<内层JavaBean>>(this)
//                Response lzyResponse = fromJson(jsonReader, type);
//                input.close();
////                int code = lzyResponse.getCode();
//                //这里的0是以下意思
//                //一般来说服务器会和客户端约定一个数表示成功，其余的表示失败，这里根据实际情况修改
////                if (code == 0) {
////                    //noinspection unchecked
////                    return (T) lzyResponse;
////                } else {
////                    //直接将服务端的错误信息抛出，onError中可以获取
////                    throw new IllegalStateException("错误代码：" + code + "，错误信息：" + lzyResponse.getMsg());
////                }
//            }
//        }
//    }

    public static <T> T fromJson(JsonReader reader, Type typeOfT) throws JsonIOException, JsonSyntaxException {
        Gson gson = new Gson();
        return gson.fromJson(reader, typeOfT);
    }
}
