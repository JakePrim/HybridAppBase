package com.prim.gkapp.ui.home

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.google.android.material.snackbar.Snackbar
import com.prim.gkapp.R
import com.prim.gkapp.config.Themer
import com.prim.gkapp.data.UserData
import com.prim.gkapp.ui.ThemeActivity
import com.prim.gkapp.ui.feeds.EventsFragment
import com.prim.lib_base.utils.showFragment
import com.prim.lib_base.utils.yes
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_layout.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.include_nav_bottom_layout.*
import org.jetbrains.anko.sdk27.coroutines.onClick

/**
 * 项目主页面
 * {@see MainPresenter}
 */
class MainActivity : ThemeActivity<MainPresenter>() {

    /**
     * 在使用时才初始化NavigationController
     */
    private val navigationController by lazy {
        NavigationController(this, nav_view, drawer_layout)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initDrawerLayout()
        initToolBar()
        initActionButton()
        initListener()
        initData()
    }

    private fun initData() {
        UserData.isLogin().yes {
            UserData.currentUser?.let {
                //                nav_iv_avatar.loadImage(it.avatar_url, it.login)
            }
        }
    }

    private fun initActionButton() {
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    private fun initToolBar() {
        setSupportActionBar(toolbar)
        //隐藏toolbar 默认显示的title
        supportActionBar?.setDisplayShowTitleEnabled(true)
        //隐藏toolbar 默认显示的左侧图片
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //设置NavigationIcon的点击事件
        toolbar.setNavigationOnClickListener {
            drawer_layout.openDrawer(GravityCompat.START)
        }
    }

    private fun initDrawerLayout() {
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        navigationController.initNavigation()
        showFragment(R.id.fl_content, EventsFragment::class.java, Bundle())
    }

    private fun initListener() {
        ll_setting.onClick {
            drawer_layout.closeDrawer(GravityCompat.START)
        }

        ll_close.onClick {
            finish()
            System.exit(0)
        }

        ll_model.setOnClickListener {
            Themer.toggle(this)
        }

//        nav_bar.onClick {
//
//        }
//
//        nav_iv_avatar.onClick {
//
//        }
//
//        nav_bar.onClick {
//
//        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        //更新通知

    }

}
