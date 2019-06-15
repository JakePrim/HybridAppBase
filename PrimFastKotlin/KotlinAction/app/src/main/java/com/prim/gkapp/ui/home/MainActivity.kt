package com.prim.gkapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.prim.gkapp.R
import com.prim.gkapp.data.UserData
import com.prim.gkapp.ext.loadImage
import com.prim.gkapp.network.service.RepositoryService
import com.prim.gkapp.ui.about.AboutFragment
import com.prim.lib_base.base.BaseActivity
import com.prim.lib_base.log.logger
import com.prim.lib_base.utils.*
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.app_bar_main2.*
import kotlinx.android.synthetic.main.include_nav_bottom_layout.*
import kotlinx.android.synthetic.main.include_nav_card_layout.*
import kotlinx.android.synthetic.main.nav_header_main2.*
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.sdk27.coroutines.onClick

/**
 * 项目主页面
 * {@see MainPresenter}
 */
class MainActivity : BaseActivity<MainPresenter>(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        initDrawerLayout()
        //设置测滑栏状态栏
        StatusBarUtil.setColorNoTranslucentForDrawerLayout(this, drawer_layout, this.getColorEx(R.color.colorPrimary))
        initNavigation()
        initToolBar()
        initActionButton()
        initListener()
        UserData.currentUser?.let {
            RepositoryService.listRepositoryOfUser(it.name, 2).subscribe({
                Log.e("listRepositoryOfUser", "hasNext:" + it.hasNext + " hasPer:" + it.hasPre + "")
            }, {
                it.printStackTrace()
            })
        }
        RepositoryService.searchRepository(2, "pushed:<" + java.util.Date().format("yyyy-MM-dd"))
            .subscribe({
                logger.debug("Pagging:hasNext:${it.paging.hasNext},hasPer:${it.paging.hasPre}")
                Log.e("searchRepository", "hasNext:" + it.paging.hasNext + " hasPer:" + it.paging.hasPre + "")
            }, {
                it.printStackTrace()
            })
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
        supportActionBar?.setDisplayShowTitleEnabled(false)
        //隐藏toolbar 默认显示的左侧图片
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        nav_menu.onClick {
            drawer_layout.openDrawer(GravityCompat.START)
        }
    }

    private fun initDrawerLayout() {
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun initNavigation() {
        nav_view.setNavigationItemSelectedListener(this)
        nav_view.doOnLayoutAvailable {
            //默认为菜单的第一个
            nav_view.setCheckedItem(R.id.nav_home)
            //nav view 判断是否初始化完毕
            UserData.isLogin().yes {
                UserData.currentUser?.let {
                    iv_avatar.loadImage(it.avatar_url, it.name)
                    tv_username.text = it.name
                    tv_email.text = it.email
                    tv_location.text = it.location
                    tv_blog.text = it.blog
                    tv_dio.text = it.bio
                    tv_followers.text = it.followers.toString()
                    tv_following.text = it.following.toString()
                    tv_repos.text = (it.public_repos + it.total_private_repos).toString()
                    tv_gists.text = (it.public_gists + it.private_gists).toString()
                } ?: run {
                    iv_avatar.imageResource = R.mipmap.ic_launcher
                    tv_username.text = "请登录"
                    ll_user_info.visibility = View.GONE
                }
            }.otherwise {
                iv_avatar.imageResource = R.mipmap.ic_launcher
                tv_username.text = "请登录"
                ll_user_info.visibility = View.GONE
            }
            ll_followers.onClick {
                drawer_layout.closeDrawer(GravityCompat.START)
            }

            ll_following.onClick {
                drawer_layout.closeDrawer(GravityCompat.START)
            }

            ll_gists.onClick {
                drawer_layout.closeDrawer(GravityCompat.START)
            }

            ll_repos.onClick {
                drawer_layout.closeDrawer(GravityCompat.START)
            }
        }
    }

    private fun initListener() {
        ll_setting.onClick {
            drawer_layout.closeDrawer(GravityCompat.START)
        }

        ll_close.onClick {
            finish()
            System.exit(0)
        }

        ll_model.onClick {
            //请求模式
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main2, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                showFragment(R.id.fl_content, HomeFragment::class.java, Bundle())
            }
            R.id.nav_about -> {
                showFragment(R.id.fl_content, AboutFragment::class.java, Bundle())
            }
            R.id.nav_dashboard -> {

            }
            R.id.nav_pull -> {

            }
            R.id.nav_issues -> {

            }
            R.id.nav_marketplace -> {

            }
            R.id.nav_explore -> {

            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onResume() {
        super.onResume()
        //更新通知

    }

}
