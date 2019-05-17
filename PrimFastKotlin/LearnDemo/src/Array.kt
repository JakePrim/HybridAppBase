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
    //forEach 遍历数组 需要继续往下运行 通过标签中断迭代 ForEach@ 相当于 break
    arrayOfChar.forEach ForEach@{
        if (it == 'c') return@ForEach
        println(it)
    }

    for ((index, value) in arrayOfChar.withIndex()) {
        println("index:$index,value:$value")
    }

    //与上面是等价的
    for (indexValue in arrayOfChar.withIndex()) {

        println("indexValue -> index:${indexValue.index},value:${indexValue.value}")
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

    var testList = MyList()

    testList.add(1)
    testList.add(2)
    testList.add(4)

    for (te in testList) {
        println(te)
    }
}

class MyIterator(var iterator: Iterator<Int>) {
    //重写操作符
    operator fun next(): Int {
        return iterator.next()
    }

    operator fun hasNext(): Boolean {
        return iterator.hasNext()
    }
}

class MyList {
    private var list1 = ArrayList<Int>()

    fun add(int: Int) {
        list1.add(int)
    }

     fun remove(int: Int) {
        list1.remove(int)
    }

    operator fun iterator(): MyIterator {
        return MyIterator(list1.iterator())
    }

}