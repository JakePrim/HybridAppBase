package function

import kotlin.NumberFormatException

class T1 : Parent() {
    //final 禁止被覆盖
    override fun hello() {
        super.hello()
    }

    override var x: Int
        get() = super.x + 1
        set(value) {}

    fun p() {
        //异常处理
        val trye = try {
            1
        } catch (e: NumberFormatException) {
            12
        } catch (e: NullPointerException) {
            0
        } finally {
            16
        }


        val b = Bar()
        b.g()
    }

    inner class Bar {
        fun g() {
            //内部类访问外部类
            super@T1.hello()//调用Parent的hello方法
            println(super@T1.x)//调用Parent的x
        }
    }

    //变长参数相当于 Java中的 ... 具名参数 默认参数
    fun argarg(double: Double = 3.0, vararg args: Char, str: Int = 0) {

    }

}

fun main() {
    val tt = T1()
    tt.p()

    val arrats = charArrayOf('3', '4', '5')
    tt.argarg(args = *arrats, str = 5)
}

