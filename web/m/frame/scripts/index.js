/**
 * 首页
 */
define(function (require, exports, module) {
    var appUtils = require("appUtils"),
        layerUtils = require("layerUtils"),
        validatorUtil = require("validatorUtil"),
        gconfig = require("gconfig"),
        userService = require("userService"),
        common = require("common"),
        pageCode = "index",
        _pageId = "#index ";

    /**
     * 初始化
     */
    function init() {
        userService.listAllCustomer(null, function (resultVo) {
            if (resultVo.errorNo == "0") {
                $(_pageId + ".account_list").empty();
                var html = "";
                for (var i = 0; i < resultVo.result.length; i++) {
                    var dataRow = resultVo.result[i];
                    html += '<div class="account">' +
                    '<div class="account-title"><ul><li class="ui layout"><div class="row-1"><p>' + dataRow.name + '</p></div><div class="row-1"><div accShow="1" class="acc-switch account-up"></div></div></li></ul></div>' +
                    '<div class="account-content"><ul>' +
                    '<li class="ui layout"><div class="row-1"><p>地址</p></div><div class="row-1"><p class="text-right total_assets">' + dataRow.address + '</p></div>' +
                    '<li class="ui layout"><div class="row-1"><p>联系方式</p></div><div class="row-1"><p class="text-right">' + dataRow.mobile + '</p></div>' +
                    '<li class="ui layout"><div class="row-1"><p>联系方式</p></div><div class="row-1"><p class="text-right"></p></div>' +
                    '<li class="ui layout"><div class="row-1"><p>仓位</p></div><div class="row-1"><p class="text-right"></p></div>' +
                    '<li class="ui layout"><div class="row-1"><p>日收益率</p></div><div class="row-1"><p class="text-right"></p></div>' +
                    '<li class="ui layout"><div class="row-1"><p>周收益率</p></div><div class="row-1"><p class="text-right"></p></div>' +
                    '<li class="ui layout"><div class="row-1"><p>月收益率</p></div><div class="row-1"><p class="text-right"></p></div>' +
                    '<li class="ui layout"><div class="row-1"><p>总收益率</p></div><div class="row-1"><p class="text-right total-yield"></p></div>' +
                    '</ul></div><div class="trade_box"><a href="javascript:void(0);" class="trade_btn" id="' + dataRow.id + '">查看订单详情</a></div></div>';
                }
                $(_pageId + ".account_list").html(html);
                appUtils.bindEvent($(_pageId + ".acc-switch"), function () {
                    var accShow = $(this).attr("accShow");
                    if (accShow == "1") {
                        $(this).attr("accShow", "0");
                        $(this).removeClass("account-up").addClass("account-down");
                        $(this).parent().parent().parent().parent().parent().children("div[class='account-content']").hide();
                    } else {
                        $(this).attr("accShow", "1");
                        $(this).removeClass("account-down").addClass("account-up");
                        $(this).parent().parent().parent().parent().parent().children("div[class='account-content']").show();
                    }
                });
                appUtils.bindEvent($(_pageId + ".trade_btn"), function () {
                    var param = {
                        "account_id": $(this).attr("id"),
                        "trade_prePageCode": pageCode
                    };
                    appUtils.pageInit(pageCode, "trade/index", param);
                });
            } else {
                layerUtils.iAlert(resultVo.errorMsg, -1);
                $(".iLoading_overlay").hide();
                $(".iLoading_showbox").hide();
                return;
            }
        });
    }

    /**
     * 事件绑定
     */
    function bindPageEvent() {

    }

    /**
     * 销毁
     */
    function destroy() {
    }

    var index = {
        "init": init,
        "bindPageEvent": bindPageEvent,
        "destroy": destroy
    };

    module.exports = index;
});