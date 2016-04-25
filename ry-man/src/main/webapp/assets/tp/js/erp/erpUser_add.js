$(function() {
	$("#member-submit-btn").click(function() {
		$('#member-submit-form').ajaxForm({
			url : $("#contextPath").val() + "/erpUser/save",
			dataType : "json",
			success : function(data) {
				if (data.mainCode == 0) {
					window.location.href = $("#contextPath").val() + "/erpUser"
				} else {
					XUI_dialog(data.result);
				}
			},
			error : function(data) {
				XUI_dialog("数据类型异常");
			}
		}).submit();
	});
	$('.select2').css('width', '200px').select2({
		allowClear : true
	})

	$('#member-submit-form').validate({
		rules : {
			loginId : {
				minlength : 2,
				required : true
			},
			password : {
				minlength : 6,
				required : true
			},
			password2 : {
				minlength : 6,
				required : true,
				equalTo : "#password"
			},
			userNo : {
				required : true
			},
			roleIds : {
				required : true
			},
			userName : {
				minlength : 1,
				required : true
			}
		},
		success : function(element) {
			element.text('OK!').addClass('valid');
		}
	});
			
	$("#loginId").focus();
});