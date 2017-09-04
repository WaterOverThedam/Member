<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>中心主题</title>
    <link rel="stylesheet" href="/css/sm.min.css">
    <link rel="stylesheet" href="/css/gym.css">
    <link href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.css" rel="stylesheet">
    <link rel="stylesheet" href="http://cdn.bootcss.com/video.js/6.0.0-RC.6/video-js.min.css">
</head>

<body>
<div class="page-group">
    <div class="page" id="topic">
        <div class="content">
            <div class="card">
                <div class="card-content gym-topic">
                    <div class="card-header no-padding no-border gym-topic">
                        <a href="/index" class="gym-banner-left external"><i class="fa fa-angle-double-left fa-3x"></i></a>
                        <div class="gym-banner-title">中心主题</div>
                        <i></i>
                    </div>
                </div>
            </div>

            <div class="card">
                <div class="card-header gym-card-title">
                    <i></i>
                    亲子班|小鸟班
                    24周
                    <i></i>
                </div>

                <div class="card-content">
                    <div class="card-content-inner">
                        <div class="content-padded">
                            <div class="gym-table">
                                <div>
                                    <div>主题：</div>
                                    <div>我的小鸟宝宝告诉我</div>
                                </div>
                                <div>
                                    <div>关键词：</div>
                                    <div>发音</div>
                                </div>
                                <div>
                                    <div>重点技能：</div>
                                    <div>P-bar上行走、悬挂</div>
                                </div>
                                <div>
                                    <div>Group：</div>
                                    <div>How Much is That Doggie In the Window? 通过我们有意识的语音规划，提高孩子的语音发展，为他们打下基础</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="card">
                <div class="card-header no-padding no-border gym-card-title"><i></i>作业 HomeWork<i></i></div>
                <div class="card-content">
                    <div class="card-content-inner no-padding">
                        <video id="example_video_1" class="video-js vjs-default-skin vjs-big-play-centered"
                               style="max-width: 100%" controls preload="auto"
                               poster="http://vjs.zencdn.net/v/oceans.png" data-setup="{}"/>
                        <source src="http://vjs.zencdn.net/v/oceans.mp4" type="video/mp4"/>
                        <source src="http://vjs.zencdn.net/v/oceans.webm" type="video/webm"/>
                        <source src="http://vjs.zencdn.net/v/oceans.ogv" type="video/ogg"/>
                        </video>
                        <p class="card-inner-text">兼容性最好，支持ie6+，及所有现代浏览器。 支持按字体的方式去动态调整图标大小，颜色等等。
                            但是因为是字体，所以不支持多色。只能使用平台里单色的图标，就算项目里有多色图标也会自动去色。</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type='text/javascript' src='/js/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='/js/sm.min.js' charset='utf-8'></script>
<script src="/js/swiper-3.4.2.jquery.min.js"></script>
<script src="http://cdn.bootcss.com/video.js/6.0.0-RC.5/video.min.js"></script>
<script>
    $.init()
</script>
</body>

</html>