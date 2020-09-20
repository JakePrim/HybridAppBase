package com.prim.gkapp.data.page

import com.prim.gkapp.data.model.Events
import com.prim.gkapp.data.model.User
import com.prim.gkapp.network.service.EventsService
import com.prim.lib_base.utils.format
import io.reactivex.Observable
import java.util.*

/**
 * @desc
 * @author prim
 * @time 2019-07-03 - 11:08
 * @version 1.0.0
 */
class EventsData(private val owner: User? = null) : ListPage<Events>() {
    override fun getData(page: Int): Observable<GithubPaging<Events>> {
        return if (owner != null) {
            EventsService.getUserReceivedEvents(owner = owner.login, page = page)
        } else {
            EventsService.getPublicEvents(page, "pushed:<" + Date().format("yyyy-MM-dd"))
        }
    }
}