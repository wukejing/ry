$(function() {
    $("#member-submit-btn").click(function () {
        $('#member-submit-form').ajaxForm({
            url: $("#contextPath").val() + "/erpUser/update",
            dataType: "json",
            success: function (data) {
               if(data.mainCode == 0){
               		window.location.href=$("#contextPath").val() + "/erpUser"
               }else{
               		XUI_dialog(data.result);
               }
            },
            error: function(data) {
				XUI_dialog("数据类型异常");
            }
        }).submit();
    });
    $('.select2').css('width','200px').select2({allowClear:true})
    
    $("#yn-checkbox").click(function () {
       if(this.checked){
       		 $("#yn").val(1);
       }
       else{
       		 $("#yn").val(-1);
       }
    }); 
			
	$("#userName").focus();
});