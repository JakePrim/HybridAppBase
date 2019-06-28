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

doString();

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

//关于递增和递减操作符 和Java中的逻辑一样的 这里就不进行讲解了 非常基础的东西
