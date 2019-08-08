
/**
 * 姓名规则 提示框 
 */
var rulelinkwrap = document.getElementById('rule-link-wrap');
var rulepopupbox = document.getElementById('rule-popup-box-01');

rulelinkwrap.addEventListener("mouseover", function (e) {
    rulepopupbox.style.display = 'block';
}, false);

rulelinkwrap.addEventListener("mouseout", function (e) {
    rulepopupbox.style.display = 'none';
}, false);



/**
 * 表单验证
 */
//提示信息 数组 
var item_index = document.getElementsByClassName("item_");

var userAccount = document.getElementById("userAccount");
var userPass = document.getElementById("userPass");
var userPass_ = document.getElementById("userPass_");
var userName = document.getElementById("userName");
var userInformation = document.getElementById("userInformation");
var userEmail = document.getElementById("userEmail");
var userPhone = document.getElementById("userPhone");

var submit = document.getElementById("submit");

var choose = document.getElementById("choose");

var test0 = false;
var test1 = false;
var test2 = false;
var test3 = false;
var test4 = false;
var test5 = true;
var test6 = false;

/**
 * 密码强度验证 
 */
var mediums = document.getElementsByClassName("mediums");

//监听键盘输入
userPass.onkeyup = function () {
    mediums[0].style.background = mediums[1].style.background = mediums[2].style.background = "#eeeeee";
    var pwd = this.value;
    var result = 0;
    for (var i = 0, len = pwd.length; i < len; ++i) {
        result |= charType(pwd.charCodeAt(i));
    }
    var level = 0;
    //对result进行四次循环，计算其level
    for (var i = 0; i <= 4; i++) {
        if (result & 1) {
            level++;
        }
        //右移一位
        result = result >>> 1;
    }
    console.log("level:"+level);  
    if (pwd.length >= 6) {
        switch (level) {
            case 1:
                mediums[0].style.background = "#FF0000";
                break;
            case 2:
                mediums[0].style.background = "#FF9900";
                mediums[1].style.background = "#FF9900";
                break;
            case 3:
            case 4:
                mediums[0].style.background = "#33CC00";
                mediums[1].style.background = "#33CC00";
                mediums[2].style.background = "#33CC00";
                break;
        }
    }
}

/*
           定义一个函数，对给定的数分为四类(判断密码类型)，返回十进制1，2，4，8
           数字 0001 -->1  48~57
           小写字母 0010 -->2  97~122
           大写字母 0100 -->4  65~90
           特殊 1000 --> 8 其它
       */
function charType(num) {
    if (num >= 48 && num <= 57) {
        return 1;
    }
    if (num >= 97 && num <= 122) {
        return 2;
    }
    if (num >= 65 && num <= 90) {
        return 4;
    }
    return 8;
}


userAccount.onblur = function () {
    var pattern = /^[A-Za-z]\w{5,29}$/;
    test0 = false;
    if (this.value == '') {
        item_index[0].style.color = 'red';
        item_index[0].innerHTML = '用户名不能为空';
    } else {
        if (pattern.test(this.value)) {
            item_index[0].style.color = 'green';
            item_index[0].innerHTML = '用户名输入正确';
            test0 = true;
        } else {
            item_index[0].style.color = 'red';
            item_index[0].innerHTML = item_index[0].title;
        }
    }
}

userPass.onblur = function () {
    var pattern = /^\S{6,20}$/;
    test1 = false;
    if (this.value == '') {
        item_index[1].style.color = 'red';
        item_index[1].innerHTML = '密码不能为空';
    } else {
        if (pattern.test(this.value)) {
            item_index[1].style.color = 'green';
            item_index[1].innerHTML = '';
            test1 = true;
        } else {
            item_index[1].style.color = 'red';
            item_index[1].innerHTML = item_index[1].title;
        }
    }
}

userPass_.onblur = function () {
    test2 = false;
    if (this.value == '') {
        item_index[2].style.color = 'red';
        item_index[2].innerHTML = '确认密码不能为空';
    } else {
        if (userPass.value === this.value) {
            item_index[2].style.color = 'green';
            item_index[2].innerHTML = '两次输入一致';
            test2 = true;
        } else {
            item_index[2].style.color = 'red';
            item_index[2].innerHTML = item_index[2].title;
        }
    }
}

userName.onblur = function () {
    test3 = false;
    var pattern = /^[\u4e00-\u9fa5a-zA-Z]{3,30}$/;
    if (this.value == '') {
        item_index[3].style.color = 'red';
        item_index[3].innerHTML = '姓名不能为空';
    } else {
        if (pattern.test(this.value)) {
            item_index[3].style.color = 'green';
            item_index[3].innerHTML = '姓名输入正确';
            test3 = true;
        } else {
            item_index[3].style.color = 'red';
            item_index[3].innerHTML = item_index[3].title;
        }
    }
}

userInformation.onblur = function () {
    test4 = false;
    var pattern = /^\d{17}[0-9x]$/;
    if (this.value == '') {
        item_index[4].style.color = 'red';
        item_index[4].innerHTML = '证件号不能为空';
    } else {
        if (pattern.test(this.value)) {
            item_index[4].style.color = 'green';
            item_index[4].innerHTML = '证件号码输入正确';
            test4 = true;
        } else {
            item_index[4].style.color = 'red';
            item_index[4].innerHTML = item_index[4].title;
        }
    }
}

userEmail.onblur = function () {
    test5 = false;
    var pattern = /^[a-z0-9]+(?:[.-_][a-z0-9]+)*@[a-z0-9]+([.-_][a-z0-9]+)*\.[a-z]{2,4}$/;
    if (this.value == '') {
        item_index[5].innerHTML = '';
        test5 = true;//邮箱为非必填项可以为空
    } else {
        if (pattern.test(this.value)) {
            item_index[5].style.color = 'green';
            item_index[5].innerHTML = '邮箱输入正确';
            test5 = true;
        } else {
            item_index[5].style.color = 'red';
            item_index[5].innerHTML = item_index[5].title;
        }
    }
}

userPhone.onblur = function () {
    test6 = false;
    var pattern = /^(?:13[0-9]|147|15[012356789]|18[0256789]|17[0-9])\d{8}$/;
    if (this.value == '') {
        item_index[6].style.color = 'red';
        item_index[6].innerHTML = '手机号不能为空';
    } else {
        if (pattern.test(this.value)) {
            item_index[6].style.color = 'green';
            item_index[6].innerHTML = '手机格式正确';
            test6 = true;
        } else {
            item_index[6].style.color = 'red';
            item_index[6].innerHTML = item_index[6].title;
        }
    }
}

submit.onclick = function () {
    if (!test0 || !test1 || !test2 || !test3 || !test4 || !test5 || !test6) {
        console.log(test0, test1, test2, test3, test4, test5, test6);
        alert('注册信息填写有误!');
    } else {
        if (choose.checked) {
            window.location.href = 'http://www.imooc.com';
        } else {
            alert('请阅读并同意服务条款');
        }

    }
}