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
                <div class="active section">活动券名单</div>
            </div>
        </div>
    </div>
    <div class="ui container">
        <div class="ui segments">

            <div class="ui segment">

            </div>
            <div class="ui segment">
                <c:if test="${not empty page.list}">
                    <div class="ui ordered list">
                        <c:forEach items="${page.list}" var="u">
                            <div class="item">
                                <div class="right floated content">
                                    <div class="ui button">${u.createTime}</div>
                                </div>
                                <img class="ui avatar image" src="${u.head_src}">
                                <div class="content">
                                    <a class="header">${u.tel}</a>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
            </div>
            <div class="ui segment right aligned ">
                <div class="ui buttons">
                    <a href="/admin/actUser?page=${page.current-1}"
                       class="ui <c:if test="${page.current<= 1}">disabled</c:if> left labeled icon button"><i
                            class="left arrow icon"></i> 上一页 </a>
                    <a class="ui blue active button"></i> ${page.current}</a>
                    <a href="/admin/actUser?page=${page.current+1}"
                       class="ui <c:if test="${page.current+1> page.total}">disabled</c:if> right labeled icon button"><i
                            class="right arrow icon"></i> 下一页 </a>
                </div>
            </div>
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


</script>
</body>

</html>
