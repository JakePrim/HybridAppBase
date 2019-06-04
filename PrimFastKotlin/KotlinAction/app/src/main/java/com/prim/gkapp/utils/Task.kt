package com.prim.gkapp.utils

import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @desc
 * @author prim
 * @time 2019-06-04 - 09:42
 * @version 1.0.0
 */
class Task {
    companion object {
        //kotlin 定义静态方法 与Java中的static相似
        fun <T> taskIo_main(): ObservableTransformer<T, T> {
            return ObservableTransformer {
                return@ObservableTransformer it.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            }
        }

        fun <T> tasMain(): ObservableTransformer<T, T> {
            return ObservableTransformer {
                return@ObservableTransformer it
                    .observeOn(AndroidSchedulers.mainThread())
            }
        }

    }
}