package com.mv.dao.impl;

import org.springframework.stereotype.Repository;

import com.mv.dao.ErpSystemDao;
import com.mv.dao.base.BaseDaoImpl;
import com.mv.domain.ErpSystem;

@Repository("erpSystemDao")
public class ErpSystemDaoImpl extends BaseDaoImpl<ErpSystem, Long> implements ErpSystemDao {
	private final static String NAMESPACE = "com.mv.dao.ErpSystemDao.";

	// 返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

}