package com.prim.gkapp.data.model

import android.os.Parcelable
import com.prim.gkapp.anno.POKO
import kotlinx.android.parcel.Parcelize

@POKO
@Parcelize
data class Events(val actor: Actor,
                  val public: Boolean = false,
                  val payload: Payload,
                  val org: Org,
                  val repo: Repo,
                  val createdAt: String = "",
                  val id: String = "",
                  val type: String = ""): Parcelable