package com.prim.primweb.core.handler;

import android.view.KeyEvent;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：6/14 0014
 * 描    述：webView 中的返回键的处理
 * 修订历史：
 * ================================================
 */
public interface IKeyEvent {
    boolean onKeyDown(int keyCode, KeyEvent event);

    boolean back();
}
