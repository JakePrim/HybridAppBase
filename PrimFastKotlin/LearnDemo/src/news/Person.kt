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

class House

class flower

class Countyard {
    public val house = House() //默认public 可见的

    private val flower = flower()

    internal val x: Int = 0 //模块内可见
}

class BDriver

interface OnExternalDriverMountListener {
    fun onMount(bDriver: BDriver)

    fun onUnmount(bDriver: BDriver)
}

abstract class Player

//object 相当于java 中的单例 将其反编译可以得到
//public final class MusicPlayer extends Player implements OnExternalDriverMountListener {
//   private static final int state = 0;
//   public static final MusicPlayer INSTANCE;
//
//   public void onUnmount(@NotNull BDriver bDriver) {
//      Intrinsics.checkParameterIsNotNull(bDriver, "bDriver");
//   }
//
//   public void onMount(@NotNull BDriver bDriver) {
//      Intrinsics.checkParameterIsNotNull(bDriver, "bDriver");
//   }
//
//   public final int getState() {
//      return state;
//   }
//
//   public final void play(@NotNull String url) {
//      Intrinsics.checkParameterIsNotNull(url, "url");
//   }
//
//   private MusicPlayer() {
//   }
//
//   static {
//      MusicPlayer var0 = new MusicPlayer();
//      INSTANCE = var0;
//   }
//}
// object 只有一个实例的类 不能自定义构造方法 可以实现接口、继承父类 本质上就是单例模式最基本的实现
public object MusicPlayer : Player(), OnExternalDriverMountListener {
    override fun onUnmount(bDriver: BDriver) {
    }

    override fun onMount(bDriver: BDriver) {
    }

    val state: Int = 0
    fun play(url: String) {

    }
}