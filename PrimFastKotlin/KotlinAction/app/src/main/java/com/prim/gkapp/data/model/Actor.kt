package com.prim.gkapp.data.model

import android.os.Parcelable
import com.prim.gkapp.anno.POKO
import kotlinx.android.parcel.Parcelize

@POKO
@Parcelize
data class Actor(val displayLogin: String = "",
                 val avatarUrl: String = "",
                 val id: Int = 0,
                 val login: String = "",
                 val gravatarId: String = "",
                 val url: String = ""): Parcelable