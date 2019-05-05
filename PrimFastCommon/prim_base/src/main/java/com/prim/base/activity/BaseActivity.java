package com.prim.base.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * @author prim
 * @version 1.0.0
 * @desc All Activity Base class
 * @time 2019/2/1 - 11:37 AM
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected Context mContext;

    protected final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        perInitCreate();
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
    }

    /**
     * per onCreate before code
     */
    protected void perInitCreate() {

    }

    private void init(Bundle savedInstanceState) {
        this.mContext = this;
        setContentView(getLayoutRes());
    }

    /**
     * get current Activity layout res
     *
     * @return LayoutRes int
     */
    @LayoutRes
    protected abstract int getLayoutRes();

    @Override
    public void onClick(View v) {

    }
}
