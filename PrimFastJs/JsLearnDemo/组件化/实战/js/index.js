(function ($) {
    //menu
    $('.menu').on('dropdown-show', function (e) {
        loadOnce($(this), buildMenuItem);
    }).dropdown({
        event: 'hover',
        css3: true,
        js: true,
    });
    function buildMenuItem(elem, data) {
        if (data.length === 0) return;
        var layer = elem.find('.dropdown-layer');
        var html = "";
        for (var i = 0; i < data.length; i++) {
            html += '<li><a href="' + data[i].url + '" target="_blank" class="menu-item">' + data[i].name + '</a></li>';
        }
        layer.html(html);
    }

    //search
    var search = $('#header-search');
    var html = "";
    var maxNum = 10;
    //订阅trigger事件
    search.on('search-getData', function (e, data) {
        var $this = $(this);
        html = createHeaderSearchLayer(data, maxNum);
        $this.search('appendLayer', html);
        if (html) {
            $this.search('showLayer');
        } else {
            $this.search('hideLayer');
        }
    }).on('search-noData', function (e) {
        $(this).search('hideLayer').search('appendLayer', '');
    }).on('click', '.search-layer-item', function () {
        search.search('setInputVal', $(this).html());
        search.search('submit');
    });

    function createHeaderSearchLayer(data, maxNum) {
        var html = "",
            result = data['result'],
            length = result.length;

        if (length === 0) {
            return '';
        }
        for (var i = 0; i < length; i++) {
            if (i >= maxNum) break
            html += '<li class="search-layer-item text-ellipsis" title=' + result[i][0] + '>' + result[i][0] + '</li>';
        }
        return html;
    }
    //search的调用方式
    search.search({
        url: 'https://suggest.taobao.com/sug?code=utf-8&_ksTS=1484204931352_18291&callback=jsonp18292&k=1&area=c2c&bucketid=6&q=',
        autocomplete: true,
        css: true,
        js: true,
        animate: 'slideUpDown',
        getDataInterval: 200
    });


    //category
    $('#focus-category').find(".dropdown")
        .on('dropdown-show', function (e) {
            loadOnce($(this), buildCategoryDetails);
        })
        .dropdown({
            css3: false,
            js: false
        });

    function buildCategoryDetails(elem, data) {
        if (data.length === 0) return;
        var html = "";
        for (var i = 0; i < data.length; i++) {
            html += '<dl class="category-detail cf">'
                + '<dt class="category-detail-title fl"><a href="###" target="_blank" class="category-detail-title-link">' + data[i].title + '</a></dt>'
                + '<dd class="category-detail-item fl">';
            for (var j = 0; j < data[i].items.length; j++) {
                html+=' <a href="###" target="_blank" class="link">'+data[i].items[j]+'</a>';
            }
            html+='</dd></dl>';
        }
        elem.find('.dropdown-layer').html(html);
    }


    /**
     * 按需加载模块获取数据
     */
    function loadOnce(elem, success) {
        var loadJson = elem.data('load');
        if (!loadJson) return;
        if (!elem.data('loaded')) {//判断是否已经加载过了
            elem.data('loaded', true);
            $.getJSON("json/" + loadJson)
                .done(function (data) {
                    console.log("loadOnce:" + data);
                    if (typeof success === 'function') {
                        success && success(elem, data);
                    }
                }).fail(function () {
                    elem.data('loaded', false);
                });
        }
    }

})(jQuery);