package com.mv.dao.impl;

import org.springframework.stereotype.Repository;

import com.mv.dao.ErpPrivilegeDao;
import com.mv.dao.base.BaseDaoImpl;
import com.mv.domain.ErpPrivilege;

@Repository("erpPrivilegeDao")
public class ErpPrivilegeDaoImpl extends BaseDaoImpl<ErpPrivilege, Long> implements ErpPrivilegeDao {
	private final static String NAMESPACE = "com.mv.dao.ErpPrivilegeDao.";

	// 返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

}