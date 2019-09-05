package com.prim.music_app.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.prim.lib_common_ui.base.BaseActivity;
import com.prim.lib_common_ui.pager_indicator.ScaleTransitionPagerTitleView;
import com.prim.music_app.R;
import com.prim.music_app.config.CHANNEL;
import com.prim.music_app.ui.home.adapter.HomePageAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

/**
 * @author prim
 * @version 1.0.0
 * @desc 首页
 * @time 2019-08-29 - 22:38
 */
public class HomeActivity extends BaseActivity {

    //频道类型
    public static CHANNEL[] CHANNELS = new CHANNEL[]{
            CHANNEL.MY, CHANNEL.DISCORY, CHANNEL.FRIEND
    };

    private ImageView search_view;
    private ImageView menu_view;

    private MagicIndicator indicator_view;

    private ViewPager view_page;

    private HomePageAdapter pageAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_layout);
        initView();
        initData();
    }

    private void initView() {
        search_view = findViewById(R.id.search_view);
        menu_view = findViewById(R.id.menu_view);
        view_page = findViewById(R.id.view_page);
        pageAdapter = new HomePageAdapter(getSupportFragmentManager(), CHANNELS);
        view_page.setAdapter(pageAdapter);
        initIndicator();
    }

    private void initIndicator() {
        indicator_view = findViewById(R.id.indicator_view);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return CHANNELS == null ? 0 : CHANNELS.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ScaleTransitionPagerTitleView titleView = new ScaleTransitionPagerTitleView(context);
                titleView.setText(CHANNELS[index].getKey());
                titleView.setTextSize(19);
                titleView.setNormalColor(Color.parseColor("#999999"));
                titleView.setSelectedColor(Color.parseColor("#333333"));
                titleView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                titleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        view_page.setCurrentItem(index);
                    }
                });
                return titleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }

            @Override
            public float getTitleWeight(Context context, int index) {
                return 1.0f;
            }
        });
        indicator_view.setNavigator(commonNavigator);
        ViewPagerHelper.bind(indicator_view, view_page);
    }

    private void initData() {

    }
}
