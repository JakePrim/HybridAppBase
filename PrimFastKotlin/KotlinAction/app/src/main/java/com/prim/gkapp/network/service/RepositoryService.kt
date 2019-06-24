package com.prim.gkapp.network.service

import com.prim.gkapp.data.model.Repository
import com.prim.gkapp.data.model.SearchRepos
import com.prim.gkapp.data.page.GithubPaging
import com.prim.gkapp.network.retrofit
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @desc 仓库相关接口
 * @author prim
 * @time 2019-06-10 - 16:45
 * @version 1.0.0
 */
interface RepositoryApi {
    @GET("/users/{owner}/repos?type=all&sort=updated")
    fun listRepositoryOfUser(
        @Path("owner") owner: String,
        @Query("page") page: Int = 1,
        @Query("per_page") per_page: Int = 20
    ): Observable<GithubPaging<Repository>>

    @GET("/search/repositories?order=desc&sort=updated")
    fun searchRepository(@Query("page") page: Int = 1, @Query("q") q: String, @Query("per_page") per_page: Int = 20): Observable<SearchRepos>
}

//单例
object RepositoryService : RepositoryApi by retrofit.create(RepositoryApi::class.java)

