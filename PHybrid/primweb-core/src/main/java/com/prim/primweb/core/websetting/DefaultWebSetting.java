package com.prim.primweb.core.websetting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.webkit.WebSettings;

import com.prim.primweb.core.utils.PrimWebUtils;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/14 0014
 * 描    述：默认的web设置
 * 修订历史：
 * ================================================
 */
public class DefaultWebSetting extends BaseAgentWebSetting<WebSettings> {
    private static final String APP_CACAHE_DIRNAME = "/webcache";

    public DefaultWebSetting(Context context) {
        super(context);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void toSetting(WebSettings webSetting) {

        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            // 通过 file url 加载的 Javascript 读取其他的本地文件 .建议关闭
            webSetting.setAllowFileAccessFromFileURLs(false);
            // 允许通过 file url 加载的 Javascript 可以访问其他的源，包括其他的文件和 http，https 等其他的源
            webSetting.setAllowUniversalAccessFromFileURLs(false);
        }
        // 允许加载本地文件html  file协议
        webSetting.setAllowFileAccess(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        } else {
            webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }
        //设置自适应屏幕
        webSetting.setUseWideViewPort(true);
        // 缩放至屏幕的大小
        webSetting.setLoadWithOverviewMode(true);
        //设置是否支持缩放，false，默认为true。
        webSetting.setSupportZoom(false);
        //设置是否显示缩放工具，默认为false
        webSetting.setBuiltInZoomControls(false);
        //异步加载图片
        webSetting.setLoadsImagesAutomatically(true);
        // 是否阻塞加载网络图片  协议http or https
        webSetting.setBlockNetworkImage(false);
        //应用可以有数据库
        webSetting.setDatabaseEnabled(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setSupportMultipleWindows(false);
//        //设置数据库缓存路径
        webSetting.setDatabasePath(context.getCacheDir().getAbsolutePath() + APP_CACAHE_DIRNAME);
        //设置  Application Caches 缓存目录
        webSetting.setAppCachePath(context.getCacheDir().getAbsolutePath() + APP_CACAHE_DIRNAME);
        //设置定位的数据库路径
        webSetting.setGeolocationDatabasePath(context.getCacheDir().getAbsolutePath() + APP_CACAHE_DIRNAME);
        //false 禁止webview上面控件获取焦点(黄色边框)
        webSetting.setNeedInitialFocus(true);
        webSetting.setDefaultTextEncodingName("utf-8");//设置编码格式
        webSetting.setDefaultFontSize(16);
        webSetting.setMinimumFontSize(12);//设置 WebView 支持的最小字体大小，默认为 8
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
    }
}
