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

function showResult(dialog,code,msg) {
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
    var url = decodeURI(location.search); //获取url中"?"符后的字串
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
//五种参数
//new Date("month dd,yyyy hh:mm:ss");
//new Date("month dd,yyyy");
//new Date(yyyy,mth,dd,hh,mm,ss);
//new Date(yyyy,mth,dd);
//new Date(ms);


function get_data_json(url,data,async) {
    var res;
    var async = async||false;
    $.ajax({
        url: url,
        dataType : "json",
        data: data,
        async: async,
        cache: false,
        type: "get",
        success: function(data) { //string;dom
            try{
                if(!data.code){
                    res = data.data;
                }else{
                    res = null;
                }
            }catch(e){
                alert(e.message)
            }
        }
    });
    return res
}


function post_data_json(url,data,async) {
    var res;
    var async = async||false;
    $.ajax({
        url: url,
        dataType : "json",
        data: data,
        async: async,
        cache: false,
        type: "post",
        success: function(data) { //string;dom
            try{
                if(!data.code){
                    res = data.data;
                }else{
                    res = null;
                }
            }catch(e){
                alert(e.message)
            }
        }
    });
    return res
}

function checkTel(tel){
    return /^1[34578]\d{9}$/.test($.trim(tel));
}

function CurTimeStamp(){
    return Date.parse(new Date())/1000;
}

/*
 当前日期 CurTime().substr(0,10)
 当前时间 CurTime().substr(10)
 */
function CurTime(type){
    var type = type||"d";
    type = type.toLowerCase();
    CurTimeStamp = Date.parse(new Date())/1000;
    dt = TimeStampToDate(CurTimeStamp);
    switch(type){
        case "d":
            return dt.substr(0,10);
        case "h":
            return dt.substr(10);
        case "f":
            return dt
        default:
            return dt.substr(0,10)
    }
}
/*
 day(d) - 86400
 hour(h) - 3600
 min(m) -60
 */
function DateAdd(dt,num,type){
    if (IsDate(dt)){
        var tp = DateToTimeStamp(dt)
        var num = num||0;
        var type = type||"d";
        type = type.toLowerCase();
        var second;
        switch(type){
            case "d":
                s=86400*num;
                break;
            case "h":
                s=3600*num;
                break;
            case "m":
                s=60*num;
                break;
            default:
                s=num;
        }
        tp = tp + s;
        return TimeStampToDate(tp).substr(0,10)
    }else{
        return "dateformat:yyyy-mm-dd";
    }
}
function IsDate(dt){
    var regexp = /^([1][7-9][0-9][0-9]|[2][0][0-9][0-9])(\-)([0][1-9]|[1][0-2])(\-)([0-2][1-9]|[3][0-1])$/g;
    // 日期范围:1700-01-01 ----2099-01-01
    return regexp.test(dt);
}
function DateToTimeStamp(string) {
    var f = string.split(' ', 2);
    var d = (f[0] ? f[0] : '').split(/[-/]/, 3);
    var t = (f[1] ? f[1] : '').split(':', 3);
    return (new Date(
            parseInt(d[0], 10) || null,
            (parseInt(d[1], 10) || 1) - 1,
            parseInt(d[2], 10) || null,
            parseInt(t[0], 10) || null,
            parseInt(t[1], 10) || null,
            parseInt(t[2], 10) || null
        )).getTime()/1000 ;
}
function add0(m){return m<10?'0'+m:m };
function TimeStampToDate(shijianchuo,sperator) {
    //shijianchuo是整数，否则要parseInt转换
    var time = new Date(shijianchuo*1000);
    var y = time.getFullYear();
    var m = time.getMonth()+1;
    var d = time.getDate();
    var h = time.getHours();
    var mm = time.getMinutes();
    var s = time.getSeconds();
    sperator  =  (typeof(sperator)=="undefined")? "-":sperator;
    return y+sperator+add0(m)+sperator+add0(d)+' '+add0(h)+':'+add0(mm)+':'+add0(s);
};
function IsDate(num){
    var regexp = /^([1][7-9][0-9][0-9]|[2][0][0-9][0-9])(\-)([0][1-9]|[1][0-2])(\-)([0-2][1-9]|[3][0-1])$/g;
    // 日期范围：1700-01-01 -2099-01-01
    return regexp.test(num)
}

function isIE() { //ie?
    if (!!window.ActiveXObject || "ActiveXObject" in window)
        return true;
    else
        return false;
}
function clipForIE()
{
    var el = document.getElementById('result');
    el.contentEditable = 'true';
    var controlRange;
    if (document.body.createControlRange) {
        controlRange = document.body.createControlRange();
        controlRange.addElement(el);
        controlRange.execCommand('Copy');
    }
    el.contentEditable = 'false';
    alert("已复制好，可贴粘到excel!");
}
