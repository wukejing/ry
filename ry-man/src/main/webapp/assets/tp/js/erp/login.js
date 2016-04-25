$(function() {
    $("#btnLogin").click(function () {
		var loginId = $.trim($("#loginId").val());
		var password =  $.trim($("#password").val());
		// 验证参数
		if (loginId == '') {
			bootbox.alert("用户名不能为空");
			$("#loginId").focus();
			return;
		}
		if (password == '') {
			bootbox.alert("密码不能为空");
			$("#password").focus();
			return;
		}
		var requestParams = getRequest();
		
		// 提交登录
		jQuery.ajax({
			cache : false,
			type : "POST",
            dataType: "json",
			url : "doLogin",
			timeout : 7000,
			data : {
				"loginId" : loginId,
				"password" : password
			},
			success : function(data) {
				if(data.mainCode == 0) {
					if ($.trim(requestParams['goto']) != '') {
		                location.replace(requestParams['goto']);
					} else {
		                location.replace("index.html");
					}
				} else {
					bootbox.alert(data.result);
				}
			},
			error : function() {
				bootbox.alert("登录失败，请联系管理员");
			}
		});
		return false;
    });
    
    $('#user-login-form').keydown(function(event){ 
		if(event.which==13){ 
			$("#btnLogin").click();
		}
	}); 

	$("#loginId").focus();
	
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