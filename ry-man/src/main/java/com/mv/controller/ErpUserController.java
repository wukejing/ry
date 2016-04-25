package com.mv.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mv.domain.ErpRole;
import com.mv.domain.ErpUser;
import com.mv.domain.ErpUserRole;
import com.mv.domain.common.Page;
import com.mv.domain.enums.OperatorStatusEnum;
import com.mv.domain.enums.YnEnum;
import com.mv.service.ErpRoleService;
import com.mv.service.ErpUserRoleService;
import com.mv.service.ErpUserService;
import com.mv.utils.Md5Utils;
import com.mv.web.RemoteResult;

@Controller
@RequestMapping(value = "/erpUser")
public class ErpUserController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ErpUserController.class);
	@Resource
	private ErpUserService erpUserService;

	@Resource
	private ErpRoleService erpRoleService;

	@Resource
	private ErpUserRoleService erpUserRoleService;

	/**
	 * 列表展示
	 * 
	 * @param erpUser
	 *            实体对象
	 * @param page
	 *            分页对象
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public String list(ErpUser erpUser, Page<ErpUser> page, Model view) throws Exception {
		try {
			erpUser.setOrderField("id");
			erpUser.setOrderFieldType("DESC");
			erpUser.setYn(YnEnum.Y.getKey());
			view.addAttribute("query", erpUser);
			view.addAttribute("page", erpUserService.selectPage(erpUser, page));
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			throw e;
		} finally {
		}
		return "erpUser/list";
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
				ErpUser erpUser = erpUserService.selectEntry(id);
				if (erpUser == null) {
					// return toJSON(RemoteResult.failure("您要修改的数据不存在或者已被删除!"));
					return null;
				}
				ErpUserRole erpUserRoleForQuery = new ErpUserRole();
				erpUserRoleForQuery.setUserId(id);
				List<ErpUserRole> userRoleList = erpUserRoleService.selectEntryList(erpUserRoleForQuery);

				// 查询有效角色列表
				ErpRole erpRoleForQuery = new ErpRole();
				erpRoleForQuery.setYn(YnEnum.Y.getKey());
				List<ErpRole> roles = erpRoleService.selectEntryList(erpRoleForQuery);

				view.addAttribute("roles", roles);
				// 此处注意以前返回的是List<Long>，改成了List<ErpRole>，需要对应修改前台
				view.addAttribute("userRoleList", userRoleList);
				view.addAttribute("erpUser", erpUser);
			}
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			throw e;
		} finally {
		}

		return "erpUser/edit";
	}

	@RequestMapping(value = "/update", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json")
	@ResponseBody
	public RemoteResult update(ErpUser erpUser, Model view) throws Exception {
		try {
			LOGGER.info("----" + erpUser.getRoleIds());
			Assert.hasText(erpUser.getUserName(), "姓名不能为空");
			Assert.hasText(erpUser.getUserNo(), "工号不能为空");
			Assert.notEmpty(erpUser.getRoleIds(), "角色不能为空");
			Date now = new Date();
			erpUser.setModified(now);

			ErpUser erpUserForQuery = new ErpUser();
			erpUserForQuery.setUserNo(erpUser.getUserNo());
			List<ErpUser> oldUsers = erpUserService.selectEntryList(erpUserForQuery);
			if (CollectionUtils.isNotEmpty(oldUsers)) {
				if (erpUser.getId().longValue() != erpUser.getId()) {
					return RemoteResult.failure("工号已存在，请重新输入");
				}
			}
			erpUserService.update(erpUser);
			operateService.saveOperate(OperatorStatusEnum.SUCCESS, "修改ERP用户成功，id：" + erpUser.getId(), getOperateLog());
			return RemoteResult.success(null);
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			return RemoteResult.failure(e.getMessage());
		}
	}

	/**
	 * 添加用户
	 * 
	 * @param view
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String regist(Model view) throws Exception {
		// 查询有效角色列表
		ErpRole erpRoleForQuery = new ErpRole();
		erpRoleForQuery.setYn(YnEnum.Y.getKey());
		List<ErpRole> roles = erpRoleService.selectEntryList(erpRoleForQuery);

		view.addAttribute("roles", roles);
		return "erpUser/add";
	}

	/**
	 * 保存方法
	 * 
	 * @param erpUser
	 *            实体对象
	 * @return
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json")
	@ResponseBody
	public RemoteResult save(ErpUser erpUser, Model view) throws Exception {
		try {
			LOGGER.info("----" + erpUser.getRoleIds());
			Assert.hasText(erpUser.getLoginId(), "登录名不能为空");
			Assert.hasText(erpUser.getPassword(), "密码不能为空");
			Assert.hasText(erpUser.getUserName(), "姓名不能为空");
			Assert.hasText(erpUser.getUserNo(), "工号不能为空");
			Assert.notEmpty(erpUser.getRoleIds(), "角色不能为空");
			Date now = new Date();
			erpUser.setPassword(Md5Utils.md5(erpUser.getPassword()));
			erpUser.setCreated(now);
			erpUser.setModified(now);
			erpUser.setYn(YnEnum.Y.getKey());
			// 验证用户名是否存在
			ErpUser erpUserForQuery = new ErpUser();
			erpUserForQuery.setUserName(erpUser.getUserName());
			List<ErpUser> erpUsers = erpUserService.selectEntryList(erpUserForQuery);
			if (CollectionUtils.isNotEmpty(erpUsers)) {
				return RemoteResult.failure("用户名已存在，请更换一个用户名");
			}
			// 验证工号是否存在
			erpUserForQuery = new ErpUser();
			erpUserForQuery.setUserNo(erpUser.getUserNo());
			erpUsers = erpUserService.selectEntryList(erpUserForQuery);
			if (CollectionUtils.isNotEmpty(erpUsers)) {
				return RemoteResult.failure("工号已存在，请更换一个工号");
			}
			erpUserService.save(erpUser);
			operateService.saveOperate(OperatorStatusEnum.SUCCESS, "添加ERP用户成功，id：" + erpUser.getId(), getOperateLog());
			return RemoteResult.success(null);
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			return RemoteResult.failure(e.getMessage());
		}
	}

	@RequestMapping(value = "/editPWD/{id}", method = RequestMethod.GET)
	public String editPWD(@PathVariable Long id, Model view) throws Exception {
		try {
			if (id != null && id > 0) {
				ErpUser erpUser = erpUserService.selectEntry(id);
				if (erpUser == null) {
					return null;
				}
				view.addAttribute("erpUser", erpUser);
			}
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			throw e;
		} finally {
		}

		return "erpUser/editPWD";
	}

	/**
	 * 修改用户密码
	 * 
	 * @param id
	 * @param newPWD
	 * @param view
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePWD", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json")
	@ResponseBody
	public RemoteResult updatePWD(Long id, String newPWD, Model view) throws Exception {
		try {
			Assert.hasText(newPWD, "密码不能为空");
			Date now = new Date();
			ErpUser erpUserForUpdate = new ErpUser();
			erpUserForUpdate.setId(id);
			erpUserForUpdate.setPassword(Md5Utils.md5(newPWD));
			erpUserForUpdate.setModified(now);
			erpUserService.updateByKey(erpUserForUpdate);
			operateService.saveOperate(OperatorStatusEnum.SUCCESS, "重置ERP用户密码成功，id：" + erpUserForUpdate.getId(), getOperateLog());
			return RemoteResult.success(null);
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			return RemoteResult.failure(e.getMessage());
		}
	}

}