import ClassAndNew.Childs

//返回一个String类型
fun getName(): String? {//? 可返回null
    return null
}

fun main(args: Array<String>) {
    var name: String = getName() ?: return//如果为null就return，否则就继续执行
    println(name.length)

    var parent = Childs()
}

