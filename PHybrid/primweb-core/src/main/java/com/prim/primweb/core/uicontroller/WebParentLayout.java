package com.prim.primweb.core.uicontroller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.prim.primweb.core.R;
import com.prim.primweb.core.webview.IAgentWebView;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：6/19 0019
 * 描    述：webView 的父类
 * 修订历史：
 * ================================================
 */
public class WebParentLayout extends FrameLayout {

    private static final String TAG = "WebParentLayout";

    private View webView;

    private View errorView;

    private View loadView;

    private IAgentWebView agentWebView;

    private @LayoutRes
    int loadLayoutRes;

    private @LayoutRes
    int errorLayoutRes;

    private @IdRes
    int clickId = -1;

    private FrameLayout mErrorLayout = null;

    private FrameLayout mLoadLayout = null;

    public WebParentLayout(@NonNull Context context) {
        this(context, null);
    }

    public WebParentLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public WebParentLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        errorLayoutRes = R.layout.lib_web_error_page;
        loadLayoutRes = R.layout.lib_web_load_page;
    }

    public void showErrorPage() {
        if (mErrorLayout != null) {
            mErrorLayout.setVisibility(VISIBLE);
        } else {
            createErrorView();
        }
    }

    public void bindUIControll(AbsWebUIController absWebUIController) {
        absWebUIController.setWebParentLayout(this);
    }

    public void createErrorView() {
        FrameLayout mFrameLayout = new FrameLayout(getContext());
        mFrameLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        if (errorView == null) {
            LayoutInflater.from(getContext()).inflate(errorLayoutRes, mFrameLayout, true);
        } else {
            mFrameLayout.addView(errorView);
        }
        FrameLayout.LayoutParams lp = new LayoutParams(-1, -1);
        this.addView(this.mErrorLayout = mFrameLayout, lp);
        mFrameLayout.setVisibility(VISIBLE);
        if (clickId != -1) {
            View clickView = mFrameLayout.findViewById(clickId);
            clickView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (agentWebView != null) {
                        agentWebView.reloadAgent();
                    }
                }
            });
        }
        mFrameLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (agentWebView != null) {
                    agentWebView.reloadAgent();
                }
            }
        });
    }

    public void hideErrorPage() {
        if (mErrorLayout != null) {
            mErrorLayout.setVisibility(GONE);
        }
    }

    public void setErrorLayoutRes(@LayoutRes int layoutRes, @IdRes int clickId) {
        this.clickId = clickId;
        if (this.clickId <= 0) {
            this.clickId = -1;
        }
        this.errorLayoutRes = layoutRes;
        if (this.errorLayoutRes <= 0) {
            this.errorLayoutRes = R.layout.lib_web_error_page;
        }
    }

    public void setErrorLayoutRes(@LayoutRes int layoutRes) {
        this.errorLayoutRes = layoutRes;
    }

    public void setErrorView(View view) {
        errorView = view;
    }

    public void showLoading() {
        if (mLoadLayout != null) {
            mLoadLayout.setVisibility(VISIBLE);
        } else {
            createLoadView();
        }
    }

    public void createLoadView() {
        FrameLayout mFrameLayout = new FrameLayout(getContext());
        if (loadView == null) {
            LayoutInflater.from(getContext()).inflate(loadLayoutRes, mFrameLayout, true);
        } else {
            mFrameLayout.addView(loadView);
        }
        FrameLayout.LayoutParams lp = new LayoutParams(-1, -1);
        this.addView(this.mLoadLayout = mFrameLayout, lp);
    }

    public void hideLoading() {
        if (mLoadLayout != null) {
            mLoadLayout.setVisibility(GONE);
        }
    }

    public void setLoadView(View view) {
        this.loadView = view;
    }

    @SuppressLint("ResourceType")
    public void setLoadLayoutRes(@LayoutRes int layoutRes) {
        this.loadLayoutRes = layoutRes;
        if (layoutRes <= 0) {
            this.loadLayoutRes = R.layout.lib_web_load_page;
        }
    }

    public void bindWebView(IAgentWebView agentWebView) {
        this.agentWebView = agentWebView;
        webView = agentWebView.getAgentWebView();
    }
}
