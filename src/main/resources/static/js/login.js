var loadingIndex;
$.ajaxSetup({
    beforeSend: function () {
        loadingIndex = layer.open({
            type: 2,
            shadeClose: false
        });
    },
    complete: function () {
        layer.close(loadingIndex)
    },
    error: function () {
        layer.close(loadingIndex)
    }
});

var mySwiper = new Swiper('.swiper-container', {})

mySwiper.lockSwipes();
$("#goSignIn").click(function () {
    mySwiper.unlockSwipes();
    mySwiper.slideNext(fadeInClass());
    mySwiper.lockSwipes();
})

function fadeInClass() {
    if ($("#loginConnect p").hasClass("animated bounceInLeft")) {
        $("#loginConnect p").removeClass("animated bounceInLeft");
        $("#regConnect p").addClass("animated bounceInLeft")
    } else {
        $("#regConnect p").removeClass("animated bounceInLeft")
        $("#loginConnect p").addClass("animated bounceInLeft");
    }
    $("input").val("");
}
function titleClass(){
    $("#loginConnect p").removeClass("animated bounceInLeft");
    $("#changConnect p").addClass("animated zoomInLeft");
}
//登陆
$("#signIn").click(function () {
    if (checkLogin()) {
        var tel = $("#login_tel").val();
        var pass = $("#login_pass").val();
        login_ajax(tel, pass);
    }
})
//注册

$("#signUp").click(function () {
    if (checkReg()) {
        var reg_tel = $("#reg_tel").val();
        var reg_valnum = $("#reg_valnum").val();
        var reg_pass = $("#reg_pass").val();
        var reg_email = $("#reg_email").val();
        regsister_ajax(reg_tel, reg_valnum, reg_pass, reg_email);
    }

})
var sendingFlag = false;
$("#btn-valnum").click(function () {
    if (sendingFlag ==true){
        layer.open({
            content: '发送中，请勿重复点击'
            , skin: 'msg'
            , time: 2 //2秒后自动关闭
        });
        return false;
    }
    sendingFlag = true;
    var tel = $("#reg_tel").val();
    var isRegNum = checkInput(/^1[34578]\d{9}$/, $("#reg_tel"), "手机", "手机号格式不正确");
    if (isRegNum) {
        var that = this;
        $.ajax({
            type: "POST",
            url: "/login/validateNum",
            data: {"tel": tel},
            contentType: "application/x-www-form-urlencoded",
            dataType: "json",
            beforeSend:function () { },
            success: function (data) {
                if (data.success == true) {
                    time($("#btn-valnum"));
                } else {
                    layer.open({
                        content: '发送失败，请稍后再试'
                        , skin: 'msg'
                        , time: 2 //2秒后自动关闭
                    });
                }
            },
            error:function () {
                layer.open({
                    content: '发送异常，请稍后再试'
                    , skin: 'msg'
                    , time: 2 //2秒后自动关闭
                });
            }
        });
    }
    sendingFlag = false;
});

//修改密码
$("#btn_change").click(function () {
    if (sendingFlag ==true){
        layer.open({
            content: '发送中，请勿重复点击'
            , skin: 'msg'
            , time: 2 //2秒后自动关闭
        });
        return false;
    }
    sendingFlag = true;
    var tel = $("#change_tel").val();
    var isRegNum = checkInput(/^1[34578]\d{9}$/, $("#change_tel"), "手机", "手机号格式不正确");

    if (isRegNum) {
        var that = this;
        $.ajax({
            type: "POST",
            url: "/login/validateNum",
            data: {"tel": tel},
            contentType: "application/x-www-form-urlencoded",
            dataType: "json",
            beforeSend:function () { },
            success: function (data) {
                if (data.success == true) {
                    time($("#btn_change"));
                } else {
                    layer.open({
                        content: '发送失败，请稍后再试'
                        , skin: 'msg'
                        , time: 2 //2秒后自动关闭
                    });
                }
            },
            error:function () {
                layer.open({
                    content: '发送异常，请稍后再试'
                    , skin: 'msg'
                    , time: 2 //2秒后自动关闭
                });
            }
        });
    }else{
        $("#change_tel").focus();
    }
    sendingFlag = false;
});

var waitTime = 60;
function time(o) {
    if (waitTime == 0) {
        o.removeAttr("disabled");
        o.text("发送验证码");
        waitTime = 60;
    } else {
        o.attr("disabled", true);//倒计时过程中禁止点击按钮
        o.text(waitTime + "s重新获取");
        waitTime--;
        setTimeout(function () {
                time(o);//循环调用
            },
            1000)
    }
}

$("#reg_tel").on('input change', function () {
    var reg_tel = $("#reg_tel").val();
    if (/^1[34578]\d{9}$/.test($.trim(reg_tel))) {
        this.focus();
        this.blur();
        exist_ajax(reg_tel);
    }
})

//修改密码
$("#change_tel").on('input change',function () {
    var change_tel = $("#change_tel").val();
    if (/^1[34578]\d{9}$/.test($.trim(change_tel))) {
        this.focus();
        this.blur();
        exist_ajax_changePass(change_tel);
    }
})

$("#forgetPass").on('click',function () {
    mySwiper.unlockSwipes();
    mySwiper.slideTo(2, 500, titleClass());
    mySwiper.lockSwipes();
})
$("#updateMyPass").on('click',function () {
    if (checkUpdatePass()==true){
        updatePass_ajax($("#change_tel").val(),$("#change_valnum").val(),$("#change_pass").val());
    }
})
var feedbackFlag = false;
function toFeedback() {
    if (feedbackFlag == true){
        layer.open({
            content: '反馈提交中，请勿重复提交'
            , skin: 'msg'
            , time: 2 //2秒后自动关闭
        });
        return false;
    }
    feedbackFlag = true;
    var Franchisee = $("#franchisee").val();
    var details = $("#details").val();
    var contactTel = $("#contactTel").val();
    var feedbackName = $("#feedback_name").val();
    feedback_ajax(Franchisee, feedbackName,details, contactTel);
    feedbackFlag = false;
}

/*
 ajax方法
 */
function login_ajax(telephone, password) {
    this.username = telephone;
    this.password = $.md5(password);
    $.ajax({
        type: "POST",
        url: "/login/tologin",
        data: {"username": this.username, "password": this.password},
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",
        success: function (data) {
            if (data.result == "登录成功") {
                window.location.href = "/index";
            } else if ( data.result == "密码错误") {
                layer.open({
                    content: '密码错误'
                    , skin: 'msg'
                    , time: 2 //2秒后自动关闭
                });
            } else if (data.result == "该用户不存在") {
                layer.open({
                    content: '该用户不存在'
                    , skin: 'msg'
                    , time: 2 //2秒后自动关闭
                });
            }else{
                layer.open({
                    content: '登录异常，请重试'
                    , skin: 'msg'
                    , time: 2 //2秒后自动关闭
                });
            }
        },
    });
}

function regsister_ajax(username, valnum, password, email) {
    this.valnum = valnum;
    this.username = username;
    this.password = $.md5(password);
    this.email = email;
    $.ajax({
        type: "POST",
        url: "/login/register",
        data: {"username": this.username, "valnum": this.valnum, "password": this.password, "email": this.email},
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",
        success: function (data) {
            console.log(data);
            if (data.success == true && data.result == "注册成功") {
                layer.open({
                    content: '注册成功'
                    , skin: 'msg'
                    , time: 2 //2秒后自动关闭
                });
                mySwiper.unlockSwipes();
                mySwiper.slidePrev(fadeInClass());
                mySwiper.lockSwipes();
                $("#login_tel").val(username);
            } else if (data.success == false) {
                layer.open({
                    content: data.message
                    , skin: 'msg'
                    , time: 2 //2秒后自动关闭
                });
            }
        }
    });
}


function exist_ajax(telephone) {
    $.ajax({
        type: "POST",
        url: "/login/exist",
        data: {"telephone": telephone},
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",

        success: function (data) {
            console.log(data);
            if (data.success == false) {
                if (data.message == "该号码非会员"){
                    layer.open({
                        content: '系统中没有显示此手机号码,点击是与我们联系'
                        , btn: ['是', '否']
                        , yes: function (index) {
                            layer.open({
                                type: 1
                                , content: $("#feedback").val()
                                , anim: 'up'
                            });
                            $("#contactTel").val($("#reg_tel").val());
                            layer.close(index);
                        }
                    });
                }else{
                    layer.open({
                        content: data.message
                        , skin: 'msg'
                        , time: 2 //2秒后自动关闭
                    });
                }

            }else{
                layer.open({
                    content: '该号码是会员帐号,可以注册√'
                    , skin: 'msg'
                    , time: 2 //2秒后自动关闭
                });
            }
        }
    });
}

function exist_ajax_changePass(telephone) {
    $.ajax({
        type: "POST",
        url: "/login/exist_reged",
        data: {"telephone": telephone},
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",
        success: function (data) {
            if (data.success == false) {
                    layer.open({
                        content: telephone+'未注册，无法修改密码'
                        , skin: 'msg'
                        , time: 3 //2秒后自动关闭
                    });
                $("#change_tel").val("");
                $("#change_tel").focus();
            }
        },
    });
}

function feedback_ajax(Franchisee,feedbackName, details, contactTel) {
    $.ajax({
        type: "POST",
        url: "/login/feedback",
        data: {"Franchisee": Franchisee,"name":feedbackName, "details": details, "contactTel": contactTel},
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",
        success: function (data) {
            layer.open({
                content: '我们已收到您的反馈'
                , skin: 'msg'
                , time: 2 //2秒后自动关闭
            });
            // location.reload();
        }
    })
}

function updatePass_ajax(tel,valNum,newPass) {
    newPass = $.md5(newPass);
    $.ajax({
        type: "POST",
        url: "/login/updatePass",
        data: {"tel": tel,"valNum":valNum, "newPass": newPass},
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",
        async: false,
        success: function (data) {
            if (data.success == true){
                layer.open({
                    content: '修改成功，正在跳转……'
                    , skin: 'msg'
                    , time: 2, //2秒后自动关闭
                });
                setTimeout(function () {
                    location.reload();
                },1000);
            }else{
                layer.open({
                    content: data.message
                    , skin: 'msg'
                    , time: 2 //2秒后自动关闭
                });
            }
        }
    })
}
function checkLogin() {
    var telChecked = checkInput(/^1[34578]\d{9}$/, $("#login_tel"), "手机", "手机号格式不正确");
    var passChecked = checkInput(/^(\w){6,20}$/, $("#login_pass"), "密码", "6-20个字母、数字、下划线");
    return telChecked && passChecked;
}
function checkUpdatePass(){
    var telChecked = checkInput(/^1[34578]\d{9}$/, $("#change_tel"), "手机", "手机号格式不正确");
    var valnumChecked = checkInput(/^\d{4}$/, $("#change_valnum"), "输入验证码", "4位数字");
    var passChecked = checkInput(/^(\w){6,20}$/, $("#change_pass"), "密码", "6-20个字母、数字、下划线");
    return telChecked && valnumChecked && passChecked;
}
function checkReg() {
    //手机
    var passEquals = ($("#reg_pass").val() == $("#reg_repass").val());
    if (!passEquals) {
        $("#reg_repass").val("");
        $("#reg_repass").addClass("input-error");
        $("#reg_repass").attr("placeholder", "两次密码不一致");
        return false;
    } else {
        $("#reg_repass").removeClass("input-error");
        $("#reg_repass").attr("placeholder", "再次输入密码");
    }
    var telChecked = checkInput(/^1[34578]\d{9}$/, $("#reg_tel"), "输入手机号码", "手机号格式不正确");
    var passChecked = checkInput(/^(\w){6,20}$/, $("#reg_pass"), "输入密码", "6-20个字母、数字、下划线");
    var valnumChecked = checkInput(/^\d{4}$/, $("#reg_valnum"), "输入验证码", "4位数字");
    var mailChecked = checkInput(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/, $("#reg_email"), "输入邮箱地址", "邮箱格式不正确");
    return telChecked && valnumChecked && passChecked && mailChecked;
}

function checkInput(regx, ele, ph, phErr) {
    if (!regx.test($.trim(ele.val()))) {
        ele.val("");
        ele.addClass("input-error");
        ele.attr("placeholder", phErr);
        return false;
    } else {
        ele.removeClass("input-error");
        ele.attr("placeholder", ph);
        return true;
    }
}

   