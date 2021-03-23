package com.prim.primweb.core.listener;

import android.view.View;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/14 0014
 * 描    述：WebView 的滚动监听
 * 修订历史：
 * ================================================
 */
public interface OnScrollChangeListener {
    void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY);
}
