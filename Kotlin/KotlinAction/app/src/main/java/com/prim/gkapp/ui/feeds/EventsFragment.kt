package com.prim.gkapp.ui.feeds

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prim.gkapp.data.model.Events
import com.prim.gkapp.ui.common.CommonListFragment

/**
 * @desc
 * @author prim
 * @time 2019-06-27 - 21:19
 * @version 1.0.0
 */
class EventsFragment : CommonListFragment<Events, EventsPresenter>() {
    override val layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
    override val adapter = EventsListAdapter()
}