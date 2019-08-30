package com.prim.lib_common_ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.prim.lib_common_ui.utils.StatusBarUtil;

/**
 * @author prim
 * @version 1.0.0
 * @desc BaseActivity
 * @time 2019-08-29 - 22:40
 */
public abstract class BaseActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode(this);
    }
}
