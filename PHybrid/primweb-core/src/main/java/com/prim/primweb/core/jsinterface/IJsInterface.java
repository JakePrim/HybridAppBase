package com.prim.primweb.core.jsinterface;

import java.util.Map;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/15 0015
 * 描    述：代理注入js的接口
 * 修订历史：
 * ================================================
 */
public interface IJsInterface {
    IJsInterface addJavaObjects(Map<String, Object> maps);

    IJsInterface addJavaObject(Object o, String name);

    IJsInterface removeJavaObject(String name);

    boolean checkJsInterface(Object o);
}
