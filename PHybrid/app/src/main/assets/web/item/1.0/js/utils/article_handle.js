define(['jquery', 'tools', 'bridge'], function ($, Tools, Bridge) {
    'use strict';

    // var defaultImg = './images/default.png'

    var defaultImg = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAfQAAAEYAQMAAACQnpYwAAAAA1BMVEXv7+/sl7qNAAAAKElEQVQYGe3BMQEAAADCIPunXgwfYAAAAAAAAAAAAAAAAAAAAAAAXARGAAABXN6ADwAAAABJRU5ErkJggg==";

    /**
     * 替换文章中的视频为图片
     * @method videoConvertImg
     * @param {String} str 文章内容
     * @return {String} 返回将video替换为图片后的内容
     */
    function videoConvertImg(str) {
        $('.atc-html video').each(function () {
            var _src = $(this).data('src') || $(this).attr('src');
            var _poster = $(this).attr('poster');

            var is_vertical = $(this).data('vertical'); //is_vertical  0 是横着  1是竖着
            var is_Width = $(this).data('vwidth');
            var is_height = $(this).data('vheight');
            // var _videoHTML = `<img src="${defaultImg}" onerror = "this.src='${defaultImg}'" data-echo="${_poster}" data-video="${_src}"/>`;
            // onload 中的 class loaded 是为了使封面图加载完毕后再显示黑色蒙层 onload="this.parentNode.classList.add('loaded')"
            var _videoHTML = '<img src="' + _poster + '" onerror="this.src=\'' + defaultImg + '\'" data-video="' + _src + '" data-vertical="' + is_vertical + '" data-vw="' + is_Width + '" data-vh="' + is_height + '"/>';
            if ($(this).parent().hasClass('play-box')) {
                $(this).parent().addClass('j-video-container').addClass('video');
                $(this).before(_videoHTML).remove();
            } else {
                $(this).before('<section class="play-box video j-video-container">' + _videoHTML + '<i class="play-icon"></i></section>').remove();
            }
        });
    };

    /**
     * 替换文章内容的src变幻为data-echo
     * @param {String} contents 文章内容
     */
    function imgSrcToEcho(contents) {
        var getImgReg = /<img[^>]*[\s]+src=['"]([^'"]+)[^>]*>/gi,

        // getImgReg = /<img [^>]*src=['"]([^'"]+)[^>]*>/gi,
        // reEchoReg = /src=\"\S{1,}\"/gi,
        // reEchoReg = /src=[\'\"]?\S{1,}[\'\"]?/gi,
        reEchoReg = /src=[\'\"]?\S{1,}[\'\"]/gi,
            reEchoRegGif = /[^\s]+\.gif/gi,
            reGifReg = /[^\s]+\.gif('|")/gi,
            reDataSrc = /data-src\s*?=\s*?([\'\"])[\s\S]*?\1/,
            // 正则data-src;
        //reDataSrc = /data-src\s*?=\s*?([‘"])[\s\S]*?\1/,   // 正则data-src;
        reWidth = /\s+width\s*=/i,
            // 正则宽
        reHeight = /\s+height\s*=\s*[\'\"]?[\s\S]*?[\'\"]/i; // 正则高度

        /* 处理已有图片数据 begin */
        var image = rmrbApp.articleInfo.content_imgs || [];
        var imageUrl = image.map(function (item) {
            return item.pic ? item.pic.replace(/\?.*/, '') || item.pic : item.pic;
        });

        var screenWidth = rmrbApp.appInfo.screen.width - 30;
        var bodyWidth = document.body.clientWidth ? document.body.clientWidth - 30 : '';
        var _maxWidth = $('.image-width').width() || bodyWidth || screenWidth;

        /* 处理已有图片数据 begin */

        /* 2019-6-21 增加 onload="rmrbApp.resetDomHeight(this)"*/
        return contents.replace(getImgReg, function (match, capture, $3) {
            /* 处理图片宽度 2019-6-29 begin */
            var isW = '';
            var isH = '';
            var cleanCapture = capture.replace(/\?.*/, '') || capture; // 去掉路径后面的参数
            var hasImg = imageUrl.indexOf(cleanCapture);

            if (_maxWidth && hasImg > -1 && !match.match(reWidth) && image[hasImg].width && image[hasImg].height) {

                isW = image[hasImg].width;
                isH = image[hasImg].height;

                if (isW > _maxWidth) {
                    var b = (isW / _maxWidth).toFixed(4);
                    isW = _maxWidth;
                    isH = parseInt(isH / b);
                }
            }

            /* 处理图片宽度 2019-6-29 over */
            match = match.replace(reHeight, ''); // 去掉图片中的height=“”
            match = match.replace(reDataSrc, ''); // 去除富文本中原始data-src

            if (rmrbApp.appInfo.isWifi) {
                return match.replace(reEchoReg, 'src="' + defaultImg + '" data-img-slide data-echo="' + capture + '" onerror="this.src=\'' + defaultImg + '\'" onload="rmrbApp.resetDomHeight(this)" width="' + isW + '" height="' + isH + '"');
            } else {
                var _flag = capture.search(reEchoRegGif);
                if (_flag == -1) {
                    // jpg
                    if (rmrbApp.appInfo.loadImg) {
                        return match.replace(reEchoReg, 'src="' + defaultImg + '" data-echo="' + capture + '" data-img-slide  onerror="this.src=\'' + defaultImg + '\'" onload="rmrbApp.resetDomHeight(this)" width="' + isW + '" height="' + isH + '"');
                    } else {
                        return match.replace(reEchoReg, 'src="' + defaultImg + '" data-src="' + capture + '" data-img-slide  onerror="this.src=\'' + defaultImg + '\'" onload="rmrbApp.resetDomHeight(this)" width="' + isW + '" height="' + isH + '"');
                    }
                } else {
                    // gif 
                    // var innerImgReg = /(peopleapp.com|aliyuncs.com|rmrbtest-image.peopleapp.com|rmrbyfb.oss-cn-beijing.aliyuncs.com|rmrbcmsonline.peopleapp.com)/i;
                    var innerImgReg = /(peopleapp.com|pdnews.cn)/i;
                    var innerImg = innerImgReg.test(capture);
                    var gifDefault = !rmrbApp.appInfo.loadImg || !innerImg ? defaultImg : cleanCapture + '?x-oss-process=image/format,jpg';
                    return match.replace(reGifReg, 'src="' + gifDefault + '" data-img-slide data-gifffer="' + capture + '" onerror="this.src=\'' + defaultImg + '\'" onload="rmrbApp.resetDomHeight(this)" width="' + isW + '" height="' + isH + '"');
                }
            }
        });
    };

    /* wify 或是 loadImg 为true */
    function wifyOnload(e) {
        var isSrc = $(e).attr('src');
        $(e).attr('src', defaultImg).attr('data-src', isSrc);
        var inner = '<span class="ball-clip-rotate"><span></span></span>\
            <em class="loading-text">加载中...</em>\
            <em class="load-text">加载失败，请点击重试</em>';
        $(e).parent('.load-img-outer').removeClass('loaded').find('.loading-img-inner').html(inner);

        $('#app-article').undelegate('.load-img-outer', 'click', clickLoadImg);
        $('#app-article').delegate('.load-img-outer', 'click', clickLoadImg);
    }

    /**
     * 绑定视频播放事件
     */
    function bindVideoPlay() {
        $('#app-article').undelegate('.j-video-container', 'click', videoToNative);
        $('#app-article').delegate('.j-video-container', 'click', videoToNative);
    };

    /**
     * 视频点击callNative
     */
    function videoToNative() {
        rmrbApp.pauseMusic();
        var _videoPostion = $(this).get(0).getBoundingClientRect();
        var params = {
            top: $(this).offset().top,
            left: _videoPostion.left,
            width: _videoPostion.width,
            height: _videoPostion.height,
            cover: $(this).find('img').attr('src'),
            video_src: $(this).find('img').attr('data-video') || '',
            is_vertical: $(this).find('img').data('vertical'),
            video_width: $(this).find('img').data('vw'),
            video_height: $(this).find('img').data('vh')
        };
        Bridge.request('playVideoInfo', params);
    }

    /**
     * 重置文章内图片的宽高
     */
    function resetImgSize(_mw) {
        var screenWidth = rmrbApp.appInfo.screen.width - 30;
        var bodyWidth = document.body.clientWidth ? document.body.clientWidth - 30 : '';
        var maxWidth = $('.image-width').width() || bodyWidth || screenWidth;
        // $('.atc-html').find('img').each(function () {
        $('#app-article').find('img').each(function () {
            var _maxWidth = _mw || $(this).parent().width() || maxWidth;

            var w,
                h,
                that = $(this);
            w = that.attr('width') || that.attr('data-gifffer-width');
            h = that.attr('height') || that.attr('data-gifffer-height');

            if (!h && !w) return;

            that.css({
                width: '',
                height: ''
            });
            // if(w && !h){
            //     h = 'auto';
            // }
            // if(h && !w){
            //     w = 'auto';
            // }
            if (!w || !h) {
                w = 'auto';
                h = 'auto';
            };
            // if (w && !h || h && !w) {
            //     w = 'auto';
            //     h = 'auto';
            // };
            if (_maxWidth && w != 'auto' && w > _maxWidth) {
                var b = (w / _maxWidth).toFixed(4);
                w = _maxWidth;
                if (h != 'auto') {
                    h = parseInt(h / b);
                }
            }

            if (w) {
                that.attr('width', w);
            }
            if (h) {
                that.attr('height', h);
            }
        });
        rmrbApp.resetDomHeight('重设图片高度');
    };

    /**
     * 重置人民号动态图片宽高
     */
    function resetDynamicImgSize(images) {
        if (images.length) {
            if (images.length == 1) {
                // var _maxWidth = ($('.atc-header').width() || $('.politics-content').width()) * .66666;
                // 2019-7-23 改
                resetImgSize();
            }
        }
    };

    // 给img添我
    function imgAddLoading() {
        $("img[data-echo]").wrap('<span class="load-img-box"><span class="load-img-outer"></span></span>').after('<span class="loading-img-inner"><span class="ball-clip-rotate" style="display: block;"><span></span></span></span>');
    };

    /* 去除video、audio标签的src */
    function replaceMediaSrc(contents) {
        var getVideoReg = /<(video|audio)[^>]*[\s]+src=['"]([^'"]+)[^>]*>/gi,
            reEchoReg = /\s+src=[\'\"]?\S{1,}[\'\"]/gi;
        return contents.replace(getVideoReg, function (match, $1, $2) {
            return match.replace(reEchoReg, ' data-src=' + $2);
        });
    }

    /**
     * 
     * @param {String} contents 文章内容字符串
     */
    function initContents(contents) {
        var _contents = Tools.htmlDecode(contents);
        _contents = imgSrcToEcho(_contents);
        _contents = replaceMediaSrc(_contents);
        return _contents;
    };

    /**
     * 图片点击 call Native 
     */
    function imgEventSlide() {
        $('[data-img-slide]').on('click', function (e) {
            e.stopPropagation();
            e.preventDefault();
            if ($(this).attr('data-echo')) return; // 加载完毕后方可点击预览

            var _imgArr = [];

            // for (var i = 0; i < $('[data-img-slide]').length; i++) {
            //     var _element = $('[data-img-slide]').eq(i);
            //     if (_element.data('echo')) {
            //         _imgArr.push(_element.data('echo'));
            //     } else if (_element.data('gifffer')) {
            //         _imgArr.push(_element.data('gifffer'));
            //     } else if (_element.attr('data-src')) {
            //         _imgArr.push(_element.attr('data-src'));
            //     } else {
            //         _imgArr.push(_element.attr('src'));
            //     }
            // }

            /* 2019-7-4 更改数据结构（安卓需要图片的宽高) */
            for (var i = 0; i < $('[data-img-slide]').length; i++) {
                var _element = $('[data-img-slide]').eq(i);
                if (_element.data('echo')) {
                    _imgArr.push({
                        "pic": _element.data('echo'),
                        "width": _element.width(),
                        "height": _element.height()
                    });
                } else if (_element.data('gifffer')) {
                    _imgArr.push({
                        "pic": _element.data('gifffer'),
                        "width": _element.width(),
                        "height": _element.height()
                    });
                } else if (_element.attr('data-src')) {
                    _imgArr.push({
                        "pic": _element.attr('data-src'),
                        "width": _element.width(),
                        "height": _element.height()
                    });
                } else {
                    _imgArr.push({
                        "pic": _element.attr('src'),
                        "width": _element.width(),
                        "height": _element.height()
                    });
                }
            }
            var getImgs = rmrbApp.articleInfo.content_imgs || [];
            var imageUrl = getImgs.map(function (item) {
                return item.pic;
            });
            var _iteratorNormalCompletion = true;
            var _didIteratorError = false;
            var _iteratorError = undefined;

            try {
                for (var _iterator = _imgArr[Symbol.iterator](), _step; !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
                    var item = _step.value;

                    var isPic = item.pic.replace(/\?.*/, '') || item.pic; // 去掉路径后面的参数
                    if (imageUrl.indexOf(isPic) > -1) {
                        item.width = getImgs[imageUrl.indexOf(isPic)].width;
                        item.height = getImgs[imageUrl.indexOf(isPic)].height;
                    }
                }
            } catch (err) {
                _didIteratorError = true;
                _iteratorError = err;
            } finally {
                try {
                    if (!_iteratorNormalCompletion && _iterator.return) {
                        _iterator.return();
                    }
                } finally {
                    if (_didIteratorError) {
                        throw _iteratorError;
                    }
                }
            }

            var _params = {
                imgIndex: $('[data-img-slide]').index($(this)) + 1,
                imgArr: _imgArr,
                position: $(this).offset().top - $(window).scrollTop(),
                positionX: $(this).offset().left,
                width: $(this).width(),
                height: $(this).height()
            };
            // console.log(_params);
            Bridge.request('clickImgSlide', _params);
        });
    };

    /**
     * 改变文字大小
     */
    function changeFontSize(scale) {
        // if (!scale || scale == '1') return;
        if (!scale) return;
        var oParent = document.querySelectorAll('[data-font-size]');
        for (var m = 0; m < oParent.length; m++) {
            var oChildren = oParent[m].querySelectorAll('*'),

            // fontSize = scale * oParent[m].getAttribute('data-font-size') + 'px';
            // _fontBeilv 定义在article.js中，是为了让详情的字号可以自适应
            fontSize = Math.ceil((_fontBeilv || 1) * scale * oParent[m].getAttribute('data-font-size')) + 'px';
            oParent[m].style.fontSize = fontSize;
            oParent[m].setAttribute('set-size', fontSize);
            for (var i = 0; i < oChildren.length; i++) {
                if (oChildren[i].getAttribute('data-unfontsize') == null) {
                    oChildren[i].style.fontSize = fontSize;
                }
            }
        }
        // 改变字号后，告诉Native并传高度
        // Bridge.request('webviewResize', {
        //     offsetHeight: document.body.offsetHeight,
        //     clientHeight: document.body.clientHeight
        // });
        //rmrbApp.webviewResize();  // 2019-6-21 改
        rmrbApp.resetDomHeight('修改字体'); // 2019-6-21 改
    };

    /**
     * 禁止主动加载图片的时候处理逻辑
     */
    function showLoadImg(loadImg) {
        var $images = loadImg ? $('[data-gifffer]') : $('[data-img-slide]');
        $images.each(function () {

            var _type = $(this).attr('data-gifffer') ? 'GIF' : '原图';

            // var box = document.createElement('span'),
            //     outer = document.createElement('span'),
            //     _size = _type == 'GIF' ? '<em class="load-gif-size">' + setImgSize($(this).attr("data-fileSize")) + '</em>' : '',
            //     inner = '<span class="loading-img-inner">\
            //                 <span class="ball-clip-rotate"><span></span></span>\
            //                 <em class="loading-text">加载中...</em>\
            //                 <em class="load-text">' + _size + '点击查看' + _type + '</em>\
            //             </span>';
            // box.className = 'load-img-box';
            // outer.className = 'load-img-outer';
            // $(this).wrap(box).wrap(outer).after(inner);


            var box = document.createElement('span'),
                outer = document.createElement('span'),
                inner = '<span class="loading-img-inner">\
                            <span class="ball-clip-rotate"><span></span></span>\
                            <em class="loading-text">加载中...</em>\
                            <em class="load-text">点击查看' + _type + '</em>\
                        </span>';

            if (_type == 'GIF') {
                inner = '<span class="loading-img-inner">\
                            <span class="ball-clip-rotate"><span></span></span>\
                            <em class="load-text">GIF</em>\
                        </span>';
            }

            box.className = 'load-img-box';
            outer.className = _type == 'GIF' ? 'load-img-outer load-img-gif' : 'load-img-outer';
            $(this).wrap(box).wrap(outer).after(inner);
        });
        $('#app-article').undelegate('.load-img-outer', 'click', clickLoadImg);
        $('#app-article').delegate('.load-img-outer', 'click', clickLoadImg);
    };

    /**
     * 点击加载图片
     */
    function clickLoadImg() {
        var that = $(this);
        if (that.hasClass('loading')) return;
        that.addClass('loading');
        var $img = that.find('img');
        var _img = new Image();
        // _img.crossOrigin = "Anonymous";
        // _img.setAttribute('crossOrigin', 'anonymous')
        var _src = $img.attr('data-gifffer') ? 'data-gifffer' : 'data-src';
        _img.onload = function () {
            // $img.attr('width', '').attr('height', '').attr('src', $img.attr(_src)).removeAttr(_src); TODO 2019-6-25
            $img.attr('src', $img.attr(_src)).removeAttr(_src);
            that.addClass('loaded').removeClass('loading');
            // 图片加载后，告诉Native并传高度
            // rmrbApp.webviewResize();  2019-6-21 改
        };
        _img.onerror = function () {
            that.removeClass('loading');
            that.find('.load-text').text('加载失败，请点击重试');
            if (that.hasClass('load-img-gif')) {
                that.removeClass('load-img-gif');
            }
        };
        _img.src = $img.attr(_src);
    }

    /**
     * 计算size
     * @param {*} size （单位KB）
     */
    function setImgSize(size) {
        var _size = parseInt(size);
        if (_size) {
            if (_size <= 1) {
                return "1k";
            } else if (_size < 1024 && _size > 1) {
                return _size + "K";
            } else if (_size < 1024 * 1024 && _size > 1024) {
                return (_size / 1024).toFixed(2) + "M";
            } else {
                return (_size / (1024 * 1024)).toFixed(2) + "G";
            }
        } else {
            return "";
        }
    }

    return {
        initContents: initContents,
        videoConvertImg: videoConvertImg,
        bindVideoPlay: bindVideoPlay,
        resetImgSize: resetImgSize,
        resetDynamicImgSize: resetDynamicImgSize,
        imgEventSlide: imgEventSlide,
        changeFontSize: changeFontSize,
        showLoadImg: showLoadImg,
        imgAddLoading: imgAddLoading,
        wifyOnload: wifyOnload
    };
});