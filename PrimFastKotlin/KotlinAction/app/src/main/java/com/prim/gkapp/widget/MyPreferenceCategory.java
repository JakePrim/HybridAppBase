package com.prim.gkapp.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceViewHolder;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019-06-28 - 15:22
 */
public class MyPreferenceCategory extends PreferenceCategory {
    public MyPreferenceCategory(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public MyPreferenceCategory(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyPreferenceCategory(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyPreferenceCategory(Context context) {
        super(context);
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
    }
}
