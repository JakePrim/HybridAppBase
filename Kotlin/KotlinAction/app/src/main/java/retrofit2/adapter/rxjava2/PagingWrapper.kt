package retrofit2.adapter.rxjava2

import com.prim.gkapp.data.page.GithubPaging

abstract class PagingWrapper<T> {
    abstract fun getElements(): List<T>

    val paging by lazy {//lazy 会存在问题 导入noarg 先解决此问题
        //also 则该对象为函数的参数。在函数块内可以通过 it 指代该对象。返回值为该对象自己
        GithubPaging<T>().also {
            it.addAll(getElements())
        }
    }
}