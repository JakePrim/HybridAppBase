package com.prim.gkapp.data.model

import com.prim.gkapp.AppContext
import com.prim.gkapp.utils.Preference

/**
 * @desc 记录用户信息
 * @author prim
 * @time 2019-05-26 - 10:48
 * @version 1.0.0
 */
class UserInfo {
    var email: String by Preference(AppContext, "email", "")
    var password: String by Preference(AppContext, "password", "")
}