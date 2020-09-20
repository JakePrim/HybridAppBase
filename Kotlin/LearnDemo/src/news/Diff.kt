package news

class Diff {

}

interface B {
    fun x(): Int = 1
}

interface C {
    fun x(): Int = 0
}

class D : B, C {
    override fun x(): Int {
        println("D x:Int")
        super<C>.x()
        return super<B>.x()
    }
}

fun main() {
    val d = D()
    val x = d.x()
    println(x)
}