package cn.prim.http.lib_net.callback;

import java.lang.reflect.Type;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019/1/3 - 4:43 PM
 */
public interface Converter<T> {
    Type getType();
}
