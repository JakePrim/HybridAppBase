package com.prim.primweb.core.uicontroller;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.prim.primweb.core.R;
import com.prim.primweb.core.utils.PrimWebUtils;
import com.prim.primweb.core.webview.AndroidAgentWebView;
import com.prim.primweb.core.webview.IAgentWebView;

import java.lang.ref.WeakReference;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/14 0014
 * 描    述：webView 的所有view的管理器
 * 修订历史：
 * ================================================
 */
public class WebViewManager implements IWebViewManager {
    private View mWebView;

    private FrameLayout mFrameLayout;

    private IAgentWebView agentWebView;

    private ViewGroup mViewGroup;

    private ViewGroup.LayoutParams lp;

    private int index;

    private boolean needTopIndicator;

    private boolean customTopIndicator;

    private View errorView;

    private View loadView;

    private BaseIndicatorView mIndicatorView;

    private WeakReference<Activity> mActivity;

    private @ColorInt
    int mColor;

    private String colorPaser;

    private int height;

    private @LayoutRes
    int errorLayout = 0;

    private @IdRes
    int errorClickId = 0;

    private @LayoutRes
    int loadLayout = 0;

    private IBaseIndicatorView baseIndicatorView;

    private AbsWebUIController absWebUIController;

    public WebViewManager(Builder builder) {
        this.agentWebView = builder.agentWebView;
        this.mViewGroup = builder.mViewGroup;
        this.index = builder.index;
        this.mActivity = new WeakReference<>(builder.mActivity);
        this.errorView = builder.errorView;
        this.loadView = builder.loadView;
        this.mColor = builder.mColor;
        this.colorPaser = builder.colorPaser;
        this.height = builder.height;
        this.lp = builder.lp;
        this.errorLayout = builder.errorLayout;
        this.errorClickId = builder.errorClickId;
        this.loadLayout = builder.loadLayout;
        this.needTopIndicator = builder.needTopProgress;
        this.customTopIndicator = builder.customTopIndicator;
        this.mIndicatorView = builder.mIndicatorView;
        this.absWebUIController = builder.absWebUIController;
        if (agentWebView != null) {
            this.mWebView = agentWebView.getAgentWebView();
        }
        create();
    }

    public static Builder createWebView() {
        return new Builder();
    }

    public static class Builder {
        private IAgentWebView agentWebView;
        private ViewGroup mViewGroup;
        private int index;
        private Activity mActivity;
        private View errorView;
        private View loadView;
        private boolean needTopProgress;
        private @ColorInt
        int mColor;
        private String colorPaser;
        private int height;
        private ViewGroup.LayoutParams lp;
        private @LayoutRes
        int errorLayout;
        private @IdRes
        int errorClickId;
        private @LayoutRes
        int loadLayout;
        private boolean customTopIndicator;
        private BaseIndicatorView mIndicatorView;
        private AbsWebUIController absWebUIController;

        public Builder setAbsWebUIController(AbsWebUIController absWebUIController) {
            this.absWebUIController = absWebUIController;
            return this;
        }

        public Builder setIndicatorView(BaseIndicatorView mIndicatorView) {
            this.mIndicatorView = mIndicatorView;
            return this;
        }

        public Builder setCustomTopIndicator(boolean customTopIndicator) {
            this.customTopIndicator = customTopIndicator;
            return this;
        }

        public Builder setErrorLayout(int errorLayout) {
            this.errorLayout = errorLayout;
            return this;
        }

        public Builder setErrorClickId(int errorClickId) {
            this.errorClickId = errorClickId;
            return this;
        }

        public Builder setLoadLayout(int loadLayout) {
            this.loadLayout = loadLayout;
            return this;
        }

        public Builder setLp(ViewGroup.LayoutParams lp) {
            this.lp = lp;
            return this;
        }

        public Builder setAgentWebView(IAgentWebView agentWebView) {
            this.agentWebView = agentWebView;
            return this;
        }

        public Builder setViewGroup(ViewGroup mViewGroup) {
            this.mViewGroup = mViewGroup;
            return this;
        }

        public Builder setIndex(int index) {
            this.index = index;
            return this;
        }

        public Builder setActivity(Activity mActivity) {
            this.mActivity = mActivity;
            return this;
        }

        public Builder setErrorView(View errorView) {
            this.errorView = errorView;
            return this;
        }

        public Builder setLoadView(View loadView) {
            this.loadView = loadView;
            return this;
        }

        public Builder setNeedTopProgress(boolean needTopProgress) {
            this.needTopProgress = needTopProgress;
            return this;
        }

        public Builder setColor(@ColorInt int mColor) {
            this.mColor = mColor;
            return this;
        }

        public Builder setColorPaser(@NonNull String colorPaser) {
            this.colorPaser = colorPaser;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public WebViewManager build() {
            return new WebViewManager(this);
        }
    }

    @Override
    public View getWebView() {
        return mWebView;
    }

    @Override
    public FrameLayout getWebParentView() {
        return mFrameLayout;
    }

    @Override
    public IWebViewManager create() {
        if (mActivity == null || mActivity.get() == null) {
            throw new NullPointerException("mActivity most not to be null!");
        }
        ViewGroup mViewGroup = this.mViewGroup;
        if (mViewGroup == null) {
            mViewGroup = mFrameLayout = createLayout();
            PrimWebUtils.scanForActivity(mActivity.get()).setContentView(mViewGroup);
        } else {
            if (index != -1) {
                mViewGroup.addView(this.mFrameLayout = createLayout(), index, lp);
            } else {
                mViewGroup.addView(this.mFrameLayout = createLayout(), lp);
            }
        }
        return this;
    }

    private FrameLayout createLayout() {
        WebParentLayout webParentLayout = new WebParentLayout(mActivity.get());
        webParentLayout.bindUIControll(absWebUIController);
        webParentLayout.setBackgroundColor(Color.WHITE);
        webParentLayout.setId(R.id.web_parent_layout);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        webParentLayout.addView(webView(), layoutParams);
        webParentLayout.bindWebView(agentWebView);
        if (errorView != null) {
            webParentLayout.setErrorView(errorView);
        }
        webParentLayout.setErrorLayoutRes(errorLayout, errorClickId);
        webParentLayout.createErrorView();
        webParentLayout.hideErrorPage();
        if (loadView != null) {
            webParentLayout.setLoadView(loadView);
        }
        webParentLayout.setLoadLayoutRes(loadLayout);
        webParentLayout.createLoadView();
        webParentLayout.hideLoading();
        if (needTopIndicator) {//设置显示顶部指示器
            if (!customTopIndicator) {//使用默认的指示器
                FrameLayout.LayoutParams layoutParams2 = null;
                WebProgressIndicatorView indicatorView = new WebProgressIndicatorView(mActivity.get());
                if (height > 0) {
                    layoutParams2 = new FrameLayout.LayoutParams(-2, PrimWebUtils.dp2px(mActivity.get(), height));
                } else {
                    layoutParams2 = indicatorView.getIndicatorLayoutParams();
                }
                if (mColor != -1) {
                    indicatorView.setIndicatorColor(mColor);
                } else if (!TextUtils.isEmpty(colorPaser)) {
                    indicatorView.setIndicatorColor(colorPaser);
                }
                layoutParams2.gravity = Gravity.TOP;
                baseIndicatorView = indicatorView;
                webParentLayout.addView(indicatorView, layoutParams2);
                indicatorView.setVisibility(View.GONE);
            } else if (mIndicatorView != null) {//使用自定义的指示器
                webParentLayout.addView(mIndicatorView, mIndicatorView.getIndicatorLayoutParams());
                baseIndicatorView = mIndicatorView;
                mIndicatorView.setVisibility(View.GONE);
            }
        }
        return webParentLayout;
    }

    private View webView() {
        View webView = null;
        if (this.mWebView != null) {
            ViewGroup parentViewGroup = (ViewGroup) mWebView.getParent();
            if (parentViewGroup != null) {
                parentViewGroup.removeView(mWebView);
            }
            webView = mWebView;
        } else {
            webView = new AndroidAgentWebView(mActivity.get());
        }
        return webView;
    }

    @Override
    public IAgentWebView getAgentWeb() {
        return agentWebView;
    }

    @Override
    public IBaseIndicatorView getIndicator() {
        return baseIndicatorView;
    }
}
