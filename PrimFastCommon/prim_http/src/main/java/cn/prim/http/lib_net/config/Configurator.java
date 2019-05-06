package cn.prim.http.lib_net.config;

import cn.prim.http.lib_net.model.HttpHeaders;
import cn.prim.http.lib_net.model.HttpParams;
import cn.prim.http.lib_net.utils.PrimHttpLog;
import cn.prim.http.lib_net.utils.Utils;
import okhttp3.Interceptor;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author prim
 * @version 1.0.0
 * @desc 网络请求配置
 * @time 2018/12/29 - 2:09 PM
 */
public class Configurator {
    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    private static final HashMap<Object, Object> CONFIGS = new HashMap<>();

    private static final HttpHeaders HEADERS = new HttpHeaders();

    private static final HttpParams PARAMS = new HttpParams();

    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

    private static long CONNECT_TIME_OUT = 5000;
    private static long READ_TIME_OUT = 5000;
    private static long WRIT_TIME_OUT = 5000;

    public Configurator() {
        CONFIGS.put(ConfigKeys.CONFIG_READY, false);
    }

    /**
     * 获取所有的配置集合
     *
     * @return
     */
    public HashMap<Object, Object> getConfigMap() {
        return CONFIGS;
    }

    /**
     * 获取某一项的配置
     *
     * @param key 配置Key
     * @param <T> 配置类型
     * @return
     */
    public <T> T getConfiguration(Object key) {
        checkConfiguration();
        return (T) CONFIGS.get(key);
    }

    /**
     * 设置host也就是base URL
     *
     * @param host base URL
     * @return
     */
    public Configurator withHost(String host) {
        CONFIGS.put(ConfigKeys.API_HOST, host);
        return this;
    }

    /**
     * 设置是否显示日志
     *
     * @param is_log true 显示日志，false 不显示日志
     * @return
     */
    public Configurator enableLog(boolean is_log) {
        PrimHttpLog.IS_LOG = is_log;
        return this;
    }

    /**
     * 添加全局通用的拦截器
     *
     * @param interceptor {@link Interceptor}
     * @return
     */
    public Configurator addCommonInterceptor(Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        return this;
    }

    /**
     * 添加全局通用的请求头
     *
     * @param key    请求头类型
     * @param header 请求头的值
     * @return
     */
    public Configurator addCommonHeaders(String key, String header) {
        HEADERS.put(Utils.checkForceNotNull(key, "请求头类型不能为空或空字符串"), Utils.checkForceNotNull(header, "请求头的值不能为空或空字符串"));
        return this;
    }

    /**
     * 添加全局通用的请求头
     *
     * @param params {@link HttpHeaders} 请求头的包装类
     * @return
     */
    public Configurator addCommonHeaders(HttpHeaders params) {
        HEADERS.put(params);
        return this;
    }

    /**
     * 设置全局的链接超时
     *
     * @param time 时长 s
     * @return
     */
    public Configurator connectionTimeout(long time) {
        CONNECT_TIME_OUT = time;
        return this;
    }

    /**
     * 设置全局的读取超时
     *
     * @param time 时长 s
     * @return
     */
    public Configurator readTimeout(long time) {
        READ_TIME_OUT = time;
        return this;
    }

    /**
     * 设置全局的写入超时
     *
     * @param time 时长 s
     * @return
     */
    public Configurator writeTimeout(long time) {
        WRIT_TIME_OUT = time;
        return this;
    }


    /**
     * 添加通用的请求参数
     *
     * @param key   参数名称
     * @param value 参数值
     * @return
     */
    public Configurator addCommonParams(String key, String value) {
        PARAMS.put(Utils.checkForceNotNull(key, "参数名不能为空或空字符串"), Utils.checkForceNotNull(value, "参数值不能为空或空字符串"));
        return this;
    }

    /**
     * 添加全局通用请求参数
     *
     * @param params {@link HttpParams} 参数的包装类
     * @return
     */
    public Configurator addCommonParams(HttpParams params) {
        if (params == null) return this;
        PARAMS.put(params);
        return this;
    }

    /**
     * 配置完成
     */
    public void build() {
        CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        CONFIGS.put(ConfigKeys.HEADERS, HEADERS);
        CONFIGS.put(ConfigKeys.PARAMS, PARAMS);
        CONFIGS.put(ConfigKeys.CONNECT_TIME_OUT, CONNECT_TIME_OUT);
        CONFIGS.put(ConfigKeys.READ_TIME_OUT, READ_TIME_OUT);
        CONFIGS.put(ConfigKeys.WRITE_TIME_OUT, WRIT_TIME_OUT);
        CONFIGS.put(ConfigKeys.CONFIG_READY, true);
    }

    /**
     * 检查配置
     */
    private void checkConfiguration() {
        boolean isReady = (boolean) CONFIGS.get(ConfigKeys.CONFIG_READY);
        if (!isReady) {
            throw new RuntimeException("PrimHttp Configuration is not build,please call build() Complete configuration settings");
        }
    }
}
