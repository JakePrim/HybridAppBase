package com.prim.lib_base.view

import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

/**
 * @desc
 * @author prim
 * @time 2019-06-28 - 19:17
 * @version 1.0.0
 */
class BaseMoreAdapter private constructor(val adapter: Adapter<ViewHolder>? = null) {

    fun getAdapters(): Adapter<ViewHolder>? {
        return adapter
    }

    init {

    }


}