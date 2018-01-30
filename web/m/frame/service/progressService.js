/**
 * H5前端service层调用接口[用户相关]
 */
define(function(require, exports, module) {
	var gconfig = require("gconfig");
	var global = gconfig.global;
	var service = require("service");
	var layerUtils = require("layerUtils");
	var serviceSingleton = new service.Service();

	/** ******************************公共代码部分******************************* */
	function commonInvoke(paraMap, callback, ctrlParam, reqParamVo) {
		reqParamVo.setReqParam(paraMap);
		ctrlParam = ctrlParam ? ctrlParam : {};
		reqParamVo.setIsLastReq(ctrlParam.isLastReq);
		reqParamVo.setIsAsync(ctrlParam.isAsync);
		reqParamVo.setIsShowWait(ctrlParam.isShowWait);
		reqParamVo.setTimeOutFunc(ctrlParam.timeOutFunc);
		reqParamVo.setIsShowOverLay(ctrlParam.isShowOverLay);
		reqParamVo.setTipsWords(ctrlParam.tipsWords);
		reqParamVo.setDataType(ctrlParam.dataType);
		reqParamVo.setProtocol(ctrlParam.protocol);
		reqParamVo.setIsGlobal(ctrlParam.isGlobal);
		serviceSingleton.invoke(reqParamVo, callback);
	}
	function destroy() {
		serviceSingleton.destroy();
	}
	var progressService = {
		"login": login,
		"listAll": listAll,
		"listProgress": listProgress,	//获取进度列表
		"loadLocation": loadLocation,	//获取进度列表
		"getInstance" : getInstance,
		"destroy" : destroy
	};
	function getInstance() {
		return progressService;
	}
	module.exports = progressService;


	/** ******************************应用接口开始******************************* */

	/**
	 * 提交
	 */


	function login(paramMap, callback, ctrlParam){
		var paraMap = {};
		paraMap["func"] = "100";
		paraMap["mobile"] = paramMap.mobile;
		var reqParamVo = new service.ReqParamVo();
		reqParamVo.setUrl(global.serverPath);
		commonInvoke(paraMap, callback, ctrlParam, reqParamVo);
	}

	function listAll(paramMap, callback, ctrlParam){
		var paraMap = {};
		paraMap["func"] = "1000";
		paraMap["mobile"] = paramMap.mobile;
		var reqParamVo = new service.ReqParamVo();
		reqParamVo.setUrl(global.serverPath);
		commonInvoke(paraMap, callback, ctrlParam, reqParamVo);
	}


	function listProgress(paramMap, callback, ctrlParam){
		var paraMap = {};
		paraMap["func"] = "1001";
		paraMap["acceptNo"] = paramMap.acceptNo;
		var reqParamVo = new service.ReqParamVo();
		reqParamVo.setUrl(global.serverPath);
		commonInvoke(paraMap, callback, ctrlParam, reqParamVo);
	}

	function loadLocation(paramMap, callback, ctrlParam){
		var paraMap = {};
		paraMap["func"] = "1002";
		paraMap["acceptNo"] = paramMap.acceptNo;
		var reqParamVo = new service.ReqParamVo();
		reqParamVo.setUrl(global.serverPath);
		commonInvoke(paraMap, callback, ctrlParam, reqParamVo);
	}

	/** ******************************应用接口结束******************************* */
});