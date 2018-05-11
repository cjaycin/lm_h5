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
        var staffSet = appUtils.getPageParam("staffSet");
        $(_pageId + '#user_name').html(appUtils.getPageParam("userName"));
        $(_pageId + "#productTypeCode").html("业务名称：" + appUtils.getPageParam("productTypeCode"));

        if (validatorUtil.isEmpty(acceptNo)) {
            layerUtils.iAlert("非法请求", -1);
        }
        initProgress(acceptNo, staffSet);

    }

    function initProgress(acceptNo, staffSet) {
        var param = {
            "acceptNo": acceptNo
        };
        progressService.listProgress(param, function (resultVo) {
        //    var resultVo = {"result":[{"progressNo":"1","progressStageName":"业务受理","progressDesc":"已完成","progressState":"1","state_start_time":"20171221144409","staff_start_time":null,"staff_name":null,"staff_mode":null},{"progressNo":"2","progressStageName":"网络施工","progressDesc":"已完成","progressState":"1","state_start_time":"20171221144824","staff_start_time":null,"staff_name":null,"staff_mode":null},{"progressNo":"3","progressStageName":"外线施工","progressDesc":"已完成","progressState":"1","state_start_time":"20171221144857","staff_start_time":"20171221211605","staff_name":"双喜","staff_mode":""},{"progressNo":"4","progressStageName":"竣工","progressDesc":"正在施工","progressState":"2","state_start_time":"20171221211729","staff_start_time":null,"staff_name":null,"staff_mode":null}],"page":{"numPerPage":0,"totalRows":0,"totalPages":0,"currentPage":0,"startIndex":0,"lastIndex":0,"data":null},"errorMsg":"调用成功","errorNo":0};;
            var result = resultVo.result;
            var html = "";
            var user_name = "";
            for (var i = result.length - 1; i >= 0; i--) {
                var data = result[i];
                var li_class = "", icon_class = "";
                switch (data.progressState) {
                    case "1":
                        break;
                    case "2":
                        li_class = "ing";
                        icon_class = "on";
                        break;
                    default :
                        state = "";
                        break;
                }

                var date_time = data.state_start_time;
                var date = validatorUtil.isEmpty(date_time) ? "" : date_time.substr(4, 2) + "-" + date_time.substr(6, 2);
                var time = validatorUtil.isEmpty(date_time) ? "" : date_time.substr(8, 2) + ":" + date_time.substr(10, 2);
                var ssTime = date + " " + time;

                var staff = "";
                var sName = data.staff_name;
                if (validatorUtil.isNotEmpty(sName)) {
                    if (sName.indexOf("_MOS") > -1) {
                        sName = sName.substr(0, sName.indexOf("_MOS"));
                    }
                    staff += sName;
                }

                var sMode = data.staff_mode;
                if (validatorUtil.isNotEmpty(sMode)) {
                    if (sMode.indexOf("86") > -1) {
                        sMode = sMode.substring(sMode.indexOf("86") + 2, sMode.length);
                    }
                    staff += sMode;
                }

                var content = "";
                if (data.progressNo == "1") {
                    content = "您的订单已受理，我们正在联系您区域内的施工人员，请耐心等待";
                } else {
                    //已为您安排张洞师傅上门施工，联系电话12345678999，会在（预约时间）之前与您联系。
                    if (validatorUtil.isEmpty(sName)) {
                        content = data.progressStageName + '【' + data.progressDesc + '】';
                    } else {
                        content = '已为您安排 ' + sName + ' 师傅上门施工';
                        if (validatorUtil.isNotEmpty(sMode)) {
                            content += '，联系电话【' + sMode + '】';
                        }

                        if (validatorUtil.isNotEmpty(ssTime)) {
                            content += '，会在【' + ssTime + '】之前与您联系';
                        }
                    }
                    if(data.progressState == "2" && staffSet == "Y"){
                        content += '，查看 <a href="javascript:void(0)" style="text-decoration: underline;" class="location" acceptNo="'+acceptNo+'">实时位置</a>'
                    }
                }

                html += '<ul class="of-storey">' +
                    '<li class="' + li_class + '">' +
                    '<span class="time">' + time + '<br><span style="font-size: 0.7em">' + date + '</span></span>' +
                    '<span class="icon ' + icon_class + '"></span>' +
                    '<span>' + content + '</span>' +
                    '</li></ul>';
            }
            $(_pageId + '#progress_list').html(html);
            appUtils.bindEvent($(_pageId + ".location"), function () {
                appUtils.pageInit(pageCode, "progress/location",{"acceptNo": $(this).attr("acceptNo")});
            });
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
