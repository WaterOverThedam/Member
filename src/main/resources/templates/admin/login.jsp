<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>手机会员后台</title>
    <link rel="stylesheet" href="/ui/semantic/semantic.min.css">
    <style>
        body {
            background-color: #DADADA;
        }
        body > .grid {
            height: 100%;
        }
        .image {
            margin-top: -100px;
        }
        .column {
            max-width: 450px;
        }
    </style>
</head>
<body>
<div class="ui middle aligned center aligned grid">
    <div class="column">
        <h2 class="ui teal header">
            <img class="img" src="/images/admin/logo.jpg"/>
            <div class="content">手机会员后台登录</div>
        </h2>
        <div class="ui form">
            <div class="ui segment">
                <div class="field">
                    <div class="ui left icon input">
                        <i class="user icon"></i>
                        <input id="username" type="text" name="username" placeholder="输入用户名"/>
                    </div>
                </div>
                <div class="field">
                    <div class="ui left icon input">
                        <i class="lock icon"></i>
                        <input id="password" type="password" name="password" placeholder="输入密码">
                    </div>
                </div>
                <div class="ui error message"></div>
                <div id="login" class="ui fluid submit button">登录</div>
            </div>
        </div>
    </div>
</div>

<script type='text/javascript' src='/js/jquery-1.11.3.min.js' charset='utf-8'></script>
<script type='text/javascript' src='/ui/semantic/semantic.min.js' charset='utf-8'></script>
<script>
    function valForm() {
        var flag = false;
        var errmessage = '<ul class="list" >';
        var itemMessage = '';
        if ($("#username").val() == ""){
            itemMessage += '<li>' + '必须输入用户名'  +  '</li>';
        }
        if ($("#password").val() == ""){
            itemMessage += '<li>' + '必须输入密码'  +  '</li>';
        }
        if (itemMessage == ''){
            flag = true;
        }
        errmessage += itemMessage;
        errmessage += '</ul>';
        if (!flag){
            $(".ui.error.message").html(errmessage);
            $(".ui.error.message").fadeIn();
        }
        return flag;
    }

    $("#login").on('click',function () {
        if(valForm()){
            ajax_login($("#username").val(),$("#password").val());
        }
    })

    function ajax_login(username,password){
        this.username = username;
        this.password = password;
        $.ajax({
            type:'POST',
            url:'/admin/valLogin',
            data:{
                'username':this.username,'password':this.password
            },
            contentType:"application/x-www-form-urlencoded",
            dataType: "json",
            success:function (data) {
                if (data.success == true && data.message == '登录成功'){
                    window.location.href = '/admin/activity.html';
                }else{
//                    alert(allFields)
                    $(".ui.error.message").text(data.message);
                    $(".ui.error.message").fadeIn();
                }
            }})
    }


</script>
</body>
</html>