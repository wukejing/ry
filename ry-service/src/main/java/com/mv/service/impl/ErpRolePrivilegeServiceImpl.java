package com.mv.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mv.dao.ErpRolePrivilegeDao;
import com.mv.dao.base.BaseDao;
import com.mv.domain.ErpRolePrivilege;
import com.mv.service.ErpRolePrivilegeService;
import com.mv.service.base.BaseServiceImpl;

@Service("erpRolePrivilegeService")
public class ErpRolePrivilegeServiceImpl extends BaseServiceImpl<ErpRolePrivilege, Long> implements ErpRolePrivilegeService {

	@Resource
	private ErpRolePrivilegeDao erpRolePrivilegeDao;

	public BaseDao<ErpRolePrivilege, Long> getDao() {
		return erpRolePrivilegeDao;
	}
}