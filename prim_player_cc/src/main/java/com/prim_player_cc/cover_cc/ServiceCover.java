package com.prim_player_cc.cover_cc;

import android.content.Context;
import android.view.View;

import com.prim_player_cc.stereotype.Service;

/**
 * @author prim
 * @version 1.0.0
 * @desc 所有到业务视图继承此类，不会被添加到view中 默默在底层处理业务逻辑
 * @time 2020/4/9 - 2:56 PM
 * @contact https://jakeprim.cn
 * @name PeopleDaily_Android_CN
 */
public class ServiceCover extends BaseCover {
    public ServiceCover(Context context) {
        super(context);
    }

    @Override
    protected View createCoverView(Context context) {
        return null;
    }

    @Override
    protected int[] setCoverLevel() {
        return new int[]{LEVEL_LOW, 0};
    }
}
