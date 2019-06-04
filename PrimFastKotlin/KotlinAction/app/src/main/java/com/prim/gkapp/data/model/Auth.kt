package com.prim.gkapp.data.model

import android.os.Parcelable
import com.prim.gkapp.anno.POKO
import com.prim.gkapp.base.Config
import kotlinx.android.parcel.Parcelize

/**
 * @desc
 * @author prim
 * @time 2019-05-29 - 10:02
 * @version 1.0.0
 */

/**
 * auth request parameters data class
 */
@POKO
data class AuthBody(
    var client_secret: String = Config.Auth.CLIENT_SECRET,
    var scopes: List<String> = Config.Auth.SCOPES,
    var note: String = Config.Auth.NOTE,
    var note_url: String = Config.Auth.NOTE_URL
)

/**
 * auth request return response data class
 */
@POKO
@Parcelize
data class AuthResponse(
    var id: Int,
    var url: String,
    var token: String,
    var token_last_eight: String,
    var hashed_token: String,
    var note: String,
    var note_url: String,
    var updated_at: String,
    var created_at: String,
    var fingerprint: String,
    var scopes: List<String>,
    var app: App
) : Parcelable

@POKO
@Parcelize
data class App(var url: String, var name: String, var client_id: String) : Parcelable