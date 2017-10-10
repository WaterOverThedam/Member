/**
 * Created by Tony on 2017/9/10.
 */


function result(data) {
    data = data.replace(/\|/g, '"')
    try {
        //alert($.parseJSON(data))
        return $.parseJSON(data)
    }catch (e){
        console.error(data)
        console.error(e.message)
    }
    return null;
}

function result(dialog,code,msg) {
    if(code){
        msg = msg ? msg : "操作失败";
    }else {
        msg = msg ? msg : "操作成功";
    }
    msg = "<p>" + msg + "</p>";
    $(dialog).find(".content").html(msg)
    $(dialog).modal('show');
}

function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg); //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

function GetRequest() {
    var url = location.search; //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for(var i = 0; i < strs.length; i ++) {
            theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
        }
    }
    return theRequest;
}


function date_end(dt,n){
    ndays = parseInt(n)*7-1;
    return date_cal(dt,ndays)
}
function date_start(dt,n){
    ndays = parseInt(n)*7;
    return date_cal(dt,ndays)
}
function date_cal(dt,ndays){
    dt = new Date(dt)
    dt.setDate(dt.getDate()+ndays);
    dtend  = dt.Format("yyyy-MM-dd");
    return dtend
}

Date.prototype.Format = function(fmt)
{ //author: meizz
    var o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt))
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));//后4位
    for(var k in o)
        if(new RegExp("("+ k +")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
    return fmt;
}



function toDate(input){
    var oDate=new Date(input);
    return oDate.getFullYear()+'-'+(f(oDate.getMonth()+1))+'-'+f(oDate.getDate())+' '+f(oDate.getHours())+':'+f(oDate.getMinutes());
    function f(s) {
        return ('00'+s).substr(-2);
    }
}



function checkTel(tel){
    return /^1[34578]\d{9}$/.test($.trim(tel));
}

