package com.mv.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mv.dao.ErpUserRoleDao;
import com.mv.dao.base.BaseDao;
import com.mv.domain.ErpUserRole;
import com.mv.service.ErpUserRoleService;
import com.mv.service.base.BaseServiceImpl;

@Service("erpUserRoleService")
public class ErpUserRoleServiceImpl extends BaseServiceImpl<ErpUserRole, Long> implements ErpUserRoleService {

	@Resource
	private ErpUserRoleDao erpUserRoleDao;

	public BaseDao<ErpUserRole, Long> getDao() {
		return erpUserRoleDao;
	}
}