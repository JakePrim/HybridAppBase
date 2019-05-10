val FINAL_HELLO_WORLD = "Hello World" //不一定是编译期常量 常量不可变的类型 与Java中的final相似

const val CONST_FINAL_HELLO_WORLD = "Test"//const 是编译器常量这时就和Java中的final一样了，会将它的引用都替换成它的值，可以提高效率

var helloWorld = "HelloWorld" //自动推导类型

fun main(args: Array<String>): Unit {//函数什么都不返回 实际上是返回 Unit
    helloWorld = "Hello Test"
    val arg1 = 10
    val arg2 = 11
    println("$arg1 + $arg2 = ${sum(arg1, arg2)}")
    val agr3 = fun(agr: Int): Long {
        return agr.toLong()
    }
    println(agr3(12))
    //使用lambda表达式
    println(sums(1, 5))
    //也可以这样写
    println(sums.invoke(1,5))
}

//lambda 表达式
val sums = { arg1: Int, arg2: Int ->
    println("$arg1 + $arg2 = ${arg1 + arg2}")
    arg1 + arg2
}

fun sum(arg1: Int, arg2: Int): Int {
    return arg1 + arg2
}