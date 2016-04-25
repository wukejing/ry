package com.mv.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mv.dao.CompanyDao;
import com.mv.dao.base.BaseDao;
import com.mv.domain.Company;
import com.mv.service.CompanyService;
import com.mv.service.base.BaseServiceImpl;

@Service("companyService")
public class CompanyServiceImpl extends BaseServiceImpl<Company, Long> implements CompanyService {

	@Resource
	private CompanyDao companyDao;

	@Override
	public BaseDao<Company, Long> getDao() {
		return companyDao;
	}
}
