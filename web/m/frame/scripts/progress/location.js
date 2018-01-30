/**
 * 首页
 */
define(function (require, exports, module) {
    var appUtils = require("appUtils"),
        layerUtils = require("layerUtils"),
        validatorUtil = require("validatorUtil"),
        progressService = require("progressService"),
        gconfig = require("gconfig"),
        pageCode = "progress/location",
        _pageId = "#progress_location ";

    require('http://api.map.baidu.com/getscript?v=2.0&ak=GaXcXn4Z8XH3LN1vUZuhHVeuoBklPB8j&services=&t=20171031174121');

    /**
     * 初始化
     */
    function init() {
        var acceptNo = appUtils.getPageParam("acceptNo");
        var param = {
            "acceptNo": acceptNo
        };
        progressService.loadLocation(param, function (resultVo) {
            if (resultVo.errorNo == 0) {
                var result = resultVo.result[0].result;
                var so = result.so.point;
                var staff = result.staff.point;
                initMap(so, staff);
            }
        });
    }

    function initMap(so, staff) {
        //标注点数组
        var soPoint = so.lng + "|" + so.lat;
        var staffPoint = staff.lng + "|" + staff.lat;
        var pointArr = [{
            title: "订单位置",
            content: "订单位置",
            point: soPoint,
            isOpen: 0,
            icon: {w: 21, h: 21, l: 0, t: 0, x: 6, lb: 5, imageUrl: "images/marker1.ico"}
        },{
            title: "工人位置",
            content: "客户位置",
            point: staffPoint,
            isOpen: 0,
            icon: {w: 21, h: 21, l: 0, t: 0, x: 6, lb: 5, imageUrl: "images/marker2.ico"}
        }];

        createMap(so, staff);//创建地图
        setMapEvent();//设置地图事件
        addMapControl();//向地图添加控件
        addMarker(pointArr);//向地图中添加marker
    }

    //创建地图函数：
    function createMap(so, staff) {
        var map = new BMap.Map("dituContent");//在百度地图容器中创建一个地图
        var pointA = new BMap.Point(so.lng, so.lat);//定义一个中心点坐标
        var pointB = new BMap.Point(staff.lng, staff.lat);//定义一个中心点坐标
        //var polyline = new BMap.Polyline([pointA, pointB], {strokeColor: "blue", strokeWeight: 3, strokeOpacity: 0.5}); //定义折线
        var x = (parseFloat(so.lng) + parseFloat(staff.lng))/2;
        var y = (parseFloat(so.lat) + parseFloat(staff.lat))/2;
        //var b = new BMap.Bounds(pointA, pointB);
        map.centerAndZoom(new BMap.Point(x, y), 13);//设定地图的中心点和坐标并将地图显示在地图容器中
        //map.addOverlay(polyline);
        //try {
        //    BMapLib.AreaRestriction.setBounds(map, b);
        //} catch (e) {
        //    alert(e);
        //}
        window.map = map;//将map变量存储在全局
    }

    //地图事件设置函数：
    function setMapEvent() {
        map.enableDragging();//启用地图拖拽事件，默认启用(可不写)
        map.enableScrollWheelZoom();//启用地图滚轮放大缩小
        map.enableDoubleClickZoom();//启用鼠标双击放大，默认启用(可不写)
        map.enableKeyboard();//启用键盘上下左右键移动地图
    }

    //地图控件添加函数：
    function addMapControl() {
        //向地图中添加缩放控件
        var ctrl_nav = new BMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_LEFT, type: BMAP_NAVIGATION_CONTROL_LARGE});
        map.addControl(ctrl_nav);
        //向地图中添加缩略图控件
        var ctrl_ove = new BMap.OverviewMapControl({anchor: BMAP_ANCHOR_BOTTOM_RIGHT, isOpen: 1});
        map.addControl(ctrl_ove);
        //向地图中添加比例尺控件
        var ctrl_sca = new BMap.ScaleControl({anchor: BMAP_ANCHOR_BOTTOM_LEFT});
        map.addControl(ctrl_sca);
    }

    //创建marker
    function addMarker(markerArr) {
        for (var i = 0; i < markerArr.length; i++) {
            var json = markerArr[i];
            var p0 = json.point.split("|")[0];
            var p1 = json.point.split("|")[1];
            var point = new BMap.Point(p0, p1);
            var iconImg = createIcon(json.icon);
            var marker = new BMap.Marker(point, {icon: iconImg});
            var iw = createInfoWindow(markerArr[i]);
            var label = new BMap.Label(json.title, {"offset": new BMap.Size(json.icon.lb - json.icon.x + 10, -20)});
            marker.setLabel(label);
            map.addOverlay(marker);
            label.setStyle({
                borderColor: "#808080",
                color: "#333",
                cursor: "pointer"
            });

            (function () {
                var index = i;
                var _iw = createInfoWindow(markerArr[i]);
                var _marker = marker;
                _marker.addEventListener("click", function () {
                    this.openInfoWindow(_iw);
                });
                _iw.addEventListener("open", function () {
                    _marker.getLabel().hide();
                })
                _iw.addEventListener("close", function () {
                    _marker.getLabel().show();
                })
                label.addEventListener("click", function () {
                    _marker.openInfoWindow(_iw);
                })
                if (!!json.isOpen) {
                    label.hide();
                    _marker.openInfoWindow(_iw);
                }
            })()
        }
    }

    //创建InfoWindow
    function createInfoWindow(json) {
        var iw = new BMap.InfoWindow("<b class='iw_poi_title' title='" + json.title + "'>" + json.title + "</b><div class='iw_poi_content'>" + json.content + "</div>");
        return iw;
    }

    //创建一个Icon
    function createIcon(json) {
        var icon = new BMap.Icon(json.imageUrl, new BMap.Size(json.w, json.h), {
            imageOffset: new BMap.Size(-json.l, -json.t),
            infoWindowOffset: new BMap.Size(json.lb + 5, 1),
            offset: new BMap.Size(json.x, json.h)
        });
        return icon;
    }

    function loadScript() {
        var script = document.createElement("script");
        script.src = "http://api.map.baidu.com/api?v=2.0&ak=GaXcXn4Z8XH3LN1vUZuhHVeuoBklPB8j";
        document.body.appendChild(script);
    }

    /**
     * 事件绑定
     */
    function bindPageEvent() {
        appUtils.bindEvent(_pageId + ".back_btn", function () {
            appUtils.pageBack();
        })
    }

    /**
     * 销毁
     */
    function destroy() {
    }

    var progress_location = {
        "init": init,
        "bindPageEvent": bindPageEvent,
        "destroy": destroy
    };

    module.exports = progress_location;
});