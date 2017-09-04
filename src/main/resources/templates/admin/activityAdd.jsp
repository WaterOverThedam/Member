<%--
  Created by IntelliJ IDEA.
  User: hibernate
  Date: 2017/5/17
  Time: 13:22
  To change this template use File | Settings | File Templates.
--%>
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
</head>

<body>
<!--先静态吧就酱 -->
<%@ include file="/WEB-INF/admin/template_slider.jsp" %>
<div class="pusher">
    <div class="ui masthead  segment">
        <div class="ui container">
            <div class="ui breadcrumb">
                <a class="section" id="menu">Home</a>
                <i class="right angle icon divider"></i>
                <div class="active section">活动</div>
            </div>
        </div>
    </div>
    <div class="ui container">
        <div class="ui segments">
            <form class="ui form segment" action="/admin/activityAdd" method="POST" enctype="multipart/form-data">

                <div class="field">
                    <label>活动头图</label>
                    <input type="file" name="banner" enctype="multipart/form-data"  accept="image/jpg,image/png,image/jpeg" >
                </div>
                <div class="field">
                    <label>活动名称</label>
                    <input name="name"  value="测试名称" type="text">
                </div>
                <div class="field">
                    <label>活动详情</label>
                    <textarea name="detail"  >测试详情</textarea>
                </div>
                <div class="fields">
                    <div class="field">
                        <label>活动开始时间</label>
                        <input name="beginDate" value="2017/05/01"  type="text">
                    </div>
                    <div class="field">
                        <label>活动结束时间</label>
                        <input name="endDate"  value="2017/05/21"  type="text">
                    </div>
                </div>
                <div class="fields">
                    <div class="field">
                        <label>活动类别</label>
                        <input name="type" type="text" value="测试类别" placeholder="First Name">
                    </div>
                    <div class="field">
                        <label>活动收费类型</label>
                        <input name="chargeType" value="测试类型" type="text" placeholder="Last Name">
                    </div>
                    <div class="field">
                        <label>活动针对人群</label>
                        <input name="crowd" type="text" value="测试人群" placeholder="Last Name">
                    </div>
                    <div class="field">
                        <label>运动强度</label>
                        <input name="strength" type="text" value="测试强度" placeholder="Last Name">
                    </div>
                </div>
                <button type="submit" class="ui primary submit button">保存</button>
                <button type="reset" class="ui reset button">重置</button>
            </form>
        </div>
    </div>
</div>
<script type='text/javascript' src='/js/jquery-1.11.3.min.js' charset='utf-8'></script>
<script type='text/javascript' src='/ui/semantic/semantic.min.js' charset='utf-8'></script>
<script>
    $('.ui.dropdown').dropdown();
    $("#menu").on('click', function () {
        $(".ui.sidebar").sidebar('toggle');
    })
    $('.special.cards .image').dimmer({
        on: 'hover'
    });

    $('.ui form')
            .form({
                fields: {
//                    banner: 'empty',
                    name: 'empty',
                    detail: 'empty',
                    beginDate: 'empty',
                    endDate: 'empty',
                    type: 'empty',
                    chargeType: 'empty',
                    crowd: 'empty',
                    strength: 'empty'
                }
            })
    ;
</script>
</body>

</html>
