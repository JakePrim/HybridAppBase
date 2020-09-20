package function

class X
//构造函数简洁的写法
class Childs(name: String) {


    //构造函数
//    constructor(name: String) {
//        println("constructor")
//    }



    val firstP = "firstP"

    //先走init 在走构造函数
    init {
        print("init 1 $firstP")
    }

    val sencodeP = "sencodeP"

    init {
        println("init 2 $sencodeP")
    }


    fun sound(name: String) {
        print("唱歌$name")
    }

    fun jump(name: String) {
        print("跳舞$name")
    }
    //默认的访问控制是public


    //lateinit 延迟初始化 只能用于var
    lateinit var c: String

    lateinit var d: X
    //lazy val 延迟初始化
    val e: X by lazy {
        println("init x")
        X()
    }

    var b = 0
        //var:get set 的实现 val：可以定义get方法 但是不可以定义set方法 因为它是不可变的
        get() {
            println("some on tries to get b.")
            return field
        }
        set(value) {
            println("some on tries to set b")
            field = value
        }

}