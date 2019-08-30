
/**
 * 防止暴露给外部不必要的属性
 */
(function () {
    var phone = document.getElementById('phone'),
        password = document.getElementById('password'),
        sigup = document.getElementById('sigup'),
        phoneInfo = document.getElementById('phoneInfo'),
        phoneIcon = document.getElementById('phoneIcon'),
        passwordIcon = document.getElementById('passwordIcon'),
        passwordInfo = document.getElementById('passwordInfo'),
        register = document.getElementById('register'),
        login = document.getElementById('login'),
        login_btn = document.getElementById('login-btn');
        passReg = /^\w{6,12}$/,
        useReg = /^1[35678]\d{9}$/,
        isRepeat = false,
        host = 'http://localhost/register/server/';

    phone.addEventListener('blur', checkUser, false);

    password.addEventListener('blur', checkPass, false);

    sigup.addEventListener('click', onSigup, false);

    login.addEventListener('click', showLogin, false);

    register.addEventListener('click', showRegister, false);

    login_btn.addEventListener('click',onLogin,false);

    function onLogin() {
        
    }

    function onSigup() {
        var user_val = phone.value, pass_val = password.value;
        if (useReg.test(user_val) && passReg.test(pass_val) && !isRepeat) {
            $.ajax({
                url: host + 'register.php',
                method: 'post',
                data: { username: user_val, userpwd: pass_val },
                success: function (data) {
                    if (data.code === 1) {
                        alert('注册成功,请登录');
                        //显示登录界面
                        showLogin();
                        //重置input
                        phone.value = "";
                        password.value = "";
                    } else {
                        alert(data.msg);
                    }
                },
                error: function () {
                    alert('注册失败,请重试');
                }
            });
        } else {
            alert('注册信息填写不正确!');
        }
    }

    function showLogin() {
        login.className = 'current';
        register.className = '';
        login_btn.className = 'show';
        sigup.className = '';
    }

    function showRegister() {
        login.className = '';
        register.className = 'current';
        login_btn.className = '';
        sigup.className = 'show';
    }

    function checkPass() {
        var value = password.value;
        console.log(passReg.test(value));
        if (!passReg.test(value)) {
            passwordInfo.innerHTML = '请输入6-12位字母、数字或下划线';
            passwordIcon.className = 'no';
        } else {
            passwordInfo.innerHTML = '';
            passwordIcon.className = 'ok';
        }
    }

    function checkUser() {
        var value = phone.value;
        if (!useReg.test(value)) {
            phoneInfo.innerHTML = '手机号码格式不正确';
            phoneIcon.className = 'no';
        } else {
            phoneInfo.innerHTML = '';
            phoneIcon.className = '';
            //判断是否已经注册
            $.ajax({
                url: host + 'isUserRepeat.php',
                data: { username: value },
                method: 'post',
                success: function (data) {
                    console.log('callback:' + data.code);
                    var code = data.code;
                    if (code === 1) {
                        phoneIcon.className = 'ok';
                        isRepeat = false;
                    } else if (code === 0) {
                        phoneInfo.innerHTML = data.msg;
                        phoneIcon.className = 'no';
                        isRepeat = true;
                    } else {
                        phoneInfo.innerHTML = '检测失败,请重试...';
                        phoneIcon.className = 'no';
                    }
                },
                error: function () {
                    phoneInfo.innerHTML = '检测失败,请重试...';
                    phoneIcon.className = 'no';
                }
            });
        }
    }

})();