define(['jquery', 'tools', 'bridge'], function ($, Tools, Bridge) {
    'use strict';

    var audioArr = [];

    /**
     * 对页面中的audio做点什么
     */
    function init() {
        audioArr = [];
        $('.atc-html audio').each(function () {
            audioArr.push($(this));
            fixAudioAttr($(this));
            renderDom($(this));
            $(this).off();
            bindEvent($(this));
        });
    };

    /**
     * 修正audio行间属性
     * @param {Object} $audio jquery对象audio
     */
    function fixAudioAttr($audio) {
        $audio.hide().removeAttr('controls').removeAttr('loop').removeAttr('autoplay');
    };

    /**
     * 渲染播放器DOM
     * @param {Object} $audio jquery对象audio
     */
    function renderDom($audio) {
        if ($audio.parents().hasClass('play-box') && $audio.parent().hasClass('rmrb-audio-player')) {
            bindEvent($audio);
            return;
        }
        if (!$audio.parent().hasClass('play-box')) {
            $audio.wrap('<section class="play-box"></section>');
        }

        $audio.wrap('<div class="rmrb-audio-player"></div>').before('<i class="audio-play-icon"></i>\
                            <div class="audio-range">\
                                <div class="audio-range-preload" style="width:0%;"></div>\
                                <div class="audio-range-progress" style="width: 0%"></div>\
                                <div class="audio-handle-btn" style="display:none;left: 0%;"></div>\
                            </div>\
                            <div class="time">00:00</div>');

        if (rmrbApp && rmrbApp.resetDomHeight) {
            rmrbApp.resetDomHeight('音频加载');
        }
    };

    /**
     * 给播放器绑定事件
     * @param {Object} $audio jquery对象audio
     */
    function bindEvent($audio) {
        var $parent = $audio.parents('.rmrb-audio-player'),
            $range = $parent.find('.audio-range'),
            $play = $parent.find('.audio-play-icon'),
            $handle = $parent.find('.audio-handle-btn'),
            $progress = $parent.find('.audio-range-progress'),
            $bufferd = $parent.find('.audio-range-preload'),
            $time = $parent.find('.time'),
            touching = false;

        // $audio.on('canplay', function () {
        //     console.log($audio[0].duration);
        // });
        $audio.unbind();
        $audio.on('error', function () {
            $audio[0].pause();
            // console.log('该音频无法播放');
        });

        $audio.on('timeupdate', function () {
            var _bufferd = 0,
                _duration = $audio[0].duration,
                _progress = $audio[0].currentTime / _duration * 100;

            if (!_duration) {
                return;
            }

            if ($audio[0].buffered.length > 0) {
                _bufferd = $audio[0].buffered.end(0) / _duration * 100;
            }

            !touching && $progress.css('width', _progress + '%');
            $bufferd.css('width', _bufferd + '%');
            !touching && $handle.css({
                'left': _progress + '%',
                'display': 'block'
            });
            var playtime = Tools.accSub(_duration, $audio[0].currentTime);
            $time.text(formatPlayTime(playtime));
        });

        $play.on('click', function () {
            if (!$($audio).attr('src')) {
                $($audio).attr('src', $($audio).data('src'));
            }
            if ($audio[0].paused) {
                pauseAudio();
                $audio[0].play();
            } else {
                $audio[0].pause();
            }
        });

        $audio.on('play', function () {
            Bridge.request('pauseReadArticle'); // 暂停阅读文章
            $play.addClass('play');
        }).on('pause', function () {
            $play.removeClass('play');
        });

        $handle.on('touchstart', function () {
            touching = true;
            var rangeWidth = $range.width(),
                _rleft = $range.offset().left,
                _l = 0;
            $handle.on('touchmove', function (e) {
                var touch = e.originalEvent.targetTouches[0],
                    _x = touch.pageX,
                    _pro = (_x - _rleft) / rangeWidth * 100;
                _l = (_x - _rleft) / rangeWidth;
                if (_pro < 0) _pro = 0;
                if (_pro > 100) _pro = 100;
                if (_l < 0) _l = 0;
                if (_l > 1) _l = 1;
                $progress.css('width', _pro + '%');
                $handle.css('left', _pro + '%');
            });
            // chrome原则上只支持由click事件触发的播放
            // 由touch相关事件触发的会出现报错
            // Uncaught (in promise) DOMException
            $handle.on('touchend', function () {
                touching = false;
                pauseAudio(); // 暂停所有播放器
                if ($audio[0].duration) {
                    $audio[0].currentTime = _l * $audio[0].duration;
                }
                _l == 1 ? $audio[0].pause() : $audio[0].play();
            });
        });
    };

    /**
     * 格式化播放时间
     * @param {String || Number} audiotime // 时间长度，单位秒 
     */
    function formatPlayTime(audiotime) {
        var result = '00：00',
            time = parseInt(audiotime),
            hour = parseInt(parseInt(audiotime / 60) / 60),
            min = parseInt(time / 60),
            sec = time % 60;

        if (min >= 60) {
            min = parseInt(min % 60);
        }

        10 > hour && (hour = "0" + hour);
        10 > min && (min = "0" + min);
        10 > sec && (sec = "0" + sec);

        return hour != '00' ? hour + ':' + min + ':' + sec : min + ':' + sec;
    }

    /**
     * 暂停所有的播放器
     */
    function pauseAudio() {
        for (var i = 0; i < audioArr.length; i++) {
            audioArr[i][0].pause();
        }
    };

    return {
        init: init,
        pauseAudio: pauseAudio
    };
});