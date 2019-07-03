package com.prim.gkapp.ui.home

import android.app.Activity
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.prim.gkapp.R.id
import com.prim.gkapp.ui.feeds.EventsFragment
import com.prim.gkapp.ui.feeds.IssuesFragment
import com.prim.gkapp.ui.repos.ReposFragment
import com.prim.gkapp.ui.settings.SettingsActivity

/**
 * @desc
 * @author prim
 * @time 2019-06-27 - 21:14
 * @version 1.0.0
 * @param groupId menu groupId;
 * @param type = 0 fragment;1 activity
 */
class NavItem private constructor(
    val groupId: Int = 0,
    val type: Int = 0,
    val title: String,
    val fragmentClass: Class<out Fragment>? = null,
    val activityClass: Class<out Activity>? = null,
    val arguments: Bundle = Bundle()
) {
    //定义静态变量
    companion object {
        //存储nav ID对应的NavItem
        private val items = mapOf(
            id.nav_feeds to NavItem(
                groupId = id.nav_menu2,
                type = 0, title = "Feeds",
                fragmentClass = EventsFragment::class.java
            ),
            id.nav_repositories to NavItem(
                groupId = id.nav_menu2,
                type = 0, title = "Repos",
                fragmentClass = ReposFragment::class.java
            ),
            id.nav_issues to NavItem(
                groupId = id.nav_menu2,
                type = 0, title = "Issues",
                fragmentClass = IssuesFragment::class.java
            ),
            id.nav_pull to NavItem(
                groupId = id.nav_menu2,
                type = 0, title = "Pull",
                fragmentClass = IssuesFragment::class.java
            ),
            id.nav_notification to NavItem(
                groupId = id.nav_menu3,
                type = 1, title = "Notification",
                fragmentClass = IssuesFragment::class.java
            ),
            id.nav_pinned to NavItem(
                groupId = id.nav_menu3,
                type = 0, title = "Pinned",
                fragmentClass = IssuesFragment::class.java
            ),
            id.nav_trending to NavItem(
                groupId = id.nav_menu3,
                type = 0, title = "Trending",
                fragmentClass = IssuesFragment::class.java
            ),
            id.nav_gists to NavItem(
                groupId = id.nav_menu3,
                type = 0, title = "Gists",
                fragmentClass = IssuesFragment::class.java
            ),
            id.nav_setting to NavItem(
                type = 1, title = "Settings",
                activityClass = SettingsActivity::class.java
            ),
            id.nav_about to NavItem(
                type = 1, title = "About",
                activityClass = SettingsActivity::class.java
            )
        )

        //重写get 操作符
        operator fun get(@IdRes navId: Int): NavItem {
            return items[navId] ?: items[id.nav_feeds]!!
        }

        operator fun get(item: NavItem): Int {
            return items.filter {
                it.value == item
            }.keys.first()
        }
    }

    override fun toString(): String {
        return super.toString()
    }
}