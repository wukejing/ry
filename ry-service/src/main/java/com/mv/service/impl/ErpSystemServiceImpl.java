package com.mv.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mv.dao.ErpSystemDao;
import com.mv.dao.base.BaseDao;
import com.mv.domain.ErpSystem;
import com.mv.domain.enums.YnEnum;
import com.mv.service.ErpSystemService;
import com.mv.service.base.BaseServiceImpl;

@Service("erpSystemService")
public class ErpSystemServiceImpl extends BaseServiceImpl<ErpSystem, Long> implements ErpSystemService {

	@Resource
	private ErpSystemDao erpSystemDao;

	public BaseDao<ErpSystem, Long> getDao() {
		return erpSystemDao;
	}
	
	/**
	 * 查询有效的子系统列表
	 * @return
	 */
	public List<ErpSystem> getAllValidSystem() {
		ErpSystem erpSystemForQuery = new ErpSystem();
		erpSystemForQuery.setYn(YnEnum.Y.getKey());
		return erpSystemDao.selectEntryList(erpSystemForQuery);
	}
}