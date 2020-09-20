package com.prim.gkapp.data.model

import android.os.Parcelable
import com.prim.gkapp.anno.POKO
import kotlinx.android.parcel.Parcelize

@POKO
@Parcelize
data class Payload(val action: String = "",
                   val description: String = "",
                   val forkee: Repository) : Parcelable