/**
 * 进度页面
 */
define(function (require, exports, module) {
    var appUtils = require("appUtils"),
        layerUtils = require("layerUtils"),
        validatorUtil = require("validatorUtil"),
        gconfig = require("gconfig"),
        progressService = require("progressService"),
        pageCode = "progress/progress_list",
        _pageId = "#progress_progress_list ";

    /**
     * 初始化
     */
    function init() {
        var acceptNo = appUtils.getPageParam("acceptNo");
        $(_pageId + '#user_name').html(appUtils.getPageParam("userName"));
        $(_pageId + "#productTypeCode").html("业务名称：" + appUtils.getPageParam("productTypeCode"));

        if (validatorUtil.isEmpty(acceptNo)) {
            layerUtils.iAlert("非法请求", -1);
        }
        var param = {
            "acceptNo": acceptNo
        };
        progressService.listProgress(param, function (resultVo) {
            if (resultVo.errorNo == "0") {
                var result = resultVo.result;
                var html = "";
                var user_name = "";
                for (var i = 0; i < result.length; i++) {
                    var data = result[i];
                    var state = "";
                    switch (data.progressState) {
                        case "1":
                            state = "on";
                            break;
                        case "2":
                            state = "ing";
                            break;
                        default :
                            state = "";
                            break;
                    }

                    var time = data.state_start_time;
                    var date = validatorUtil.isEmpty(time) ? "" : "【" + time.substr(0, 4) + "-" + time.substr(4, 2) + "-" + time.substr(6, 2) + " " + time.substr(8, 2) + ":" + time.substr(10, 2) + "】";

                    var staff = "";
                    var sName = data.staff_name;
                    if (validatorUtil.isNotEmpty(sName)) {
                        if (sName.indexOf("_MOS") > -1) {
                            sName= sName.substr(0, sName.indexOf("_MOS"));
                        }
                        staff += sName;
                    }

                    var sMode = data.staff_mode;
                    if (validatorUtil.isNotEmpty(sMode)) {
                        if (sMode.indexOf("86") > -1) {
                            sMode = "【" + sMode.substring(sMode.indexOf("86") + 2, sMode.length) + "】";
                        }
                        staff += "【" + sMode + "】";
                    }

                    var progress = "";
                    var ssTime = data.staff_start_time;
                    if (validatorUtil.isNotEmpty(ssTime)) {
                        ssTime = "【" + ssTime.substr(0, 4) + "-" + ssTime.substr(4, 2) + "-" + ssTime.substr(6, 2) + " " + ssTime.substr(8, 2) + ":" + ssTime.substr(10, 2) + "】";
                        progress += ssTime;
                    }

                    var content = "";
                    if (data.progressState != "2") {
                        progress += data.progressDesc;
                        content = '<span class="time">' + staff + '</span>' +
                        '<span class="time">' + progress + '</span>';
                    } else {
                        //已为您安排张洞师傅上门施工，联系电话12345678999，会在（预约时间）之前与您联系。
                        content = '<span class="time">已为您安排 ' + sName + '师傅上门施工，联系电话' + sMode + '，会在' + ssTime + '之前与您联系。</span>';
                    }

                    html += '<ul class="of-storey">' +
                    '<li>' +
                    '<span class="icon ' + state + '"></span>' +
                    '<span>' + date + data.progressStageName + '</span>' +
                    content +
                    '</li>' +
                    '</ul>';
                }
                $(_pageId + '#progress_list').html(html);
            }
        });
    }

    /**
     * 事件绑定
     */
    function bindPageEvent() {
        appUtils.bindEvent($(_pageId + ".back_btn"), function () {
            appUtils.pageBack();
        })
    }

    /**
     * 销毁
     */
    function destroy() {
    }

    var progress_progress_list = {
        "init": init,
        "bindPageEvent": bindPageEvent,
        "destroy": destroy
    };

    module.exports = progress_progress_list;
});