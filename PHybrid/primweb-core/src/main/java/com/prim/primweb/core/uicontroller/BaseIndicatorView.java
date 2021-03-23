package com.prim.primweb.core.uicontroller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：6/20 0020
 * 描    述：进度条指示器的基类View
 * 修订历史：
 * ================================================
 */
public abstract class BaseIndicatorView extends FrameLayout implements IBaseIndicatorView {
    public BaseIndicatorView(@NonNull Context context) {
        super(context);
    }

    public BaseIndicatorView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseIndicatorView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void reset() {

    }

    @Override
    public void setProgress(int progress) {

    }
}
