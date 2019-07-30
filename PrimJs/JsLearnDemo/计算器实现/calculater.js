/**
 * @ OCP 原则(开放与封闭原则)
 * 模块化
 */
(function () {
    var calcul = document.querySelector('#calculater');
    /**
     * 将零散的变量变成对象中的属性
     */
    var calculatorElem = {
        fromerInput: calcul.querySelector('.fromerInput'),
        calType: calcul.querySelector('.calType'),
        laterInput: calcul.querySelector('.laterInput'),
        result: calcul.querySelector('.result'),
        btns: calcul.querySelectorAll('.btn'),
    };

    /**
     * each 
     * @param fn 回调函数 i 索引 和 值
     */
    function each(array, fn) {
        for (var i = 0; i < array.length; i++) {
            fn(i, array[i]);
        }
    }

    each(calculatorElem.btns, function (index, btn) {
        btn.onclick = function () {
            updateSign(this.value);
            outputResult(operate(this.title, calculatorElem.fromerInput.value, calculatorElem.laterInput.value));
        }
    });

    //运算
    var operate = (function(){
        function operate(name) {
            if (!option[name]) throw new Error('不存在' + name + "的方法");
            //传递多个参数 截掉arguments的第一个参数
            return option[name].apply(option,[].slice.call(arguments,1,arguments.length));
        }
    
        var option = {
            add: function (num1, num2) {
                return +num1 + +num2;
            },
            sub: function (num1, num2) {
                return num1 - num2;
            },
            mul: function (num1, num2) {
                return num1 * num2;
            },
            divide: function (num1, num2) {
                return num1 / num2;
            },
            addOption: function (name, fn) {//添加其他的计算方式
                if (!option[name]) {
                    option[name] = fn;
                }
                return option;
            }
        }
        //给operate 添加属性 addOption
        operate.addOption = option.addOption;
        //返回operate
        return operate;
    })();

    //添加其他的计算方式 this -> operate.addOption 而不是指向 option.addOption 将this 指向option
    operate.addOption('mod', function (num1, num2) {
        return num1 % num2;
    });

    function updateSign(symblo) {
        calculatorElem.calType.innerHTML = symblo;
    }

    function outputResult(res) {
        calculatorElem.result.innerHTML = res;
    }
})();
