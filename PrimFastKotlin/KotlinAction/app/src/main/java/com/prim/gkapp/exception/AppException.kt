package com.prim.gkapp.exception

import com.prim.gkapp.network.entities.AuthResponse

/**
 * @desc
 * @author prim
 * @time 2019-05-30 - 23:12
 * @version 1.0.0
 */
object AppException {
    class AccountException(val response:AuthResponse):Exception("Already logged ")
}