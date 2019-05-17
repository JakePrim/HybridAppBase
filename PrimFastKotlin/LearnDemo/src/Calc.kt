import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.NumberFormatException
import java.lang.UnsupportedOperationException

fun main(args: Array<String>) {
    while (true) {
        try {
            println("请输入算式 例如：3 + 4")
            val intPut = readLine() ?: break
            val split = intPut.trim().split(" ")//trim 清除前面的空格
            val size = split.size
            if (size < 3) {
                throw IllegalArgumentException("参数个数不对")
            }
            val arg1 = split[0].toDouble()
            val op = split[1]
            val arg2 = split[2].toDouble()
            println("arg:$arg1 $op arg2:$arg2 = ${Operators(op).invoke(arg1, arg2)}")
        } catch (e: NumberFormatException) {
            println("您确定输入的是数字吗？")
        } catch (e: IllegalArgumentException) {
            println("您确定您的输入是用空格分割的吗？")
        } catch (e: Exception) {
            println("程序遇到未知的异常：${e.message}")
        }
        println("是否再来一发？【Y】")
        val cmd = readLine()
        if (cmd == null || cmd.toLowerCase() != "y") {
            break
        }
    }
    println("感谢您使用我们的计算器")
}

class Operators(op: String) {
    val opFun: (left: Double, right: Double) -> Double

    init {
        opFun = when (op) {
            "+" -> { l, r -> l + r }
            "-" -> { l, r -> l - r }
            "*" -> { l, r -> l * r }
            "/" -> { l, r -> l / r }
            "%" -> { l, r -> l % r }
            else -> throw UnsupportedOperationException(op)
        }
    }

    fun invoke(left: Double, right: Double): Double {
        return opFun(left, right)
    }
}