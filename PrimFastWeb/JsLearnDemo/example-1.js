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

doNumber();

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
}