package sealedclass

//sealed 密封类 sealed的子类 只能定义在同一个文件中 或者内部类其他地方不能调用；子类可数
sealed class PlayerCmd {
    class play(val url: String, val position: Long = 0) : PlayerCmd()

    class Seek(val position: Long) : PlayerCmd()

    object Pause : PlayerCmd()

    object Resume : PlayerCmd()

    object Stop : PlayerCmd()

}

class play(val url: String, val position: Long = 0) : PlayerCmd()

class Seek(val position: Long) : PlayerCmd()

object Pause : PlayerCmd()

object Resume : PlayerCmd()

object Stop : PlayerCmd()

enum class PlayerState{
    IDLE,PAUSE,PLAYING
}