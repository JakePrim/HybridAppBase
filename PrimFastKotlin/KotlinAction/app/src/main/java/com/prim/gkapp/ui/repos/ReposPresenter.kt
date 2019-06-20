package com.prim.gkapp.ui.repos

import com.prim.gkapp.data.RepositoryData
import com.prim.gkapp.data.UserData
import com.prim.gkapp.data.model.Repository
import com.prim.gkapp.data.page.ListPage
import com.prim.gkapp.ui.common.CommonListPresenter

/**
 * @desc
 * @author prim
 * @time 2019-06-11 - 09:47
 * @version 1.0.0
 */
class ReposPresenter : CommonListPresenter<Repository, ReposFragment>() {
    override val listPage: ListPage<Repository>
        get() = RepositoryData(UserData.currentUser)

}