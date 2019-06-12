package com.prim.gkapp.data.model

import android.os.Parcelable
import com.prim.gkapp.anno.POKO
import kotlinx.android.parcel.Parcelize

/**
 * github repos data class
 */
@POKO
@Parcelize
data class Repository(
    var id: String,
    var note_id:String,
    var name:String,
    var full_name:String,
    var private:String,
    var html_url:String,
    var description:String,
    var fork:String,
    var language:String,
    var open_issues_count:Int,
    var open_issues:Int,
    var watchers:Int,
    var forks:Int
) : Parcelable