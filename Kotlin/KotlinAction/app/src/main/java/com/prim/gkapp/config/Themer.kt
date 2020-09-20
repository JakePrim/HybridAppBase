package com.prim.gkapp.config

import android.app.Activity
import androidx.annotation.StyleRes
import com.prim.gkapp.R
import com.prim.gkapp.data.UserData
import com.prim.lib_base.utils.otherwise
import com.prim.lib_base.utils.yes

/**
 * @desc 改变主题
 * @author prim
 * @time 2019-06-27 - 09:39
 * @version 1.0.0
 */
//object 声明单例模式
object Themer {
    //定义theme 样式类型
    enum class ThemeMode(@StyleRes val themeStyle: Int, @StyleRes val translucent: Int) {
        DAY(R.style.AppTheme, R.style.AppTheme_Translucent),
        NIGHT(R.style.AppTheme_Dark, R.style.AppTheme_Dark_Translucent)
    }

    fun applyProperTheme(activity: Activity, translucent: Boolean = false) {
        //let 可以调用函数的任何方法 并返回一个对象
        activity.setTheme(currentTheme().let {
            translucent.yes {
                it.translucent
            }.otherwise {
                it.themeStyle
            }
        })
    }

    fun currentTheme() = ThemeMode.valueOf(UserData.themeMode)

    fun toggle(activity: Activity) {
        when (currentTheme()) {
            Themer.ThemeMode.DAY -> {
                UserData.themeMode = ThemeMode.NIGHT.name
            }
            Themer.ThemeMode.NIGHT -> {
                UserData.themeMode = ThemeMode.DAY.name
            }
        }
        activity.recreate()
    }
}