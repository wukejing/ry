package com.mv.dao.impl;

import org.springframework.stereotype.Repository;

import com.mv.dao.ErpRoleDao;
import com.mv.dao.base.BaseDaoImpl;
import com.mv.domain.ErpRole;

@Repository("erpRoleDao")
public class ErpRoleDaoImpl extends BaseDaoImpl<ErpRole, Long> implements ErpRoleDao {
	private final static String NAMESPACE = "com.mv.dao.ErpRoleDao.";

	// 返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
}