package com.prim.gkapp.data

import com.prim.gkapp.data.model.Repository
import com.prim.gkapp.data.model.User
import com.prim.gkapp.data.page.GithubPaging
import com.prim.gkapp.data.page.ListPage
import com.prim.gkapp.network.service.RepositoryService
import com.prim.lib_base.utils.format
import io.reactivex.Observable
import java.util.*

/**
 * @desc get repository data
 * @author prim
 * @time 2019-06-15 - 07:15
 * @version 1.0.0
 */
class RepositoryData(val owner: User? = null) : ListPage<Repository>() {
    override fun getData(page: Int): Observable<GithubPaging<Repository>> {
        return if (owner != null) {
            RepositoryService.listRepositoryOfUser(owner.login, page)
        } else {
            //没有用户就查询所有的仓库
            RepositoryService.searchRepository(page, "pushed:<" + Date().format("yyyy-MM-dd")).map { it.paging }
        }
    }
}