package com.prim.primweb.core.file;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/18 0018
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CommonBuilder {
     public Context context;
    public View view;
    private int resStyle = -1;
    public boolean cancelTouchout;
    public int gravity = Gravity.CENTER;
    private final SparseArray<View> views;//将viewid 存储第两次弹出就不用在findViewById 浪费性能

    public CommonBuilder(Context context) {
        this.context = context;
        this.views = new SparseArray<>();
    }

    /**
     * 布局
     *
     * @param resView
     * @return
     */
    public CommonBuilder view(@LayoutRes int resView) {
        view = LayoutInflater.from(context).inflate(resView, null, false);
        return this;
    }

    /**
     * 弹出框的位置
     *
     * @param gravity
     * @return
     */
    public CommonBuilder gravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    /**
     * dialog样式
     *
     * @param resStyle
     * @return
     */
    public CommonBuilder style(int resStyle) {
        this.resStyle = resStyle;
        return this;
    }

    /**
     * 添加一个点击事件
     *
     * @param viewRes
     * @param listener
     * @return
     */
    public CommonBuilder addViewOnclick(@IdRes int viewRes, View.OnClickListener listener) {
        view.findViewById(viewRes).setOnClickListener(listener);
        return this;
    }

    /**
     * 触摸dialog外部是否可以取消
     *
     * @param val false 不可dismiss
     * @return
     */
    public CommonBuilder cancelTouchout(boolean val) {
        cancelTouchout = val;
        return this;
    }

    public CommonBuilder setPicture(@IdRes int viewRes, String text) {
        TextView textView = getViewId(viewRes);
        if (!TextUtils.isEmpty(text) && text.equals(FileType.WEB_IMAGE)) {
            textView.setText("拍照");
        } else if (!TextUtils.isEmpty(text) && text.equals(FileType.WEB_VIDEO)) {
            textView.setText("录像");
        }
        return this;
    }

    public CommonBuilder setSencoed(@IdRes int viewRes, String text) {
        TextView textView = getViewId(viewRes);
        if (!TextUtils.isEmpty(text) && text.equals(FileType.WEB_IMAGE)) {
            textView.setText("相册");
        } else if (!TextUtils.isEmpty(text) && text.equals(FileType.WEB_VIDEO)) {
            textView.setText("视频");
        }
        return this;
    }

    public CommonBuilder setReview(@IdRes int viewRes, @IdRes int viewRes2, boolean isReview) {
        getViewId(viewRes).setVisibility(isReview ? View.VISIBLE : View.GONE);
        if (isReview) {// 审核状态
            TextView textView = getViewId(viewRes2);
            textView.setText("好的");
        } else {//免审状态
            TextView textView = getViewId(viewRes2);
            textView.setText("查看预览");
        }
        return this;
    }

    public CommonBuilder setText(@IdRes int viewRes, @StringRes int strId) {
        TextView textView = getViewId(viewRes);
        textView.setText(strId);
        return this;
    }

    public CommonBuilder setText(@IdRes int viewRes, CharSequence value) {
        TextView textView = getViewId(viewRes);
        textView.setText(value);
        return this;
    }

    public CommonBuilder setTextColor(@IdRes int viewRes, @ColorInt int color) {
        TextView textView = getViewId(viewRes);
        textView.setTextColor(color);
        return this;
    }

    protected <T extends View> T getViewId(int viewId) {
        View viewT = views.get(viewId);
        if (viewT == null) {
            viewT = view.findViewById(viewId);
            views.put(viewId, viewT);
        }
        return (T) viewT;
    }

    public CommonDialog build() {
        if (resStyle != -1) {
            return new CommonDialog(this, resStyle);
        } else {
            return new CommonDialog(this);
        }
    }
}
