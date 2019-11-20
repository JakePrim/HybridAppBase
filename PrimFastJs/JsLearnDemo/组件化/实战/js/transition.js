/**
 * 判断是否支持transition
 */
(function(){
    var transitionEndEventName = {
        transition:'transitionend',
        MozTransition:'transitionend',
        WebkitTransition:'webkitTransitionEnd',
        OTransition:'oTransitionEnd otransitionend'
    }
    var transitionName = '';
    var isSupport = false;
    for(var name in transitionEndEventName){
        if(document.body.style[name] !== undefined){
            transitionName = transitionEndEventName[name];
            isSupport = true;
            break;
        }
    }

    console.log('transitionName:'+transitionName);

    //向全局作用域中暴露
    window.mt = window.mt || {};
    window.mt.transitionEnd = {
        name:transitionName,
        isSupport:isSupport
    }
})();