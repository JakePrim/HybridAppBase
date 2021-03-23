package com.prim.primweb.core.uicontroller;

import android.util.Log;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：6/20 0020
 * 描    述：指示器的处理类
 * 修订历史：
 * ================================================
 */
public class IndicatorHandler implements IndicatorController {
    private static final IndicatorHandler INSTANCE = new IndicatorHandler();
    private IBaseIndicatorView baseIndicatorView;

    private static final String TAG = "IndicatorHandler";

    public static IndicatorHandler getInstance() {
        return INSTANCE;
    }

    public IndicatorHandler setIndicator(IBaseIndicatorView baseIndicatorView) {
        this.baseIndicatorView = baseIndicatorView;
        return this;
    }

    @Override
    public void show() {
        if (baseIndicatorView != null) {
            baseIndicatorView.show();
        }
    }

    @Override
    public void hide() {
        if (baseIndicatorView != null) {
            baseIndicatorView.hide();
        }
    }

    @Override
    public void reset() {
        if (baseIndicatorView != null) {
            baseIndicatorView.reset();
        }
    }

    @Override
    public void setProgress(int n) {
        if (baseIndicatorView != null) {
            baseIndicatorView.setProgress(n);
        }
    }

    @Override
    public void progress(int newProgress) {
        Log.e(TAG, "progress: " + newProgress);
        if (newProgress == 0) {
            reset();
        } else if (newProgress > 0 && newProgress <= 10) {
            show();
        } else if (newProgress > 10 && newProgress < 95) {
            setProgress(newProgress);
        } else {
            setProgress(newProgress);
            hide();
        }
    }

    @Override
    public IBaseIndicatorView getIndicator() {
        return baseIndicatorView;
    }
}
