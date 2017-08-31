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
    <title>后台</title>
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
                <div class="active section">反馈</div>
            </div>
        </div>
    </div>
    <div class="ui container">
        <div class="ui segments">
            <div class="ui segment">
                <h4 class="ui horizontal divider header">
                    <i class="tag icon"></i>
                    Description
                </h4>
                <p>Doggie treats are good for all times of the year. Proven to be eaten by 99.9% of all dogs worldwide.</p>
                <h4 class="ui horizontal divider header">
                    <i class="bar chart icon"></i>
                    Specifications
                </h4>
                <table class="ui definition table">
                    <tbody>
                    <tr>
                        <td class="two wide column">Size</td>
                        <td>1" x 2"</td>
                    </tr>
                    <tr>
                        <td>Weight</td>
                        <td>6 ounces</td>
                    </tr>
                    <tr>
                        <td>Color</td>
                        <td>Yellowish</td>
                    </tr>
                    <tr>
                        <td>Odor</td>
                        <td>Not Much Usually</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<%--<!-- modal -->--%>
<%--<div class="ui modal">--%>
<%--<i class="close icon"></i>--%>
<%--<div class="header">--%>
<%--反馈详情--%>
<%--</div>--%>
<%--<div class="image content">--%>

<%--<div class="description">--%>
<%--<div class="ui header"></div>--%>
<%--<p></p>--%>
<%--<p></p>--%>
<%--</div>--%>
<%--</div>--%>
<%--<div class="actions">--%>
<%--<div class="ui black deny button">--%>
<%--Nope--%>
<%--</div>--%>
<%--<div class="ui positive right labeled icon button">--%>
<%--Yep, that's me--%>
<%--<i class="checkmark icon"></i>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<script type='text/javascript' src='/js/jquery-1.11.3.min.js' charset='utf-8'></script>
<script type='text/javascript' src='/ui/semantic/semantic.min.js' charset='utf-8'></script>
<script>
    $("#menu").on('click', function () {
        $(".ui.sidebar").sidebar('toggle');
    })

    //    $(".feedback-details").on('click',function () {
    //        $('.ui.modal').modal('show');
    //    })

</script>
</body>

</html>
