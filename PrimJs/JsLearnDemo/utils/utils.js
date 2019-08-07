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