package com.mv.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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

import com.mv.domain.ErpPrivilege;
import com.mv.domain.ErpSystem;
import com.mv.domain.enums.OperatorStatusEnum;
import com.mv.domain.enums.YnEnum;
import com.mv.service.ErpPrivilegeService;
import com.mv.service.ErpSystemService;
import com.mv.service.OperateService;
import com.mv.web.RemoteResult;

/**
 * 权限controller
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/erpPrivilege")
public class ErpPrivilegeController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ErpPrivilegeController.class);

	@Resource
	private ErpPrivilegeService erpPrivilegeService;

	@Resource
	private ErpSystemService erpSystemService;

	@Resource
	private OperateService operateService;

	/**
	 * 列表展示
	 * 
	 * @param systemId
	 *            子系统id
	 * @param pid
	 *            父id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Long systemId, Long pid, Model model) throws Exception {
		try {
			if (pid == null) {
				pid = 0L;
			}
			// 创建查询对象
			ErpPrivilege erpPrivilegeForQuery = new ErpPrivilege();
			erpPrivilegeForQuery.setPid(pid);
			erpPrivilegeForQuery.setYn(YnEnum.Y.getKey());
			erpPrivilegeForQuery.setErpSysId(systemId);

			// 查询该pid的权限实体
			ErpPrivilege privilege = erpPrivilegeService.selectEntry(pid);

			// 查询该子系统id、该父id下的有效权限列表
			List<ErpPrivilege> privilegelist = erpPrivilegeService.selectEntryList(erpPrivilegeForQuery);

			// 查询有效的子系统列表
			List<ErpSystem> systemList = erpSystemService.getAllValidSystem();

			model.addAttribute("systemId", systemId);
			model.addAttribute("pid", pid);
			model.addAttribute("privilege", privilege); // 该pid的权限实体
			model.addAttribute("erpPrivilege", erpPrivilegeForQuery);
			model.addAttribute("list", privilegelist); // 该子系统id、该父id下的有效权限列表
			model.addAttribute("systemList", systemList); // 子系统列表
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			throw e;
		} finally {
		}
		return "erpPrivilege/list";
	}

	/**
	 * (修改)页面
	 * 
	 * @param id
	 *            对象编号
	 * @return
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable Long id, Model view) throws Exception {
		try {
			if (id != null && id > 0) {
				ErpPrivilege erpPrivilege = erpPrivilegeService.selectEntry(id);
				if (erpPrivilege == null) {
					return null;
				}

				// 查询有效的子系统列表
				List<ErpSystem> systemList = erpSystemService.getAllValidSystem();

				view.addAttribute("systemList", systemList);
				view.addAttribute("privilege", erpPrivilege);
			}
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			throw e;
		} finally {
		}

		return "erpPrivilege/edit";
	}

	/**
	 * 修改
	 * 
	 * @param privilege
	 * @param view
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json")
	@ResponseBody
	public RemoteResult update(ErpPrivilege privilege, Model view) throws Exception {
		try {
			Assert.hasText(privilege.getPrivilegeName(), "资源名称不能为空");
			Date now = new Date();
			privilege.setModified(now);
			// 排序号默认0
			if (privilege.getOrderNum() == null) {
				privilege.setOrderNum(0);
			}
			erpPrivilegeService.updateByKey(privilege);
			operateService.saveOperate(OperatorStatusEnum.SUCCESS, "修改资源成功，资源id：" + privilege.getId(), getOperateLog());
			return RemoteResult.success(null);
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			return RemoteResult.failure(e.getMessage());
		}
	}

	/**
	 * 新增界面
	 * @param pid
	 * @param view
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Long pid, Model view) throws Exception {
		try {
			// 获取父级节点实体，如果pid为0，父级就是根节点
			ErpPrivilege parentPrivilege = new ErpPrivilege();
			if (pid == null || pid == 0) {
				parentPrivilege.setId(0L);
				parentPrivilege.setPrivilegeName("根节点");
			} else {
				parentPrivilege = erpPrivilegeService.selectEntry(pid);
			}
			LOGGER.info("---" + parentPrivilege.getId());

			// 返回父级实体和子系统列表
			view.addAttribute("systemList", erpSystemService.getAllValidSystem());
			view.addAttribute("parentPrivilege", parentPrivilege);
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			throw e;
		} finally {
		}

		return "erpPrivilege/add";
	}

	/**
	 * 新增
	 * 
	 * @param erpPrivilege
	 *            实体对象
	 * @return
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json")
	public @ResponseBody RemoteResult save(ErpPrivilege erpPrivilege, Model view) throws Exception {
		try {
			Date now = new Date();
			// 排序号默认0
			if (erpPrivilege.getOrderNum() == null) {
				erpPrivilege.setOrderNum(0);
			}
			erpPrivilege.setPrivilegeCode(UUID.randomUUID().toString());
			erpPrivilege.setCreated(now);
			erpPrivilege.setModified(now);
			erpPrivilege.setYn(YnEnum.Y.getKey());
			erpPrivilegeService.insertEntry(erpPrivilege);
			operateService.saveOperate(OperatorStatusEnum.SUCCESS, "新增资源成功，资源id：" + erpPrivilege.getId(), getOperateLog());
			return RemoteResult.success(null);
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			return RemoteResult.failure("添加资源失败");
		}
	}

}