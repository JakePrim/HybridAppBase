package cn.prim.http.lib_net.request.helper;

import okhttp3.HttpUrl;

import java.lang.reflect.Field;

/**
 * 动态切换 baseUrl
 */
public class HttpUrlHelper {
    private static final Field hostField;

    static {
        Field field = null;
        try {
            field = HttpUrl.class.getDeclaredField("host");
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        hostField = field;
    }

    private HttpUrl httpUrl;

    public HttpUrlHelper(HttpUrl httpUrl) {
        this.httpUrl = httpUrl;
    }

    public void setHost(String url){
        try {
            hostField.set(httpUrl,url);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
