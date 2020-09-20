//Boolean 类型
val flag: Boolean = true
val aFlag: Boolean = false

//Number类型
var aInt: Int = 8
val aInte1: Int = 0xFF
val maxInt: Int = Int.MAX_VALUE
val minInt: Int = Int.MIN_VALUE

val aLong: Long = 38923892

val aFloat: Float = 2.0f

val aDouble: Double = 2.0

val aShort: Short = 127

val aByte: Byte = 127

val maxByte: Byte = Byte.MAX_VALUE
val minByte: Byte = Byte.MIN_VALUE
//数字面值中的下划线 使数组常量更易读懂
val oneMillion = 1_000
val hexBtyes = 0xFF_EC_DE_5E

//kotlin 会自动帮你装箱拆箱

val aChar: Char = '0'
val bChar: Char = '中'
val cChar: Char = '\u000f'//unicode 编码

val aString: String = "Hello World"
val fromChars = String(charArrayOf('H', 'E', 'L', 'L', 'O'))

//类、对象和继承
class Girl(e1: String, e2: String, e3: String) : People(e1, e2, e3)

class Boy(e1: String, e2: String, e3: String) : People(e1, e2, e3)

open class People(e1: String, e2: String, e3: String) {
    init {
        println("new了一个${this.javaClass.simpleName}，性格$e1，长相:$e2，声音:$e3")
    }
}


//闭区间[]
val range: IntRange = 0..1024
//半开区间
val range_exclute: IntRange = 0 until 1024 //[0-1024) - [0-1023] (0 - 1023)

fun main(args: Array<String>) {

    println("$oneMillion : $hexBtyes")
    //判断是否在这个区间中
    println(50 in range)
    //迭代区间
    for (i in range_exclute) {
        print("$i,")
    }
    //创建一个对象
    val newGirl: Girl = Girl("温柔", "甜美", "动人")
    val newBoy: Boy = Boy("彪悍", "帅气", "浑厚")
    println(newGirl is People)
    //基本类型的转换
    aInt = aLong.toInt()
    println(maxInt)
    println(minInt)
    println(maxByte)
    println(minByte)

    println(aChar + ":" + bChar + ":" + cChar)

    println(aString + ":" + fromChars)

    val ax = 10
    //拼接字符串 字符串模版
    println("$aInt+$aDouble=${aInt + aDouble}")

    //原始字符 转义字符无法使用
    val rawString: String = """
         \t
         \n
         $ax
    """.trimIndent()
    println("$rawString : ${rawString.length}")
    intBox()
}

/**
 * 注意数字装箱拆箱的问题
 * 如果我们需要一个可空的引用（如 Int?）或泛型，数字装箱不一定保留同一性
 * 但是保留了相等性
 */
fun intBox(){
    val a = 1_000
    println(a===a)//输出true
    val boxedA:Int? = a
    val boxedB:Int? = a
    println(boxedA === boxedB)//输出false
    println(boxedA == boxedB)//输出true
}