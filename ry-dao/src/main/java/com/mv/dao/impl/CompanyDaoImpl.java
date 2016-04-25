package com.mv.dao.impl;

import org.springframework.stereotype.Repository;

import com.mv.dao.CompanyDao;
import com.mv.dao.base.BaseDaoImpl;
import com.mv.domain.Company;

@Repository("companyDao")
public class CompanyDaoImpl extends BaseDaoImpl<Company,Long> implements CompanyDao {
	private final static String NAMESPACE = "com.mv.dao.CompanyDao.";
	
	@Override
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
}