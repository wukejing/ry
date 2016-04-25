package com.mv.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mv.domain.ErpPrivilege;
import com.mv.domain.ErpSystem;
import com.mv.domain.enums.PrivilegeEnum;
import com.mv.service.ErpSystemService;
import com.mv.service.ErpUserService;

/**
 * 生成用户菜单
 */
@Controller
@RequestMapping(value = "/menu")
public class MenuController extends BaseController{
	private static final Logger LOGGER = LoggerFactory.getLogger(MenuController.class);
	
	@Resource
	private ErpUserService erpUserService;
	
	@Resource
	private ErpSystemService erpSystemService;

	/**
	 * 获取登录用户的菜单
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="getUserMenu",method = {RequestMethod.GET,RequestMethod.POST})
	public String getUserMenu(HttpServletRequest request, HttpServletResponse response ,Model view , String callback){
		try{
			List<ErpPrivilege> privilegeList = erpUserService.getUserPrivilege(getCurrentUserId());
			String str = createMenu(privilegeList, request);
			String menuStr = callback + "(\"" + str + "\")" ;
			view.addAttribute("menuStr", menuStr);
		}catch(RuntimeException re){
			LOGGER.error("生成用户菜单失败" , re);
		}
		return "menu/userMenu";
	}
	

	/**
	 * 构建一级菜单（系统）
	 * @param privilegeList
	 * @return
	 */
	private String createMenu(List<ErpPrivilege> privilegeList, HttpServletRequest request) {
		StringBuffer sb = new StringBuffer(2048);
		List<ErpSystem> systemList = erpSystemService.getAllValidSystem();
		for(ErpSystem system : systemList){
			boolean hasSubMenu = false ;
			for(ErpPrivilege privilege : privilegeList){
				//只要系统下面有一个权限， 就认为可以展示该系统菜单（一级菜单）
				if(system.getId().longValue() == privilege.getErpSysId()){
					hasSubMenu = true ;
					break;
				}
			}
			if(hasSubMenu){
				sb.append("<li id='sys_" + system.getId() + "'>");
				sb.append("<a href='javascript:void(0)' class='dropdown-toggle'>");
				if(StringUtils.isNotBlank(system.getIcon())){
					sb.append("<i class='" + system.getIcon() + "'></i>");
				}
				else{
					sb.append("<i class='icon-lock'></i>");
				}
				sb.append("<span class=menu-text>")
					.append(system.getSysName())
					.append("</span>")
					.append("<b class='arrow icon-angle-down'></b></a>")
					.append("<ul class='submenu'>");
				createSubMenu(privilegeList, sb, system , 0L, request);
				sb.append("</ul></li>");
			}
			
		}
		LOGGER.debug(sb.toString());
		return sb.toString();
	}

	/**
	 * 构建二级以下菜单
	 * @param privilegeList
	 * @param sb
	 * @param system
	 */
	private void createSubMenu(List<ErpPrivilege> privilegeList,StringBuffer sb, ErpSystem erpSystem , Long pid, HttpServletRequest request) {
		for(ErpPrivilege privilege : privilegeList){
			if(privilege.getPrivilegeType() == PrivilegeEnum.menu.getKey()){
				if(privilege.getErpSysId() == erpSystem.getId().longValue() && privilege.getPid() == pid.longValue()){
					String url = erpSystem.getSysDomain() + privilege.getPrivilegeUrl() ;
					sb.append("<li id='menu_" + privilege.getId() + "' class='wm_menu_item'>")
						.append("<a href='" + url + "' class='dropdown-toggle menu_for_click' menu_id='" + privilege.getId() + "'>");
			    	if(StringUtils.isNotBlank(privilege.getIcon())){
			    		sb.append("<i class='" + privilege.getIcon() + "'></i>");
			    	}
			    	else{
			    		sb.append("<i class='icon-double-angle-right'></i>");
			    	}
			    	sb.append("<span class=menu-text>" + privilege.getPrivilegeName() + "</span>");
			    	if(privilege.getFlagLeaf() == 0){
			    		sb.append("<b class='arrow icon-angle-down'></b>");
			    	}
			    	sb.append("</a>");
			    	if(privilege.getFlagLeaf() == 0){
			    		sb.append("<ul class='submenu'>");
			    		createSubMenu(privilegeList, sb, erpSystem, privilege.getId(), request);
						sb.append("</ul>");
			    	}
			    	sb.append("</li>");
				}
			}
		}
	}
	
}
