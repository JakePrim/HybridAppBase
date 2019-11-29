/**
 * 使用面向对象的方式实现 封装搜索组件
 */
(function ($) {
    'use strict';

    var cache = {
        data:{},
        count:0,
        addData:function (key,data) {
            this.data[key] = data;
            this.count++;
        },
        removeDataByKey:function (key) {
            delete this.data[key];
            this.count--;
        },
        getData:function (key) {
            return this.data[key];
        }
    };

    function Search(elem, options) {
        this.elem = elem;
        this.options = options;
        this.input = this.elem.find('.search-inputbox');
        this.layer = this.elem.find('.search-layer');
        this.from = this.elem.find('.search-from');
        this.loadedLayer = false;
        //通过事件代理button事件 注意将this的问题 
        this.elem.on('click', '.search-btn', $.proxy(this.submit, this));
        if (this.options.autocomplete) {
            this.autocomplete();
        }
    }
    //默认参数配置
    Search.DEFAULT = {
        url: '',
        autocomplete: false,
        css: false,
        js: false,
        animate: 'fade',
        getDataInterval: 200
    }

    //通用方法
    /**
     * 提交
     */
    Search.prototype.submit = function () {
        if (this.getInputVal() === '') {
            return false;
        }
        this.from.submit();
    }

    /**
     * 自动获取数据绑定事件
     */
    Search.prototype.autocomplete = function () {
        console.log("autocomplete:" + this.input);
        //减少每次输入不必要的网络请求
        var timer = null;
        var self = this;
        this.input
            .on('input', function () {
                if (self.options.getDataInterval) {
                    clearTimeout(timer);
                    //每过200ms根据输入的值请求一次网络数据,防止快速输入值请求次数过多
                    timer = setTimeout(function () {//定时器排队 异步执行
                        self.getData();
                    }, self.options.getDataInterval);
                } else {
                    self.getData();
                }
            })
            .on('focus', $.proxy(this.showLayer, this))
            .on('click', function () {
                // 防止事件冒泡 阻止事件
                return false;
            });
        //点击页面空白消失 需要注意事件冒泡
        $(document).on('click', $.proxy(this.hideLayer, this));
        //使用showHide.js 初始化下拉层 传递配置和动画属性
        this.layer.showHide(this.options);
    }

    /**
     * 获取数据
     */
    Search.prototype.getData = function () {
        var self = this;//保存this
        var value = encodeURIComponent(this.getInputVal());
        if (value === '') return self.elem.trigger('search-noData');
        if(cache.getData(value)){
            console.log("获取缓存数据");
            return self.elem.trigger('search-getData', [cache.getData(value)]);
        }

        //每进行一个请求都应该 如果上一个请求没有完成则终止上一个请求
        if (this.jqXHR) {
            this.jqXHR.abort();
        }
        this.jqXHR = $.ajax({
            url: this.options.url + encodeURIComponent(this.getInputVal()),
            timeout: 5000,
            dataType: "jsonp",
        }).done(function (response) {//使用异步的方式 避免回调
            console.log("response:" + response);
            //发送成功订阅
            self.elem.trigger('search-getData', [response]);
            cache.addData(value,response);
        }).fail(function (error) {
            //发送订阅
            //发送失败订阅
            self.elem.trigger('search-noData');
        }).always(function () {
            self.jqXHR = null;
        });
    }

    /**
     * 显示下拉层
     */
    Search.prototype.showLayer = function () {
        if (!this.loadedLayer) {
            return;
        }
        //使用showHide.js 显示和隐藏
        this.layer.showHide('show');
    }

    /**
     * 隐藏下拉层
     */
    Search.prototype.hideLayer = function () {
        //使用showHide.js 显示和隐藏
        this.layer.showHide('hide');
    }

    Search.prototype.appendLayer = function (html) {
        //使用showHide.js 显示和隐藏
        this.layer.html(html);
        this.loadedLayer = !!html; // !!是将变为boolean类型
    }

    /**
     * 获取输入框输入的值
     */
    Search.prototype.getInputVal = function () {
        return $.trim(this.input.val());
    }

    /**
     * 设置输入框的值
     */
    Search.prototype.setInputVal = function (value) {
        this.input.val(removeHtmlTags(value));
        function removeHtmlTags(str) {
            return str.replace(/<(?:[^>'"]|"[^"]*"|'[^']*')*>/g, "");
        };
    }

    /**
     * 对外提供方法
     */
    $.fn.extend({
        search: function (option, value) {
            return this.each(function () {
                var search = $(this).data('search');
                var options = $.extend({}, Search.DEFAULTS, $(this).data(),
                    typeof option === 'object' && option);
                if (!search) {//防止每次调用 都要创建对象 使用单例
                    $(this).data('search', search = new Search($(this), options));
                }
                // var dropdown = new Dropdown(this, options);//实例化了多次
                if (typeof search[option] === 'function') {
                    search[option](value);
                }
            });
        }
    });


})(jQuery);



// (function ($) {
//     'use strict'

//     var ui = $('.search');
//     var input = ui.find('.search-inputbox');
//     var btn = ui.find('.search-btn');
//     var layer = ui.find('.search-layer');
//     var from = ui.find('.search-from');
//     //在from表单触发提交事件 判断输入框是否为空
//     from.on('submit', function () {
//         if ($.trim(input.val()) === '') {
//             return false;
//         }
//     });

//     input.on('input', function () {
//         var url = 'https://suggest.taobao.com/sug?code=utf-8&_ksTS=1484204931352_18291&callback=jsonp18292&k=1&area=c2c&bucketid=6&q='
//             + encodeURIComponent($.trim(input.val()));//进行编码 encodeURIComponent 防止错误编码导致请求失败
//         $.ajax({
//             url: url,
//             timeout: 5000,
//             dataType: "jsonp",
//         }).done(function (response) {//使用异步的方式 避免回调
//             console.log(response);
//             var html = "",
//                 data = response['result'],
//                 length = data.length,
//                 maxNum = 10;

//             if (length === 0) {
//                 layer.hide().html('');
//                 return;
//             }
//             for (var i = 0; i < length; i++) {
//                 if (i >= maxNum) break
//                 html += '<li class="search-layer-item text-ellipsis" title=' + data[i][0] + '>' + data[i][0] + '</li>';
//             }
//             layer.html(html).show();
//         }).fail(function (error) {
//             console.log(error);
//             layer.hide().html('');
//         }).always(function () {
//             console.log("不管成功失败都会调用");
//         });
//     });

//     //通过jquery的事件代理来实现 不确定的layer-item的点击 ,能够冒泡的才可以事件代理
//     layer.on('click', '.search-layer-item', function () {
//         input.val(removeHtmlTags($(this).html()));//给输入框设置值
//         from.submit();//提交表单
//     });

//     function removeHtmlTags(str) {
//         return str.replace(/<(?:[^>'"]|"[^"]*"|'[^']*')*>/g, "");
//     };

//     //隐藏下拉层 炸一看类似 获取焦点和失去焦点 但是点击item失效 blur按下鼠标触发 和click 松开鼠标触发 冲突了
//     // input.on('focus',function(){
//     //     layer.show();
//     // }).on('blur',function(){
//     //     layer.hide();
//     // });
//     input.on('focus', function () {
//         layer.show();
//     }).on('click',function(){
//         // 防止事件冒泡 阻止事件
//         return false;
//     });
//     //点击页面空白消失 需要注意事件冒泡
//     $(document).on('click', function () {
//         layer.hide();
//     });
// })(jQuery);