package com.prim.gkapp.ui.repos

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prim.gkapp.data.model.Repository
import com.prim.gkapp.ui.common.CommonListFragment

/**
 * @desc 用户代码仓库Fragment
 * @author prim
 * @time 2019-06-11 - 09:47
 * @version 1.0.0
 */
class ReposFragment : CommonListFragment<Repository, ReposPresenter>() {
    override val adapter = ReposListAdapter()

    override val layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

    companion object {
        const val TAG = "ReposFragment"
    }
}