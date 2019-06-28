package com.prim.gkapp.ui.home

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.prim.gkapp.R
import com.prim.lib_base.utils.*

/**
 * @desc Navigation Controller
 * @author prim
 * @time 2019-06-27 - 21:05
 * @version 1.0.0
 */
class NavigationController(
    val activity: AppCompatActivity,
    val navigationView: NavigationView,
    val drawerLayout: DrawerLayout
) : NavigationView.OnNavigationItemSelectedListener {

    //navigation item 点击监听
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        navigationView.apply {
            val navItem = NavItem[item.itemId]
            drawerLayout.afterClose {
                (navItem.type == 0).yes {
                    navItem.fragmentClass?.let { activity.showFragment(R.id.fl_content, it, navItem.arguments) }
                }.otherwise {
                    navItem.activityClass?.let { activity.go(it) }
                }
            }
        }
        return true
    }

    //初始化 相当于在构造函数中初始化
    init {
        //设置监听
        navigationView.setNavigationItemSelectedListener(this)
    }

    fun initNavigation() {

    }

    private fun loginLayout() {

    }

    private fun logoutLayout() {

    }

}