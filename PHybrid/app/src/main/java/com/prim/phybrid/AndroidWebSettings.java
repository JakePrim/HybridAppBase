package com.prim.phybrid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.webkit.WebSettings;
import com.prim.primweb.core.utils.PrimWebUtils;
import com.prim.primweb.core.websetting.BaseAgentWebSetting;


/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/18 0018
 * 描    述：需要设置 WebSettings 的类型
 * 修订历史：
 * ================================================
 */
public class AndroidWebSettings extends BaseAgentWebSetting<WebSettings> {

    private WebSettings webSettings;

    private static final String APP_CACAHE_DIRNAME = "/webcache";

    public AndroidWebSettings(Context context) {
        super(context);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void toSetting(WebSettings webSetting) {
        //        if (ObjectUtil.isNull(webSetting)) return;
        this.webSettings = webSetting;
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            //关闭跨域访问 可能存在跨域访问漏洞
            // 通过 file url 加载的 Javascript 读取其他的本地文件 .建议关闭
            webSetting.setAllowFileAccessFromFileURLs(false);
            // 允许通过 file url 加载的 Javascript 可以访问其他的源，包括其他的文件和 http，https 等其他的源
            webSetting.setAllowUniversalAccessFromFileURLs(false);
        }
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);//设置渲染的优先级
        //允许Js执行
        webSetting.setJavaScriptEnabled(true);
        // 允许加载本地文件html  file协议
        webSetting.setAllowFileAccess(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        } else {
            webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }
        int SDK_INT = Build.VERSION.SDK_INT;
        if (SDK_INT > 16) {
            //在SDK>16的版本 禁止了自动播放音频，需要手势才能播放，将这里改为false.允许自动播放音频
            webSetting.setMediaPlaybackRequiresUserGesture(false);
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            //5.0之后 WebView默认不加载Mixed Content的解决方案 修改为加载 这样做很危险 会加载不安全的资源 比如https 加载 http资源
//            webSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        }
        webSetting.setUseWideViewPort(true);
        webSetting.setLoadWithOverviewMode(true);
        //控制字体大小不随系统设置变化
        webSetting.setTextZoom(100);
        //不支持缩放
        webSetting.setSupportZoom(false);
        //异步加载图片
        webSetting.setLoadsImagesAutomatically(true);
        //是否阻塞加载网络图片  协议http or https 先不加载网络图片
        webSetting.setBlockNetworkImage(false);
        //应用可以有数据库
        webSetting.setDatabaseEnabled(true);
        //开启cache存储机制
        webSetting.setAppCacheEnabled(true);
        webSetting.setSupportMultipleWindows(false);
        //设置数据库缓存路径
        webSetting.setDatabasePath(context.getCacheDir().getAbsolutePath() + APP_CACAHE_DIRNAME);
        //设置  Application Caches 缓存目录
        webSetting.setAppCachePath(context.getCacheDir().getAbsolutePath() + APP_CACAHE_DIRNAME);
        //设置定位的数据库路径
        webSetting.setGeolocationDatabasePath(context.getCacheDir().getAbsolutePath() + APP_CACAHE_DIRNAME);
        //设置缓存大小
        webSetting.setAppCacheMaxSize(20 * 1024 * 1024);
        webSetting.setNeedInitialFocus(true);
        //设置编码格式
        webSetting.setDefaultTextEncodingName("utf-8");
        //设置 WebView 默认的字体大小
        webSetting.setDefaultFontSize(16);
        //设置 WebView 支持的最小字体大小，默认为 8
        webSetting.setMinimumFontSize(12);
        //启用地理定位
        webSetting.setGeolocationEnabled(true);
        //设置可以使用localStorage
        webSetting.setDomStorageEnabled(true);
        //关闭webview自动保存密码的功能
        webSetting.setSavePassword(false);
        if (PrimWebUtils.checkNetwork(context)) {
            //根据cache-control获取数据。
            webSetting.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            //没网，则从本地获取，即离线加载
            webSetting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        String systemUserAgent = webSetting.getUserAgentString();
        String customUserAgent = "DailyNewspaper/" + getVersion(0).toString() + "(Android; " + Build.VERSION.RELEASE + ";" + Build.MODEL + ")";
        String mergeUserAgent = systemUserAgent + ";" + customUserAgent;
//        String queryParams = "?" + "env=" + BuildInfo.UA_TAG;
//        mergeUserAgent = mergeUserAgent + queryParams;
        //设置userAgent
        webSetting.setUserAgentString(mergeUserAgent);
    }

    private Object getVersion(int type) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            if (type == 0) {
                return info.versionName;
            } else {
                return info.versionCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setAppCacheEnabled(boolean isEnabled) {
        if (webSettings != null) {
            webSettings.setAppCacheEnabled(isEnabled);
            if (isEnabled) {
                webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);// 默认使用缓存
            } else {
                webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);// 不使用缓存
            }
        }
    }
}
