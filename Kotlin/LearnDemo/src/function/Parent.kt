package function

open class Parent {
    //允许被子类覆盖 需要加上open
    open fun hello() {
       println("hello")
    }

    open var x: Int = 0

    //不允许被子类覆盖
    fun hello1() {

    }
}