package com.prim.gkapp.ui

import android.os.Bundle
import com.gyf.immersionbar.ImmersionBar
import com.prim.gkapp.R
import com.prim.gkapp.config.Themer
import com.prim.lib_base.base.BaseActivity
import com.prim.lib_base.mvp.impl.BasePresenter
import com.prim.lib_base.utils.yes

/**
 * @desc
 * @author prim
 * @time 2019-06-28 - 15:42
 * @version 1.0.0
 */
abstract class ThemeActivity<P : BasePresenter<BaseActivity<P>>> : BaseActivity<P>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Themer.applyProperTheme(this)
        var barColor = R.color.colorPrimary
        (Themer.currentTheme() == Themer.ThemeMode.NIGHT).yes {
            barColor = R.color.colorPrimaryDark
        }
        ImmersionBar.with(this)
            .statusBarDarkFont(Themer.currentTheme() == Themer.ThemeMode.DAY)
            .statusBarColor(barColor)
            .init()
    }
}