$(function() {
	// 注销按钮点击
	$("#logout").click(function() {
		// 提交注销
		jQuery.ajax({
			cache : false,
			type : "POST",
			url : $("#contextPath").val() + "/doLogout",
			timeout : 7000,
			success : function(data) {
				location.replace($("#contextPath").val() + "/login.html");
			},
			error : function() {
				alert("注销失败");
			}
		});
		return false;
	});
	
	/**
	 * 读取URL请求参数
	 * @returns {Object}
	 */
	function getRequest() {
		var url = location.search; // 获取url中"?"符后的字串
		var theRequest = new Object();
		if (url.indexOf("?") != -1) {
			var str = url.substr(1);
			var strs = str.split("&");
			for (var i = 0; i < strs.length; i++) {
				theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
			}
		}
		if (theRequest.areaId) {
			theRequest.areaId = theRequest.areaId.split(",")[1];
		}
		return theRequest;
	}
});