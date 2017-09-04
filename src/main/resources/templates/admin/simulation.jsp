<%--
  Created by IntelliJ IDEA.
  User: hibernate
  Date: 2017/5/12
  Time: 14:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>后台-会员登录</title>
    <link rel="stylesheet" href="/ui/semantic/semantic.min.css">
</head>
<body>
<%@ include file="template_slider.jsp" %>
<div class="pusher">
    <div class="ui masthead  segment">
        <div class="ui container">
            <div class="ui breadcrumb">
                <a class="section" id="menu">Home</a>
                <i class="right angle icon divider"></i>
                <div class="active section">会员登录</div>
            </div>
        </div>
    </div>
    <div class="ui container">
        <div class="ui segments">
            <div class="ui segment">
                <div class="ui blue statistic">
                    <div class="value">
                        ${totalMember}
                    </div>
                    <div class="label">
                        已注册会员数量
                    </div>
                </div>
            </div>
            <div class="ui segment">
                <div class="ui form">
                    <div class="ui fluid action input">
                        <input id="tel" name="tel" value="${requestScope.tel}" type="text" placeholder="输入会员手机号">
                        <button id = "enter" class="ui teal small right labeled icon button">
                            <i class="hand pointer icon"></i>
                            以该会员身份进入
                        </button>
                    </div>
                    <div class="ui error message"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type='text/javascript' src='/js/jquery-1.11.3.min.js' charset='utf-8'></script>
<script type='text/javascript' src='/ui/semantic/semantic.min.js' charset='utf-8'></script>
<script>
    $("#menu").on('click', function () {
        $(".ui.sidebar").sidebar('toggle');
    })
    $('#enter').on('click', function () {
        var tel = $('#tel').val();
        if (/^1[34578]\d{9}$/.test($.trim(tel))) {
            ajax_admin_member(tel);
        }else{
            var errmessage = '<ul><li>' +  '手机号格式错误' + '</li></ul>'
            $(".ui.error.message").html(errmessage);
            $(".ui.error.message").fadeIn();
        }
    })

    function ajax_admin_member(tel) {
        $.ajax({
            type: "POST",
            url: "/admin/member",
            data: {
                "tel": tel
            },
            contentType: "application/x-www-form-urlencoded",
            dataType: "json",
            success: function (data) {
                if (data.success == true) {
                    location.href="/index";
                } else {
                    var errmessage = '<ul><li>' +  data.message + '</li></ul>'
                    $(".ui.error.message").html(errmessage);
                    $(".ui.error.message").fadeIn();
                }
            }
        })
    }

</script>
</body>
</html>
