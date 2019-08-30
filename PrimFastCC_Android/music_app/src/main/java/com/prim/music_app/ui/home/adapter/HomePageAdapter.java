package com.prim.music_app.ui.home.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.prim.music_app.config.CHANNEL;
import com.prim.music_app.ui.discory.DiscoryFragment;
import com.prim.music_app.ui.friend.FriendFragment;
import com.prim.music_app.ui.mine.MineFragment;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019-08-30 - 06:34
 */
public class HomePageAdapter extends FragmentPagerAdapter {
    private CHANNEL[] channels;


    public HomePageAdapter(FragmentManager fm, CHANNEL[] channels) {
        super(fm);
        this.channels = channels;
    }

    @Override
    public Fragment getItem(int i) {
        int value = channels[i].getValue();
        switch (value) {
            case CHANNEL.MY_ID:
                return MineFragment.newInstance();
            case CHANNEL.DISCORY_ID:
                return DiscoryFragment.newInstance();
            case CHANNEL.FRIEND_ID:
                return FriendFragment.newInstance();
        }
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return channels[position].getKey();
    }

    @Override
    public int getCount() {
        return channels == null ? 0 : channels.length;
    }
}
