package com.prim.primweb.core.webview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ActionMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.prim.primweb.core.jsloader.AgentValueCallback;
import com.prim.primweb.core.listener.OnDownloadListener;
import com.prim.primweb.core.utils.PWLog;
import com.prim.primweb.core.utils.PrimWebUtils;
import com.prim.primweb.core.webview.detail.PrimScrollView;
import com.prim.primweb.core.webview.detail.WebViewTouchHelper;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.lang.reflect.Method;
import java.util.Map;


/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/14 0014
 * 描    述：代理腾讯x5的webView
 * 修订历史：
 * ================================================
 */
public class X5AgentWebView extends WebView implements IAgentWebView<WebSettings> {
    private com.prim.primweb.core.listener.OnScrollChangeListener listener;

    private static final String TAG = "X5AgentWebView";

    private WebView.HitTestResult result;

    private WebViewTouchHelper helper;

    private boolean isTouched;

    private OnDownloadListener onDownloadListener;

    private OnWebViewLongClick onWebViewLongClick;

    public X5AgentWebView(Context context) {
        this(context, null);
    }

    public X5AgentWebView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, -1);
    }

    public X5AgentWebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        getView().setVerticalScrollBarEnabled(false);
        getView().setOverScrollMode(OVER_SCROLL_NEVER);

        setOnLongClickListener(this);
    }

    @Override
    public void setDownloadListener(DownloadListener downloadListener) {
        DownloadListener downloadListener1 = new DownloadListener() {
            @Override
            public void onDownloadStart(String s, String s1, String s2, String s3, long l) {
                if (onDownloadListener != null) {
                    onDownloadListener.onDownloadStart(s, s1, s2, s3, l);
                }
            }
        };
        super.setDownloadListener(downloadListener1);
    }

    @Override
    public void setOnWebViewLongClick(OnWebViewLongClick onWebViewLongClick) {
        this.onWebViewLongClick = onWebViewLongClick;
    }


    @Override
    public boolean onLongClick(View view) {
        result = this.getHitTestResult();
        if (null == result)
            return false;
        int type = result.getType();
        if (type == WebView.HitTestResult.IMAGE_TYPE || type == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
            final String saveImgUrl = result.getExtra();
            if (onWebViewLongClick != null) {
                return onWebViewLongClick.onClickWebImage(saveImgUrl);
            }
            return false;
        }
        if (onWebViewLongClick != null) {
            return onWebViewLongClick.onClick(type, result);
        }
        return false;
    }

    @Override
    public void setAgentDownloadListener(OnDownloadListener onDownloadListener) {
        this.onDownloadListener = onDownloadListener;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        isTouched = ev.getAction() == MotionEvent.ACTION_DOWN || ev.getAction() == MotionEvent.ACTION_MOVE;
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void removeJavascriptInterfaceAgent(String name) {
        this.removeJavascriptInterface(name);
    }

    @Override
    public void removeRiskJavascriptInterface() {
        //显式移除有风险的 Webview 系统隐藏接口
        this.removeJavascriptInterface("searchBoxJavaBridge_");
        this.removeJavascriptInterface("accessibility");
        this.removeJavascriptInterface("accessibilityTraversal");
    }

    @Override
    public Object getAgentHitTestResult() {
        return this.getHitTestResult();
    }

    @Override
    public int getAgentHeight() {
        return getView().getHeight();
    }

    @Override
    public int getAgentContentHeight() {
        return getContentHeight();
    }

    @Override
    public float getAgentScale() {
        return getScale();
    }

    @Override
    public void agentScrollTo(int x, int y) {
        getView().scrollTo(x, y);
    }

    @Override
    public void agentScrollBy(int x, int y) {
        getView().scrollBy(x, y);
    }

    @Override
    public String getAgentUrl() {
        return this.getUrl();
    }

    /**
     * 使用Chrome DevTools 远程调试WebView
     */
    @TargetApi(19)
    @Override
    public void setWebChromeDebuggingEnabled() {
        if (PrimWebUtils.isDebug() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class<?> clazz = WebView.class;
                Method method = clazz.getMethod("setWebContentsDebuggingEnabled", boolean.class);
                method.invoke(null, true);
            } catch (Throwable e) {
                if (PrimWebUtils.isDebug()) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public View getAgentWebView() {
        return this;
    }

    @Override
    public WebSettings getWebSetting() {
        return this.getSettings();
    }

    @Override
    public void loadAgentJs(String js) {
        this.loadUrl(js);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void loadAgentJs(String js, final AgentValueCallback<String> callback) {
        ValueCallback<String> valueCallback = new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String o) {
                if (null != callback) {
                    callback.onReceiveValue(o);
                }
            }
        };
        this.evaluateJavascript(js, valueCallback);
    }

    @Override
    public void loadAgentUrl(String url) {
        PWLog.d("X5内核加载地址:" + url);
        this.loadUrl(url);
    }

    @Override
    public void reloadAgent() {
        this.reload();
    }

    @Override
    public void stopLoadingAgent() {
        this.stopLoading();
    }

    @Override
    public void postUrlAgent(String url, byte[] params) {
        this.postUrl(url, params);
    }

    @Override
    public void loadDataAgent(String data, String mimeType, String encoding) {
        this.loadData(data, mimeType, encoding);
    }

    @Override
    public void loadDataWithBaseURLAgent(String baseUrl, String data, String mimeType, String encoding, String historyUrl) {
        this.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl);
    }

    @Override
    public void addJavascriptInterfaceAgent(Object object, String name) {
        Log.e(TAG, "addJavascriptInterfaceAgent: name --> " + name + "| object --> " + object.getClass().getSimpleName());
        this.addJavascriptInterface(object, name);
    }

    @Override
    public void setAgentWebViewClient(android.webkit.WebViewClient webViewClient) {

    }

    @Override
    public void setAgentWebViewClient(WebViewClient webViewClient) {
        this.setWebViewClient(webViewClient);
    }

    @Override
    public void setAgentWebChromeClient(android.webkit.WebChromeClient webChromeClient) {

    }


    @Override
    public void setAgentWebChromeClient(WebChromeClient webChromeClient) {
        this.setWebChromeClient(webChromeClient);
    }

    @Override
    public void loadAgentUrl(String url, Map headers) {
        this.loadUrl(url, headers);
    }

    @Override
    public void setOnScrollChangeListener(com.prim.primweb.core.listener.OnScrollChangeListener listener) {
        this.listener = listener;
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (null != listener) {
            listener.onScrollChange(this, l, t, oldl, oldt);
        }
        if (scrollBarShowListener != null) {
            scrollBarShowListener.onShow();
        }
        int deltaY = t - oldt;
        int height = getHeight() - getPaddingTop() - getPaddingBottom();
        if (helper != null) {
            helper.overScrollBy(deltaY, oldt, computeVerticalScrollRange() - height, isTouched);//计算速度
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (helper == null) {
            return super.onTouchEvent(e);
        }
        if (!helper.onTouchEvent(e)) {
            return false;
        }
        return super.onTouchEvent(e);
    }


    @Override
    public ActionMode startActionMode(ActionMode.Callback callback) {
        PWLog.e("startActionMode");
        return super.startActionMode(callback);
    }

    @Override
    public ActionMode startActionMode(ActionMode.Callback callback, int type) {
        PWLog.e("startActionMode");
        return super.startActionMode(callback, type);
    }

    @Override
    public ActionMode startActionModeForChild(View originalView, ActionMode.Callback callback) {
        PWLog.e("startActionModeForChild");
        return super.startActionModeForChild(originalView, callback);
    }

    @Override
    public ActionMode startActionModeForChild(View originalView, ActionMode.Callback callback, int type) {
        return super.startActionModeForChild(originalView, callback, type);
    }

    @Override
    public void destroy() {
        removeAllViewsInLayout();
        ViewParent parent = getParent();
        if (parent instanceof ViewGroup) {//从父容器中移除webview
            ((ViewGroup) parent).removeAllViewsInLayout();
        }
        super.destroy();
    }

    @Override
    public void onAgentResume() {
        if (Build.VERSION.SDK_INT >= 11) {
            this.onResume();
        }
        this.resumeTimers();
    }

    @Override
    public void onAgentPause() {
        if (Build.VERSION.SDK_INT >= 11) {
            this.onPause();
        }
        this.pauseTimers();
    }

    @Override
    public void onAgentDestory() {
        this.resumeTimers();
        if (Looper.myLooper() != Looper.getMainLooper()) {
            return;
        }
        this.loadUrl("about:blank");
        this.stopLoading();
        if (this.getHandler() != null) {
            this.getHandler().removeCallbacksAndMessages(null);
        }
        this.removeAllViews();
        ViewGroup mViewGroup = null;
        if ((mViewGroup = ((ViewGroup) this.getParent())) != null) {
            mViewGroup.removeView(this);
        }
        this.setWebChromeClient(null);
        this.setWebViewClient(null);
        this.setTag(null);
        this.clearHistory();
        this.destroy();
    }

    @Override
    public boolean goBackAgent() {
        if (this.canGoBack() && !getUrl().equals("about:blank")) {
            this.goBack();
            return true;
        }
        return false;
    }

    @Override
    public void setScrollView(PrimScrollView scrollView) {
        helper = new WebViewTouchHelper(scrollView, this);
    }

    @Override
    public void customScrollBy(int dy) {
        getView().scrollBy(0, dy);
    }

    @Override
    public void customScrollTo(int toY) {
        getView().scrollTo(0, toY);
    }

    @Override
    public int customGetContentHeight() {
        return (int) (getContentHeight() * getScale());
    }

    @Override
    public int customGetWebScrollY() {
        return getWebScrollY();
    }

    @Override
    public int customComputeVerticalScrollRange() {
        return super.computeVerticalScrollRange();
    }

    @Override
    public void startFling(int vy) {
        flingScroll(0, vy);
    }

    private PrimScrollView.OnScrollChangeListener scrollChangeListener;

    @Override
    public void setOnDetailScrollChangeListener(PrimScrollView.OnScrollChangeListener scrollChangeListener) {
        this.scrollChangeListener = scrollChangeListener;
    }

    private PrimScrollView.OnScrollBarShowListener scrollBarShowListener;

    @Override
    public void setOnScrollBarShowListener(PrimScrollView.OnScrollBarShowListener listener) {
        this.scrollBarShowListener = listener;
    }

//    @Override
//    public boolean canScrollVertically(int direction) {
//        return super.canScrollVertically(direction);///direction = -1 表示能否向下滚动 = 1 表示能否向上滚动
//    }

    @Override
    public boolean canScrollVertically(int direction) {
        final int offset = getWebScrollY();
        final int range = computeVerticalScrollRange() - computeVerticalScrollExtent();
        if (range == 0) return false;
        if (direction < 0) {
            return offset > 0;
        } else {
            return offset < range - 1;
        }
    }

    @Override
    public boolean canScrollHorizontally(int direction) {
        return super.canScrollHorizontally(direction);
    }

    @Override
    public void clearWeb() {
        PWLog.e("Web-Log - clearWeb");
        this.loadUrl("about:blank");//about:blank
        this.stopLoading();
        if (this.getHandler() != null) {
            this.getHandler().removeCallbacksAndMessages(null);
        }
        this.setWebChromeClient(null);
        this.setWebViewClient(null);
        this.setTag(null);
        this.clearCache(true);
        this.clearHistory();
        PWLog.e("Web-Log - clearWeb url:" + getUrl());
    }

    @Override
    public void clearHistoryAgent() {
        this.loadUrl("about:blank");//about:blank
        clearHistory();//清除历史记录 但是会保留当前页面
        clearView();
    }
}
