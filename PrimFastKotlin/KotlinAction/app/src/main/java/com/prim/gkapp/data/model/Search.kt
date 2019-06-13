package com.prim.gkapp.data.model

import com.prim.gkapp.anno.POKO

@POKO
data class SearchRepos(var total_count: Int, var incomplete_results: Boolean, var items: List<Repository>)