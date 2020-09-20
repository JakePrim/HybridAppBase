import function.Childs

//返回一个String类型
fun getName(): String? {//? 可返回null
    return null
}

val FINAL_HELLO_WORD = "hello word" //val 常量不可变的变量 运行时常量：编译的时候不知道它的值 注意它不是编译期常量  不等于Java中的final
const val TEST = "test"//编译期常量：在编译的时候就知道了它的值了 并且代码中对它的引用也都变成了值 可以提高代码的执行效率 等于Java中的final
var helloWorlds = "helloWorld" // var 变量是可变的

fun main() {
//    println(FINAL_HELLO_WORD)
//    helloWorlds = "test"

//    var name: String = getName() ?: return//如果为null就return，否则就继续执行
//    println(name.length)

    var arg1s = "23"

    var arg1 = arg1s.toInt()
    var arg2 = 10
    println("arg1:$arg1+arg2:$arg2=sum:${sum1(arg1, arg2)}")
    println(intLong(10))
    sum2(1, 3)
    sum2.invoke(1, 3)//运算符重载

}

fun sum1(arg1: Int, arg2: Int) = arg1 + arg2

//lambda 表达式 (Int,Int) -> Int
val sum2 = { arg1: Int, arg2: Int ->
    println("arg1:$arg1+arg2:$arg2=${arg1 + arg2}")
    arg1 + arg2
}

val printlnHello = {
    println("hello")
}

//匿名函数
val intLong = fun(x: Int): Long = x.toLong()

val intTpLong = { x: Int -> x.toLong() }


