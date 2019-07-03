package com.prim.gkapp.data.model

import android.os.Parcelable
import com.prim.gkapp.anno.POKO
import kotlinx.android.parcel.Parcelize


@POKO
@Parcelize
data class Repo(val name: String = "",
                val id: Int = 0,
                val url: String = ""): Parcelable