// 下拉菜单模块
// 不要暴露在全局作用域下 使用局部的函数
(function ($) {
    'use strict'
    /**
     * 通过构造函数的形式封装供外部调用
     */
    function Dropdown(elem, options) {
        this.ui = $(elem);
        this.activeClass = options.active + '-active';
        console.log("activeClass:"+this.activeClass);
        this.showHideElem = this.ui.find('.dropdown-layer');
        this.options = options;
        this.init();
    }

    Dropdown.DEFAULTS = {
        event: 'hover',
        css3: false,
        js: false,
        animate: 'fade',
        active: 'dropdown',
        delay: 0
    };

    Dropdown.prototype.init = function () {
        var self = this;
        this.showHideElem.showHide(this.options);
        this.showHideElem.on('show shown hide hidden', function (e) {
            self.ui.trigger('dropdown-' + e.type);
        });
        if (this.options.event === 'click') {
            this.ui.on('click', function (e) {
                self.show();
                e.stopPropagation();//防止事件冒泡
            });
            $(document).on('click', $.proxy(this.hide, this));
        } else {
            // 修改指向的this
            this.ui.hover($.proxy(this.show, this), $.proxy(this.hide, this));
        }
    }

    //将通用 show 和 hide方法放到原型链中
    Dropdown.prototype.show = function () {
        var self = this;
        //显示的时候加一个延时,否则滑动太快菜单也会显示出来
        if (this.options.delay) {
            this.timer = setTimeout(function () {
                _show();
            }, this.options.delay);
        } else {
            _show();
        }
        function _show() {
            self.ui.addClass(self.activeClass);// 获取自定义的属性 data-active,注意key不用data-active而是使用active
            self.showHideElem.showHide('show');
        }
    };

    Dropdown.prototype.hide = function () {
        if (this.options.delay) {
            clearTimeout(this.timer);
        }
        this.ui.removeClass(this.activeClass);
        this.showHideElem.showHide('hide');
    };
    /**
     * 通过jquery插件的形式供外部调用 
     */
    $.fn.extend({
        dropdown: function (option) {
            return this.each(function () {
                var dropdown = $(this).data('dropdown');
                console.log("data:"+$(this).data());
                var options = $.extend({}, Dropdown.DEFAULTS, $(this).data(),
                    typeof option === 'object' && option);
                if(!dropdown){//防止每次调用 都要创建对象 使用单例
                    $(this).data('dropdown',dropdown = new Dropdown($(this), options));
                }
                // var dropdown = new Dropdown(this, options);//实例化了多次
                console.log(typeof dropdown[option]);
                if(typeof dropdown[option] === 'function'){
                    dropdown[option]();
                }
            });
        }
    });

    function dropdown(elem, options) {
        var ui = $(elem),
            activeClass = ui.data('active') + '-active';
        var showHideElem = ui.find('.dropdown-layer');
        showHideElem.showHide(options);
        ui.hover(function () {
            ui.addClass(activeClass);// 获取自定义的属性 data-active,注意key不用data-active而是使用active
            showHideElem.showHide('show');
        }, function () {
            ui.removeClass(activeClass);
            showHideElem.showHide('hide');
        });
    }
})(jQuery);
