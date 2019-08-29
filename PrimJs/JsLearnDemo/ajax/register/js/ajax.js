var $ = {
    ajax: function (options) {
        var xhr = createAjax(),//创建XMLHttpRequest
            url = options.url,//请求的URL
            method = options.method || 'get',//请求类型
            async = typeof (options.async) === 'undefined' ? true : options.async,//是否异步请求
            data = options.data || null,
            sendParams = '',
            callback = options.success,
            error = options.error;
        //转换为字符串
        if (data) {
            for (const key in data) {
                if (data.hasOwnProperty(key)) {
                    const element = data[key];
                    sendParams += key + "=" + element + "&";
                }
            }
            sendParams = sendParams.replace(/&$/, "");
        }
        //get 方式请求处理 url
        if (method === 'get') {
            url += "?" + sendParams;
        }
        console.log('====================================');
        console.log('url:' + url + "\n method:" + method + "\n async:" + async + "\n param:" + sendParams);
        console.log('====================================');
        // 请求响应
        xhr.onreadystatechange = function () {
            //readyState == 4表示异步调用成功
            if (xhr.readyState === 4) {
                if ((xhr.status >= 200 && xhr.status < 300) || xhr.status === 304) {
                    //xhr.responseText 返回的是json字符串,我们需要转换为object对象
                    //1 可以使用eval来转换为对象,但是eval存在这一些问题 他可以执行不符合json格式的代码
                    // data = eval("("+xhr.responseText+")");
                    //2 推荐使用JSON来转换
                    var data = JSON.parse(xhr.responseText);
                    console.log('====================================');
                    console.log('responseText:' + xhr.responseText);
                    console.log('====================================');
                    //将对象转换为json字符串
                    // JSON.stringify(data);
                    callback && callback(data);
                } else {
                    console.log('====================================');
                    console.log('error');
                    console.log('====================================');
                    error && error();
                }
            }
        }
        //创建请求
        xhr.open(method, url, async);
        xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhr.send(sendParams);
    },
    jsonp: function (url, callback) {
        if (!url) {
            return;
        }
        var a = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k'],
            r1 = Math.floor(Math.random() * 10),
            r2 = Math.floor(Math.random() * 10),
            r3 = Math.floor(Math.random() * 10),
            name = "getJSONP" + a[r1] + a[r2] + a[r3],
            cbname = 'getJSONP.' + name;
        console.log(cbname);
        //判断url地址中是否有问号
        if (url.indexOf('?') === -1) {
            url += "?jsonp=" + cbname;
        } else {
            url += "&jsonp=" + cbname;
        }
        console.log(url);
        //生成script标签
        var script = document.createElement('script');
        //定义被脚本执行的函数
        getJSONP[name] = function (data) {
            try {
                callback && callback(data);
            } catch (e) {

            } finally {
                //删除函数和移除script标签
                delete getJSONP[name];
                script.parentNode.removeChild(script);
            }
        }
        script.src = url;
        document.getElementsByTagName('head')[0].appendChild(script);
    }
}

/**
 * 封装XmlHttpRequest,兼容各个版本
 */
function createAjax() {
    // 判断浏览器是否将XMLHttpRequest 作为本地对象实现,针对 IE7 firefox opera等
    if (typeof XMLHttpRequest != 'undefined') {
        return new XMLHttpRequest();
    } else if (typeof ActiveXObject != 'undefined') {
        //将所有可能出现等ActiveXObject 放到一个数组中
        var xhrArr = ['Microsoft.XMHTTP', 'MSXML2.XMLHTTP.6.0', 'MSXML2.XMLHTTP.5.0',
            'MSXML2.XMLHTTP.4.0', 'MSXML2.XMLHTTP.3.0', 'MSXML2.XMLHTTP.2.0'];
        //遍历创建XMLHttpRequest 对象
        var len = xhrArr.length, xhr;
        for (var i = 0; i < len; i++) {
            try {
                xhr = new ActiveXObject(xhrArr[i]);
                break;
            } catch (e) {

            }
            return xhr;
        }
        //由于javascript没有块级作用域 在for循环后将i释放 
        // i = null;
    } else {
        throw new Error("XMLHttpRequest not support!");
    }

}