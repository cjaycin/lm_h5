/**
 * 进度页面
 */
define(function (require, exports, module) {
    var appUtils = require("appUtils"),
        layerUtils = require("layerUtils"),
        validatorUtil = require("validatorUtil"),
        gconfig = require("gconfig"),
        progressService = require("progressService"),
        pageCode = "progress/business_list",
        _pageId = "#progress_business_list ";

    /**
     * 初始化
     */
    function init() {
        var mobile = appUtils.getPageParam("mobile");
        $(_pageId + '#user_name').html(appUtils.getPageParam("userName"));
        if (validatorUtil.isEmpty(mobile)) {
            layerUtils.iAlert("非法请求", -1);
        }

        var param = {
            "mobile": mobile
        };
        progressService.listAll(param, function (resultVo) {
            if (resultVo.errorNo == "0") {
                var order_list = resultVo.result[0].data;
                var html = "";
                var user_name = "";
                for (var i = 0; i < order_list.length; i++) {
                    var order = order_list[i];
                    html += '<div class="list_table" acceptNo="' + order.acceptNo + '" productTypeCode="' + order.productTypeCode + '">' +
                    '<table>' +
                    '<tr>' +
                    '<td>' + (i + 1) + '.' + order.productTypeCode + '</td>' +
                        //'<td><h4>' + order.userName + '</h4></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td colspan="2" class="info_b">' +
                    '<span>' + order.eparchyCode + '</span>' +
                    '<td>' +
                    '</tr>' +
                    '</table>' +
                    '</div>';
                }
                $(_pageId + '#business_list').html(html);
            }
        });
    }

    /**
     * 事件绑定
     */
    function bindPageEvent() {
        appUtils.preBindEvent($(_pageId + "#business_list"), ".list_table", function () {
            appUtils.pageInit(pageCode, "progress/progress_list", {
                "acceptNo": $(this).attr("acceptNo"),
                "productTypeCode": $(this).attr("productTypeCode")
            });
        });
        appUtils.bindEvent($(_pageId + ".back_btn"), function () {
            appUtils.clearSStorage("userName");
            appUtils.pageInit(pageCode, "progress/index");
        })
    }

    /**
     * 销毁
     */
    function destroy() {
    }

    var progress_business_list = {
        "init": init,
        "bindPageEvent": bindPageEvent,
        "destroy": destroy
    };

    module.exports = progress_business_list;
});