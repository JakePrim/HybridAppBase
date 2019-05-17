package news

//如果不是 abstract 如果需要被继承必须要加上 open
abstract class Person(open val age: Int) {
    //覆写已有的方法 必须要 open
    open fun work() {

    }

    abstract fun work1()
}

class MaNong(age: Int) : Person(age) {

    //修改父类的属性
    override val age: Int
        get() = 0

    override fun work1() {

    }

    override fun work() {
        super.work()
        println("我是码农我在写代码")
    }
}

class Doctor(age: Int) : Person(age) {
    override fun work1() {

    }

    override fun work() {
        super.work()
        println("我是医生我在给病人看病")
    }
}

fun main() {
    val manong = MaNong(23)
    manong.work()
    println(manong.age)

    val doctor = Doctor(34)
    doctor.work()
    println(doctor.age)
}