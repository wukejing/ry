package com.mv.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mv.dao.ErpUserDao;
import com.mv.dao.base.BaseDaoImpl;
import com.mv.domain.ErpPrivilege;
import com.mv.domain.ErpUser;

@Repository("erpUserDao")
public class ErpUserDaoImpl extends BaseDaoImpl<ErpUser, Long> implements ErpUserDao {
	private final static String NAMESPACE = "com.mv.dao.ErpUserDao.";

	// 返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public List<ErpPrivilege> getUserPrivilege(Long userId) {
		return this.selectList(getNameSpace("getUserPrivilege"), userId);
	}

}