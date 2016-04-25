
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

import com.mv.domain.ErpUserRole;
import com.mv.domain.common.Page;
import com.mv.service.ErpUserRoleService;
import com.mv.web.RemoteResult;


@Controller
@RequestMapping(value = "/erpUserRole")
public class ErpUserRoleController{
	private static final Logger LOGGER = LoggerFactory.getLogger(ErpUserRoleController.class);
	@Resource private ErpUserRoleService erpUserRoleService;
	
	
	/**
	 * 列表展示
	 * @param erpUserRole 实体对象
	 * @param page 分页对象
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.GET,RequestMethod.POST})
	public String list(ErpUserRole erpUserRole,Page<ErpUserRole> page,Model model) throws Exception{
		try {
			model.addAttribute("erpUserRole",erpUserRole);
			model.addAttribute("page",erpUserRoleService.selectPage(erpUserRole,page));			
		} catch (Exception e) {
			LOGGER.error("失败:"+e.getMessage(),e);
			throw e;
		}finally{
		}	
		return "erpUserRole/list";
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
				ErpUserRole erpUserRole = erpUserRoleService.selectEntry(id);
				if(erpUserRole == null) {
//					return toJSON(RemoteResult.failure("您要修改的数据不存在或者已被删除!"));
					return null;
				}
				view.addAttribute("erpUserRole",erpUserRole);
			}			
		} catch (Exception e) {
			LOGGER.error("失败:"+e.getMessage(),e);
			throw e;
		}finally{
		}

		return "erpUserRole/edit";
	}
	
	/**
	 * 通过编号删除对象
	 * @param id 对象编号
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public @ResponseBody RemoteResult del(@PathVariable Long id,Model model) throws Exception{
    	RemoteResult msg = null;
    	try {
			int res = erpUserRoleService.deleteByKey(id);
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
	public String view(@PathVariable Long id,Model model) throws Exception{
		try {
			ErpUserRole erpUserRole = erpUserRoleService.selectEntry(id);
			if(erpUserRole == null) {
				return null;
			}
			model.addAttribute("erpUserRole",erpUserRole);
		} catch (Exception e) {
			LOGGER.error("失败:"+e.getMessage(),e);
			throw e;
		}finally{
		}

		return "erpUserRole/view";
	}
	
	/**
	 * 保存方法
	 * @param erpUserRole 实体对象
	 * @return
	 */
	@RequestMapping(value="/save",method = {RequestMethod.POST,RequestMethod.GET},produces="application/json")
	public @ResponseBody RemoteResult save(ErpUserRole erpUserRole,Model model) throws Exception{
    	RemoteResult msg= null;
    	try {
    		Long res = erpUserRoleService.saveOrUpdate(erpUserRole);
			msg  = res > 0 ? RemoteResult.success(null) : RemoteResult.failure();
		} catch (Exception e) {
			LOGGER.error("失败:"+e.getMessage(),e);
			msg = RemoteResult.failure();
		}finally{
		}
		return msg;
	}
	
}