package com.mv.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mv.dao.ErpUserDao;
import com.mv.dao.ErpUserRoleDao;
import com.mv.dao.base.BaseDao;
import com.mv.domain.ErpPrivilege;
import com.mv.domain.ErpUser;
import com.mv.domain.ErpUserRole;
import com.mv.service.ErpUserService;
import com.mv.service.base.BaseServiceImpl;

@Service("erpUserService")
public class ErpUserServiceImpl extends BaseServiceImpl<ErpUser, Long> implements ErpUserService {

	@Resource
	private ErpUserDao erpUserDao;

	@Resource
	private ErpUserRoleDao erpUserRoleDao;

	public BaseDao<ErpUser, Long> getDao() {
		return erpUserDao;
	}

	@Transactional
	public void save(ErpUser erpUser) {
		erpUserDao.insertEntryCreateId(erpUser);
		for (Long roleId : erpUser.getRoleIds()) {
			LOGGER.info("role id :" + roleId);
			ErpUserRole erpUserRole = new ErpUserRole();
			erpUserRole.setUserId(erpUser.getId());
			erpUserRole.setRoleId(roleId);
			erpUserRoleDao.insertEntry(erpUserRole);
		}

	}

	@Transactional
	public void update(ErpUser erpUser) {
		// 1、更新用户基本信息
		erpUserDao.updateByKey(erpUser);
		// 2、清除用户原角色
		ErpUserRole erpUserRoleForDelete = new ErpUserRole();
		erpUserRoleForDelete.setUserId(erpUser.getId());
		erpUserRoleDao.deleteByKey(erpUserRoleForDelete);
		// 3、授权用户新角色
		for (Long roleId : erpUser.getRoleIds()) {
			LOGGER.info("role id :" + roleId);
			ErpUserRole erpUserRole = new ErpUserRole();
			erpUserRole.setUserId(erpUser.getId());
			erpUserRole.setRoleId(roleId);
			erpUserRoleDao.insertEntry(erpUserRole);
		}
	}

	@Override
	public List<ErpPrivilege> getUserPrivilege(Long userId) {
		return erpUserDao.getUserPrivilege(userId);
	}
}