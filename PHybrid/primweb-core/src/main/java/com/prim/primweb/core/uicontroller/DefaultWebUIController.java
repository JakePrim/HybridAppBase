package com.prim.primweb.core.uicontroller;

import android.app.Activity;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：6/20 0020
 * 描    述：默认实现的UIController
 * 修订历史：
 * ================================================
 */
public class DefaultWebUIController extends AbsWebUIController {
    private WebParentLayout webParentLayout;

    public DefaultWebUIController(Activity activity) {
        super(activity);
    }

    @Override
    public void bindWebParentLayout(WebParentLayout webParentLayout) {
        this.webParentLayout = webParentLayout;
    }

    @Override
    public void onOpenOtherPage(String url) {

    }

    @Override
    public void onShowErrorPage(int errorCode, String description, String failingUrl) {
        if (webParentLayout != null) {
            webParentLayout.showErrorPage();
        }
    }

    @Override
    public void onHideErrorPage() {
        if (webParentLayout != null) {
            webParentLayout.hideErrorPage();
        }
    }
}
