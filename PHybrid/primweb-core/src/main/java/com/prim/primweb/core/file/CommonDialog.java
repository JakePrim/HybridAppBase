package com.prim.primweb.core.file;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/18 0018
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CommonDialog extends Dialog {
    private View mView;
    private Activity context;
    private WindowManager.LayoutParams lp;
    private int gravity;

    public CommonDialog(CommonBuilder builder) {
        super(builder.context);
        context = (Activity) builder.context;
        mView = builder.view;
        gravity = builder.gravity;
    }

    public CommonDialog(CommonBuilder builder, int resStyle) {
        super(builder.context, resStyle);
        context = (Activity) builder.context;
        mView = builder.view;
        gravity = builder.gravity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mView);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        Window window = getWindow();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        window.getDecorView().setPadding(0, 0, 0, 0);
        lp = window.getAttributes();
        lp.gravity = gravity;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.width = getDisplaySize(context).widthPixels;
        window.setAttributes(lp);
    }

    public DisplayMetrics getDisplaySize(Activity act) {
        DisplayMetrics dm = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
    }
}
