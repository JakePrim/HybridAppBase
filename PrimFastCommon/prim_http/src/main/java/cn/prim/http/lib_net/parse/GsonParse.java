package cn.prim.http.lib_net.parse;

import cn.prim.http.lib_net.model.Response;
import cn.prim.http.lib_net.model.SimpleResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author prim
 * @version 1.0.0
 * @desc gson 解析 参考了RxEasyHttp
 * @time 2019-05-12 - 14:03
 */
public class GsonParse<T> implements IParse<T> {
    private Gson gson;

    private static final String TAG = "GsonParse";

    public GsonParse() {
        gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .serializeNulls()
                .create();
    }

    @Override
    public T parseResponse(ResponseBody responseBody, Type type) {
        if (responseBody == null) return null;
        if (type == null) {
            // 如果没有通过构造函数传进来，就自动解析父类泛型的真实类型（有局限性，继承后就无法解析到）
            Type genType = getClass().getGenericSuperclass();
            type = ((ParameterizedType) genType).getActualTypeArguments()[0];
        }
        if (type instanceof ParameterizedType) {//嵌套多个泛型
            return parseParameterizedType(responseBody, (ParameterizedType) type);
        } else if (type instanceof Class) {//一个泛型
            return parseClass(responseBody, (Class<?>) type);
        } else {//其他情况的处理
            return parseType(responseBody, type);
        }
    }

    private T parseType(ResponseBody responseBody, Type type) {
        if (type == null) {
            return null;
        }
        try {
            JsonReader jsonReader = new JsonReader(responseBody.charStream());
            return (T) gson.fromJson(jsonReader, type);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            responseBody.close();
        }
        return null;
    }

    private T parseClass(ResponseBody responseBody, Class<?> type) {
        if (type == null) {
            return null;
        }
        try {
            JsonReader jsonReader = new JsonReader(responseBody.charStream());
            if (type == String.class) {
                return (T) responseBody.string();
            } else if (type == JSONObject.class) {
                return (T) new JSONObject(responseBody.string());
            } else if (type == JSONArray.class) {
                return (T) new JSONArray(responseBody.string());
            } else {
                return (T) gson.fromJson(jsonReader, type);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            responseBody.close();
        }
        return null;
    }

    private T parseParameterizedType(ResponseBody responseBody, ParameterizedType type) {
        if (type == null) return null;
        JsonReader jsonReader = new JsonReader(responseBody.charStream());
        //获取泛型的实际类型
        Type rawType = type.getRawType();
        //获取泛型的参数<Response<Bean>> Bean 就是参数
        Type typeArgument = type.getActualTypeArguments()[0];
        if (rawType != Response.class) {//返回是<Bean<bean>>,而不是本库标准的嵌套类型比如<Response<Bean>>
            T result = gson.fromJson(jsonReader, type);
            responseBody.close();
            return result;
        } else {//本库标准的嵌套类型比如<Response<Bean>>
            if (typeArgument == Void.class) {//<Response<Void>>
                SimpleResponse response = gson.fromJson(jsonReader, SimpleResponse.class);
                responseBody.close();
                return (T) response.toResponse();
            } else {
                Response response = gson.fromJson(jsonReader, type);
                responseBody.close();
                return (T) response;
            }
        }
    }
}
