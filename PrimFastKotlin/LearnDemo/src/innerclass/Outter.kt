package innerclass

class Outter{
    var a= 1

    //默认是静态内部类
    class Innter{

    }

    //非静态内部类
    inner class Inner{
        fun hello(){
            println(this@Outter.a)
        }
    }
}

fun main() {
    var inner = Outter.Innter()

    var inners = Outter().Inner()
}