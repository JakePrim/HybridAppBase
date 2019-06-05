package com.prim.gkapp.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.prim.gkapp.R
import com.prim.gkapp.base.BaseActivity
import com.prim.gkapp.data.model.UserData
import com.prim.gkapp.ext.*
import com.prim.gkapp.ui.about.AboutFragment
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.app_bar_main2.*
import kotlinx.android.synthetic.main.nav_header_main2.*
import org.jetbrains.anko.imageResource

/**
 * 项目主页面
 * {@see MainPresenter}
 */
class MainActivity : BaseActivity<MainPresenter>(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        setSupportActionBar(toolbar)
        toolbar.title = ""
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        initDrawerLayout()
        initNavigation()
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
            UserData.isLogin().yes {
                UserData.currentUser?.let {
                    iv_avatar.loadImage(it.avatar_url, it.name)
                    tv_username.text = it.name
                    tv_email.text = it.email
                } ?: run {
                    iv_avatar.imageResource = R.mipmap.ic_launcher_round
                    tv_username.text = "请登录"
                    tv_email.visibility = View.GONE
                }
            }.otherwise {
                iv_avatar.imageResource = R.mipmap.ic_launcher_round
                tv_username.text = "请登录"
                tv_email.visibility = View.GONE
            }
        }
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
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
//                toolbar.title = "码乎.GitHub"
            }
            R.id.nav_about -> {
//                toolbar.title = "关于"
                showFragment(R.id.fl_content, AboutFragment::class.java, Bundle())
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

}
