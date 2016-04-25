$(function() {
	$("#member-submit-btn").click(function() {
		$('#member-submit-form').ajaxForm({
			url : $("#contextPath").val() + "/updatePWD",
			dataType : "json",
			success : function(data) {
				if (data.mainCode == 0) {
					window.location.href = $("#contextPath").val()
							+ "/";
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
			oldPWD : {
				minlength : 2,
				required : true
			},
			newPWD : {
				minlength : 6,
				required : true
			},
			newPWD2 : {
				minlength : 6,
				required : true,
				equalTo : "#newPWD"
			}
		},
		success : function(element) {
			element.text('OK!').addClass('valid');
			}
	});
	
	$("#oldPWD").focus();
});