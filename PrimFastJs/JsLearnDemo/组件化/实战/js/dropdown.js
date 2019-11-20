// 下拉菜单模块
// 不要暴露在全局作用域下 使用局部的函数
(function ($) {
    'use strict'
    $.fn.extend({
        dropdown: function () {
            return this.each(function () {
                dropdown(this);
            });
        }
    });

    function dropdown(elem) {
        var ui = $(elem),
            activeClass = ui.data('active') + '-active';
        ui.hover(function () {
            ui.addClass(activeClass);// 获取自定义的属性 data-active,注意key不用data-active而是使用active
        }, function () {
            ui.removeClass(activeClass);
        });
    }
})(jQuery);
