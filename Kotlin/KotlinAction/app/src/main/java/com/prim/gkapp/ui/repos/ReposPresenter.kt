package com.prim.gkapp.ui.repos

import com.prim.gkapp.data.RepositoryData
import com.prim.gkapp.data.UserData
import com.prim.gkapp.data.model.Repository
import com.prim.gkapp.ui.common.CommonListPresenter

/**
 * @desc
 * @author prim
 * @time 2019-06-11 - 09:47
 * @version 1.0.0
 */
class ReposPresenter : CommonListPresenter<Repository, ReposFragment>() {
    override val listPage by lazy {
        //注意此处在用到的时候 才进行初始化 不要在get 方法中每次初始化
        RepositoryData(UserData.currentUser)
    }

}