package com.mv.dao.impl;

import org.springframework.stereotype.Repository;

import com.mv.dao.ErpUserRoleDao;
import com.mv.dao.base.BaseDaoImpl;
import com.mv.domain.ErpUserRole;

@Repository("erpUserRoleDao")
public class ErpUserRoleDaoImpl extends BaseDaoImpl<ErpUserRole, Long> implements ErpUserRoleDao {
	private final static String NAMESPACE = "com.mv.dao.ErpUserRoleDao.";

	// 返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

}