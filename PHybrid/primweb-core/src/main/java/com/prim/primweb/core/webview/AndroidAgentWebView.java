package com.prim.primweb.core.webview;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.prim.primweb.core.jsloader.AgentValueCallback;
import com.prim.primweb.core.listener.OnDownloadListener;
import com.prim.primweb.core.utils.PrimWebUtils;
import com.prim.primweb.core.webview.detail.PrimScrollView;
import com.prim.primweb.core.webview.detail.WebViewTouchHelper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;


/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/11 0011
 * 描    述：代理webview
 * 修订历史：1.0.0
 * ================================================
 */
public class AndroidAgentWebView extends WebView implements IAgentWebView<WebSettings>, View.OnLongClickListener {

    private static final String TAG = "PrimAgentWebView";
    public com.prim.primweb.core.listener.OnScrollChangeListener listener;
    private WebView.HitTestResult result;
    private OnDownloadListener onDownloadListener;

    private WebViewTouchHelper helper;

    private boolean isTouched;

    private OnWebViewLongClick onWebViewLongClick;

    public AndroidAgentWebView(Context context) {
        this(context, null);
    }

    public AndroidAgentWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AndroidAgentWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AndroidAgentWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        removeRiskJavascriptInterface();
        setOnLongClickListener(this);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            setWebContentsDebuggingEnabled(true);
//        }
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
    public boolean onLongClick(View v) {
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
    public void setOnWebViewLongClick(OnWebViewLongClick onWebViewLongClick) {
        this.onWebViewLongClick = onWebViewLongClick;
    }

    @Override
    public void setOnLongClickListener(@Nullable View.OnLongClickListener l) {
        super.setOnLongClickListener(l);
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
    public void setOnScrollChangeListener(com.prim.primweb.core.listener.OnScrollChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        isTouched = ev.getAction() == MotionEvent.ACTION_DOWN || ev.getAction() == MotionEvent.ACTION_MOVE;
        return super.dispatchTouchEvent(ev);
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
    public void setAgentDownloadListener(OnDownloadListener onDownloadListener) {
        this.onDownloadListener = onDownloadListener;
    }

    @Override
    public void clearWeb() {
        this.stopLoading();
        this.loadUrl("about:blank");
        if (this.getHandler() != null) {
            this.getHandler().removeCallbacksAndMessages(null);
        }
        this.setWebChromeClient(null);
        this.setWebViewClient(null);
        this.setTag(null);
        this.clearCache(true);
        this.clearHistory();
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
        if (this.canGoBack()) {
            this.goBack();
            return true;
        }
        return false;
    }

    @Override
    public Object getAgentHitTestResult() {
        return this.getHitTestResult();
    }

    @Override
    public int getAgentHeight() {
        return getHeight();
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
        this.scrollTo(x, y);
    }

    @Override
    public void agentScrollBy(int x, int y) {
        this.scrollTo(x, y);
    }

    @Override
    public String getAgentUrl() {
        return this.getUrl();
    }

    @Override
    public void removeRiskJavascriptInterface() {
        //显式移除有风险的 Webview 系统隐藏接口
        this.removeJavascriptInterface("searchBoxJavaBridge_");
        this.removeJavascriptInterface("accessibility");
        this.removeJavascriptInterface("accessibilityTraversal");
    }

    @Override
    public void removeJavascriptInterfaceAgent(String name) {
        this.removeJavascriptInterface(name);
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

    @TargetApi(Build.VERSION_CODES.KITKAT)
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
        this.loadUrl(url);
    }

    @Override
    public void loadAgentUrl(String url, Map<String, String> headers) {
        this.loadUrl(url, headers);
    }

    @Override
    public void loadUrl(String url) {
        //是在调用webView.destroy() 之前调用了loadurl操作发生的，也不是毕现问题，所以只能跟进源码查看
        try {
            super.loadUrl(url);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
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
    public void setAgentWebViewClient(WebViewClient webViewClient) {
        this.setWebViewClient(webViewClient);
    }

    @Override
    public void setAgentWebViewClient(com.tencent.smtt.sdk.WebViewClient webViewClient) {

    }

    @Override
    public void setAgentWebChromeClient(WebChromeClient webChromeClient) {
        this.setWebChromeClient(webChromeClient);
    }

    @Override
    public void setAgentWebChromeClient(com.tencent.smtt.sdk.WebChromeClient webChromeClient) {

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public void destroy() {
        removeAllViewsInLayout();
        ViewParent parent = getParent();
        if (parent instanceof ViewGroup) {//从父容器中移除webview
            ((ViewGroup) parent).removeAllViewsInLayout();
        }
        releaseConfigCallback();
        super.destroy();
    }

    @Override
    public void clearHistory() {
        super.clearHistory();
    }

    @Override
    public void setWebViewClient(WebViewClient client) {
        super.setWebViewClient(client);
    }

    @SuppressLint("JavascriptInterface")
    @Override
    public void addJavascriptInterfaceAgent(Object object, String name) {
        this.addJavascriptInterface(object, name);
    }

    @Override
    protected void onCreateContextMenu(ContextMenu menu) {
        try {
//            menu.add("测试");
            Method emulateShiftHeld = getClass().getMethod("emulateShiftHeld", new Class[0]);
            emulateShiftHeld.setAccessible(true);
            emulateShiftHeld.invoke(this, (Object[]) null);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    // 解决WebView内存泄漏问题；参考AgentWeb X5 webview 解决了此问题，具体看x5webview源码
    private void releaseConfigCallback() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) { // JELLY_BEAN
            try {
                Field field = WebView.class.getDeclaredField("mWebViewCore");
                field = field.getType().getDeclaredField("mBrowserFrame");
                field = field.getType().getDeclaredField("sConfigCallback");
                field.setAccessible(true);
                field.set(null, null);
            } catch (NoSuchFieldException e) {
                if (PrimWebUtils.isDebug()) {
                    e.printStackTrace();
                }
            } catch (IllegalAccessException e) {
                if (PrimWebUtils.isDebug()) {
                    e.printStackTrace();
                }
            }
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) { // KITKAT
            try {
                Field sConfigCallback = Class.forName("android.webkit.BrowserFrame").getDeclaredField("sConfigCallback");
                if (sConfigCallback != null) {
                    sConfigCallback.setAccessible(true);
                    sConfigCallback.set(null, null);
                }
            } catch (NoSuchFieldException e) {
                if (PrimWebUtils.isDebug()) {
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                if (PrimWebUtils.isDebug()) {
                    e.printStackTrace();
                }
            } catch (IllegalAccessException e) {
                if (PrimWebUtils.isDebug()) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void setScrollView(PrimScrollView scrollView) {
        helper = new WebViewTouchHelper(scrollView, this);
    }

    @Override
    public void customScrollBy(int dy) {
        scrollBy(0, dy);
    }

    @Override
    public void customScrollTo(int toY) {
        scrollTo(0, toY);
    }

    @Override
    public int customGetContentHeight() {
        return (int) (getContentHeight() * getScale());
    }

    @Override
    public int customGetWebScrollY() {
        return getScrollY();
    }

    @Override
    public int customComputeVerticalScrollRange() {
        return super.computeVerticalScrollRange();
    }

    @Override
    public void startFling(int vy) {
        flingScroll(0, vy);
    }

    private PrimScrollView.OnScrollBarShowListener scrollBarShowListener;

    private PrimScrollView.OnScrollChangeListener scrollChangeListener;

    @Override
    public void setOnDetailScrollChangeListener(PrimScrollView.OnScrollChangeListener scrollChangeListener) {
        this.scrollChangeListener = scrollChangeListener;
    }

    @Override
    public void setOnScrollBarShowListener(PrimScrollView.OnScrollBarShowListener listener) {
        this.scrollBarShowListener = listener;
    }

    @Override
    public boolean canScrollVertically(int direction) {
        return super.canScrollVertically(direction);
    }

    @Override
    public boolean canScrollHorizontally(int direction) {
        return super.canScrollHorizontally(direction);
    }

    @Override
    public void setOverScrollMode(int mode) {
        //在创建 WebView 时崩溃，跟进栈信息，我们需要在 setOverScrollMode 方法上加异常保护处理
        try {
            super.setOverScrollMode(mode);
        } catch (Exception e) {
            String messageCause = e.getCause() == null ? e.toString() : e.getCause().toString();
            String trace = Log.getStackTraceString(e);
            if (trace.contains("android.content.pm.PackageManager$NameNotFoundException")
                    || trace.contains("java.lang.RuntimeException: Cannot load WebView")
                    || trace.contains("android.webkit.WebViewFactory$MissingWebViewPackageException: Failed to load WebView provider: No WebView installed")) {
                e.printStackTrace();
            } else {
                throw e;
            }
        }
    }

    @Override
    public void clearHistoryAgent() {
        this.loadUrl("about:blank");//about:blank
        this.clearHistory();//清除历史记录 但是会保留当前页面
        this.clearView();
    }
}
