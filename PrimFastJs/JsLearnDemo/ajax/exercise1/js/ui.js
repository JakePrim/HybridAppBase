
$.fn.UISearch = function(){
    var ui = $(this);//ui - ui-search
    //在ui的元素内 查找ui-search-select元素
    $('.ui-search-select',ui).on('click',function(){
        $('.ui-search-select-list',ui).show();
        //如果加上return 事件会一直向下传递,直到传递到body 然后执行body点击事件又隐藏了
        return false;
    });
    $('.ui-search-select-list a',ui).on('click',function () {
        $('.ui-search-select',ui).text($(this).text());//赋值
        $('.ui-search-select-list',ui).hide();
        return false; 
    });
    $('body').on('click',function(){
        $('.ui-search-select-list',ui).hide();
    });
}

/**
 * @param {string} header TAB 组件选项卡切换部分className,里面有若干个.item
 * @param {string} content TAB 组件的内容区域部分className,里面有若干个 .item
 * @param {string} focus_prefix 选中前缀
 */
$.fn.UITab = function(header,content,focus_prefix) {
    var ui = $(this);
    var tabs = $(header,ui);
    var cons = $(content,ui);
    var focus_prefix = focus_prefix || '';
    tabs.on('click',function(){
        //点击的是哪个item
        console.log('UITab');
        var index = $(this).index();
        tabs.removeClass(focus_prefix+'item-focus').eq(index).addClass(focus_prefix+'item-focus');
        cons.hide().eq(index).show();
        return false;
    });
}

//滑动显示到顶部
$.fn.UIBackTop = function(){
    var ui = $(this);
    var el = $('<a class = "ui-back-top" href="#0"></a>');
    ui.append(el);
    var windowHeight = $(window).height()/2;
    $(window).on("scroll", function() {
        //获取滚动的高度 有些浏览器不兼容body,有的浏览器需要使用html来进行兼容. 两个一块使用就可以获取到高度了
        var top = $('html,body').scrollTop();
        console.log("top:"+top);
        if(top > windowHeight){
            el.show();
        }else{
            el.hide();
        }
    });
    //点击回到顶部
    el.on('click',function(){
        $(window).scrollTop(0);
    });
}

//UI-slider
/**
 * 1.左右箭头可以控制翻页
 * 2.翻页时进度联动
 * 3.翻到最后一页需要回到第一页
 * 4.进度点,在点击到时候需要切换到对应的页面
 * 5.自动滚动
 * 6.滚动过程中,屏蔽其他操作:自动滚动 左右翻页 进度点点击
 * 7. 无缝滚动
 */
$.fn.UISlider = function () {
    var ui = $(this);
    var warp = $('.ui-slider-warp',ui);
    var items = $('.ui-slider-warp .item',ui);
    var btn_prev = $('.ui-slider-arrow .left',ui);
    var btn_next = $('.ui-slider-arrow .right',ui);
    var tips = $('.ui-slider-process .item',ui);

    // 默认显示第0个
    var current = 0;
    var size = items.length;//表示有几个图片 在jquery 1.8中已经不在使用size() 而是使用length
    var enableAuto = true;
    var width = items.eq(0).width();

    //自动滚动感应
    ui.on('mouseover',function(){
        enableAuto = false;

    });

    ui.on('mouseout',function(){
        enableAuto = true;
    });

    //自定义事件操作
    warp.on('move_perv',function(){
        if(current <=0){
            current = size;
        }
        current = current-1;
        warp.triggerHandler('move_to',current);
    })
    .on('move_next',function(){
        if(current >= size-1){
            current = -1;
        }
        current = current+1;
        warp.triggerHandler('move_to',current);
    })
    .on('move_to',function(evt,index){
        warp.css('left',index*width*-1);
        tips.removeClass('item-focus').eq(index).addClass('item-focus');//注意添加class选择器的时候不需要.
    })
    .on('auto_move',function () { 
        setInterval(function(){
            enableAuto && warp.triggerHandler('move_next');
        },2000);//setTimeout会触发一次,如果需要持续触发使用setInterval 每隔两秒就会触发
    })
    .triggerHandler('auto_move');//自动开启

    //具体的事件
    btn_prev.on('click',function(){
        warp.triggerHandler('move_perv');
    });
    btn_next.on('click',function(){
        warp.triggerHandler('move_next');
    });
    tips.on('click',function(){
        var index = $(this).index();
        warp.triggerHandler('move_to',index);
    });
}

//ui-cascading
$.fn.UICascading = function () {
    var ui = $(this);
    var selects  = $('select',ui);//拿到所有的下拉元素
    selects.on('change',function(){
        var val = $(this).val();
        var index = selects.index(this);
        console.log("val:"+val+" index:"+index);
        //触发下一个select 的更新,根据当前的值
        var where = $(this).attr('data-where');
        where = where ? where.split(','):[];
        where.push(val);
        selects.eq(index+1).attr('data-where',where.join(',')).triggerHandler('reloadOptions');
        //触发下一个之后的select 的初始化 清除不应该的数据项
        ui.find('select:gt('+(index+1)+')').each(function(){
            console.log('find select:');
            $(this).attr('data-where','').triggerHandler('reloadOptions');
        });
    })
    .on('reloadOptions',function(){
        console.log("reloadOptions");
        //获取方法名
        var method = $(this).attr('data-search');
        //获取参数名
        var args = $(this).attr('data-where').split(',');
        var data = AjaxRemoteGetData[ method ].apply(this,args);
        //关于 apply的用法
        //func.apply(thisArg, [argsArray])
        //thisArg:在 func 函数运行时使用的 this 值。请注意，
        //this可能不是该方法看到的实际值：如果这个函数处于非严格模式下，则指定为 null 或 undefined 时会自动替换为指向全局对象，原始值会被包装。
        //argsArray:一个数组或者类数组对象，其中的数组元素将作为单独的参数传给 func 函数。
        //如果该参数的值为 null 或  undefined，则表示不需要传入任何参数。从ECMAScript 5 开始可以使用类数组对象
        var select = $(this);
        select.find('option').remove();//移除默认的option 添加到网络请求的option
        //jquery each的用法
        $.each(data,function(i,item){
            var el = $('<option value="'+item+'">'+item+'</option>');
            select.append(el);
        });
    });
    selects.eq(0).triggerHandler('reloadOptions');//默认加载第一个
}

/**
 * 页面的脚本逻辑,页面加载完毕后 执行function内的脚本
 */
$(function(){
    //选择ui-search dom,然后调用UISearch()方法 自定义的方法
    $('.ui-search').UISearch();
    //使用子代选择符 >,它只会选择在元素下的子代选择符. 而常用的空格是子孙选择符 会选择元素包裹下的所有的选择的元素
    //对每个item 添加click事件
    $('.tab').UITab('.caption > .item','.block > .item');
    $('.tab .block .item').UITab('.block-caption > a', '.block-content > .block-warp','block-caption-');
    //顶部滑动
    $('body').UIBackTop();
    $('.ui-slider').UISlider();//注意:如果是类选择器需要加上.
    $('.ui-cascading').UICascading();
});