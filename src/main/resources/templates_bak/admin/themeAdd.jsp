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
<%@ include file="template_slider.jsp" %>
<div class="pusher">
    <div class="ui masthead  segment">
        <div class="ui container">
            <div class="ui breadcrumb">
                <a class="section" id="menu">Home</a>
                <i class="right angle icon divider"></i>
                <div class="active section">课程亮点</div>
            </div>
        </div>
    </div>
    <div class="ui container">
        <div class="ui segments">
            <form class="ui form segment" id="theme_form">

                <div class="field">
                    <label>视频上传</label>
                    <input type="file" id="videoFile" name="videoFile"  enctype="multipart/form-data"  accept="audio/mp4,video/mp4" >
                </div>
                <div class="field">
                    <label>类型</label>
                    <input name="type"  value="funnyBug" type="text">
                </div>
                <div class="field">
                    <label>主题名称</label>
                    <input name="name"  value="funnyBug" type="text">
                </div>
                <div class="field">
                    <label>详情</label>
                    <textarea name="detail"  >测试详情</textarea>
                </div>
                <div class="fields">
                    <div class="field">
                        <label>主题开始时间</label>
                        <input name="beginDate" value="2017-08-28"  type="text">
                    </div>
                    <div class="field">
                        <label>主题结束时间</label>
                        <input name="endDate"  value="2017-09-03"  type="text">
                    </div>
                    <div class="field">
                        <label>学期的第几周</label>
                        <input name="weekNum"  value="35"  type="text">
                    </div>
                </div>

                <button type="submit" class="ui primary submit button" id="save">保存</button>
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


    Date.prototype.getWeekOfYear = function(weekStart) {
        // weekStart：每周开始于周几：周日：0，周一：1，周二：2 ...，默认为周日
        weekStart = (weekStart || 0) - 0;
        if(isNaN(weekStart) || weekStart > 6)
            weekStart = 0;

        var year = this.getFullYear();
        var firstDay = new Date(year, 0, 1);
        var firstWeekDays = 7 - firstDay.getDay() + weekStart;
        var dayOfYear = (((new Date(year, this.getMonth(), this.getDate())) - firstDay) / (24 * 3600 * 1000)) + 1;
        return Math.ceil((dayOfYear - firstWeekDays) / 7) + 1;
    }


    //var date = new Date("2017-08-28"); // 注意：JS 中月的取值范围为 0~11
    //var weekOfYear = date.getWeekOfYear(); // 当前日期是本年度第几周
    $("#save").on('click', function () {
        data_update();
    })
    function data_update() {
             var formData = new FormData(document.getElementById("theme_form"));
             var uploadFile = $("#videoFile");

             if (uploadFile.val() == "") {
                 alert("请选择视频文件！");
                 return false;
             }
             var fileExtension = uploadFile.val().substring(uploadFile.val().lastIndexOf("."), uploadFile.val().length);
             var extensions = ".mp4";
             if (extensions.indexOf(fileExtension) < 0) {
                 alert("请选择正确的视频格式!");
                 return false;
             }

             var imageSize = uploadFile[0].files[0].size;
             if (imageSize > 1024 * 1024 * 40) {
                  alert("视频大于40M,请压缩后重新上传！")
                 return false;
             }
            $.ajax({
                url: '/admin/addTo/theme',
                type: 'POST',
                data: formData,
                async: false,
//              cache: false,
                contentType: false,
                processData: false,
                beforeSend: function () {

                },
                success: function (data) {
                    if (data.success == true) {
                        alert("上传成功");
                        location.reload();
                    }else{
                        alert(data.message);
                    }

                },
                complete: function () {

                },
                error: function (data) {
                     alert("异常错误,稍后再试");
                }
            });
    }

</script>
</body>

</html>
