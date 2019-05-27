package com.prim.gkapp

import com.prim.gkapp.utils.Preference

/**
 * @desc
 * @author prim
 * @time 2019-05-26 - 10:48
 * @version 1.0.0
 */
class Setting {
    var email: String by Preference(AppContext, "email", "")
    var password: String by Preference(AppContext, "password", "")
}