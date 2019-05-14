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
public class ParseResponseFunction<T> implements Function<ResponseBody, T> {
    private Type type;

    private IParse<T> parse;

    public ParseResponseFunction(Type type, IParse<T> parse) {
        this.type = type;
        this.parse = parse;
    }

    private static final String TAG = "ParseResponseFunction";

    @Override
    public T apply(ResponseBody input) {
        T response = parse.parseResponse(input, type);
        PrimHttpLog.e(TAG, "转换的json数据：" + response.toString());
        return response;
    }
}
