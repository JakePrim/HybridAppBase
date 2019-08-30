function doSoming(){
    "use strict"//启用严格模式 对于某些不安全的操作会抛出错误,严格模式下JavaScript的执行结果会有很大的不同
    var sum = 1+10 //即使没有分号也是有效的语句 - 不推荐 解释器会浪费性能 确定语句结尾插入分号;
    var diff = 1- 10;//有效语句 - 推荐 

    var text = false;
    //条件语句中推荐使用 {}代码块 让编码意图更清晰
    if(text){
        test = false;
        alert(test);
    }

    //ECMAScript 的变量是松散类型,可以保存任何类型的数据.换句话说每个变量就是一个用于保存值的占位符
    //定义变量时使用var操作符 ,正因为这种的定义使的JavaScript 的变量的类型可以改变,不建议改变保存值的类型
    var message = "message";

    // m1 = "m1";// 在没有var操作符的情况下的变量 变成全局变量,但是这种写法并不推荐 因为会很难维护和导致混乱
    //在严格模式下会抛出错误

    // alert(typeof message);
    // alert(typeof sum);
    // alert(typeof text);
    let t1 = 1;

    var t2;//未经初始化的值就是 undefined
    alert(t2);

    //定义的变量如果将来用在保存对象,那么最好将变量初始化为null ;null 是一个空对象指针
    var car = null;
    if(car != null){

    }

    //boolean 类型
    var message = "mes";
    var messageBoolean = Boolean(message);//字符串不为空 返回true; 非0的数字值返回true;object 不为空 返回true
    alert(messageBoolean);

    //注意不要使用下面的方式
    //字符串message会被自动转换成了对应的Boolean值,由于存在这种自动执行的Boolean转换,要确切的知道什么是变量
    if(message){
        alert("Value is true");
    }
}
//注意message,var操作符定义的变量将成为定义该变量的作用域中的局部变量,也就是说这个变量在函数退出后就会被销毁
//Uncaught ReferenceError: message is not defined
// doSoming();
// alert(m1);

// doNumber();

/**
 * 数值类型的理解
 */
function doNumber(){
    //永远不要测试某个特定的浮点数值
    console.log(0.1+0.2);//0.30000000000000004
    if(0.1+0.2 == 0.3){
        alert("You Got 0.3");
    }

    //数值范围
    var result = Number.MAX_VALUE + Number.MAX_VALUE;
    alert(result);//false 超出数值范围

    //NaN 
    console.log("NaN == NaN:"+(NaN == NaN));//NaN 和任何数值都不相等 包括NaN本身

    //isNaN 会帮我们确定这个参数是否“不是数值”;如果返回true 表示不能转换为数值
    console.log(isNaN(NaN));//true 
    console.log(isNaN(10));//false
    console.log(isNaN('10'));//false
    console.log(isNaN("blue"));//true 
    console.log(isNaN(true));// 1 false

    //数值转换 将非数值转换成数值
    //Number()
    var num1 = Number("hello");//NaN
    var num2 = Number("");//0
    var num3 = Number(000011);//9
    var num4 = Number(true);//1
    var num5 = Number("00011");//11

    console.log("num1",num1,"num2",num2,"num3",num3,"num4",num4,"num5",num5);

    //parseInt 转换规则
    //parseInt  处理整数是更常用的函数 会看其是否符合数值模式
    var p1 = parseInt("1234blue");//1234
    var p2 = parseInt("");//NaN
    var p3 = parseInt("0xA");//十六进制 10
    var p4 = parseInt(22.5);//22 
    var p5 = parseInt("070",8);//ECMAScript 3 认为是八进制 56;ECMAScript 5认为是十进制  70;为此我们可以指定基数来保证正确的结果
    var p6 = parseInt("70");//70

    var p7 = parseInt("0xf");//15
    var p8 = parseInt("f",16);//15 p7 和 p8 可以看成是相等的

    console.log("p1",p1,"p2",p2,"p3",p3,"p4",p4,"p5",p5,"p6",p6,"p7",p8);

    //parseFloat 和 parseInt类似;需要注意的parseFloat只解析 十进制,十六进制字符串会被转换成0,解析字符串中的第一个小数点是有效的
    //第二个小数点是无效的
    var pf1 = parseFloat("1234blue");//1234
    var pf2 = parseFloat("0xA");//0
    var pf3 = parseFloat("22.5");//22.5
    var pf4 = parseFloat("22.34.5");//22.34
    var pf5 = parseFloat("0908.5");//908.5

    console.log(pf1,pf2,pf3,pf4,pf5);
}

// doString();

function doString(){
     //js 中字符串可以使用“或者单引号‘表示;而Java中的字符串只能以双引号表示
     //ECMAScript 中这两种形式没有区别,表示的字符串完全相同;注意以双引号开头必须以双引号结尾,以单引号开头必须以单引号结尾
     var frist = "frist";
     var last = 'last';
     var text = 'This is the letter sigma \n This is the letter prim';
     console.log(text);
     //ECMAScript 中的字符串是不可变的,也就是说字符串一旦创建 它们的值就不能改变
     //要改变某个变量保存的字符串,首先要销毁原来的字符串,然后再用另一个新值的字符串填充该变量
     var lang = "java";
     lang = lang + "Script";
     //首先创建一个容纳10个字符的新字符串,然后在这个字符串中填充“java" 和 “Script” 最后一步销毁原来的字符串
     //“Java”和字符串“Script” 这也是在旧版浏览器拼接字符串很慢的原因 现在的浏览器已经解决了这个低效率的问题(如何解决??)

     //转换字符串 toString 可以输出以二进制 八进制 十六进制的字符串值
     var nume = "10";
     console.log(nume.toString());
     console.log(nume.toString(2));
     console.log(nume.toString(8));
     console.log(nume.toString(10));
     console.log(nume.toString(16));

     //object 类型 创建对象和Java中的类似 都是通过new 关键字创建的
     var o = new Object();//可以省去原括号 但是不推荐
     //关于 object 的属性和构造函数 会在后面中详细的讲解
}

//关于递增和递减操作符和其他基本的操作符和Java中的逻辑一样的 这里就不进行讲解了 非常基础的东西

// doStatement();

/**
 * 理解ECMAScript 的基本语句
 */
function doStatement(){

    //if 语句,javascript的if语句和Java中的if语句一样 if语句后可以跟一行代码或代码块不过业界推崇的是始终使用代码块
    //注意Javascript 的if语句中的条件可以是任意表达式,而且表达式的求值不一定是布尔值,ECMAScript会自动调用Boolean()转换函数
    //将这个表达式的结果转换为一个布尔值,这一点跟Java的有所不同,Java的if语句条件必须是布尔值.
    var i = 10;
    if(i > 25)
        alert('Greater than 25.');//单行语句
    else{
        alert('Less than or equal to 25.'); //代码块中的语句
    }
    
    //do-while while for 语句和Java的语句并没有区别,举个例子
    //do-while语句
    var li = 0;
    do{
        i+=2;
    }while(i<10);
    //for语句
    for (let i = 0; i < array.length; i++) {
        const element = array[i];
    }
    //包括for无限循环也和Java的一模一样
    for(;;){
        //TODO 
    }
    //只给出控制表达式实际上就是把for循环转换成了while循环
    for(;li<20;){
       li++;
    }

    //while 语句
    while (li<20) {
        li+=2;
    }

    alert(i);

    //for-in 语句,而在Java中没有for-in语句但是在kotlin中有for-in语句,kotlin中的一些语法也借鉴了ECMAScript中的语法
    //语法的简洁确实在编程中提高了效率,这也是我为什么喜欢上了JavaScript和Kotlin这两种语言
    //下面我们看这个例子
    for (const propName in window) {
       document.write(propName);
    }
    //需要注意的是ECMAScript对象的属性没有顺序,所以遍历返回的先后次序可能会因浏览器而异
    //还需要注意的是如果表达式的对象的变量值为null或undefined,for-in语句会抛出错误.
    //ECMAScript5 更正了这一行为,不再抛出错误,而是不执行循环体,建议在使用for-in循环之前,先检测该对象的值不是null或undefined
    for (const key in object) {
        if (object.hasOwnProperty(key)) {
            const element = object[key];
        }
    }
}

// doLabel();

function doLabel(){
 //label 语句,在Java中同样没有这样的语句但是在kotlin 中有这样的语句,kotlin大量借鉴了JavaScript语法
    //label :statement label语句可以在代码中添加标签,以便将来使用,看下面的一个例子:
    //label 语句一般都要与for语句等循环语句配合使用

    //break 和continue语句都可以与label语句联合使用,从而返回代码中特定的位置
    //这种联合使用的情况多发生在循环嵌套的情况中
    var num = 0;
    start:for(var i=0;i<11;i++){
       for(var j = 0;j<10;j++){
           if(i==5 && j==5){
               break start;//强制退出内部和外部循环
           }
           num++
       }
    }
    alert(num);//55
}
// doWith();

function doWith(){
    //with 语句的作用是将代码的作用域设置到一个特定的对象中,定义with语句的目的主要是为了简化多次编写同一个对象的工作
    //这种形式的语句同样在Java中是没有的,但是在kotlin中是存在的如:apply let run 等特殊函数
    //通常使用的写法如下:
    // var s = location.search.substring(1);
    // var hostName = location.hostName;
    // var url = location.url;

    //使用with语句
    with(location){
       var s = hostname;
       alert(s);
    }
    //注意:在严格模式下 不允许使用with语句,否则视为语法错误,由于大量使用with语句会导致性能下降,同时也会给调试代码造成困难
    //在开发大型应用程序时,不建议使用with语句
}

//switch 语句的用法和Java中的一样,ECMAScript中switch语句可以使用任何类型,其次case的值不一定是常量还可以是变量
//甚至表达式.而在Java中case的值不能是变量和表达式的
function doSwitch(){
    var i = 10;
    switch (i) {
        case 10:
            alert("10");
            break;
    
        default:
            break;
    }
    //switch语句是全等操作符,不会发生类型转换(例如字符串“10”不等于数值10)
}

// sayHi("JakePrim","Hello Word");

//ECMAScript 中函数是通过function关键字声明的,对函数的命名没有什么要求,但是在严格模式下不能把函数/参数命名为eval或arguments
//ECMAScript中的函数在定义时不必指定是否返回值,任何函数在任何时候都可以通过return语句后跟要返回的值
//在Java和kotlin中必须要指定返回值的类型的
// function sayHi(name,message){
//     alert(name,message);
//     eval(name);
//     return 100+name;
// }

//报错误:Uncaught SyntaxError: Unexpected eval or arguments in strict mode 
// function eval(arguments){
//     "use strict"//启用严格模式 对于某些不安全的操作会抛出错误,严格模式下JavaScript的执行结果会有很大的不同
//     alert(arguments);
// }

//函数中的参数:
//ECMAScript 函数不介意传递进来多少个参数,也不在乎传进来参数是什么数据类型.
//也就是说,即便定义的函数只接收两个参数,在调用这个函数时也未必一定要传递两个参数,可以传递一个、三个甚至不传递参数
//(PS:感觉ECMAScript的这种弱定义不是很理解,这种方法容易出现错误,不像Java中的强制定义一样)
//ECMAScript 为什么会这么做呢?
//ECMAScript中的参数在内部是用一个数组来表示的.函数接收到的始终都是这个数组,而不关心数组中包含哪些参数.
//实际上函数体内可以通过arguments对象(这也是为什么在严格模式下arguments不能用作参数和函数名的原因)来访问这个参数数组
//,从而获取传递给函数的每一个参数.arguments对象与数组类似. 使用arguments获取参数
//看下面的一个例子
function sayHi(){
    alert("1: "+arguments[0]+" 2:"+arguments[1]);
}
sayHi("JakePrim","HelloWorld");
//ECMAScript 函数的一个重要特点:命名的参数只提供便利,但不是必需的.ECMAScript 函数不存在函数重载这一重要特性(因为传递的参数没有个数和类型限制)
//
//另一个与参数相关的重要方面,arguments对象可以与命名参数一起使用
//arguments的值永远与对应命名参数的值保持同步
//没有传递值的命名参数将自动被赋予undefined 值
function doAdd(num1,num2){
    arguments[1] = 10;//相当于重写了第二个参数,如果只传递了一个参数那么num2 还是undefined,如果传递了两个参数 num
    if(arguments.length == 1){
        alert(num1+10);
    }else if(arguments.length == 2){
        alert(arguments[0]+num2);
    }
}
doAdd();

//ECMAScript 函数没有签名,因为其参数是有包含零或多个值的数组来表示的. 而没有函数签名 真正的重载是不可能做到的

function addSomeNumber(params) {
    return params + 100;
}

function addSomeNumber(params) {
    return params + 200;
}
// 函数addSomeNumber 被定义了两次,后定义的函数覆盖了先定义的函数,因此这个函数的返回结果就是300
var result = addSomeNumber(100);//300

console.log(result);

//总结ECMAScript的基本要素:
//ECMAScript 中的基本数据类型包括Undefined、Null、Boolean、Number、String
//与其他语言不同,ECMAScript没有为整数和浮点值分别定义不同的数据类型,Number类型可用于表示所有数值
//严格模式为这门语言容易出错的地方施加了限制
//ECMAScript提供了很多与C及其他类C语言中相同的基本操作符,包括算术操作符、布尔操作符、关系操作符、相等操作符及赋值操作符
//无须指定函数的返回值,因为任何ECMAScript函数都可以在任何时候返回任何值
//实际上,未指定返回值的函数返回的是一个特殊的undefined值
//ECMAScript中也没有函数签名的概念,因为其函数参数是以一个包含零或多个值的数组的形式传递的
//可以向ECMAScript函数传递任意数量的参数,并且通过arguments对象来访问这些参数
//由于不存在函数签名的特性,ECMAScript函数不能重载






