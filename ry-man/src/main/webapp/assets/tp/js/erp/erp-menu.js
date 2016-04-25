jQuery(function(){
	var erpUrl = $("#erpPath").val();
	var menuUrl = erpUrl + "menu/getUserMenu";
	erpInitShortcutsMenu(erpUrl);
	//生成菜单
	jQuery.ajax({ 
		url:menuUrl,
		dataType:"jsonp",
		success: function (data) {
           jQuery("#menu_nav").html(data);
           erpCheckMenu();
           bindClickMenu();
        },
        error: function(data) {
			bootbox.alert("获取菜单失败，请稍后重试");
        }
	});
	
	function erpCheckMenu(){
		//设置菜单选中状态
		var menu_id = jQuery.cookie('wm_menu_id');
		if(menu_id){
			var menu = $("#" + menu_id) ;
			menu.addClass("active");
			var parent_menu = menu.parents('li') ;
			for(var i=0;i<parent_menu.length;i++){
				$(parent_menu[i]).addClass("active open");
			}
		}
	}
	
	function bindClickMenu(){
		var path = $("#contextPath").val();
		if (path == "") {
			path = "/";
		}
		$(".menu_for_click").each(function() {
			$(this).click(function() {
				jQuery.cookie("wm_menu_id" , "menu_" + $(this).attr("menu_id") , {path: path });
			});
		});
	}
})

//初始化shortcut菜单
function erpInitShortcutsMenu(erpUrl){
	var html = '<a class="btn btn-success" href="#" title="增加用户"><i class="icon-plus"></i></a> ';
	html+='<a class="btn btn-info" href="#" title="会员查看"><i class="icon-male"></i></a> ';
	html+='<a class="btn btn-warning" href="#"><i class="icon-group"></i></a> ';
	html+='<a class="btn btn-danger" href="#"><i class="icon-picture"></i></a> ';

	jQuery("#erp-shortcuts-menu").html(html);
}
