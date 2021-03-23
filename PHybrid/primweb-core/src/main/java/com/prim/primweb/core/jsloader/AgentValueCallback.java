package com.prim.primweb.core.jsloader;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/14 0014
 * 描    述：将ValueCallBack 代理出来
 * 修订历史：
 * ================================================
 */
public interface AgentValueCallback<T> {
     void onReceiveValue(T value);
}
