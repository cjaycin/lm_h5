define(function (require, exports, module) {

    var gconfig = require("gconfig");
    var appUtils = require("appUtils");
    var layerUtils = require("layerUtils");
    var global = gconfig.global;
    var userService = require("userService");
    var validatorUtil = require("validatorUtil");

    function toLoginPage(pageCode) {
        var toLoginfunc = function () {
            var app = navigator.appVersion;
            var pageParam = appUtils.getPageParam();
            var pageParamStr = "";
            if (pageParam) {
                for (var key in pageParam) {
                    pageParamStr = pageParamStr + "&" + key + "=" + pageParam[key];
                }
            }
            if(pageParamStr){
                pageParamStr = pageParamStr.replace(/&/,"?");
            }
            var  url = window.location.protocol+"//" + window.location.host + "/m/frame/index.html#!/" + pageCode + ".html" + pageParamStr;
            if(app.indexOf(global.new_yzh_userAgent) >= 0) { //新的一账户客户端登录
                var json = '{"func":"33001","url":"#!/me/login.html","data":"toLogin:from_mncg|' + url + '"}';
                eno.request('{"func":"30011"}');//关闭界面
                eno.request(json);//登录页面
            }
        };
        $(".iLoading_overlay").hide();
        $(".iLoading_showbox").hide();
        layerUtils.iAlert("会话已经失效，请重新登录！",-1,toLoginfunc);
    }

    function  yzhlogin(){
        var from = appUtils.getPageParam("from");
        if(global.from_yzh == from){
            //一账户登录
            var token = appUtils.getPageParam("token"); //页面传参:token
            if(validatorUtil.isNotEmpty(token)){
                var callback = function(resultVo){
                    if(resultVo.errorNo == "0"){
                        return;
                    }else{
                        layerUtils.iAlert("登录失败：" + resultVo.errorMsg,-1,function(){
                            var app = navigator.appVersion;
                            if (app.indexOf(global.new_yzh_userAgent) >= 0) {
                                eno.request('{"func":"30011"}');
                            }
                        });
                    }
                };
                var reqInfo = {
                    "func": "100001",
                    "token":token
                };
                ajaxInvoke(reqInfo, callback, false,false);
            }
        } else if(global.from_ytg == from){
            //云投顾模拟跟投登录
            var token = appUtils.getPageParam("token"); //页面传参:token
            if(validatorUtil.isNotEmpty(token)){
                var callback = function(resultVo){
                    if(resultVo.errorNo == "0"){
                        return;
                    }else{
                        layerUtils.iAlert("登录失败：" + resultVo.errorMsg,-1,function(){
                            appUtils.sendDirect(gconfig.global.ytg_callback_url+appUtils.getPageParam("portfolio_id"));
                        });
                    }
                };
                var reqInfo = {
                    "func": "100001",
                    "token":token
                };
                ajaxInvoke(reqInfo, callback, false,false);
            }
        }else{
            return;
        }
    }

    /**
     * ajax请求
     */
    function ajaxInvoke(reqInfo, callback, async,cache) {
        $.ajax({
            type: "post", //以post方式与后台沟通
            url : global.serverPath,
            data: reqInfo,
            dataType:'json',
            cache:cache,
            async:async,
            success: function(resultVo) {
                callback(resultVo);
            }
        });
    }

    function footerTab(pageId,pageCode) {
        appUtils.bindEvent($(pageId + ".footer_nav .row-1"), function () {
            var curIndex = $(pageId + ".footer_nav .row-1").index(this);
            if("0" == curIndex && "index" != pageCode){
                appUtils.pageInit(pageCode, "index", {});
            }else if("1" == curIndex && "match/matchIndex" != pageCode){
                appUtils.pageInit(pageCode, "match/matchIndex", {});
            }
            else if("2" == curIndex && "rank/index" != pageCode){
                appUtils.pageInit(pageCode, "rank/index", {});
            }
            else if("3" == curIndex && "recommend/recommendIndex" != pageCode){
                appUtils.pageInit(pageCode, "recommend/recommendIndex", {});
            }
        })
    }

    function endWith (str,endStr){
        var d = str.length - endStr.length;
        return(d >= 0 && str.indexOf(endStr) == d);
    }

    function quit(){
        var callBack = function(resultVo){
            var app = navigator.appVersion;
            if (app.indexOf(gconfig.global.new_yzh_userAgent) >= 0) {
                eno.request('{"func":"30011"}');
            }
        };
        var param ={
        };
        var ctrlParam ={
            "isShowWait":true,
            "isShowOverLay":true,
            "isLastReq":true
        };
        userService.quit(param,callBack,ctrlParam);

    }

    function isTradeTime(beginTime, endTime){
        var beginArray = beginTime.split(":");
        if(beginArray.length != 2){
            return false;
        }
        var endArray = endTime.split(":");
        if(endArray.length != 2){
            return false;
        }
        var begin = new Date();
        var end = new Date();
        var now = new Date();

        begin.setHours(beginArray[0]);
        begin.setMinutes(beginArray[1]);
        end.setHours(endArray[0]);
        end.setMinutes(endArray[1]);

        if (now.getTime() > begin.getTime() && now.getTime() < end.getTime()){
            return true;
        } else {
            return false;
        }
    }

    function logout(){
        var callBack = function(resultVo){
            var portfolio_id = appUtils.getSStorageInfo("portfolio_id");
            if(portfolio_id){
                appUtils.sendDirect(gconfig.global.ytg_callback_url+portfolio_id);
            }else{
                var app = navigator.appVersion;
                if (app.indexOf(gconfig.global.new_yzh_userAgent) >= 0) {
                    eno.request('{"func":"30011"}');
                }
            }
        };
        var param = {};
        var ctrlParam = {
            "isShowWait":true,
            "isShowOverLay":true,
            "isLastReq":true
        };
        userService.quit(param, callBack, ctrlParam);
    }

    var common = {
        "ajaxInvoke":ajaxInvoke,
        "footerTab":footerTab,
        "endWith":endWith,
        "quit":quit,
        "yzhlogin":yzhlogin,
        "toLoginPage":toLoginPage,
        "isTradeTime": isTradeTime,
        "logout": logout
    };
    module.exports = common;
});