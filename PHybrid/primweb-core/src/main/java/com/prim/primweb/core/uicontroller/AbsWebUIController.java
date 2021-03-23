package com.prim.primweb.core.uicontroller;

import android.app.Activity;

import java.lang.ref.WeakReference;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：6/20 0020
 * 描    述：PrimWeb 的UI控制类
 * 修订历史：
 * ================================================
 */
public abstract class AbsWebUIController {
    private WeakReference<Activity> mActivity;

    protected AbsWebUIController mAbsWebUIController;

    private WebParentLayout webParentLayout;

    private static final String TAG = "AbsWebUIController";

    public AbsWebUIController(Activity activity) {
        mActivity = new WeakReference<Activity>(activity);
    }

    public synchronized void setWebParentLayout(WebParentLayout webParentLayout) {
        this.webParentLayout = webParentLayout;
        bindWebParentLayout(webParentLayout);
    }

    public abstract void bindWebParentLayout(WebParentLayout webParentLayout);

    /**
     * 从web页面前往其他应用 弹窗询问用户是否前往
     *
     * @param url
     *         地址
     */
    public abstract void onOpenOtherPage(String url);

    /**
     * 显示错误页面
     *
     * @param errorCode
     *         错误码
     * @param description
     *         错误信息
     * @param failingUrl
     *         错误url
     */
    public abstract void onShowErrorPage(int errorCode, String description, String failingUrl);

    /**
     * 隐藏错误界面
     */
    public abstract void onHideErrorPage();
}
