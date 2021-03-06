$(function(){
    $("#member-submit-btn").click(function () {
        $('#member-submit-form').ajaxForm({
            url: $("#contextPath").val() + "/erpSystem/save",
            dataType: "json",
            success: function (data) {
               if(data.mainCode == 0){
               		window.location.href = $("#contextPath").val() + "/erpSystem/list"
               }else{
               		XUI_dialog(data.result);
               }
            },
            error: function(data) {
				XUI_dialog("数据异常");
            }
        }).submit();
    });
    
    $('#member-submit-form').validate(
		 {
		  rules: {
		    sysName: {
		      maxlength: 45,
		      required: true
		    },
		    sysDomain: {
		   	  maxlength: 100,
		      required: true
		    },
		    sysOwner: {
		   	  maxlength: 45,
		      required: false
		    },
		    remark: {
		   	  maxlength: 200,
		      required: false
		    }
		  },
		  success: function(element) {
		    element.text('OK!').addClass('valid');
		  }
		});
		$("#sysName").focus();
    
});