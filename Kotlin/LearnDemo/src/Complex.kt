class Complex(var real: Double, var imaginary: Double) {
    //重写操作符
    operator fun plus(other: Complex): Complex {
        return Complex(real + other.real, imaginary + other.imaginary)
    }

    override fun toString(): String {
        return "$real + ${imaginary}"
    }
}

fun main() {
    val c1 = Complex(3.4, 4.0)
    val c3 = Complex(2.0, 7.5)

    println(c1 + c3)
}