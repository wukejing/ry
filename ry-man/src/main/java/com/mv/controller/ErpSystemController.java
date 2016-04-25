package com.mv.controller;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mv.domain.ErpSystem;
import com.mv.domain.common.Page;
import com.mv.domain.enums.OperatorStatusEnum;
import com.mv.domain.enums.YnEnum;
import com.mv.service.ErpSystemService;
import com.mv.web.RemoteResult;

@Controller
@RequestMapping(value = "/erpSystem")
public class ErpSystemController extends BaseController{
	private static final Logger LOGGER = LoggerFactory.getLogger(ErpSystemController.class);
	
	@Resource private ErpSystemService erpSystemService;
	
	/**
	 * 列表展示
	 * @param erpSystem 实体对象
	 * @param page 分页对象
	 * @return
	 */
	@RequestMapping(value="/list",method = {RequestMethod.GET,RequestMethod.POST})
	public String list(ErpSystem erpSystem,Page<ErpSystem> page,Model view) throws Exception{
		try {
			erpSystem.setYn(YnEnum.Y.getKey());
			view.addAttribute("erpSystem",erpSystem);
			view.addAttribute("page",erpSystemService.selectPage(erpSystem,page));			
		} catch (Exception e) {
			LOGGER.error("失败:"+e.getMessage(),e);
			throw e;
		}finally{
		}	
		return "erpSystem/list";
	}
	
	/**
	 * 添加系统
	 * @param view
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model view) throws Exception{
		return "erpSystem/add";
	}
	
	/**
	 * 保存方法
	 * @param erpRole 实体对象
	 * @return
	 */
	@RequestMapping(value="/save",method = RequestMethod.POST,produces="application/json")
	public @ResponseBody RemoteResult save(ErpSystem erpSystem,Model view) throws Exception{
    	try {
    		Date now = new Date();
    		erpSystem.setCreated(now);
    		erpSystem.setModified(now);
    		erpSystem.setYn(YnEnum.Y.getKey());
    		erpSystemService.insertEntryCreateId(erpSystem);
    		operateService.saveOperate(OperatorStatusEnum.SUCCESS,"添加系统成功，id："+erpSystem.getId(),getOperateLog());
			return RemoteResult.success(null);
		} catch (Exception e) {
			LOGGER.error("失败:"+e.getMessage(),e);
			return RemoteResult.failure("添加系统失败:" + e.getMessage());
		}
	}
	
	/**
	 * 响应新增(修改)页面
	 * @param id 对象编号
	 * @return
	 */
	@RequestMapping(value="/edit/{id}",method=RequestMethod.GET)
	public String edit(@PathVariable Long id,Model view) throws Exception{
		try {
			if(id != null && id > 0) {
				ErpSystem erpSystem = erpSystemService.selectEntry(id);
				if(erpSystem == null) {
					return null;
				}
				view.addAttribute("system",erpSystem);
			}			
		} catch (Exception e) {
			LOGGER.error("失败:"+e.getMessage(),e);
			throw e;
		}

		return "erpSystem/edit";
	}
	
	/**
	 * 修改保存系统信息
	 * @param system
	 * @param view
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/update",method = RequestMethod.POST,produces="application/json")
	public @ResponseBody RemoteResult update(ErpSystem erpSystem,Model view) throws Exception{
    	try {
    		Assert.hasText(erpSystem.getSysName(), "系统名称不能为空");
    		Assert.hasText(erpSystem.getSysDomain(), "域名不能为空");
    		erpSystem.setModified(new Date());
    		erpSystemService.updateByKey(erpSystem);
    		operateService.saveOperate(OperatorStatusEnum.SUCCESS,"修改系统成功，id："+erpSystem.getId(),getOperateLog());
			return RemoteResult.success(null) ;
		} catch (Exception e) {
			LOGGER.error("失败:"+e.getMessage(),e);
			return RemoteResult.failure(e.getMessage());
		}
	}
}