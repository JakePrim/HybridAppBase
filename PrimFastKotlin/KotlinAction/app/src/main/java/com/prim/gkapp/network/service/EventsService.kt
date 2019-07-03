package com.prim.gkapp.network.service

import com.prim.gkapp.data.model.Events
import com.prim.gkapp.data.page.GithubPaging
import com.prim.gkapp.data.page.ListPage
import com.prim.gkapp.network.retrofit
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @desc
 * @author prim
 * @time 2019-07-03 - 10:59
 * @version 1.0.0
 */
interface EventsApi {
    @GET("/users/{owner}/received_events?type=all&sort=updated")
    fun getUserReceivedEvents(@Path("owner") owner: String,
                              @Query("page") page: Int = 1,
                              @Query("per_page") per_page: Int = 20): Observable<GithubPaging<Events>>

    @GET("/users/{owner}/events?type=all&sort=updated")
    fun getUserEvents(@Path("owner") owner: String,
                      @Query("page") page: Int = 1,
                      @Query("per_page") per_page: Int = 20): Observable<GithubPaging<Events>>

    @GET("/events")
    fun getPublicEvents(@Query("page") page: Int = 1,
                        @Query("q") q: String,
                        @Query("per_page") per_page: Int = ListPage.PAGE_SIZE): Observable<GithubPaging<Events>>
}

object EventsService : EventsApi by retrofit.create(EventsApi::class.java)