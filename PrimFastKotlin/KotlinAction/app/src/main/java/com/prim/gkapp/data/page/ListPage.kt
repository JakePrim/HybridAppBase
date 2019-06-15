package com.prim.gkapp.data.page

import com.prim.lib_base.log.logger
import io.reactivex.Observable

/**
 * @desc
 * @author prim
 * @time 2019-06-15 - 06:57
 * @version 1.0.0
 */
abstract class ListPage<T> : DateProvider<T> {

    companion object {
        const val PAGE_SIZE = 20
    }

    var currentPage = 1
        private set

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
    fun fromInfrist(pageCount: Int) = Observable.range(1, pageCount)
        .concatMap {
            getData(it)
        }
        .doOnError {
            logger.error("loadMore error", it.message)
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