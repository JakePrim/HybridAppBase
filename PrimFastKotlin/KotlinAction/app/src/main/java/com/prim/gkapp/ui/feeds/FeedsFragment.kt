package com.prim.gkapp.ui.feeds

import androidx.recyclerview.widget.RecyclerView
import com.prim.gkapp.data.model.Repository
import com.prim.gkapp.ui.common.CommonListAdapter
import com.prim.gkapp.ui.common.CommonListFragment

/**
 * @desc
 * @author prim
 * @time 2019-06-27 - 21:19
 * @version 1.0.0
 */
class FeedsFragment : CommonListFragment<Repository, FeedsPresenter>(){
    override val layoutManager: RecyclerView.LayoutManager
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val adapter: CommonListAdapter<Repository>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

}