/**
 * 首页
 */
define(function (require, exports, module) {
    var appUtils = require("appUtils"),
        layerUtils = require("layerUtils"),
        validatorUtil = require("validatorUtil"),
        gconfig = require("gconfig"),
        progressService = require("progressService"),
        pageCode = "progress/index",
        _pageId = "#progress_index ";

    /**
     * 初始化
     */
    function init() {
    }

    /**
     * 事件绑定
     */
    function bindPageEvent() {
        appUtils.bindEvent($(_pageId + "#submit"), function () {
            var mobile = $(_pageId + "#mobile").val();
            if (validatorUtil.isEmpty(mobile)) {
                layerUtils.iAlert("请输入预留手机号！", -1);
                return;
            }
            if (!(/^1[34578]\d{9}$/.test(mobile))) {
                layerUtils.iAlert("预留手机号格式错误，请确认！", -1);
                return;
            }
            var param = {
                "mobile": mobile
            };
            progressService.login(param, function (resultVo) {
                if (resultVo.errorNo == "0") {
                    var order_list = resultVo.result[0].data;
                    if (order_list.length == 0) {
                        layerUtils.iAlert("您当前无正在受理的业务", -1);
                    } else if (order_list.length == 1) {
                        appUtils.pageInit(pageCode, "progress/progress_list", {
                            "acceptNo": order_list[0].acceptNo,
                            "productTypeCode": order_list[0].productTypeCode,
                            "userName": order_list[0].userName,
                            "staffSet": order_list[0].staffSet
                        });
                    } else {
                        appUtils.pageInit(pageCode, "progress/business_list", {
                            "mobile": mobile,
                            "userName": order_list[0].userName,
                            "staffSet": order_list[0].staffSet
                        });
                    }
                } else {
                    layerUtils.iAlert(resultVo.errorMsg);
                }
            });
        });
    }

    /**
     * 销毁
     */
    function destroy() {
    }

    var progress_index = {
        "init": init,
        "bindPageEvent": bindPageEvent,
        "destroy": destroy
    };

    module.exports = progress_index;
});