package com.prim.gkapp.ui.common

import android.util.Log
import com.prim.gkapp.data.page.ListPage
import com.prim.lib_base.mvp.impl.BasePresenter
import io.reactivex.disposables.Disposable

/**
 * @desc
 * @author prim
 * @time 2019-06-18 - 14:21
 * @version 1.0.0
 */
abstract class CommonListPresenter<T, out View : CommonListFragment<T, CommonListPresenter<T, View>>>
    : BasePresenter<View>() {
    //自动实现第一页和自动加载更多
    abstract val listPage: ListPage<T>

    private var listSubscription = ArrayList<Disposable>()

    companion object {
        const val TAG = "CommonListPresenter"
    }

    fun refreshData() {
        Log.e(TAG, "refreshData")
        listPage.fromInfrist().subscribe({
            if (it.isEmpty()) {
                view.onDataInitWithNothing()
            } else {
                view.onDataRefresh(it)
            }
        }, {
            view.onDataRefreshWithError("网络异常,稍后重试")
        }).let {
            listSubscription.add(it)
        }
    }

    fun initData() {
        Log.e(TAG, "initData")
        listPage.fromInfrist().subscribe({
            Log.e(TAG, "initData size" + it.size)
            if (it.isEmpty()) {
                view.onDataInitWithNothing()
            } else {
                view.onDataInit(it)
            }
        }, {
            view.onDataInitWithError("网络异常,稍后重试")
        }).let {
            listSubscription.add(it)
        }
    }

    fun loadMoreData() {
        Log.e(TAG, "initData")
        listPage.loadMore().subscribe({
            view.onDataWithMoreLoad(it)
        }, {
            view.onDataWithMoreLoadError("网络异常,稍后重试")
        }).let {
            listSubscription.add(it)
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        listSubscription.forEach {
            it.dispose()
        }
        listSubscription.clear()
    }
}