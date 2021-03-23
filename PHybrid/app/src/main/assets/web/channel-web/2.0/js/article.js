var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

require.config({
    baseUrl: './js/',
    paths: {
        jquery: "libs/jquery-1.12.4.min",
        vue: 'libs/vue.min',
        bridge: 'libs/bridge',
        tools: 'utils/tools',
        articleHandle: 'utils/article_handle',
        filters: 'utils/filters',
        audiojs: 'utils/audio'
    }
});

require(['vue', 'bridge', 'tools', 'articleHandle', 'filters', 'audiojs'], function (Vue, Bridge, Tools, articleHandle, Filter, audiojs) {
    /**
     * 定义全局字体倍率系数
     * 默认375情况下为1，屏幕宽度相较于 375 的倍数（取小数点后3位）作为标题、详情内容字号的倍数
     */
    var _fontBeilv = window.innerWidth / 375 > 1 ? (window.innerWidth / 375).toFixed(3) : 1 || 1;
    window._fontBeilv = _fontBeilv > 2 ? 2 : _fontBeilv;

    window.rmrbApp = new Vue({
        el: '#app-article',
        data: {
            importantClause: "特别声明：本内容为人民日报新媒体平台“人民号”作者上传并发布，仅代表作者观点。人民日报仅提供信息发布平台。",
            loadArticle: false,
            loadDynamic: false,
            scrollTop: 0,
            canEcho: false,
            appInfo: {},
            articleInfo: {
                gov: []
            },
            dynamic: {
                // is_gov: '0'
            },
            contents: '',
            defaultHtml: '',
            followLoading: false,
            stopPushHeight: false, // 停止推送高度
            hasPushHeight: false, // 是否已经开启高度推送
            pushDomNum: 0, // 推送高度次数
            pushTimer: null,

            voteBg: ['#D8E043', ' #FFD257', '#A5DD9D', '#51D7CC', '#A0D6EB', '#AFB7D0', '#A48FC3', '#EBA0B3', '#FEC6B5', '#FED194'],
            voteLoading: true
        },
        computed: {
            /**
             * 计算帮的百分比
             */
            help_percent: function help_percent() {
                var _percent = parseInt(this.articleInfo.love_num / this.articleInfo.aim_love_num * 100);
                _percent == 0 && this.articleInfo.love_num > 0 && (_percent = 1);
                if (_percent > 100) {
                    _percent = 100;
                    this.articleInfo.love_num = this.articleInfo.aim_love_num;
                }
                return _percent;
            }
        },
        created: function created() {
            /**
             * 数据同步
             */
            if (!this.loadArticle) {
                this.loadArticle = true;
                this.appInfo = window.rmrbAppArticleInfo.appInfo;
                this.articleInfo = window.rmrbAppArticleInfo.articleInfo;
            }
            if (!this.loadDynamic) {
                this.loadDynamic = true;
                this.dynamic = window.rmrbAppArticleInfo.dynamic;
            }
            if (this.appInfo.screen && this.appInfo.screen.width) {
                this.resetFontBeilv(this.appInfo.screen.width);
            }
            // $('#article-placeholder').hide();
        },
        watch: {
            /**
             * 监听view_type的改变，作出对应的处理
             * @param {String} value view_type 文章的类型
             */
            // 'articleInfo.view_type'(value) {
            //     if (!value) return;
            //     // 人民号图文、短视频。小视频
            //     if (value == 'gov_dynamic' || value == 'gov_dynamic_video' || value == 'gov_small_video') {
            //         this.$nextTick(() => {
            //             this.sendDomReaedy();
            //             this.sendGovPostion(); // 发送人民号位置
            //             if ((value == 'gov_small_video' || value == 'gov_dynamic_video')) {
            //                 // if (value == 'gov_dynamic_video' && this.articleInfo.video && this.articleInfo.video.length) {
            //                 var _videoPostion = $('.j-video-container').get(0).getBoundingClientRect();
            //                 this.$nextTick(() => {
            //                     Bridge.request('dynamicVideoPostion', {
            //                         top: $('.j-video-container').offset().top,
            //                         left: _videoPostion.left,
            //                         width: _videoPostion.width,
            //                         height: _videoPostion.height,
            //                         cover: $('.j-video-container').find('img').attr('src'),
            //                         video_src: $('.j-video-container').find('img').data('video') || ''
            //                     });
            //                 });
            //                 articleHandle.bindVideoPlay(); //  点击播放视频
            //             } else {
            //                 articleHandle.resetDynamicImgSize(this.articleInfo.image); // 重置img尺寸
            //             }
            //             // articleHandle.imgAddLoading(); // 给img添加loading
            //             articleHandle.changeFontSize(this.appInfo.fontScale);
            //             articleHandle.imgEventSlide(); // 图片点击call Native
            //             this.echoInit();
            //             if (!this.appInfo.isWifi) {
            //                 // articleHandle.showLoadImg(this.appInfo.loadImg);
            //             }
            //         })
            //     }
            //     // 问政
            //     if (value == 'politics') {
            //         this.$nextTick(() => {
            //             this.sendDomReaedy();
            //             articleHandle.resetImgSize(); // 重置img尺寸
            //             articleHandle.imgEventSlide(); // 图片点击call Native
            //             articleHandle.changeFontSize(this.appInfo.fontScale);
            //             this.echoInit();
            //         })
            //     }
            // },
            // /**
            //  * 监听文章内容的改变（普通图文、人民号图文）
            //  * @param {String} value 文章内容
            //  */
            // 'articleInfo.contents'(value) { // 普通图文
            //     if (value === '') {
            //         this.sendDomReaedy();
            //         return;
            //     }
            //     // console.time('contents');
            //     this.contents = articleHandle.initContents(value);

            //     // console.timeEnd('contents');
            //     this.$nextTick(() => {
            //         this.sendDomReaedy();
            //         // console.time('time')
            //         this.sendGovPostion(); // 发送人民号位置
            //         articleHandle.imgAddLoading(); // 给img添加loading
            //         articleHandle.videoConvertImg(); // video转换为图片+icon
            //         articleHandle.bindVideoPlay(); //  点击播放视频
            //         articleHandle.resetImgSize(); // 重置img尺寸
            //         articleHandle.changeFontSize(this.appInfo.fontScale);
            //         articleHandle.imgEventSlide(); // 图片点击call Native
            //         audiojs.init();
            //         this.$nextTick(() => {
            //             this.echoInit();
            //             if (!this.appInfo.isWifi) {
            //                 articleHandle.showLoadImg(this.appInfo.loadImg);
            //             }
            //         })
            //         // console.timeEnd('time')
            //     });
            // },

            articleInfo: function articleInfo(val) {
                this.checkViewType(val.view_type);
                this.checkContens(val.contents);
            }
        },
        mounted: function mounted() {

            // var endTime = +new Date();
            // console.log('页面加载到构建视图一共用了', endTime - startTime + 'ms'+'-----页面高度：'+document.body.clientHeight);
            // var _div = document.createElement('div');
            // _div.innerText = '页面加载到构建视图一共用了' + (endTime - startTime) + 'ms'+'页面高度：'+document.body.clientHeight;
            // document.body.appendChild(_div);
            // this.pushDomHeight();

        },

        methods: {
            checkViewType: function checkViewType(value) {
                var _this = this;

                if (!value) return;
                // 人民号图文、短视频。小视频
                if (value == 'gov_dynamic' || value == 'gov_dynamic_video' || value == 'gov_small_video') {
                    this.$nextTick(function () {
                        _this.sendDomReaedy();
                        _this.sendGovPostion(); // 发送人民号位置
                        if (value == 'gov_small_video' || value == 'gov_dynamic_video') {
                            // if (value == 'gov_dynamic_video' && this.articleInfo.video && this.articleInfo.video.length) {
                            var _videoPostion = $('.j-video-container').get(0).getBoundingClientRect();
                            _this.$nextTick(function () {
                                Bridge.request('dynamicVideoPostion', {
                                    top: $('.j-video-container').offset().top,
                                    left: _videoPostion.left,
                                    width: _videoPostion.width,
                                    height: _videoPostion.height,
                                    cover: $('.j-video-container').find('img').attr('src'),
                                    video_src: $('.j-video-container').find('img').data('video') || ''
                                });
                            });
                            articleHandle.bindVideoPlay(); //  点击播放视频
                        } else {
                            articleHandle.resetDynamicImgSize(_this.articleInfo.image); // 重置img尺寸
                        }
                        // articleHandle.imgAddLoading(); // 给img添加loading
                        articleHandle.changeFontSize(_this.appInfo.fontScale);
                        articleHandle.imgEventSlide(); // 图片点击call Native
                        _this.echoInit();
                        if (!_this.appInfo.isWifi) {
                            // articleHandle.showLoadImg(this.appInfo.loadImg);
                        }
                    });
                }
                // 问政
                if (value == 'politics') {
                    this.$nextTick(function () {
                        _this.sendDomReaedy();
                        articleHandle.resetImgSize(); // 重置img尺寸
                        articleHandle.imgEventSlide(); // 图片点击call Native
                        articleHandle.changeFontSize(_this.appInfo.fontScale);
                        _this.echoInit();
                    });
                }
            },
            checkContens: function checkContens(value) {
                var _this2 = this;

                if (!value) {
                    this.$nextTick(function () {
                        _this2.sendDomReaedy();
                        if (_this2.appInfo.fontScale) {
                            _this2.sendGovPostion();
                            articleHandle.changeFontSize(_this2.appInfo.fontScale);
                        }
                    });
                    return;
                }

                // console.time('contents');
                this.contents = articleHandle.initContents(value);
                // console.timeEnd('contents');
                this.$nextTick(function () {

                    _this2.setAlink();
                    _this2.sendDomReaedy();
                    // console.time('time')
                    _this2.sendGovPostion(); // 发送人民号位置
                    articleHandle.imgAddLoading(); // 给img添加loading
                    articleHandle.videoConvertImg(); // video转换为图片+icon
                    articleHandle.bindVideoPlay(); //  点击播放视频
                    articleHandle.resetImgSize(); // 重置img尺寸
                    articleHandle.changeFontSize(_this2.appInfo.fontScale);
                    articleHandle.imgEventSlide(); // 图片点击call Native
                    audiojs.init();
                    _this2.$nextTick(function () {
                        _this2.echoInit();
                        if (!_this2.appInfo.isWifi) {
                            articleHandle.showLoadImg(_this2.appInfo.loadImg);
                        }
                    });
                    // console.timeEnd('time')
                });
            },


            /**
             * 推送DOM的高度
             * 了解HTML5中的MutationObserver https://segmentfault.com/a/1190000012787829
             */
            pushDomHeight: function pushDomHeight(bj) {
                var that = this;

                if (this.stopPushHeight) {
                    clearTimeout(this.pushTimer);
                    return;
                };

                this.pushDomNum++;
                if (this.pushDomNum > 4 && !bj) return;
                // console.log('this.pushDomNum',this.pushDomNum+'===='+this.pushTimer)
                if (this.pushTimer) {
                    clearTimeout(this.pushTimer);
                    this.pushTimer = null;
                }
                this.pushTimer = setTimeout(function () {
                    that.pushDomHeight();
                }, 300);
                that.webviewResize();
            },

            /**
             * 更新webview高度
             */
            webviewResize: function webviewResize() {
                Bridge.request('webviewResize', {
                    offsetHeight: document.body.clientHeight,
                    clientHeight: document.body.clientHeight
                });
            },

            /**
             * 图片加载设置
             */
            echoInit: function echoInit() {
                var _this3 = this;

                // if (this.appInfo.loadImg) {
                this.canEcho = true;
                // }
                setTimeout(function () {
                    _this3.setScrollTop(0);
                }, 1);
                // Echo.init({
                //     offset: 400,
                //     throttle: 0
                // });
            },

            /**
             * 替换折行符号的方法
             */
            replaceFold: function replaceFold(str) {
                str = str.replace(/\n|\\n|\r\n|\\r\\n/g, "<br>");
                return str;
            },

            /**
             * 设置滚动条高度
             * @param {Number} top 滚动高度
             */
            setScrollTop: function setScrollTop(top) {
                var that = this;
                that.scrollTop = top;
                if (!that.canEcho) return;
                $('[data-echo]').each(function () {
                    $(this).on('load', function () {
                        // 隐藏loading
                        if ($(this).attr('src').indexOf('data:image/png;base64') < 0) {
                            $(this).parents('.load-img-outer').addClass('loaded');
                        }
                        // rmrbApp.webviewResize();  2019-6-21 改
                    });

                    var _offsetTop = $(this).offset().top;
                    var isHeight = $(this).height() + _offsetTop;
                    // if ((_offsetTop - 400) <= (that.appInfo.screen.height + top)) {
                    //     // console.log('this loaded', $(this).attr('data-echo'));
                    //     $(this).attr('src', $(this).attr('data-echo')).removeAttr('data-echo');
                    // }

                    var ifLoadOne = _offsetTop <= that.appInfo.screen.height + top && _offsetTop >= top;
                    var isLoadTwo = isHeight <= that.appInfo.screen.height + top && isHeight >= top;
                    var isLoadThree = _offsetTop < top && isHeight > that.appInfo.screen.height + top;
                    if (ifLoadOne || ifLoadOne || isLoadThree) {
                        // console.log('this loaded', $(this).attr('data-echo'));
                        $(this).attr('src', $(this).attr('data-echo')).removeAttr('data-echo');
                    }
                });
            },

            /**
             * 跳转到人民号号主页
             */
            jumpToGov: function jumpToGov() {
                Bridge.request('govern', {
                    id: this.articleInfo.gov[0].id,
                    title: this.articleInfo.gov[0].name
                }, null, "rmrbApp['errorCallback']");
            },

            /**
             * 发送人民号位置
             */
            sendGovPostion: function sendGovPostion() {
                if (!this.articleInfo.gov.length || !$('.gov-info').length) return;
                var $gov = $('.gov-info'),
                    _gov = this.articleInfo.gov[0];
                Bridge.request('govPosition', {
                    top: $gov.offset().top.toFixed(2), // 保留到两位小数，安卓最多识别到两位小数
                    height: $gov.height().toFixed(2),
                    image: _gov.logo,
                    title: _gov.name
                    // state: _gov.is_gov, //1未关注 2 已关注
                });
            },

            /**
             * 加载完dom
             */
            sendDomReaedy: function sendDomReaedy() {
                // var endTime = +new Date();
                // console.log('domReady：', endTime - startTime + 'ms');
                // var _div = document.createElement('div');
                // _div.innerText = 'domReady：' + (endTime - startTime) + 'ms'+'页面高度：'+document.body.clientHeight;
                // document.body.appendChild(_div);

                this.stopPushHeight = false;

                this.pushDomNum = 0;
                this.pushDomHeight();
                Bridge.request('domReady', {});
            },
            logPageHeight: function logPageHeight(str) {
                Bridge.request('log', {
                    webHeight: str + document.body.clientHeight
                });
            },

            /**
             * 问政-点击关注
             */
            politicsFollow: function politicsFollow() {
                Bridge.request('politicsAttention', {}, "rmrbApp['politicsAttentionCallback']");
            },

            /**
             * 点击关注
             */
            handleFollow: function handleFollow() {
                Bridge.request('attention', {
                    id: this.articleInfo.gov[0].id
                }, "rmrbApp['followCallback']");
            },

            /**
             * 人民号关注按钮loading
             * @param {Boolean} boole loading状态
             */
            changeFollowLoading: function changeFollowLoading(boole) {
                this.followLoading = boole;
            },

            /**
             * 人民号关注或取消关注后的回调
             * @param {Boolean Number} status 关注状态 布尔或01
             */
            followCallback: function followCallback(status) {
                this.dynamic.is_gov = status;
            },

            /**
             * 人民号关注或取消关注后的回调
             * @param {Boolean Number} status 关注状态 布尔或01
             */
            politicsAttentionCallback: function politicsAttentionCallback(status) {
                this.articleInfo.is_attention = status;
            },

            /**
             * 暂停所有音频播放器
             */
            pauseMusic: function pauseMusic() {
                audiojs.pauseAudio();
            },

            /**
             * 改变帮的进度
             * @param {Number String} love_num 当前帮的已有数量
             */
            changeHelpPersent: function changeHelpPersent(love_num) {
                this.articleInfo.love_num = love_num;
            },

            /**
             * 动态改变字号
             * @param {Number} scale 字体的倍率 .89/1/1.11/1.22/1.33
             *  小：字号18 行高28
                标准：字号22 行高32
                大：字号24 行高34
                巨大：字号28  行高38
                小       0.81
                中       1
                大       1.09
                巨大   1.27
             */
            changeFontSize: function changeFontSize(scale) {
                this.appInfo.fontScale = scale;
                articleHandle.changeFontSize(this.appInfo.fontScale);
            },

            /* 
                处理标呗语音停顿
             */
            setBbStyle: function setBbStyle(str, time) {
                if (!str) return;
                var timer = time || 1000;
                // let pattern = /[\u3002|\uff1f|\uff01|\uff0c|\u3001|\uff1b|\uff1a|\u201c|\u201d|\u2018|\u2019|\uff08|\uff09|\u300a|\u300b|\u3008|\u3009|\u3010|\u3011|\u300e|\u300f|\u300c|\u300d|\ufe43|\ufe44|\u3014|\u3015|\u2026|\u2014|\uff5e|\ufe4f|\uffe5|\x21-\x2f\x3a-\x40\x5b-\x60\x7B-\x7F]/;
                var pattern = /[\。|\，|\！|\？|\；|\’|\”|\'|\"|\…|\—|\～|\.|\,|\!|\?|\-|\s]*$/;
                var paragraph = /[\。|\，|\！|\？|\…|\.|\,|\!|\?]/;
                str = str.replace(pattern, function (mat, index, el) {
                    if (mat) {
                        return mat.replace(paragraph, function (reg) {
                            reg = '<pause type=#3><silence len=' + timer + '>' + (reg || '');
                            return reg;
                        });
                    }
                    mat = '<pause type=#3><silence len=' + timer + '>' + (mat || '');
                    return mat;
                });
                return str;
            },

            /**
             * Native调用-人声阅读所需文章内容
             */
            getArticleText: function getArticleText(type) {
                /* "audio_desc": "语音播报 0不播报 1摘要 2正文 3摘要+正文 " */
                //  7.1.6更改 5： 播放； 其他不播放
                // var description = this.articleInfo.description || '';
                var short_title = this.articleInfo.short_title ? this.setBbStyle(this.articleInfo.short_title, 700) : '';
                var title_inner = this.articleInfo.title_inner ? this.setBbStyle(this.articleInfo.title_inner, 700) : '';
                var title_down = this.articleInfo.title_down ? this.setBbStyle(this.articleInfo.title_down, 700) : '';
                var title = short_title + title_inner + title_down;
                var _text = '';
                try {
                    // nodeType 1:元素节点; 3:文本节点
                    var isContent = Tools.removeMediaTag(this.articleInfo.contents);
                    var isDom = document.createElement("div");
                    isDom.innerHTML = isContent;
                    var _iteratorNormalCompletion = true;
                    var _didIteratorError = false;
                    var _iteratorError = undefined;

                    try {
                        for (var _iterator = isDom.childNodes[Symbol.iterator](), _step; !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
                            var item = _step.value;

                            if (item.nodeType == 1) {
                                var isInner = item.innerHTML || '';
                                if (isInner && Tools.removeHtmlTag(isInner) && Tools.removeHtmlTag(isInner).replace(/&nbsp;/g, "")) {
                                    var isTxt = Tools.removeHtmlTag(isInner);
                                    _text += this.setBbStyle(isTxt, 500);
                                }
                            } else if (item.nodeType == 3 && item.nodeValue.replace(/&nbsp;/g, "")) {
                                _text += this.setBbStyle(item.nodeValue.replace(/&nbsp;/g, ""), 500);
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

                    _text = _text.replace(/&nbsp;/g, "");
                } catch (err) {
                    _text = Tools.htmlToText(this.articleInfo.contents);
                }

                var content = '';
                switch (type) {
                    case '5':
                        content = title + _text;
                        break;
                    default:
                        content = "";
                }
                if (Bridge.isAndroid) {
                    Bridge.request('sendArticleText', {
                        content: content
                    });
                }
                return content;
            },
            errorCallback: function errorCallback(res) {
                // console.warning(res);
            },


            /* 投票 */
            handleVote: function handleVote(id) {
                if (!this.voteLoading) return;
                Bridge.request('vote', {
                    id: id
                }, "rmrbApp.voteCallback");
                this.voteLoading = false;
            },
            voteCallback: function voteCallback(data) {
                this.voteLoading = true;
                if (!data) return;
                if (typeof data == 'string') {
                    data = eval('(' + data + ')');
                }
                if (data.result.errorCode == 0 || data.result.errorCode == '0') {
                    this.$set(this.articleInfo, 'vote_info', data.data);
                }
            },


            /* 去除标题中的html标签 */
            removeHtml: function removeHtml(str) {
                if (!str) return str;
                var _str = str.replace(/<br\s*\/?>/g, '');
                return _str;
            },
            clearData: function clearData() {
                this.stopPushHeight = true;
                // this.hasPushHeight = false;

                this.loadArticle = false;
                this.loadDynamic = false;
                this.scrollTop = 0;
                this.canEcho = false;
                this.appInfo = {};
                this.articleInfo = {
                    gov: []
                };
                this.dynamic = {
                    // is_gov = '0'
                };
                this.contents = '';
                this.defaultHtml = '';
                this.followLoading = false;
            },


            // 重新定义全局字体倍率系数
            resetFontBeilv: function resetFontBeilv(val) {
                if (!val) return;
                var isWidth = val;
                _fontBeilv = isWidth / 375 > 1 ? (isWidth / 375).toFixed(3) : 1 || 1;
                window._fontBeilv = _fontBeilv > 2 ? 2 : _fontBeilv;
            },
            resetDomHeight: function resetDomHeight(e) {
                // console.log('e',e)
                rmrbApp.pushDomHeight(true);
            },
            wifyOnload: function wifyOnload(e) {
                articleHandle.wifyOnload(e);
            },


            // 处理a链接展示
            setAlink: function setAlink() {
                try {
                    var allElements = document.getElementsByTagName('a');
                    var that = this;
                    for (var i = 0; i < allElements.length; i++) {
                        var nodeList = allElements[i].childNodes;
                        if (nodeList.length == 1 && nodeList[0].nodeType == 3) {
                            var isHtml = "<span class='iconfont iconalink'></span>" + allElements[i].innerHTML;
                            allElements[i].innerHTML = isHtml;
                        }
                        allElements[i].onclick = function (e) {
                            e.preventDefault();
                            var nativeurl = this.getAttribute('data-rmrbnative');
                            try {
                                if (nativeurl) {
                                    var Base64 = Tools.Base64();
                                    nativeurl = Base64.decode(nativeurl);
                                    nativeurl = JSON.parse(nativeurl);
                                }
                            } catch (error) {}
                            // that.jsonToStr(nativeurl);
                            // const weburl = this.getAttribute('data-rmrbweb');
                            var weburl = this.getAttribute('href');
                            // console.log('weburl',weburl);
                            if (nativeurl && (typeof nativeurl === 'undefined' ? 'undefined' : _typeof(nativeurl)) == 'object' && nativeurl.jumpType && nativeurl.jumpType == 'internal') {
                                nativeurl.linkType = 2; // 1: 头部，2：内容；
                                Bridge.request('linkJump', nativeurl);
                            } else {
                                Bridge.request('linkJump', {
                                    link: weburl,
                                    linkType: 2
                                });
                            }
                        };
                    }
                } catch (error) {}
            },
            openWbview: function openWbview(val) {
                if (val.jumpType == 'internal') {
                    val.linkType = 1; // 1: 头部，2：内容；
                    Bridge.request('linkJump', val);
                } else {
                    Bridge.request('linkJump', {
                        link: val.share_url,
                        linkType: 1
                    });
                }
            }
        }
    });
});