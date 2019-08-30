/**
 * jquery 的$符号相当于
 */

// jQuery = function( selector, context ) {

//     // The jQuery object is actually just the init constructor 'enhanced'
//     // Need init if jQuery is called (just allow error to be thrown if not included)
//     return new jQuery.fn.init( selector, context );
// },
// $ = window.jQuery = jQuery;
// $(aaa);
// jQuery(aaa);

// $.noConflict();//变量冲突时的使用

//jquery 的书写格式
// $('div').addClass('div');

/**
 * 一般的书写格式:推荐
 */
// $(document).ready(function(){
//     $('div').addClass('Tom');
// });

$(document).ready(function () {
    // $("a").click(function () {
    //     // 此处写代码
    //     console.log($(this).index());
    //     //查找某个img的位置 siblings 所有的兄弟元素
    //     $('img').eq($(this).index()).css({ "opacity": "1" }).siblings().css({ "opacity": "0" });
    // })

    //过滤器child
    // $('details:first-child');//details下面的第一个子标签

    // $('details > p:first-child');//details下面的第一个子标签必须是p标签

    // $('details > p:nth-child(2)');//表示details下面的第2个子标签,nth-child(1) 则为第一个子标签

    // $('details > p:nth-last-child(2)');//表示倒数第二个子标签是p标签才能找到

    // $('details > p:only-child');//表示details只有一个子标签,而且只有p标签才能匹配

    // //过滤器type
    // $('details > p:first-of-type');
    // $('details > p:last-of-type');
    // $('details > p:nth-of-type(2)');//从1开始 even偶数2n, odd奇数2n+1,3n
    // $('details > p:nth-last-of-type(2)');
    // $('details > p:only-of-type');//表示detail只有一个p标签

    /**
     * 简化格式,不推荐 阅读性差
     */
    // $().ready(function(){

    // })

    // $(function(){

    // });

    //层级选择器

    // $('div a');// 祖先后代选择

    // $('div > a');//直接后代选择 

    // $('perv + next');//跟在perv 后面的next元素

    // $('perv ~ siblings');//匹配所有perv之后的,siblings元素,同一个层级下

    // //属性选择器
    // $('[class]');//属性名选择器 返回包含这个属性名的所有元素

    // //属性值选择器
    // $('[class=tool]');//属性值选择器 

    // //非属性值选择器
    // $('[class!=tool]');//该属性不等于tool的所有元素

    // $('[class^=tool]');//属性值头部=tool

    // $('[class$=tool]');//属性值尾部=tool

    // $('[class*=tool]');//属性值包含tool

    // $('[type][src]');//多个属性选择器 


    //表单相关
    // $(':input');
    // $(':enabled');
    // $(':disabled');
    // $(':checked');
    // $(':selected');

    //查找
    // var js = $('aside').find('.javascript');//查找aside的所有的元素
    // $('aside').children('details');//查找aside的下一级的元素
    // var jsParent = js.parent();//查找父元素
    // console.log(jsParent);

    // //next perv
    // var coffee = js.next();//下一个
    // var perv = js.prev();//上一个
    // var li = $('li');
    // li.eq(3);//第三个li
    // js.siblings();//js所有的兄弟元素

    // //过滤
    // li.filter('.python');// 过滤 expr:字符串值;object:现有的jQuery对象,匹配当前的元素;element:用于匹配元素的DOM元素;fn :函数用来作为测试元素的集合
    // li.filter(function(index){
    //     console.log(index);
    // })

});
