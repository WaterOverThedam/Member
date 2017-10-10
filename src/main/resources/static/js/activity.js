/**
 * Created by TONY on 2017/10/10.
 */

/****************动态加载开始***********************************/
$("#home").addClass("active");

var loading = false;
// 最多可加载的条目
var maxItems = 40;
// 每次加载添加多少条目
var itemsPerLoad = 4;
//预先加载x条
try{
    //var map = new BMap.Map("bdMapBox");
    var nowCity = new BMap.LocalCity();
    nowCity.get(bdGetPosition);
}catch (e){
    console.error(e)
}

//初始值
var lastIndex=1;
function bdGetPosition(result){
    var cityName = result.name; //当前的城市名
    /*自定义代码*/
    $(".mycity").text(cityName);
    //预先加载4条;同时会确定 maxItems
    ajax_getItems(4, lastIndex, cityName);
    $('#keyword').val(cityName)

}

// 接上次加载的序号，并注册
$(document).on('infinite', '.infinite-scroll-bottom', function () {
    if (loading) return;
    loading = true;
    // 模拟1s的加载过程
    setTimeout(function () {
        // 重置加载flag
        loading = false;
        if (lastIndex >= maxItems) {
            // 加载完毕，则注销无限加载事件，以防不必要的加载
            $.detachInfiniteScroll($('.infinite-scroll'));
            // 删除加载提示符
            $('.infinite-scroll-preloader').remove();
            return;
        }
        // 添加新条目
        lastIndex++;
        ajax_getItems(itemsPerLoad,lastIndex,$('#keyword').val());
        // 更新最后加载的页面
        //lastIndex = $('.card-container .card').length;
        //容器发生改变,如果是js滚动，需要刷新滚动
        $.refreshScroller();
    }, 1000);

});

$(document).on('refresh', '.pull-to-refresh-content', function (e) {
    // 模拟2s的加载过程
    setTimeout(function () {
        // 加载完毕需要重置
        $.pullToRefreshDone('.pull-to-refresh-content');
        location.reload();
    }, 2000);

});



function toggleLike() {
    var iconLike = $("#icon-like i");
    if (iconLike.hasClass("fa fa-heart fa-2x")) {
        $("#icon-like i").removeClass("fa fa-heart fa-2x");
        $("#icon-like i").addClass("fa fa-heart-o fa-2x");
        $.toast("已取消", 500);
    } else {
        $("#icon-like i").removeClass("fa fa-heart-o fa-2x");
        $("#icon-like i").addClass("fa fa-heart fa-2x");
        $.toast("已喜欢", 500);
    }
}

$("#add_val").on('click',function () {
    var tel = $("#add_phone").val();
    if (!checkTel(tel)){
        $.alert("请输入正确的手机号");
        return;
    }
    ajax_val(tel);
})

var waitTime = 60;
var valTimer;
function time(o) {
    if (waitTime == 0) {
        o.removeAttr("disabled");
        o.removeClass("disabled");
        o.text("发送验证码");
        waitTime = 60;
    } else {
        o.addClass("disabled");
        o.attr("disabled",true);
        o.text(waitTime + "s重新获取");
        waitTime--;
        valTimer = setTimeout(function () {
                time(o);//循环调用
            },
            1000)
    }
}

function checkAdd() {
    if (!checkTel($("#add_phone").val())) {
        $.alert("请输入正确的手机");
        return false;
    }
    if ($.trim($("#add_name").val()).length > 16 || $.trim($("#add_name").val()) == '') {
        $.alert("请输入正确的名字");
        return false;
    }
    if ($.trim($("#role").val())=="家长/孩子"||$.trim($("#role").val())=="") {
        $.alert("请选择家长/孩子");
        return false;
    }
//        if (!/^\d{4}$/.test(($.trim($("#add_num").val())))){
//            $.alert("验证码为4位数字");
//            return false;
//        }
    return true;
}



/****************myinfo-开始***********************************/
$("#info").on('click', function () {
    $.popup('.popup-profile');
});

$("#city-picker").cityPicker({});
$("#changeCity").on('click', function () {
    $("#city-picker").picker("open");
})



/*activity_Enrol*/
$("div[id^='icon-']").on('click', function () {
    idNative = $(this).attr("id");
    idOffset = idNative.split("-")[1];
    if (idOffset != 'review' && idOffset != 'like') {
        $('.content').scrollTop($("#" + idOffset).offset().top)
    }
});

$("#icon-like").on('click', function () {
    toggleLike();
});



$(".icon.icon-edit").on('click', function () {
    var buttons1 = [
        {
            text: '请选择',
            label: true
        },
        {
            text: '修改',
            onClick: function () {
                $.alert("你选择了“修改信息“");
            }
        },
        {
            text: '删除',
            bold: true,
            color: 'danger',
            onClick: function () {
                $.popup('.popup-add');
            }
        },
        {
            text: '添加',
            bold: true,
            color: 'danger',
            onClick: function () {
                $.popup('.popup-add');
            }
        },
    ];
    var buttons2 = [
        {
            text: '取消',
            bg: 'danger'
        }
    ];
    var groups = [buttons1, buttons2];
    $.actions(groups);
});

$("#pickerActivity").toggleClass("disabled");
$("#agree").on('change', function () {
    var is_checked = $(this).prop("checked");
    $("#pickerActivity").toggleClass("disabled");
})




//1：报名；0：取消；3：预报名
$("#pickerActivity").on('click', function () {
    if (!$(this).hasClass("disabled")) {
        $("#activity").data("status",3);
        if($(".gym-main").length == 0){
            $.confirm('请先填写报名信息', function () {
                $.popup('.popup-add');
            });
//                html = ' <div class="gym-main">'+
//                    '<i class="fa fa-user"></i>报名人：<label class="gym-person-name">刘翔</label>'+
//                    '</div>';
        }else {
            enrol();
        }
    } else {
        $.alert("请先阅读活动内容及规则！")
    }

});



$("#update_submit").on('click',function () {
    if (checkUpdatePar()){
        var $up_name = $('.up-name');
        var up_name = '';
        for(name in $up_name){
            up_name = name.val() +'、';
        }
        //TODO
        var actId = 1;
        ajax_update_par(id,actId,up_name);
    }
    ajax_update_par();
    $.alert("修改成功");
})

function resetPopup(popup){
    var $popup = $(popup);
    $popup.find('input').val('');//清空表单
    var val = $popup.find('button');
    val.removeAttr('disabled');
    val.removeClass('disabled');
    val.text('获取验证码');
    clearTimeout(valTimer);//移除定时器
}
