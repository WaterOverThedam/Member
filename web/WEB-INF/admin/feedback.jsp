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
                <%--<div class="ui selection dropdown">--%>
                    <%--<input name="gender" type="hidden" value="default">--%>
                    <%--<i class="dropdown icon"></i>--%>
                    <%--<div class="text" >待处理</div>--%>
                    <%--<div class="menu">--%>
                        <%--<div class="item" data-value="0">待处理</div>--%>
                        <%--<div class="item" data-value="1">已处理</div>--%>
                        <%--<div class="item" data-value="-1">全部</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                    <div class="ui blue statistic">
                        <div class="value">
                            ${handledCount}
                        </div>
                        <div class="label">
                            已处理反馈
                        </div>
                    </div>
            </div>
            <div class="ui segment">
                <c:if test="${not empty page.list}">
                    <div class="ui ordered list">
                        <div class="ui dimmer">
                            <div class="ui text loader">加载中</div>
                        </div>
                        <c:forEach items="${page.list}" var="feedback">
                            <div class="item">
                                <div class="content">
                                    <a href="javascript:;" data-id="${feedback.id}" class="item feedback-details">
                                        <c:choose>
                                            <c:when test="${fn:trim(feedback.details) =='' }">无</c:when>
                                            <c:otherwise>
                                                <c:choose>
                                                    <c:when test="${fn:length(feedback.details) > 40}">
                                                        <c:out value="${fn:substring(feedback.details, 0, 40)}……"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:out value="${feedback.details}"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:otherwise>
                                        </c:choose>
                                    </a>
                                    <div class="ui orange  right tag label">${feedback.type}</div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
            </div>
            <div class="ui segment right aligned ">
                <div class="ui buttons">
                    <a href="/admin/feedback?page=${page.current-1}"
                       class="ui <c:if test="${page.current<= 1}">disabled</c:if> left labeled icon button"><i
                            class="left arrow icon"></i> 上一页 </a>
                    <a class="ui blue active button"></i> ${page.current}</a>
                    <a href="/admin/feedback?page=${page.current+1}"
                       class="ui <c:if test="${page.current+1> page.total}">disabled</c:if> right labeled icon button"><i
                            class="right arrow icon"></i> 下一页 </a>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- modal -->
<div class="ui small modal">
    <i class="close icon"></i>
    <div class="header">
        反馈详情
    </div>
    <div class="content">
        <div class="ui feed">
            <div class="event">
                <div class="label">
                    <img src="/images/member/head.png">
                </div>
                <div class="content">
                    <div class="date">
                        <i class="volume control phone icon"></i>
                        <i id="feedback_tel" date-tel="" class="ui violet tiny header"></i>
                    </div>
                    <div class="summary">
                        <a href="/admin/simulation?tel=" target="_blank" class="user" id="feedback_name">
                        </a>
                        <div class="date">
                            <span id="feedback_createTime"></span>
                            <div class="ui label" id="feedback_franchisee"></div>
                            <div class="ui  label" id="feedback_type"></div>
                        </div>
                    </div>
                    <div class="extra text" id="feedback_details">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="actions">
        <div class="ui black deny button">
            关闭
        </div>
        <div id="sign" data-id="" class="ui positive right labeled icon button">
            标记为已处理
            <i class="checkmark icon"></i>
        </div>
    </div>
</div>

<script type='text/javascript' src='/js/jquery-1.11.3.min.js' charset='utf-8'></script>
<script type='text/javascript' src='/ui/semantic/semantic.min.js' charset='utf-8'></script>
<script>
    $("#menu").on('click', function () {
//        $(".ui.sidebar").sidebar('setting', 'transition', 'overlay');
        $(".ui.sidebar").sidebar('toggle');
    })
    <%--$('.dropdown')--%>
            <%--.dropdown('set value',${selectedFeedback} + '');--%>
    <%--;--%>
    <%--$('.dropdown')--%>
            <%--.dropdown({--%>
                <%--// you can use any ui transition--%>
                <%--transition: 'drop',--%>
                <%--onChange: function (value, text, $selectedItem) {--%>
                    <%--location.href="/admin/feedback" + "?type=" + value;--%>
                <%--},--%>
            <%--});--%>
    <%--;--%>

    $(".feedback-details").on('click', function () {
        var that = $(this);
        var id = that.data('id');
        inFeedback(id);
        $('.ui.modal').modal('show');
    })

    $("#sign").on('click', function () {
        var id = $(this).data('id');
        ajax_signed(id)
    })

    function inFeedback(id) {
        $.ajax({
            type: 'POST',
            url: '/admin/feedbackView',
            data: {
                'id': id
            },
            async: false,
            contentType: "application/x-www-form-urlencoded",
            dataType: "json",
            beforeSend: function () {
            },
            success: function (data) {
                if (data != null) {
                    var feedback = data;
                    $("#feedback_tel").text(feedback.contactTel);
                    $("#feedback_tel").data('tel', feedback.contactTel);
                    $("#feedback_name").text(feedback.name);
                    $("#feedback_name").attr("href", '/admin/simulation?tel=' + feedback.contactTel);
                    $("#feedback_type").text(feedback.type);
                    $("#feedback_contractTel").text(feedback.contractTel);
                    $("#feedback_createTime").text(feedback.createTime);
                    $("#feedback_franchisee").text(feedback.franchisee);
                    $("#feedback_details").text(feedback.details);
                    $("#sign").data('id', feedback.id + '');
                }
            },
            complete: function () {
            },
            error: function () {
            }
        })
    }

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
                    location.href = "/index";
                } else {
                    var errmessage = '<ul><li>' + data.message + '</li></ul>'
                    $(".ui.error.message").html(errmessage);
                    $(".ui.error.message").fadeIn();
                }
            }
        })
    }

    function ajax_signed(id) {
        $.ajax({
            type: "POST",
            url: "/admin/sign",
            data: {
                "id": id
            },
            contentType: "application/x-www-form-urlencoded",
            dataType: "json",
            success: function (data) {
                if (data.success == true) {
                    location.reload();
                } else {
                    alert("标记失败，请重试");
                }
            },
            error: function () {
                alert("异常错误，请重试");
            }
        })
    }
</script>
</body>

</html>
