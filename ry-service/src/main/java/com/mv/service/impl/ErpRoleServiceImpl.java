package com.mv.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mv.dao.ErpRoleDao;
import com.mv.dao.ErpRolePrivilegeDao;
import com.mv.dao.base.BaseDao;
import com.mv.domain.ErpRole;
import com.mv.domain.ErpRolePrivilege;
import com.mv.service.ErpRoleService;
import com.mv.service.base.BaseServiceImpl;

@Service("erpRoleService")
public class ErpRoleServiceImpl extends BaseServiceImpl<ErpRole, Long> implements ErpRoleService {

	@Resource
	private ErpRoleDao erpRoleDao;

	@Resource
	private ErpRolePrivilegeDao erpRolePrivilegeDao;

	public BaseDao<ErpRole, Long> getDao() {
		return erpRoleDao;
	}

	@Override
	@Transactional
	public void grant(Long roleId, List<Long> privilegeList) {
		ErpRolePrivilege erpRolePrivilegeForDelete = new ErpRolePrivilege();
		erpRolePrivilegeForDelete.setRoleId(roleId);
		// 1、清除原来的权限
		erpRolePrivilegeDao.deleteByKey(erpRolePrivilegeForDelete);
		
		// 2、授予新的权限
		for (Long privilegeId : privilegeList) {
			ErpRolePrivilege rolePrivilege = new ErpRolePrivilege();
			rolePrivilege.setRoleId(roleId);
			rolePrivilege.setPrivilegeId(privilegeId);
			erpRolePrivilegeDao.insertEntry(rolePrivilege);
		}
	}
}