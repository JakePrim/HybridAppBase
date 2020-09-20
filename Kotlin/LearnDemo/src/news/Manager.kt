package news

class Manager : Divier, Wirter {
    override fun wirter() {

    }

    override fun divier() {

    }
}

interface Divier {
    fun divier()
}

interface Wirter {
    fun wirter()
}

// by 关键字 接口代理或属性代理
class ZiManager(val divier: Divier, val wirter: Wirter) : Divier by divier, Wirter by wirter

class CarDivier : Divier {
    override fun divier() {
        println("开车呢")
    }
}

class PPTWirter : Wirter {
    override fun wirter() {
        println("写PPT呢")
    }

}

fun main() {
    val divier = CarDivier()
    val wirter = PPTWirter()
    val ziManager = ZiManager(divier, wirter)
    ziManager.divier()
    ziManager.wirter()
}