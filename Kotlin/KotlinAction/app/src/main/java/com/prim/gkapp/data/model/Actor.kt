package com.prim.gkapp.data.model

import android.os.Parcelable
import com.prim.gkapp.anno.POKO
import kotlinx.android.parcel.Parcelize

@POKO
@Parcelize
data class Actor(val display_login: String = "",
                 val avatar_url: String = "",
                 val id: Int = 0,
                 val login: String = "",
                 val gravatar_id: String = "",
                 val url: String = ""): Parcelable