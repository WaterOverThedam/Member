<%--
  Created by IntelliJ IDEA.
  User: 汁
  Date: 2017/4/5
  Time: 20:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>宝宝信息</title>
    <link rel="stylesheet" href="/css/sm.min.css">
    <link rel="stylesheet" href="/css/gym.css">
    <link href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.css" rel="stylesheet">
</head>

<body>
<div class="page-group">
    <div class="page" id="page_myinfo">
        <div class="content">
            <div class="card">
                <div class="card-header no-padding no-border gym-myinfo">
                    <a href="/index" class="gym-banner-left external"><i class="fa fa-angle-double-left fa-3x"></i></a>
                    <div class="gym-banner-title">我的信息</div>
                    <i></i>
                </div>
            </div>

            <div class="card">
                <div class="card-header">
                    <div class="facebook-avatar">
                        <a class="open-avatar" data-popup="popup popup-avatar">
                            <img id="avatar" src="${fn:toLowerCase(sessionScope.user.head_src)}"
                                 onerror="this.src='/images/member/head.png'"/>
                        </a>
                        <div class="gym-myinfo-info">
                            <p class="avatar-name">${childObj['name']}</p>
                            <p class="avatar-detail">年龄：${childObj['age']}</p>
                        </div>
                    </div>
                    <a href="/exist" class="button">退出登录</a>
                </div>

                <div class="card-content">
                    <c:choose>
                        <c:when test="${not empty listContract}">
                            <div class="card-content-inner" id="contract" data-value="0">
                                <div class="list-block gym-list">
                                    <ul>
                                        <li class="item-content">
                                            <div class="item-inner">
                                                <div class="item-title">报名日期：<label
                                                        id="c_regDate">${listContract[0]['报名日期']}</label></div>
                                            </div>
                                            <div class="item-inner">
                                                <div class="item-title">有效期：<label
                                                        id="c_validity">${listContract[0]['有效期']}</label></div>
                                            </div>
                                        </li>
                                        <li class="item-content">
                                            <div class="item-inner">
                                                <div class="item-title">剩余课时数：<label
                                                        id="c_residuePeriods">${listContract[0]['剩余课时数']}</label>节
                                                </div>
                                            </div>
                                            <div class="item-inner">
                                                <div class="item-title">报名课时数：<label
                                                        id="c_regPeriods">${listContract[0]['报名课时数']}</label>节
                                                </div>
                                            </div>
                                        </li>
                                        <li class="item-content">
                                            <div class="item-inner">
                                                <div class="item-title">累计请假：<label
                                                        id="c_totalLeave">${listContract[0]['累计请假数']}</label>节
                                                </div>
                                            </div>
                                            <%--<div class="item-inner">--%>
                                                <%--<div class="item-title">报名金额：<label--%>
                                                        <%--id="c_regSum">${listContract[0]['合同金额']}</label></div>--%>
                                            <%--</div>--%>
                                            <div class="item-inner">
                                                <div class="item-title">班级：<label
                                                        id="c_class">${listContract[0]['课程']}</label></div>
                                            </div>
                                        </li>
                                        <li class="item-content">

                                            <div class="item-inner">
                                                <div class="item-title">赠课：<label
                                                        id="c_give">${listContract[0]['赠课']}</label></div>
                                            </div>
                                            <div class="item-inner">
                                                <div class="item-title">积分：<label
                                                        id="c_score">${listContract[0]['积分']}</label></div>
                                            </div>
                                        </li>
                                        <li class="item-content">

                                            <div class="item-inner">
                                                <a id="view" class="item-title">所有合同&nbsp;<i
                                                        class="fa fa-angle-double-right"
                                                        aria-hidden="true"></i></a>
                                            </div>
                                        </li>

                                        <!-- 反馈 -->
                                        <li class="item-content  open-popup" data-popup=".popup-feedback">
                                            <div class="item-media">
                                                <i class="fa fa-meh-o fa-2x" aria-hidden="true"></i>
                                            </div>
                                            <div class="item-inner">
                                                <div class="item-title" >数据有问题？点击此处向我们反馈</div>
                                                <div class="item-after"><i
                                                        class="fa fa-angle-double-right fa-2x"
                                                        aria-hidden="true"></i></div>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="card-content-inner">
                                <p class="color-danger text-center">没有找到有效期内的合同~</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <!-- 我的优惠券 -->
            <div class="card">
                <div class="card-header  gym-card-title">
                    我的优惠券
                </div>
                <div class="card-content">
                    <div class="card-content-inner">
                        <c:choose>
                            <c:when test="${not empty couponMap}">
                                <div class="coupon-box">
                                    <c:choose>
                                        <c:when test="${couponMap['value']['used']==false}">
                                            <a href="javascript:;" class="coupon" data-value="1"><img
                                                    src="/images/member/coupon_unused.png">
                                            </a>
                                            <span class="fa fa-question-circle fa-2x coupon-rule  open-popup"
                                                  data-popup=".popup-coupon-rule"></span>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="javascript:;" class="coupon" data-value="0"><img
                                                    src="/images/member/coupon_used.png"> <span
                                                    class="fa fa-question-circle fa-2x coupon-rule open-popup"
                                                    data-popup=".popup-coupon-rule"></span> </a>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </c:when>

                            <c:otherwise>
                                <div class="no-coupon text-center">
                                    <img src="/images/member/no_coupon.png"/>
                                    <p>您目前没有优惠券可用</p>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>

            <div class="card activity">
                <div class="card-header no-border no-padding gym-month-activity">
                    <i></i>
                    <div class="gym-banner-title">
                        <small>中心本月活动</small>
                    </div>
                    <i></i>
                </div>
            </div>
        </div>

    </div>
</div>

<!-- Avatar Popup -->
<div class="popup popup-avatar">
    <form id="uploadForm" enctype="multipart/form-data">
        <div class="card facebook-card no-border">
            <div class="card-content">
                <img src="${sessionScope.user.head_src}" id="pre_avatar" class="pre-avatar">
            </div>
        </div>
        <div class="content-block">
            <p><a href="javascript:;" class="button  button-big file"><input type="file" name="file" id="avatarFile"
                                                                             accept="image/jpg,image/png,image/jpeg">更换头像</a>
            </p>
            <p><a href="javascript:;" class="button  button-big" id="updateAvatar">确定</a></p>
            <p><a href="javascript:;" class="button  button-danger button-big close-popup" id="cancelUpdate">取消</a></p>
        </div>
    </form>
</div>
<div class="popup popup-coupon-rule">
    <header class="bar bar-nav">

        <h1 class="title">活动规则</h1>
    </header>
    <div class="content">
        <div class="content-block">
            <p>1、获得优惠券之日起，有限期为一年，用于会员本人续报课程</p>
            <p>2、此券不与其他优惠共享</p>
            <p>3、不支持兑换现金</p>
            <p><a href="javascript:;" class="close-popup">关闭</a></p>
        </div>
    </div>
</div>

<div class="popup popup-feedback">
    <header class="bar bar-nav">

        <h1 class="title">提交反馈</h1>
    </header>
    <div class="content">
        <div class="list-block">
            <ul>
                <!-- Text inputs -->
                <li>
                    <div class="item-content">
                        <div class="item-inner">
                            <div class="item-title label">姓名</div>
                            <div class="item-input">
                                <input type="text" id="feedback_name">
                            </div>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="item-content">
                        <div class="item-inner">
                            <div class="item-title label">中心</div>
                            <div class="item-input">
                                <input type="text" id="feedback_fs" value="${listGymSelectedSession[0].gym['gymName']}">
                            </div>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="item-content">
                        <div class="item-inner">
                            <div class="item-title label">手机</div>
                            <div class="item-input">
                                <input type="text" id="feedback_tel" placeholder="手机" value="${sessionScope.user.tel}">
                            </div>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="item-content">
                        <div class="item-inner">
                            <div class="item-title label">类型</div>
                            <div class="item-input">
                                <select id="feedback_type">
                                    <option value="剩余课时数">剩余课时数</option>
                                    <option value="报名课时数">报名课时数</option>
                                    <option value="累计请假">累计请假</option>
                                    <option value="报名金额">报名金额</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </li>

                <li class="align-top">
                    <div class="item-content">
                        <div class="item-inner">
                            <div class="item-title label">问题详情</div>
                            <div class="item-input">
                                <textarea id="feedback_details"></textarea>
                            </div>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
        <div class="content-block">
            <div class="row">
                <div class="col-50"><a href="javascript:;" class="button button-big button-fill button-danger close-popup">取消</a>
                </div>
                <div class="col-50"><a href="javascript:;" id="feedback_sub" class="button button-big button-fill button-success">提交</a>
                </div>
            </div>
        </div>
    </div>

</div>

<script type='text/javascript' src='/js/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='/js/sm.min.js' charset='utf-8'></script>

<script>
    $("#feedback_sub").on('click', function () {
        var feedbackName = $('#feedback_name').val();
        var feedFs = $('#feedback_fs').val();
        var feedTel = $('#feedback_tel').val();
        var feedDetails = $('#feedback_details').val();
        var type= $('#feedback_type').val();
        ajax_feedback(feedFs, feedbackName, feedDetails, feedTel,type);
    })

    $(document).on('open','.popup-feedback', function () {
        $('#feedback_details').val("");
    });


    $(".open-avatar").on('click', function () {
        $.popup('.popup-avatar');
    });
    $(".activity").on('click', function () {
        $.alert("敬请期待...");
        return;
    });
    $(".coupon").on('click', function () {
        if ($(this).attr("data-value") == "0") {
            return;
        }
        $.prompt('请输入核销码',function (value) {
                    if ($.trim(value) == "") {
                        $.alert("核销码值不能为空");
                        return;
                    }
                    ajax_useCoupon(value);
                }
        );
    })
    $("#cancelUpdate").on('click', function () {
        $("#pre_avatar").attr("src", $("#avatar").attr("src"));
        $("#avatarFile").val("");
    })
    $("#updateAvatar").on('click', function () {
        upload_ajax();
    })
    $("#avatarFile").on('change', function () {
        var objUrl = getObjectURL($("#avatarFile")[0].files[0]);
        if (objUrl) {
            $("#pre_avatar").attr("src", objUrl);
        }
    });

    function upload_ajax() {
        var uploadFile = $("#avatarFile");
        if (uploadFile.val() == "") {
            $.closeModal(".popup-avatar");
            return false;
        }
        var fileExtension = uploadFile.val().substring(uploadFile.val().lastIndexOf("."), uploadFile.val().length);
        var extensions = ".jpg,.JPG,.jpeg,.JPEG,.png,.PNG";
        if (extensions.indexOf(fileExtension) < 0) {
            $.alert("请选择正确的图片格式!");
            return false;
        }
        var formData = new FormData();
        formData.append('file', uploadFile[0].files[0]);
        var imageSize = uploadFile[0].files[0].size;

        if (imageSize > 1024 * 1024 * 3) {
            $.alert("图片大于3M,请重新选择！")
            return;
        }
        $.ajax({
            url: '/index/upload',
            type: 'POST',
            data: formData,
            async: false,
//            cache: false,
            contentType: false,
            processData: false,
            beforeSend: function () {
                $.showIndicator();
            },
            success: function (data) {
                if (data.success == true) {
                    $.toast("上传成功");
                    location.reload();
                }
            },
            complete: function () {
                $.hideIndicator();
            },
            error: function (data) {
                $.alert("异常错误,稍后再试");
            }
        });
    }

    //获得文件的网络路径
    function getObjectURL(file) {
        var url = null;
        if (window.createObjectURL != undefined) { // basic
            url = window.createObjectURL(file);
        } else if (window.URL != undefined) { // mozilla(firefox)
            url = window.URL.createObjectURL(file);
        } else if (window.webkitURL != undefined) { // webkit or chrome
            url = window.webkitURL.createObjectURL(file);
        }
        return url;
    }

    //全局
    var jsonArrayContract = '';
    <c:if test="${not empty listContract}">
    jsonArrayContract = ${listContract};
    </c:if>

    $('#view').on('click', function () {
        var selectedContractId = $("#contract").attr("data-value");
        var buttons1 = [
            {
                text: '请选择合同',
                label: true
            }
        ];
        var buttons2 = [
            {
                text: '取消',
                bg: 'danger'
            }
        ];
        var contractName = "";
        var contractIndex = "";

        $.each(jsonArrayContract, function (index, contract) {
            contractName = contract['报名日期'] + '报名合同';
            contractIndex = index;
            var button_json = {
                text: contractName,
                id: contractIndex,
                disabled: selectedContractId == contractIndex,
                onClick: function () {
                    if (!this.disabled) {
                        contract_change(this.id);
                    }
                }
            };
            buttons1.push(button_json);
        })
        var groups = [buttons1, buttons2];
        $.actions(groups);
    });
    function contract_change(index) {
        $("#contract").attr('data-value', index);
        $("#c_regDate").text(jsonArrayContract[index]['报名日期']);
        $("#c_validity").text(jsonArrayContract[index]['有效期']);
        $("#c_residuePeriods").text(jsonArrayContract[index]['剩余课时数']);
        $("#c_regPeriods").text(jsonArrayContract[index]['报名课时数']);
        $("#c_totalLeave").text(jsonArrayContract[index]['累计请假数']);
//        $("#c_regSum").text(jsonArrayContract[index]['合同金额']);
        $("#c_class").text(jsonArrayContract[index]['课程']);
        $("#c_give").text(jsonArrayContract[index]['赠课']);
        $("#c_score").text(jsonArrayContract[index]['积分']);
    }

    function ajax_useCoupon(code) {
        this.code = code;

        $.ajax({
            url: '/index/coupon',
            type: 'POST',
            data: {'code': this.code},
//            async: false,
            contentType: "application/x-www-form-urlencoded",
            dataType: "json",
            beforeSend: function () {
                $.showIndicator();
            },
            success: function (data) {
                if (data.success == true) {
                    $.toast("使用成功√");
                    location.reload();
                } else if (data.success == false) {
                    $.alert("使用失败," + data.message);
                }
            },
            complete: function () {
                $.hideIndicator();
            },
            error: function (data) {
                $.alert("异常错误,稍后再试");
            }
        });
    }

    function ajax_feedback(Franchisee, feedbackName, details, contactTel,type) {
        $.ajax({
            type: "POST",
            url: "/login/feedback",
            data: {"Franchisee": Franchisee, "name": feedbackName, "details": details, "contactTel": contactTel,"type":type},
            contentType: "application/x-www-form-urlencoded",
            dataType: "json",
            success: function (data) {
                $.toast('已提交，谢谢你的反馈');
                $.closeModal('.popup-feedback');
            }
        })
    }
    $.init();
</script>
</body>

</html>