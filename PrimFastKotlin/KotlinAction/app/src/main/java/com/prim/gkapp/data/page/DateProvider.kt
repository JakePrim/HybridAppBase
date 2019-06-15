package com.prim.gkapp.data.page

import io.reactivex.Observable

/**
 * @desc
 * @author prim
 * @time 2019-06-15 - 06:56
 * @version 1.0.0
 */
interface DateProvider<T> {
    fun getData(page: Int): Observable<GithubPaging<T>>
}