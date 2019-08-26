
/**
 * 防止暴露给外部不必要的属性
 */
(function () {
    var phone = document.getElementById('phone'),
        password = document.getElementById('password'),
        sigup = document.getElementById('sigup'),
        phoneInfo = document.getElementById('phoneInfo'),
        phoneIcon = document.getElementById('phoneIcon');


    phone.addEventListener('blur', checkPhone);

    function checkPhone() {
        var value = phone.value;
        var reg = /^1[35678]\d{9}$/;
        if(!reg.test(value)){
            phoneInfo.innerHTML = '手机号码格式不正确';
            
        }

    }
    document.getElementsByTagName

})();