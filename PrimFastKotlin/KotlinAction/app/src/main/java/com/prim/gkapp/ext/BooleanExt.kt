package com.prim.gkapp.ext

/**
 * @desc boolean 的扩展类
 * @author prim
 * @time 2019-05-25 - 22:24
 * @version 1.0.0
 */
//sealed 密封类，sealed修饰的BooleanExt的子类只能定义在同一个文件或者内部类，其他地方不能调用
//out ?? 型变注解
sealed class BooleanExt<out T>

//返回一个BooleanExt的实例对象
//object 实现了一个单例
object Otherwise : BooleanExt<Nothing>()

//返回一个BooleanExt的实例对象
class WithData<T>(val data: T) : BooleanExt<T>()

//inline ?? 作用
//block 匿名函数 传递一个函数
/**
 * 判断 boolean是否为true 且为true的处理 扩展方法 不用再去写if else等代码
 *  @NotNull
 */
fun <T> Boolean.yes(block: () -> T) =
    when {
        this -> {
            WithData(block())
        }
        else -> {
            Otherwise
        }
    }

/**
 * 判断boolean是否为false
 */
inline fun <T> Boolean.no(block: () -> T) =
    when {
        !this -> {
            WithData(block())
        }
        else -> {
            Otherwise
        }
    }

/**
 * 相反等方法扩展
 */
inline fun <T> BooleanExt<T>.otherwise(block: () -> T): T =
    when (this) {
        is Otherwise -> block()
        is WithData -> this.data
    }

fun main() {//测试
    val result = true.yes {
        1
    }.otherwise {
        2
    }

}

