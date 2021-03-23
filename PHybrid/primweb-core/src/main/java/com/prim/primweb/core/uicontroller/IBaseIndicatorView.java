package com.prim.primweb.core.uicontroller;

import android.widget.FrameLayout;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：6/20 0020
 * 描    述：
 * 修订历史：
 * ================================================
 */
public interface IBaseIndicatorView {
    void show();

    void hide();

    void reset();

    void setProgress(int progress);

    FrameLayout.LayoutParams getIndicatorLayoutParams();
}
