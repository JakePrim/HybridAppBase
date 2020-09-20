package com.prim.gkapp.data.model

import com.prim.gkapp.anno.POKO
import retrofit2.adapter.rxjava2.PagingWrapper

@POKO
data class SearchRepos(var total_count: Int, var incomplete_results: Boolean, var items: List<Repository>) :
    PagingWrapper<Repository>() {
    override fun getElements(): List<Repository> {
        return items
    }

}