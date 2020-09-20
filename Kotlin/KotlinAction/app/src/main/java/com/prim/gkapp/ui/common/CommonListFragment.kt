package com.prim.gkapp.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.prim.gkapp.R
import com.prim.gkapp.data.page.GithubPaging
import com.prim.lib_base.base.BaseFragment
import com.prim.lib_base.utils.no
import com.prim.lib_base.utils.otherwise
import com.prim.lib_base.view.PromptInfoView
import kotlinx.android.synthetic.main.common_list_layout.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.toast

/**
 * @desc common list fragment
 * @param T data type
 * @param Presenter extend CommonListPresenter
 * @author prim
 * @time 2019-06-18 - 14:22
 * @version 1.0.0
 */
abstract class CommonListFragment<T, out Presenter : CommonListPresenter<T, CommonListFragment<T, Presenter>>>
    : BaseFragment<Presenter>(), OnLoadMoreListener {

    //adapter extend CommonListAdapter<T> abstract
    protected abstract val adapter: CommonListAdapter<T>

    protected abstract val layoutManager: RecyclerView.LayoutManager

    protected open val itemAnimator: RecyclerView.ItemAnimator = DefaultItemAnimator()

    companion object {
        const val TAG = "CommonListFragment"
    }

    //info prompt view
    protected val promptView by lazy {
        PromptInfoView(rootView)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.common_list_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //设置刷新view
        refreshLayout.setColorSchemeResources(
                R.color.google_red,
                R.color.google_blue, R.color.google_green, R.color.google_yellow
        )
        adapter.setOnLoadMoreListeners(this)
        //默认自动刷新
        refreshLayout.isRefreshing = true
        //添加layoutManager
        recyclerView.layoutManager = layoutManager
        //添加item动画
        recyclerView.itemAnimator = itemAnimator
        //添加下划线
//        recyclerView.addItemDecoration()
        //添加适配器
        recyclerView.adapter = adapter

        refreshLayout.onRefresh {
            //刷新
            presenter.refreshData()
        }
        //初始化数据
        presenter.initData()
    }

    override fun onLoadMore() {
        presenter.loadMoreData()
    }

    /**
     * once request data init
     */
    fun onDataInit(data: GithubPaging<T>) {
        if (!data.isEmpty()) {
            adapter.data.clear()
            adapter.data.addAll(data)
            onNextData(data)
            refreshLayout.isRefreshing = false
            dismissError()
        }
    }

    /**
     * Refresh data
     */
    fun onDataRefresh(data: GithubPaging<T>) {
        onDataInit(data)
    }

    /**
     * Empty handling when init data
     */
    fun onDataInitWithNothing(msg: String = "No Data,Click Refresh", block: () -> Unit = { presenter.refreshData() }) {
        showError(msg, block)
        refreshLayout.isRefreshing = false
    }

    /**
     * Error handling when init data
     */
    fun onDataInitWithError(error: String = "", block: () -> Unit = { presenter.refreshData() }) {
        showError(error, block)
        refreshLayout.isRefreshing = false
    }

    /**
     * Error handling when refresh data
     */
    fun onDataRefreshWithError(error: String, block: () -> Unit = { presenter.refreshData() }) {
        refreshLayout.isRefreshing = false
        if (adapter.data.isEmpty()) {
            showError(error, block)
        } else {
            toast(error)
            adapter.onErrorMore()
        }
    }

    /**
     * Success handling when loading more data
     */
    fun onDataWithMoreLoad(data: GithubPaging<T>) {
        adapter.data.update(data)
        onNextData(data)
        dismissError()
    }

    private fun onNextData(data: GithubPaging<T>) {
        data.hasNext.no {
            //没有下一个
            adapter.onNotMore()
        }.otherwise {
            adapter.onNextMore()
        }
    }

    /**
     * Error handling when loading more data
     */
    fun onDataWithMoreLoadError(error: String) {
        toast(error)
        adapter.onErrorMore()
    }

    protected open fun showError(error: String, block: () -> Unit) {
        promptView.show(error)
        promptView.onClick {
            dismissError()
            refreshLayout.isRefreshing = true
            block()
        }
    }

    protected open fun dismissError() {
        promptView.dissmiss()
    }

}