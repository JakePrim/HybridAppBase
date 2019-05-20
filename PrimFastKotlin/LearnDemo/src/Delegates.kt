import kotlin.reflect.KProperty

class Delegates {
    val hello by lazy {
        "HelloWorld"
    }

    val hello2 by X()

    var hello3 by X()
}

class X {
    private var value: String? = null
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        println("getValue:$thisRef")
        return value ?: ""
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        this.value = value
    }
}

fun main() {
    val delegates = Delegates()
    println(delegates.hello)
    println(delegates.hello2)
    println(delegates.hello3)
    delegates.hello3 = "hello3"
    println(delegates.hello3)
}