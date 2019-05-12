package cn.prim.http.lib_net.parse;

import android.text.TextUtils;
import cn.prim.http.lib_net.model.Response;
import cn.prim.http.lib_net.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.ResponseBody;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc gson 解析 参考了RxEasyHttp
 * @time 2019-05-12 - 14:03
 */
public class GsonParse<T> implements IParse<T> {
    private Gson gson;

    public GsonParse() {
        gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .serializeNulls()
                .create();
    }

    @Override
    public Response<T> parseResponse(ResponseBody responseBody, Type type) {
        Response<T> response = new Response<>();
        response.setCode(-1);
        if (type instanceof ParameterizedType) {
            Class<T> cls = (Class<T>) ((ParameterizedType) type).getRawType();
            if (Response.class.isAssignableFrom(cls)) {
                final Type[] params = ((ParameterizedType) type).getActualTypeArguments();
                final Class clazz = Utils.getClass(params[0], 0);
                final Class rawType = Utils.getClass(type, 0);
                try {
                    String json = responseBody.string();
                    //增加是List<String>判断错误的问题
                    if (!List.class.isAssignableFrom(rawType) && clazz.equals(String.class)) {
                        response.setData((T) json);
                        response.setCode(0);
                    } else {
                        Response result = gson.fromJson(json, type);
                        if (result != null) {
                            response = result;
                        } else {
                            response.setMsg("json is null");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setMsg(e.getMessage());
                } finally {
                    responseBody.close();
                }
            } else {
                response.setMsg("ApiResult.class.isAssignableFrom(cls) err!!");
            }
        } else {
            try {
                final String json = responseBody.string();
                final Class<T> clazz = Utils.getClass(type, 0);
                if (clazz.equals(String.class)) {
                    final Response result = parseApiResult(json, response);
                    if (result != null) {
                        response = result;
                        response.setData((T) json);
                    } else {
                        response.setMsg("json is null");
                    }
                } else {
                    final Response result = parseApiResult(json, response);
                    if (result != null) {
                        response = result;
                        if (response.getData() != null) {
                            T data = gson.fromJson(response.getData().toString(), clazz);
                            response.setData(data);
                        } else {
                            response.setMsg("ApiResult's data is null");
                        }
                    } else {
                        response.setMsg("json is null");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                response.setMsg(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                response.setMsg(e.getMessage());
            } finally {
                responseBody.close();
            }
        }
        return response;
    }

    @Override
    public T parse(ResponseBody responseBody, Type type) {
        return null;
    }

    private Response parseApiResult(String json, Response apiResult) throws JSONException {
        if (TextUtils.isEmpty(json))
            return null;
        JSONObject jsonObject = new JSONObject(json);
        if (jsonObject.has("code")) {
            apiResult.setCode(jsonObject.getInt("code"));
        }
        if (jsonObject.has("data")) {
            apiResult.setData(jsonObject.getString("data"));
        }
        if (jsonObject.has("msg")) {
            apiResult.setMsg(jsonObject.getString("msg"));
        }
        return apiResult;
    }
}
