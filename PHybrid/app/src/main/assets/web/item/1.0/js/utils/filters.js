define(['vue'], function (Vue) {
    'use strict';

    /**
     * 时间 - 规则1
     * 规则：1分钟内显示刚刚；1小时内显示xx分钟前；24小时内显示xx小时前；3天内显示xx天前；
     * 3天后显示xx-xx；非本年显示xxxx-xxx-xx
     */

    Vue.filter("dateFormat1", function (date) {
        //全局方法 Vue.filter() 注册一个自定义过滤器,必须放在Vue实例化前面
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
            // 1分钟以内
            return "刚刚";
        } else if (time < 60 * 60 && time >= 60 * 1) {
            // 1小时以内 xx分钟前
            return s = Math.floor(time / 60) + "分钟前";
        } else if (time < 60 * 60 * 24 && time >= 60 * 60) {
            //超过1小时少于24小时 格式：x小时前
            s = Math.floor(time / 60 / 60);
            return s + "小时前";
        } else if (time < 60 * 60 * 24 * 3 && time >= 60 * 60 * 24) {
            // 超过1天小于3天 格式：x天前
            s = Math.floor(time / 60 / 60 / 24);
            return s + "天前";
        } else if (time > 60 * 60 * 24 * 3 && currentYear == targetYear) {
            // 超过3天，但是是当前年份 格式：08-27
            var _mouth = date2.getMonth() + 1 >= 10 ? date2.getMonth() + 1 : '0' + (date2.getMonth() + 1);
            var _date = date2.getDate() >= 10 ? date2.getDate() : "0" + date2.getDate();
            return _mouth + '-' + _date;

            // var _mouth = date2.getMonth() + 1;
            // return _mouth + '月' + date2.getDate()+'日';
        } else {
            //超过4天非当前年份 2017-08-27
            return date2.getFullYear() + "-" + (date2.getMonth() + 1) + "-" + date2.getDate();
        }
    });

    Vue.filter("dateFormat2", function (date, fmt) {
        //全局方法 Vue.filter() 注册一个自定义过滤器,必须放在Vue实例化前面

        if (!!!fmt) {
            fmt = 'yyyy-MM-dd hh:mm';
        }
        var date = new Date(date * 1000);
        if (/(y+)/.test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length));
        }
        var o = {
            'M+': date.getMonth() + 1,
            'd+': date.getDate(),
            'h+': date.getHours(),
            'm+': date.getMinutes(),
            's+': date.getSeconds()
        };
        for (var k in o) {
            if (new RegExp('(' + k + ')').test(fmt)) {
                var str = o[k] + '';
                fmt = fmt.replace(RegExp.$1, RegExp.$1.length === 1 ? str : ('00' + str).substr(str.length));
            }
        }
        return fmt;
    });

    /**
     * 数量 - 规则1
     * 规则：0不显示该字段，大于9999显示x.x万；大于9999.9万显示x.x亿
     * 显示页面：浏览量，直播参与人数
     */
    Vue.filter("numberFormat1", function (value) {
        //全局方法 Vue.filter() 注册一个自定义过滤器,必须放在Vue实例化前面
        var result = value;
        if (value > 99999999) {
            result = parseFloat(value / 100000000).toFixed(1) + '亿';
        } else if (value > 9999) {
            result = parseFloat(value / 10000).toFixed(1) + '万';
        }
        return result;
    });
});