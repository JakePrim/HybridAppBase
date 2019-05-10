import com.sun.xml.internal.fastinfoset.util.StringArray

class City {
    override fun toString(): String {
        return "City"
    }
}

fun main(args: Array<String>) {
    //数组的类型
    val arrayOfInt: IntArray = intArrayOf(1, 2, 3, 4)
    val arrayOfChar: CharArray = charArrayOf('a', 'b', 'c')
    val arrayOfString: Array<String> = arrayOf("我是", "码农")
    val arrayOfObjects: Array<City> = arrayOf(City(), City())

    //数组的长度
    println(arrayOfInt.size)

    //遍历数组
//    for (i in arrayOfChar) {
//        print(" $i ")
//    }
    //forEach 遍历数组
    arrayOfChar.forEach ForEach@{
        if (it == 'c') return@ForEach
        println(it)
    }
    //更简洁的写法
    arrayOfChar.forEach(::println)
    //it 更换名称
    arrayOfInt.forEach { element -> println(element) }
    //获取数组的值
    println(arrayOfString[1])
    //改变数组中的某个值
    arrayOfString[1] = "码神"
    //将char数组转换成string
    println(arrayOfChar.joinToString(""))
    //获取某段数组
    println(arrayOfInt.slice(1..2))
}