package com.mv.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
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
import com.mv.domain.ErpRole;
import com.mv.domain.ErpRolePrivilege;
import com.mv.domain.ErpSystem;
import com.mv.domain.common.Page;
import com.mv.domain.enums.OperatorStatusEnum;
import com.mv.domain.enums.YnEnum;
import com.mv.service.ErpPrivilegeService;
import com.mv.service.ErpRolePrivilegeService;
import com.mv.service.ErpRoleService;
import com.mv.service.ErpSystemService;
import com.mv.web.RemoteResult;

/**
 * 角色controller
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/erpRole")
public class ErpRoleController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ErpRoleController.class);
	@Resource
	private ErpRoleService erpRoleService;
	@Resource
	private ErpPrivilegeService erpPrivilegeService;

	@Resource
	private ErpRolePrivilegeService erpRolePrivilegeService;

	@Resource
	private ErpSystemService erpSystemService;

	/**
	 * 列表展示
	 * 
	 * @param erpRole
	 *            实体对象
	 * @param page
	 *            分页对象
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public String list(ErpRole erpRole, Page<ErpRole> page, Model view) throws Exception {
		try {
			erpRole.setYn(YnEnum.Y.getKey());
			view.addAttribute("erpRole", erpRole);
			view.addAttribute("page", erpRoleService.selectPage(erpRole, page));
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			throw e;
		} finally {
		}
		return "erpRole/list";
	}

	/**
	 * 响应新增(修改)页面
	 * 
	 * @param id
	 *            对象编号
	 * @return
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable Long id, Model view) throws Exception {
		try {
			if (id != null && id > 0) {
				ErpRole role = erpRoleService.selectEntry(id);
				if (role == null) {
					return null;
				}
				view.addAttribute("role", role);
			}
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			throw e;
		} finally {
		}

		return "erpRole/edit";
	}

	/**
	 * 修改保存角色信息
	 * 
	 * @param role
	 * @param view
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json")
	@ResponseBody
	public RemoteResult update(ErpRole role, Model view) throws Exception {
		try {
			Assert.hasText(role.getRoleName(), "角色名称不能为空");
			Date now = new Date();
			role.setModified(now);
			erpRoleService.updateByKey(role);
			operateService.saveOperate(OperatorStatusEnum.SUCCESS, "修改角色成功，id：" + role.getId(), getOperateLog());
			return RemoteResult.success(null);
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			return RemoteResult.failure(e.getMessage());
		}
	}

	/**
	 * 新增界面
	 * 
	 * @param view
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model view) throws Exception {
		return "erpRole/add";
	}

	/**
	 * 新增
	 * 
	 * @param erpRole
	 *            实体对象
	 * @return
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json")
	public @ResponseBody RemoteResult save(ErpRole erpRole, Model view) throws Exception {
		try {
			Date now = new Date();
			erpRole.setCreated(now);
			erpRole.setModified(now);
			erpRole.setYn(YnEnum.Y.getKey());
			erpRoleService.insertEntryCreateId(erpRole);
			operateService.saveOperate(OperatorStatusEnum.SUCCESS, "添加角色成功，id：" + erpRole.getId(), getOperateLog());
			return RemoteResult.success(null);
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			return RemoteResult.failure("添加角色失败:" + e.getMessage());
		}
	}

	/**
	 * 授权页面
	 * 
	 * @param id
	 * @param view
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/grant/{id}", method = RequestMethod.GET)
	public String grant(@PathVariable Long id, Model view) throws Exception {
		try {
			if (id != null && id > 0) {
				ErpPrivilege privilege = new ErpPrivilege();
				privilege.setYn(YnEnum.Y.getKey());
				ErpRole erpRole = erpRoleService.selectEntry(id);
				if (erpRole == null) {
					return null;
				}
				ErpRolePrivilege rolePrivilege = new ErpRolePrivilege();
				rolePrivilege.setRoleId(id);
				List<ErpRolePrivilege> rolePrivilegelist = erpRolePrivilegeService.selectEntryList(rolePrivilege);
				List<ErpPrivilege> privilegeList = erpPrivilegeService.selectEntryList(privilege);
				for (ErpPrivilege erpPrivlege : privilegeList) {
					if (erpPrivlege.getPid() == 0) {
						// 如果PID为0，表示根节点菜单。为页面展示方便，将上级节点设置为“所属系统”
						erpPrivlege.setPidStr("id_" + erpPrivlege.getErpSysId());
					} else {
						erpPrivlege.setPidStr("" + erpPrivlege.getPid());
					}
				}

				List<ErpSystem> systemList = erpSystemService.getAllValidSystem();

				view.addAttribute("systemList", systemList);
				view.addAttribute("privilegeList", privilegeList);
				view.addAttribute("rolePrivilegelist", rolePrivilegelist);
				view.addAttribute("erpRole", erpRole);
			}
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			throw e;
		} finally {
		}

		return "erpRole/grant";
	}

	/**
	 * 授权
	 * @param roleId
	 * @param privilegeStr
	 * @param view
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/doGrant", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json")
	public @ResponseBody RemoteResult doGrant(Long roleId, String privilegeStr, Model view) throws Exception {
		try {
			Assert.hasText(privilegeStr, "授权资源为空");
			String privilegeStrArray[] = privilegeStr.split(",");
			List<Long> privilegeList = new ArrayList<Long>();
			for (String privilegeIdStr : privilegeStrArray) {
				Assert.isTrue(StringUtils.isNumeric(privilegeIdStr), "不合法的资源");
				privilegeList.add(NumberUtils.toLong(privilegeIdStr));
			}
			LOGGER.info("---" + privilegeStr);
			LOGGER.info("---" + privilegeList);
			erpRoleService.grant(roleId, privilegeList);
			String log = "授权成功，角色:" + roleId + ",资源：" + privilegeList.toString();
			operateService.saveOperate(OperatorStatusEnum.SUCCESS, log, getOperateLog());
			return RemoteResult.success(null);
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			return RemoteResult.failure();
		}
	}

}