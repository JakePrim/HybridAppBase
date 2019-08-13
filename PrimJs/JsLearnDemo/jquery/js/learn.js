
// window.onload();

// window.resizeTo();

//resize 要用window
// $(window).resize(function () {

// });

$(document).ready(function () {

    var indexs = 0;

    $("a").click(function () {
        // 此处写代码
        console.log($(this).index());
        //查找某个img的位置 siblings 所有的兄弟元素
        swiper($(this).index());
    });

    $(document).keydown(function (event) {
        console.log(event);
        if (event.keyCode == 39) {
            indexs == $('a').length - 1 ? swiper(0) : swiper(indexs + 1);
        } else if (event.keyCode == 37) {
            indexs > 0 ? swiper(indexs - 1) : swiper($('a').length - 1);
        } else {
            return false;
        }
    })

    var swiper = function (index) {
        indexs = index;
        $('img').eq(index).css({ "opacity": "1" }).siblings().css({ "opacity": "0" });
    }

    //mouseenter mouseleave 
    //鼠标进入元素
    $('a').mouseenter(function () {
        //查找某个img的位置 siblings 所有的兄弟元素
        $('img').eq($(this).index()).css({ "opacity": "1" }).siblings().css({ "opacity": "0" });
    });
    //鼠标离开元素
    // $('a').mouseleave(function(){

    // });
    //hover进入退出两种状态相当于 mouseenter, mouseleave;传入两个函数.
    // $('a').hover(function () {
    //     $('img').eq($(this).index()).css({ "opacity": "1" }).siblings().css({ "opacity": "0" });

    // }, function () {
    //     $('img').eq($(this).index()).css({ "opacity": "0" }).siblings().css({ "opacity": "1" });
    // });

    //mouseover mouseout 鼠标进入或者移除指定元素及其子元素的时候触发,子元素进入 离开都会被触发

    //mousemove 鼠标移动事件
    // $('nav').mousemove(function(event){


    // });

    //滚动事件
    // $('div').scroll(function(){

    // });

    //键盘事件
    //keydown 按下键盘时触发
    // $(document).keydown(function (event) {
    //     console.log(event.keyCode + ":" + event.key);
    // });

    //keyup 当按钮被松开时触发
    // $(document).keyup(function(event){

    // });

    //keypress 用来检测用户输了什么字符
    // $('input').keypress(function (event) {
    //     console.log(event);
    // });
    //keydown 与 keypress 的区别:keypress 就是用来检测用户输了啥字符的，而 keydown 则是单纯的检测用户是否按了键盘上的按键，所以 keypress 常用。

    //获取input输入的值
    // $('input').val();

    //焦点事件
    //获取焦点
    // $('input').focus(function () {
    //     console.log('获得焦点');
    // });
    // //失去焦点
    // $('input').blur(function () {
    //     console.log('失去焦点');
    // });

    //input 的value值发生改变时才会触发,当元素当值发生改变时,会发生change事件
    // $('input').change(function () {


    // });

    //select 事件 需要选中时才可以用select事件,只能针对输入框,可编辑的文本,textarea 或input 中的文本被选择时就会触发
    // $('input').select(function(){

    // });

    //提交表单
    // $('form').submit();
    // //阻止表单提交
    // $('form').submit(function(){
    //     //..... false 阻止提交 true 提交表单
    //     return false;
    // });
});