package com.prim.gkapp.ui.feeds

import com.prim.gkapp.data.model.Repository
import com.prim.gkapp.data.page.ListPage
import com.prim.gkapp.ui.common.CommonListPresenter

/**
 * @desc
 * @author prim
 * @time 2019-06-27 - 21:21
 * @version 1.0.0
 */
class FeedsPresenter : CommonListPresenter<Repository, FeedsFragment>() {
    override val listPage: ListPage<Repository>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
}