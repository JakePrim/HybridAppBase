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

$(document).ready(function() {
    $("a").click(function() {
        // 此处写代码
        console.log($(this).index());
        $('img').eq($(this).index()).css({"opacity":"1"}).siblings().css({"opacity":"0"});
    })
})

/**
 * 简化格式,不推荐 阅读性差
 */
// $().ready(function(){

// })

// $(function(){

// });