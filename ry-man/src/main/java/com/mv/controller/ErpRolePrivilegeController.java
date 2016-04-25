package com.mv.controller;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mv.domain.ErpRolePrivilege;
import com.mv.domain.common.Page;
import com.mv.service.ErpRolePrivilegeService;
import com.mv.web.RemoteResult;

/**
 * 角色权限controller
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/erpRolePrivilege")
public class ErpRolePrivilegeController{
	private static final Logger LOGGER = LoggerFactory.getLogger(ErpRolePrivilegeController.class);
	@Resource private ErpRolePrivilegeService erpRolePrivilegeService;
	
	/**
	 * 列表展示
	 * @param erpRolePrivilege 实体对象
	 * @param page 分页对象
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.GET,RequestMethod.POST})
	public String list(ErpRolePrivilege erpRolePrivilege,Page<ErpRolePrivilege> page,Model view) throws Exception{
		try {
			view.addAttribute("erpRolePrivilege",erpRolePrivilege);
			view.addAttribute("page",erpRolePrivilegeService.selectPage(erpRolePrivilege,page));			
		} catch (Exception e) {
			LOGGER.error("失败:"+e.getMessage(),e);
			throw e;
		}finally{
		}	
		return "erpRolePrivilege/list";
	}
	
	/**
	 * 响应新增(修改)页面
	 * @param id 对象编号
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public String edit(@PathVariable Long id,Model view) throws Exception{
		try {
			if(id != null && id > 0) {
				ErpRolePrivilege erpRolePrivilege = erpRolePrivilegeService.selectEntry(id);
				if(erpRolePrivilege == null) {
//					return toJSON(Message.failure("您要修改的数据不存在或者已被删除!"));
					return null;
				}
				view.addAttribute("erpRolePrivilege",erpRolePrivilege);
			}			
		} catch (Exception e) {
			LOGGER.error("失败:"+e.getMessage(),e);
			throw e;
		}finally{
		}

		return "erpRolePrivilege/edit";
	}
	
	/**
	 * 通过编号删除对象
	 * @param id 对象编号
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public @ResponseBody RemoteResult del(@PathVariable Long id,Model view) throws Exception{
    	RemoteResult msg = null;
    	try {
			int res = erpRolePrivilegeService.deleteByKey(id);
			msg  = res > 0 ? RemoteResult.success(null) : RemoteResult.failure();
		} catch (Exception e) {
			LOGGER.error("失败:"+e.getMessage(),e);
			msg = RemoteResult.failure();
		}finally{
		}

		return msg;
	}
	
	/**
	 * 通过编号查看对象
	 * @param id 对象编号
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public String view(@PathVariable Long id,Model view) throws Exception{
		try {
			ErpRolePrivilege erpRolePrivilege = erpRolePrivilegeService.selectEntry(id);
			if(erpRolePrivilege == null) {
				return null;
			}
			view.addAttribute("erpRolePrivilege",erpRolePrivilege);
		} catch (Exception e) {
			LOGGER.error("失败:"+e.getMessage(),e);
			throw e;
		}finally{
		}

		return "erpRolePrivilege/view";
	}
	
	/**
	 * 保存方法
	 * @param erpRolePrivilege 实体对象
	 * @return
	 */
	@RequestMapping(value="/save",method = {RequestMethod.POST,RequestMethod.GET},produces="application/json")
	public @ResponseBody RemoteResult save(ErpRolePrivilege erpRolePrivilege,Model view) throws Exception{
    	RemoteResult msg= null;
    	try {
    		Long res = erpRolePrivilegeService.saveOrUpdate(erpRolePrivilege);
			msg  = res > 0 ? RemoteResult.success(null) : RemoteResult.failure();
		} catch (Exception e) {
			LOGGER.error("失败:"+e.getMessage(),e);
			msg = RemoteResult.failure();
		}finally{
		}
		return msg;
	}
	
}