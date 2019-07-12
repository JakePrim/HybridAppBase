// 变量 作用域和内存问题

// - 理解基本类型和引用类型的值
// - 理解执行环境
// - 理解垃圾收集

//JavaScript 变量松散类型的本质,决定了它只是在特定的时间用于保存特定值的名字而已,由于不存在定义某个变量
//必须要保存何种数据类型的规则,变量的值和变量的数据可以在脚本的声明周期内改变
//尽管 某种程度上来看,这可能是一个既有趣又强大 同时又容易出问题的特性,JavaScript的变量的复杂程度还远不止这些
//下面来深入的理解JavaScript的变量

//基本类型和引用类型的值
//ECMAScript 变量可能包含两种不同数据类型的值:基本类型值和引用类型值. 
//基本类型值,包括5中基本数据类型:Undefined Null Boolean Number String
//引用类型值: 保存在内存中的对象,JavaScript 不允许直接访问内存中的位置,不能直接操作对象的内存空间
//实际上是操作对象的引用而不是实际的对象(在Java中 操作对象也是操作对象的引用)

//动态的属性
//引用类型的操作 可以为其添加属性和方法,也可以改变和删除其属性和方法
var person = new Object();
person.name = "JakePrim";
console.log("引用类型动态添加属性",person.name);

//基本类型不能动态添加属性和方法 比如:
var name = "Prim";
name.age = 28;
console.log("基本类型添加属性",name.age);//undefined
//只能给引用类型的值动态添加属性

//- 复制变量值 从一个变量向另一个变量复制基本类型值和引用类型值时也存在不同

// - 复制基本类型
//上述代码中,num1保存的值是10,使用num1初始化num2,在num2 中也保存了值10,注意num2中的10和num1中的10是完全独立的,该值
//只是num1中10的副本,两个变量任何操作不会相互影响.
//(这与java是类似,在java中两个变量互相赋值,两个变量的任何操作不会互相影响)
var num1 = 10;
var num2 = num1;


// - 复制引用类型
//一个变量向另一个变量复制引用类型的值时,这个值的副本实际上是一个指针,而这个
//学过C/C++的都知道指针是指向存储在堆的一个对象,指针保存的就是堆内存的一个地址,如果改变了这个指针的指向
//值也会跟着改变  如下例子:obj1和obj2指向的都是同一个对象,当obj1添加了name属性后
//也可以通过obj2来访问name属性.
var obj1 = new Object();
var obj2 = obj1;
obj1.name = "JakeRPrs";
console.log(obj2.name);//JakeRPrs

//- 传递参数
/**
 * ECMAScript中所有函数的参数都是按值传递的. 也就是说上,把外部函数的值复制给函数内部
 * 的参数,就相当于把值从一个变量复制到另一个变量一样.
 */

 /**
  * 向参数传递基本类型的值时,被传递的值会被复制给一个局部变量(就是arguments对象中的一个元素).
  * 向参数传递引用类型的值时,会把这个值在内存中的地址复制给一个局部变量(arguments),这个局部变量的变化
  * 会反映在函数的外部.
  * 看下面的例子:
  * 下面代码,是传递的基本类型,count的值并没有受到影响
  */
 function addTen(params) {
     params+=10;
     return params;
 }

 var count = 20;
 var result = addTen(count);
 console.log(count);//20
 console.log(result);//30

 /**
  * 在看下面传递引用类型:
  * 下面代码,创建了一个对象 person,然后复制给了params,两个变量同时指向同一个对象
  * 当其中一个变量发生变换,另一个变量也会变换.
  */
 function setName(params) {
     params.name = "Nicholas";
 }

 var person = new Object();
 setName(person);
 console.log(person.name);//Nicholas

 //- 检测类型
 /**
  * 检测变量的类型 typeof
  */
 var s= "Prim";
 var b = true;
 var i =22;
 var u;
 var n = null;//Object
 var o = new Object();//Object

 console.log("s",typeof s,"b",typeof b,"i",typeof i,"u",typeof u,"n",typeof n,"o",typeof o);

//ECMAScript 还提供了instanceof 和Java中的一样的用法

//执行环境及作用域,通过如下例子来讲解
var color = "blue";

function changeColor() {
    if(color === "blue"){
       color = "red";
    }else{
        color = "blue";
    }
}

changeColor();

alert("Color is now "+color);

/**
 * 函数changeColor的作用域链包含两个对象:它自己的变量对象(其中定义着arguments对象)和全局环境的变量对象.
 * 可以在函数内部访问color,就是因为可以在这个作用域链中找到它
 */

 //此外,在局部作用域中定义的变量可以在局部环境和全局变量互换使用,这一点和Java很相似
var color1 = "blue";
function changeColor1(){
    var anotherColor = "red";
    function swapColors(){
        var tempColor = anotherColor;
        anotherColor = color1;
        color1 = tempColor;
        //这个可以访问color、anotherColor、tempColor
    }
    //这里可以访问color和anotherColor,但不能访问tempColor
    swapColors();
}
//这里只能访问color
changeColor1();

//延长作用域链

/**
 * 虽然执行环境的类型总共只有两种 --- 全局和局部(函数).但还有其他方法来延长作用域链:try-catch语句的catch块;with语句,这两个语句
 * 可以在作用域链的前端临时增加一个变量对象
 */

 //with
 function buildUrl(){
     var qs = "?debug=true";

     with(location){
         var url = herf + qs;
     }
     return url;
 }

 //没有块级作用域,c++/Java中都有块级作用域一般是由花括号封闭的代码,JavaScript没有块级作用域,由花括号封闭的代码,就是它自己的执行环境
if(true){
    var color = "blue";
}
console.log(color);//blue 如果是在C/C++ java 中,color会在if语句执行完毕后被销毁.
//但是在Javascript中,if语句中的变量声明会将变量添加到当前的执行环境(这里是全局环境中),再比如for循环如下:
for(var i=0;i<10;i++){

}

console.log(i);//对于C++/Java 有块级作用与的语言来说,for语句初始化变量的表达式所定义的变量,
//只会存在于循环的环境之中.而对于Javascript来说,由for语句创建的变量i即使在for循环执行结束后
//也依旧会存在于循环之外的执行环境中(PS:搞不懂JavaScript为什么要这样设计?)

//1. 声明变量
/**
 * 使用var声明的变量会自动被添加到最接近的环境中.在函数内部,最接近的环境就是函数的局部环境;
 * 在with语句中,最接近的环境就是函数环境.如果初始化变量时没有使用var声明,该变量会自动被添加到全局环境. 
 */

function add(num1,num2){
    var sum = num1+num2;
    if(true){
       color = "blue";
    }
    return sum;
}

var result = add(10,20);
alert(color);

/**
 * 如上述代码,函数add()定义了一个名为sum的局部变量,但是sum在函数外部是访问不到的.color 没有var关键字是全局变量可以被访问
 */

 /**
  * JavaScript 的垃圾收集机制:
  * Javascript的垃圾收集和Java中有差不多的策略,但是垃圾收集策略相对于Java要简单一些(如果你有兴趣可以看Java的垃圾收集机制)
  * Javascript的垃圾收集机制的原理很简单:找出那些不在继续使用的变量,然后释放其占用的内存.为此,垃圾收集器会按照固定的时间间隔
  * (或代码中预定的收集时间 周期性地执行这一操作)
  */

  // 标记清除






