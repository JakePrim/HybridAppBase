package com.prim.gkapp.data.page

import android.util.Log
import com.prim.lib_base.log.logger
import io.reactivex.Observable

/**
 * @desc list page，auto impl load more data and load page count
 * @author prim
 * @time 2019-06-15 - 06:57
 * @version 1.0.0
 */
abstract class ListPage<T> : DateProvider<T> {

    companion object {
        //The number of pages displayed,number default 20
        const val PAGE_SIZE = 20
    }

    //Record current number of pages,set method private not allow
    var currentPage = 1
        private set

    //Record current data of pages
    val data = GithubPaging<T>()

    /**
     * request more data
     */
    fun loadMore() =
            getData(currentPage + 1).doOnNext {
                currentPage++
            }.doOnError {
                logger.error("loadMore error", it.message)
            }.map {
                data.mergeData(it)
                data
            }

    /**
     * load page count,form page = 1 start
     */
    fun fromInfrist(pageCount: Int = currentPage) = Observable.range(1, pageCount)
            .concatMap {
                getData(it)
            }
            .doOnError {
                Log.e("loadMore error", it.message)
            }
            .reduce { acc, pageData ->
                acc.mergeData(pageData)
            }
            .doOnSuccess {
                data.clear()
                data.mergeData(it)
                currentPage = pageCount
            }


}