package com.prim.gkapp.ui.feeds

import com.prim.gkapp.data.UserData
import com.prim.gkapp.data.model.Events
import com.prim.gkapp.data.page.EventsData
import com.prim.gkapp.data.page.ListPage
import com.prim.gkapp.ui.common.CommonListPresenter

/**
 * @desc
 * @author prim
 * @time 2019-06-27 - 21:21
 * @version 1.0.0
 */
class EventsPresenter : CommonListPresenter<Events, EventsFragment>() {
    override val listPage: ListPage<Events> by lazy {
        EventsData(UserData.currentUser)
    }
}