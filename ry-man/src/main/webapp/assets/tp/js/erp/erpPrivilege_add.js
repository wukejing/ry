$(function() {
			$("#member-submit-btn").click(function() {
				$('#member-submit-form').ajaxForm({
					url : $("#contextPath").val() + "/erpPrivilege/save",
					dataType : "json",
					success : function(data) {
						if (data.mainCode == 0) {
							window.location.href = $("#contextPath").val() + "/erpPrivilege?pid=" + $("#pid").val()
						} else {
							XUI_dialog(data.result);
						}
					},
					error : function(data) {
						XUI_dialog("数据异常");
					}
				}).submit();
			});

			$('#member-submit-form').validate({
						rules : {
							privilegeName : {
								required : true
							},
							success : function(element) {
								element.text('OK!').addClass('valid');
							}
						}
					});
			$("#privilegeName").focus();
		});