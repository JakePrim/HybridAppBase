package news

fun main() {
    val a = minOf(1, 2)//包级函数

    val ofDouble = Latitude.ofDouble(3.0)
    val ofLatitude = Latitude.ofLatitude(ofDouble)

    println(Latitude.TAG)
}

class Latitude private constructor(val value: Double) {
    //类的半生对象 其实就相当于Java中的静态方法
    //每个类可以对应一个伴生对象
    //静态成员考虑用包级函数
    companion object {
        @JvmStatic //在Java中调用可以加上这个注解
        fun ofDouble(double: Double): Latitude {
            return Latitude(double)
        }

        fun ofLatitude(latitude: Latitude): Latitude {
            return Latitude(latitude.value)
        }

        @JvmField
        val TAG: String = "Latitude"
    }
}