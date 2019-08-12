/**
 *@author jakeprim
 *@description js常用工具 
 */

var regexp = {
    cn: function () {

    },
    qq: function () { },
    toCamelCase: function () { },
    toBlankCase: function () {

    },
}

//转驼峰工具类
function toCamelCase(str) {
    var pattern = /-([a-z])/gi;
    return str.replace(pattern, function (all, letter) {
        return letter.toUpperCase();
    });
}

/**
 * 去除首尾空白
 */
function toBlankCase(str) {
    var pattern = /^\s+|\s+$/g;
    return str.replace(pattern, '');
}

/**
 * 浏览器绑定事件兼容处理
 */

/**
 * 修复对象不兼容的地方
 */
function fixEventObj(e) {
    e.target = e.target || e.srcElement;
    e.preventDefault = e.preventDefault || function () {
        e.returnValue = true;
    };
    e.stopPropagation = e.stopPropagation || function () {
        e.cancelBubble = true;
    };
}

/**
 * 跨浏览器的绑定事件
 */
function on(elem, type, handle) {
    if (elem.addEventListener) { // 检测是否有标准方法
        elem.addEventListener(type, handle, false);
    } else if (elem.attachEvent) { // 试图使用 `attachEvent`
        elem.attachEvent('on' + type, function (event) {
            event = fixEventObj(event);
            handle.call(elem, event); // 使用 call 来改变 handle 的作用域，使其指向 elem
        });
    } else { // 兜底
        elem['on' + type] = function () {
            var event = fixEventObj(window.event);
            handle.call(elem, event);
        }
    }
}

/**
 * 解除事件
 */
function off(elem, type, handle) {
    if (elem.removeEventListener) {
        elem.removeEventListener(type, handle);
    } else if (elem.detachEvent) {
        elem.detachEvent('on' + type, function (event) {
            event = fixEventObj(event);
            handle.call(elem, event); // 使用 call 来改变 handle 的作用域，使其指向 elem
        })
    } else { // 兜底
        elem['on' + type] = function () {
            var event = fixEventObj(window.event);
            handle.call(elem, event);
        }
    }
}
// 调用
on(document.body, 'click', function (e) {
    console.log('哈哈哈，好用！', e);
});