package com.prim.lib_common_ui.pager_indicator;

import android.content.Context;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

/**
 * @author prim
 * @version 1.0.0
 * @desc 带颜色渐变和缩放带指示器标题
 * @time 2019-09-04 - 18:00
 */
public class ScaleTransitionPagerTitleView extends ColorTransitionPagerTitleView {
    private float mMinScale = 0.9f;

    public ScaleTransitionPagerTitleView(Context context) {
        super(context);
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        super.onEnter(index, totalCount, enterPercent, leftToRight);
        setScaleX(mMinScale + (1.f - mMinScale) * enterPercent);
        setScaleY(mMinScale + (1.f - mMinScale) * enterPercent);
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        super.onLeave(index, totalCount, leavePercent, leftToRight);
        setScaleX(1.f + (mMinScale - 1.f) * leavePercent);
        setScaleY(1.f + (mMinScale - 1.f) * leavePercent);
    }

    public float getMinScale() {
        return mMinScale;
    }

    public void setMinScale(float mMinScale) {
        this.mMinScale = mMinScale;
    }
}
