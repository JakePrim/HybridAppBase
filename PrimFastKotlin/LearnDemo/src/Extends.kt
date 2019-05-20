import news.Manager
import news.Person

fun main() {
    println("abc".multiply(16))
    println("abc" * 16)
    "abc".a
    "abc".b = 16
}

//扩展方法 当前的类进行扩展
//反编译
//@NotNull
//   public static final String multiply(@NotNull String $this$multiply, int var1) {
//      Intrinsics.checkParameterIsNotNull($this$multiply, "$this$multiply");
//      StringBuilder stringBuilder = new StringBuilder();
//      int var3 = 0;
//
//      for(int var4 = var1; var3 < var4; ++var3) {
//         stringBuilder.append($this$multiply);
//      }
//
//      String var10000 = stringBuilder.toString();
//      Intrinsics.checkExpressionValueIsNotNull(var10000, "stringBuilder.toString()");
//      return var10000;
//   }
fun String.multiply(int: Int): String {
    val stringBuilder = StringBuilder()
    for (i in 0 until int) {
        stringBuilder.append(this)
    }
    return stringBuilder.toString()
}

//运算符重载
operator fun String.times(int: Int): String {
    val stringBuilder = StringBuilder()
    for (i in 0 until int) {
        stringBuilder.append(this)
    }
    return stringBuilder.toString()
}

val String.a: String
    get() = "abc"

var String.b: Int
    set(value) {

    }
    get() = 5

var Manager.a: String
    get() = "test"
    set(value) {

    }