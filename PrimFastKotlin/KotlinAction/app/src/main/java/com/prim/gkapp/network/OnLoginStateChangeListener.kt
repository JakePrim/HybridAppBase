package com.prim.gkapp.network

import com.prim.gkapp.network.entities.User

/**
 * @desc
 * @author prim
 * @time 2019-05-30 - 23:31
 * @version 1.0.0
 */
interface OnLoginStateChangeListener {
    fun login(user:User)

    fun logout()
}