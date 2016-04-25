$(function(){
	// 修改密码按钮点击
	$("#edit_pwd_btn").click(function() {
		window.location.href=$("#contextPath").val() + "/editPWD";
	});
	
	// 退出按钮点击
	$("#logout-btn").click(function() {
		bootbox.confirm("确定要退出吗?", function(result) {
			if(result) {
				window.location.href= $("#contextPath").val() + "/logout";
			}
		});
	});
});