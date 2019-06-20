package com.prim.gkapp.ui.repos

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.prim.gkapp.R
import com.prim.gkapp.data.model.Repository
import com.prim.gkapp.ui.common.CommonListAdapter
import kotlinx.android.synthetic.main.item_repos_layout.view.*

/**
 * @desc
 * @author prim
 * @time 2019-06-19 - 07:06
 * @version 1.0.0
 */
class ReposListAdapter : CommonListAdapter<Repository>(R.layout.item_repos_layout) {
    override fun onBindItem(holder: RecyclerView.ViewHolder, item: Repository) {
        holder.itemView.apply {
            item_title.text = item.name
            item_dec.text = item.description
        }
    }

    override fun onItemClick(view: View, item: Repository) {
    }
}