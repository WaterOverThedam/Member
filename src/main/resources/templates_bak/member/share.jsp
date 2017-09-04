<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>分享</title>
    <link rel="stylesheet" href="/css/sm.min.css">
    <link rel="stylesheet" href="/css/gym.css">
    <link href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.css" rel="stylesheet">
</head>

<body>
<div class="page-group">
    <div class="page" id="page_share">
        <div class="content">
            <div class="card" >
                <div class="card-header no-border gym-card-title">
                    <a href="/index?linkId=1225"><img width="90px" src="/images/member/share_logo.png"> </a>
                    <label>加入小小运动馆<br/><div class="text-right"><label class="big">${shareMap['tian']}</label>天</div></label>
                </div>
            </div>
            <div class="card">
                <div class="card-content">
                    <div class="card-content-inner">
                        <div class="justify"><i></i><div class="share-circle-big">总共训练<label class="big">${shareMap['mins']}</label>分钟</div><i></i></div>
                        <div class="justify">
                            <div class="share-circle">过去三个月超过<label class="big">${shareMap['outpass']}%</label>会员</div>
                            <div class="share-circle">所有会员中排名<label class="big">${shareMap['ranking']}</label>名</div>
                            <div class="share-circle">每周平均锻炼<label class="big">${shareMap['times_per_week']}</label>次</div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="card">
                <div class="card-header">
                    <div class="share-avatar">
                        <a class="open-avatar">
                            <img id="avatar" src="${shareMap['avatar']}" onerror="this.src='/images/member/head.png'"/>
                        </a>
                        <div class="gym-share-info">
                            <p class="share-name">我是${shareMap['name']}</p>
                            <p class="share-detail">长按二维码加入小小运动馆</p>
                        </div>

                    </div>
                    <div class="share-img">
                        <img src="/images/member/share_incode.png">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type='text/javascript' src='/js/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='/js/sm.min.js' charset='utf-8'></script>

<script>
//    function drawCircle(r) {
//        // 选出页面上所有class为process的canvas元素,然后迭代每一个元素画图(这里用Jquery的选择器选的)
//        $('canvas.share-circle').each(function () {
//            // 一个canvas标签
//            var canvas = this;
//            // 拿到绘图上下文,目前只支持"2d"
//            var context = canvas.getContext('2d');
//            // 将绘图区域清空,如果是第一次在这个画布上画图,画布上没有东西,这步就不需要了
//            context.clearRect(0, 0, r, r);
//
//            // ***开始画一个灰色的圆
//            context.beginPath();
//            // 坐标移动到圆心
//            context.moveTo(r/2, r/2);
//            // 画圆,圆心是24,24,半径24,从角度0开始,画到2PI结束,最后一个参数是方向顺时针还是逆时针
//            context.arc(r/2, r/2, r/2, 0, Math.PI * 2, false);
//            context.closePath();
//            // 填充颜色
//            context.fillStyle = '#ddd';
//            context.fill();
//            // ***灰色的圆画完
//
//            // 画进度
//            context.beginPath();
//            // 画扇形的时候这步很重要,画笔不在圆心画出来的不是扇形
//            context.moveTo(r/2, r/2);
//            // 跟上面的圆唯一的区别在这里,不画满圆,画个扇形
//            context.arc(r/2, r/2, r/2, 0, Math.PI * 2 * 90 / 100, false);
//            context.closePath();
//            context.fillStyle = '#33CCCC';
//            context.fill();
//
//            // 画内部空白
//            context.beginPath();
//            context.moveTo(r/2, r/2);
//            context.arc(r/2, r/2, r/2 -5, 0, Math.PI * 2, true);
//            context.closePath();
//            context.fillStyle = 'rgba(255,255,255,1)';
//            context.fill();
//
//            // 画一条线
//            context.beginPath();
//            context.arc(r/2, r/2, r/2 -5, 0, Math.PI * 2, true);
//            context.closePath();
//            // 与画实心圆的区别,fill是填充,stroke是画线
//            context.strokeStyle = '#ddd';
//            context.stroke();
//
//            //在中间写字
//            context.font = "bold 9pt Arial";
//            context.fillStyle = '#3d4145';
//            context.textAlign = 'center';
//            context.textBaseline = 'middle';
//            context.moveTo(r/2, r/2);
//            context.fillText('总共训练225分钟', r/2, r/2);
//        });
//    }
//    function drawCircleBig(r) {
//        // 选出页面上所有class为process的canvas元素,然后迭代每一个元素画图(这里用Jquery的选择器选的)
//        $('canvas.share-circle-big').each(function () {
//            // 一个canvas标签
//            var canvas = this;
//            // 拿到绘图上下文,目前只支持"2d"
//            var context = canvas.getContext('2d');
//            // 将绘图区域清空,如果是第一次在这个画布上画图,画布上没有东西,这步就不需要了
//            context.clearRect(0, 0, r, r);
//
//            // ***开始画一个灰色的圆
//            context.beginPath();
//            // 坐标移动到圆心
//            context.moveTo(r/2, r/2);
//            // 画圆,圆心是24,24,半径24,从角度0开始,画到2PI结束,最后一个参数是方向顺时针还是逆时针
//            context.arc(r/2, r/2, r/2, 0, Math.PI * 2, false);
//            context.closePath();
//            // 填充颜色
//            context.fillStyle = '#ddd';
//            context.fill();
//            // ***灰色的圆画完
//
//            // 画进度
//            context.beginPath();
//            // 画扇形的时候这步很重要,画笔不在圆心画出来的不是扇形
//            context.moveTo(r/2, r/2);
//            // 跟上面的圆唯一的区别在这里,不画满圆,画个扇形
//            context.arc(r/2, r/2, r/2, 0, Math.PI * 2 * 90 / 100, false);
//            context.closePath();
//            context.fillStyle = '#3300CC';
//            context.fill();
//
//            // 画内部空白
//            context.beginPath();
//            context.moveTo(r/2, r/2);
//            context.arc(r/2, r/2, r/2 -5, 0, Math.PI * 2, true);
//            context.closePath();
//            context.fillStyle = 'rgba(255,255,255,1)';
//            context.fill();
//
//            // 画一条线
//            context.beginPath();
//            context.arc(r/2, r/2, r/2 -5, 0, Math.PI * 2, true);
//            context.closePath();
//            // 与画实心圆的区别,fill是填充,stroke是画线
//            context.strokeStyle = '#ddd';
//            context.stroke();
//
//            //在中间写字
//            context.font = "bold 9pt Arial";
//            context.fillStyle = '#3d4145';
//            context.textAlign = 'center';
//            context.textBaseline = 'middle';
//            context.moveTo(r/2, r/2);
//            context.fillText('总共训练225分钟', r/2, r/2);
//        });
//    }
//        drawCircle(100);
//        drawCircleBig(150);
        $.init();
</script>
</body>

</html>