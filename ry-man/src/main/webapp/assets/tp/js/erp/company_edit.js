$(function() {
    $("#member-submit-btn").click(function () {
        $('#member-submit-form').ajaxForm({
            url: $("#contextPath").val() + "/company/update",
            dataType: "json",
            success: function (data) {
               if(data.mainCode == 0){
               		window.location.href=$("#contextPath").val() + "/company"
               }else{
               		XUI_dialog(data.result);
               }
            },
            error: function(data) {
				XUI_dialog("数据异常");
            }
        }).submit();
    });
    
    
	$("#yn-checkbox").click(function () {
       if(this.checked){
       		 $("#yn").val(1);
       }
       else{
       		 $("#yn").val(-1);
       }
    }); 
    
	$("#expressName").focus();
});