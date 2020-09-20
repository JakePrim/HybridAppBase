package com.prim.gkapp.data.page

import com.prim.lib_base.log.logger

/**
 * @desc this is github list paging handle
 * @author prim
 * @time 2019-06-05 - 06:17
 * @version 1.0.0
 */
class GithubPaging<T> : ArrayList<T>() {
    companion object {
        const val URL = """(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"""
    }

    private val relMap = HashMap<String, String?>().withDefault { null }

    private val first by relMap
    private val last by relMap
    private val next by relMap
    private val prev by relMap

    val isLast
        get() = last == null

    val hasNext
        get() = next != null

    val isFirst
        get() = first == null

    val hasPre
        get() = prev != null

    operator fun get(key: String): String? {
        return relMap.get(key)
    }

    fun setupLink(link: String) {
        Regex("""<($URL)>; rel="(\w+)"""").findAll(link).asIterable().map {
            val url = it.groupValues[1]
            relMap[it.groupValues[3]] = url //next=....
            logger.warn("${it.groupValues[3]} => ${it.groupValues[1]}")
        }
    }

    fun mergeData(paging: GithubPaging<T>): GithubPaging<T> {
        addAll(paging)
        relMap.clear()
        relMap.putAll(paging.relMap)
        return this
    }
}