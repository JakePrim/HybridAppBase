define(['jquery'], function ($) {
    'use strict';

    /**
     * 时间转化规则
     */

    function dateFormat(date) {
        if (!date) return;
        var date2 = new Date(parseInt(date) * 1000);
        //获取js 时间戳
        var time = new Date().getTime();
        //去掉 js 时间戳后三位，与php 时间戳保持一致
        time = parseInt((time - date * 1000) / 1000);

        // 当前年份
        var currentYear = new Date().getFullYear();
        // 参数年份
        var targetYear = new Date(date * 1000).getFullYear();
        //存储转换值
        var s;

        if (time < 60 * 1) {
            //1分钟内
            return '刚刚';
        } else if (time < 60 * 60 && time >= 60 * 1) {
            //超过1分钟少于1小时
            s = Math.floor(time / 60);
            return s + "分钟前";
        } else if (time < 60 * 60 * 24 && time >= 60 * 60) {
            //超过1小时少于24小时
            s = Math.floor(time / 60 / 60);
            return s + "小时前";
        } else if (time < 60 * 60 * 24 * 4 && time >= 60 * 60 * 24) {
            //超过1天少于4天内
            s = Math.floor(time / 60 / 60 / 24);
            return s + "天前";
        } else if (time > 60 * 60 * 24 * 4 && currentYear == targetYear) {
            // 超过4天，但是是当前年份
            return date2.getMonth() + 1 + '-' + date2.getDate();
        } else {
            //超过4天非当前年份
            return date2.getFullYear() + "-" + (date2.getMonth() + 1) + "-" + date2.getDate();
        }
    }

    /**
     * 超过1000，返回1000+
     */
    function overThousand(value) {
        if (!value) return;
        if (value > 1000) {
            return '1000+';
        } else {
            return value;
        }
    }

    /**
     * html decode
     */
    function htmlDecode(str) {
        var s = "";
        if (!str || str.length == 0) return "";
        try {
            s = str.replace(/&gt;/g, ">");
            s = s.replace(/&lt;/g, "<");
            s = s.replace(/&nbsp;/g, " ");
            s = s.replace(/&#39;/g, "\'");
            s = s.replace(/&quot;/g, "\"");
            s = s.replace(/&ldquo;/g, "\“");
            s = s.replace(/&rdquo;/g, "\”");
            s = s.replace(/&ndash;/g, "–");
            s = s.replace(/&mdash;/g, "—");
            s = s.replace(/&amp;/g, "&");
            s = s.replace(/<br>/g, "\n");
        } catch (err) {
            s = str;
        }

        return s;
    }

    // #JavaScript 浮点数运算的精度问题 https://www.css88.com/archives/7340
    /**
     * 加法函数，用来得到精确的加法结果
     * 说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。
     * 调用：accAdd(arg1,arg2)
     * 返回值：arg1加上arg2的精确结果
     **/
    function accAdd(arg1, arg2) {
        var r1, r2, m, c;
        try {
            r1 = arg1.toString().split(".")[1].length;
        } catch (e) {
            r1 = 0;
        }
        try {
            r2 = arg2.toString().split(".")[1].length;
        } catch (e) {
            r2 = 0;
        }
        c = Math.abs(r1 - r2);
        m = Math.pow(10, Math.max(r1, r2));
        if (c > 0) {
            var cm = Math.pow(10, c);
            if (r1 > r2) {
                arg1 = Number(arg1.toString().replace(".", ""));
                arg2 = Number(arg2.toString().replace(".", "")) * cm;
            } else {
                arg1 = Number(arg1.toString().replace(".", "")) * cm;
                arg2 = Number(arg2.toString().replace(".", ""));
            }
        } else {
            arg1 = Number(arg1.toString().replace(".", ""));
            arg2 = Number(arg2.toString().replace(".", ""));
        }
        return (arg1 + arg2) / m;
    }

    /**
     * 减法函数，用来得到精确的减法结果
     * 说明：javascript的减法结果会有误差，在两个浮点数相减的时候会比较明显。这个函数返回较为精确的减法结果。
     * 调用：accSub(arg1,arg2)
     * 返回值：arg1加上arg2的精确结果
     **/
    function accSub(arg1, arg2) {
        var r1, r2, m, n;
        try {
            r1 = arg1.toString().split(".")[1].length;
        } catch (e) {
            r1 = 0;
        }
        try {
            r2 = arg2.toString().split(".")[1].length;
        } catch (e) {
            r2 = 0;
        }
        m = Math.pow(10, Math.max(r1, r2)); //last modify by deeka //动态控制精度长度
        n = r1 >= r2 ? r1 : r2;
        return ((arg1 * m - arg2 * m) / m).toFixed(n);
    }

    /**
     * 乘法函数，用来得到精确的乘法结果
     * 说明：javascript的乘法结果会有误差，在两个浮点数相乘的时候会比较明显。这个函数返回较为精确的乘法结果。
     * 调用：accMul(arg1,arg2)
     * 返回值：arg1乘以 arg2的精确结果
     **/
    function accMul(arg1, arg2) {
        var m = 0,
            s1 = arg1.toString(),
            s2 = arg2.toString();
        try {
            m += s1.split(".")[1].length;
        } catch (e) {}
        try {
            m += s2.split(".")[1].length;
        } catch (e) {}
        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
    }

    /** 
     * 除法函数，用来得到精确的除法结果
     * 说明：javascript的除法结果会有误差，在两个浮点数相除的时候会比较明显。这个函数返回较为精确的除法结果。
     * 调用：accDiv(arg1,arg2)
     * 返回值：arg1除以arg2的精确结果
     **/
    function accDiv(arg1, arg2) {
        var t1 = 0,
            t2 = 0,
            r1 = 0,
            r2 = 0;
        try {
            t1 = arg1.toString().split(".")[1].length;
        } catch (e) {}
        try {
            t2 = arg2.toString().split(".")[1].length;
        } catch (e) {}
        // with(Math) {
        r1 = Number(arg1.toString().replace(".", ""));
        r2 = Number(arg2.toString().replace(".", ""));
        return r1 / r2 * Math.pow(10, t2 - t1);
        // }
    }

    /**
     * html2text
     */
    function htmlToText(str) {
        return removeTag(htmlDecode(str));
    };

    /**
     * 过滤标签
     * @param {String} str 富文本字符串
     */
    function removeTag(str) {
        var _str = str.replace(/<img [^>]+>/g, "。");
        _str = _str.replace(/<video [^>]+>([^<]+)?<\/video>/g, "。");
        _str = _str.replace(/<audio [^>]+>([^<]+)?<\/audio>/g, "。");
        return _str.replace(/<[^>]+>/g, "");
    }
    /* 
        去除媒体标签
    */
    function removeMediaTag(str) {
        if (!str) return str;
        var _str = htmlDecode(str);
        _str = _str.replace(/<img [^>]+>/g, "");
        _str = _str.replace(/<video [^>]+>([^<]+)?<\/video>/g, "");
        _str = _str.replace(/<audio [^>]+>([^<]+)?<\/audio>/g, "");
        return _str;
    }
    /* 
        去除所有标签
    */
    function removeHtmlTag(str) {
        if (!str) return str;
        return str.replace(/<[^>]+>/g, "");
    }

    /* 
        base64
    */
    function Base64() {
        // private property
        var _keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
        // private method for UTF-8 decoding
        var _utf8_decode = function _utf8_decode(utftext) {
            var string = "";
            var i = 0;
            var c = 0;
            var c1 = 0;
            var c2 = 0;
            var c3;
            while (i < utftext.length) {
                c = utftext.charCodeAt(i);
                if (c < 128) {
                    string += String.fromCharCode(c);
                    i++;
                } else if (c > 191 && c < 224) {
                    c2 = utftext.charCodeAt(i + 1);
                    string += String.fromCharCode((c & 31) << 6 | c2 & 63);
                    i += 2;
                } else {
                    c2 = utftext.charCodeAt(i + 1);
                    c3 = utftext.charCodeAt(i + 2);
                    string += String.fromCharCode((c & 15) << 12 | (c2 & 63) << 6 | c3 & 63);
                    i += 3;
                }
            }
            return string;
        };

        return {
            decode: function decode(input) {
                var output = "";
                var chr1, chr2, chr3;
                var enc1, enc2, enc3, enc4;
                var i = 0;
                input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
                while (i < input.length) {
                    enc1 = _keyStr.indexOf(input.charAt(i++));
                    enc2 = _keyStr.indexOf(input.charAt(i++));
                    enc3 = _keyStr.indexOf(input.charAt(i++));
                    enc4 = _keyStr.indexOf(input.charAt(i++));
                    chr1 = enc1 << 2 | enc2 >> 4;
                    chr2 = (enc2 & 15) << 4 | enc3 >> 2;
                    chr3 = (enc3 & 3) << 6 | enc4;
                    output = output + String.fromCharCode(chr1);
                    if (enc3 != 64) {
                        output = output + String.fromCharCode(chr2);
                    }
                    if (enc4 != 64) {
                        output = output + String.fromCharCode(chr3);
                    }
                }
                output = _utf8_decode(output);
                return output;
            }
        };
    }
    return {
        dateFormat: dateFormat,
        overThousand: overThousand,
        htmlDecode: htmlDecode,
        accAdd: accAdd,
        accSub: accSub,
        accMul: accMul,
        accDiv: accDiv,
        htmlToText: htmlToText,
        removeTag: removeTag,
        removeMediaTag: removeMediaTag,
        removeHtmlTag: removeHtmlTag,
        Base64: Base64
    };
});