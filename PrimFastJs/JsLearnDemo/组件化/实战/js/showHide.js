(function ($) {
    function init(elem, hiddenCallback) {
        if (elem.is(':hidden')) {//判断元素是否显示 判断选择的元素是否匹配选择器 :hidden是一个选择器 用于选取隐藏的元素
            elem.data('status', 'hidden');
            if (typeof (hiddenCallback) === 'function') hiddenCallback && hiddenCallback();
        } else {
            elem.data('status', 'shown');
        }
    }

    function show(elem, callback) {
        if (elem.data('status') === 'show') return;
        if (elem.data('status') === 'shown') return;
        elem.data('status', 'show').trigger('show');
        callback && callback();
    }

    function hide(elem, callback) {
        if (elem.data('status') === 'hide') return;
        if (elem.data('status') === 'hidden') return;
        elem.data('status', 'hide').trigger('hide');
        callback && callback();
    }

    var silent = {
        init: init,
        //传递jquery对象
        show: function (elem) {
            show(elem, function () {
                elem.show();
                elem.data('status', 'shown').trigger('shown');//发布显示完毕事件
            });
        },
        hide: function (elem) {
            hide(elem, function () {
                elem.hide();
                elem.data('status', 'hidden').trigger('hidden');
            });
        }
    };

    var transitionEnd = window.mt.transitionEnd;

    var css3 = {
        fade: {
            init: function (elem) {
                css3._init(elem, 'fadeOut');
            },
            show: function (elem) {
                css3._show(elem, 'fadeOut');
            },
            hide: function (elem) {
                css3._hide(elem, 'fadeOut');
            }
        },
        slideUpDown: {
            init: function (elem) {
                elem.height(elem.height());
                css3._init(elem, 'slideUpDown');
            },
            show: function (elem) {
                css3._show(elem, 'slideUpDown');
            },
            hide: function (elem) {
                css3._hide(elem, 'slideUpDown');
            }
        },
        slideLeftRight: {
            init: function (elem) {
                elem.width(elem.width());
                css3._init(elem, 'slideLeftRight');
            },
            show: function (elem) {
                css3._show(elem, 'slideLeftRight');
            },
            hide: function (elem) {
                css3._hide(elem, 'slideLeftRight');
            }
        },
        slideFadeUpDown: {
            init: function (elem) {
                elem.height(elem.height());
                css3._init(elem, 'slideUpDown fadeOut');
            },
            show: function (elem) {
                css3._show(elem, 'slideUpDown fadeOut');
            },
            hide: function (elem) {
                css3._hide(elem, 'slideUpDown fadeOut');
            }
        },
        slideFadeLeftRight: {
            init: function (elem) {
                elem.width(elem.width());
                css3._init(elem, 'slideLeftRight fadeOut');
            },
            show: function (elem) {
                css3._show(elem, 'slideLeftRight fadeOut');
            },
            hide: function (elem) {
                css3._hide(elem, 'slideLeftRight fadeOut');
            }
        },
    };
    //_ 表示供内部使用 外部无法调用
    css3._init = function (elem, className) {
        elem.addClass('transition');
        //对元素进行初始化,如果是隐藏的状态 css动画样式需要提前添加,当移除动画样式时会执行css动画
        init(elem, function () {
            elem.addClass(className);
        });
    }
    css3._show = function (elem, className) {
        show(elem, function () {
            //off 先off掉
            elem.off(transitionEnd.name).one(transitionEnd.name, function () {//one 只绑定一次 完毕之后自动销毁
                elem.data('status', 'shown').trigger('shown');
            });
            elem.show();//先显示元素 再做动画
            setTimeout(function () {
                elem.removeClass(className);
            }, 20);
        });
    }
    css3._hide = function (elem, className) {
        hide(elem, function () {
            //监听动画执行完成
            elem.off(transitionEnd.name).one(transitionEnd.name, function () {
                elem.hide();
                elem.data('status', 'hidden').trigger('hidden');
            });
            elem.addClass(className);
        });
    }
    var showHideJs = {
        fade: {
            init: function (elem) {
                showHideJs._init(elem);
            },
            show: function (elem) {
                showHideJs._show(elem, 'fadeIn');
            },
            hide: function (elem) {
                showHideJs._hide(elem, 'fadeOut');
            }
        },
        slideUpDown: {
            init: function (elem) {
                showHideJs._init(elem);
            },
            show: function (elem) {
                showHideJs._show(elem, 'slideDown');
            },
            hide: function (elem) {
                showHideJs._hide(elem, 'slideUp');
            }
        },
        slideLeftRight: {
            init: function (elem) {
                showHideJs._customInit(elem, {
                    'width': 0,
                    'padding-left': 0,
                    'padding-right': 0
                });
            },
            show: function (elem) {
                showHideJs._customShow(elem);
            },
            hide: function (elem) {
                showHideJs._customHide(elem);
            }
        },
        slideFadeUpDown: {
            init: function (elem) {
                showHideJs._customInit(elem, {
                    'opacity': 0,
                    'height': 0,
                    'padding-top': 0,
                    'padding-bottom': 0
                });
            },
            show: function (elem) {
                showHideJs._customShow(elem);
            },
            hide: function (elem) {
                showHideJs._customHide(elem);
            }
        },
        slideFadeLeftRight: {
            init: function (elem) {
                showHideJs._customInit(elem, {
                    'opacity': 0,
                    'width': 0,
                    'padding-left': 0,
                    'padding-right': 0
                });
            },
            show: function (elem) {
                showHideJs._customShow(elem);
            },
            hide: function (elem) {
                showHideJs._customHide(elem);
            }
        },
    };
    showHideJs._init = function (elem, hiddenCallback) {
        //需要注意在选中的元素中添加.transition样式,这样会导致jquery动画不会执行,所以为了以防万一 进行移除
        elem.removeClass('transition');
        init(elem, hiddenCallback);
    }
    showHideJs._customInit = function (elem, options) {
        var styles = {};
        for (var p in options) {
            styles[p] = elem.css(p);
        }
        //将数据保存到elem
        elem.data('styles', styles);
        showHideJs._init(elem, function () {
            elem.css(options);
        });
    }
    showHideJs._show = function (elem, mode) {
        show(elem, function () {
            //jquery 自带的封装 注意在执行动画之前 打断该元素上的所有的动画,执行新的动画
            elem.stop()[mode](function () {
                elem.data('status', 'shown').trigger('shown');
            });
        });
    }
    showHideJs._customShow = function (elem) {
        elem.show();
        show(elem, function () {
            elem.stop().animate(elem.data('styles'), function () {
                elem.data('status', 'shown').trigger('shown');
            });
        });
    }
    showHideJs._hide = function (elem, mode) {
        hide(elem, function () {
            //jquery 自带的封装
            elem.stop()[mode](function () {
                elem.data('status', 'hidden').trigger('hidden');
            });
        });
    }
    showHideJs._customHide = function (elem) {
        var styles = elem.data('styles');
        var options = {};
        for (var p in styles) {
            options[p] = 0;
        }
        hide(elem, function () {
            elem.stop().animate(options, function () {
                elem.hide();
                elem.data('status', 'hidden').trigger('hidden');
            });
        });
    }

    var defaults = {
        css3: false,
        js: false,
        animate: 'fade'
    }

    function showHide(elem, options) {
        //将传递过来的options 与 defaults 相同的属性覆盖 defaults,返回一个新的对象
        options = $.extend({}, defaults, options);
        console.log(options);
        var mode;
        if (options.css3 && transitionEnd.isSupport) {
            //获取到要调用的css3的动画方法,如果动画不存在就使用默认的动画 
            mode = css3[options.animate] || css3[defaults.animate];
        } else if (options.js) {
            mode = showHideJs[options.animate] || showHideJs[defaults.animate];
        } else {
            mode = silent;
        }
        console.log("mode:"+mode);
        mode.init(elem);
        return {
            //$.proxy 将参数设置进入,不用再次传递参数了
            show: $.proxy(mode.show, this, elem),
            hide: $.proxy(mode.hide, this, elem)
        }
    }
    //将其封装为一个插件 对外提供调用方法
    $.fn.extend({
        showHide: function (option) {
            return this.each(
                function () {
                    var elem = $(this);
                    var options = $.extend({}, defaults, typeof option === 'object' && option);
                    var mode = elem.data('showHide');
                    if (!mode) {//执行过一次就不需要再去执行了
                        elem.data('showHide', mode = showHide(elem, options))
                    }
                    if (typeof mode[option] === 'function') {
                        mode[option]();
                    }
                }
            );
        }
    });
})(jQuery);
