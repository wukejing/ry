

(function() {
	/**
	 * bootbox弹框全局设置
	 */
	bootbox.setDefaults({
				locale : 'zh_CN'
			});

	/**
	 * 页面提示dialog框
	 */
	window.XUI_dialog = function(message) {
		bootbox.dialog({
					title : "系统提示",
					message : "<span class='bigger-110'>" + message + "</span>",
					buttons : {
						"button" : {
							"label" : "关闭",
							"className" : "btn-sm",
							callback : function() {
							}
						}
					}
				});
	};

	/**
	 * Ajax异步删除操作函数
	 */
	window.XUI_ajax_delete = function(url, params, config) {
		var cfg = $.extend(true, {}, {
					"confirm" : "<h5>您确定要删除吗?</br>删除后，数据将不可恢复。</h5>"
				}, config || {});
		bootbox.confirm(cfg.confirm, function(result) {
					if (result) {// 确定删除
						$.ajax({
									type : "POST",
									cache : false,
									url : url,
									data : params,
									dataType : "json",
									success : function(data) {
										if (cfg.callback != null) {
											// 自定义回调函数
											cfg.callback(data);
										} else {
											XUI_dialog(data.result);
										}
									},
									error : function() {
										XUI_dialog(data.result);
									}
								});
					}
				});
	};

	/**
	 * Ajax异步提交操作
	 */
	window.XUI_ajax_send = function(url, params, config) {
		var cfg = $.extend(true, {}, {
					"callback" : null
				}, config || {});
		$.ajax({
					type : "POST",
					cache : false,
					url : url,
					data : params,
					dataType : "json",
					success : function(data) {
						if (cfg.callback != null) {
							// 自定义回调函数
							cfg.callback(data);
						} else {
							XUI_dialog(data.result);
						}
					},
					error : function() {
						XUI_dialog(data.result);
					}
				});
	};

	/**
	 * 提交form表单
	 */
	window.XUI_form_submit = function(frmObj, config) {
		XUI_ajax_send($(frmObj).attr("action"), $(frmObj).serialize(), config);
	};
})();