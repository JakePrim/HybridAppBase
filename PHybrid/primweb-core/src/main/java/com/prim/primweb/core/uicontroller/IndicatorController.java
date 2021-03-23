package com.prim.primweb.core.uicontroller;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：6/20 0020
 * 描    述：
 * 修订历史：
 * ================================================
 */
public interface IndicatorController {
    void show();

    void hide();

    void reset();

    void setProgress(int n);

    void progress(int newProgress);

    IBaseIndicatorView getIndicator();
}
