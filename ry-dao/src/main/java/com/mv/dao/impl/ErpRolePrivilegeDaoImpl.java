package com.mv.dao.impl;

import org.springframework.stereotype.Repository;

import com.mv.dao.ErpRolePrivilegeDao;
import com.mv.dao.base.BaseDaoImpl;
import com.mv.domain.ErpRolePrivilege;

@Repository("erpRolePrivilegeDao")
public class ErpRolePrivilegeDaoImpl extends BaseDaoImpl<ErpRolePrivilege, Long> implements ErpRolePrivilegeDao {
	private final static String NAMESPACE = "com.mv.dao.ErpRolePrivilegeDao.";

	// 返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

}