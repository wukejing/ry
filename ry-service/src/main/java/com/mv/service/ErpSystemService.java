package com.mv.service;

import java.util.List;

import com.mv.domain.ErpSystem;
import com.mv.service.base.BaseService;

public interface ErpSystemService extends BaseService<ErpSystem, Long> {

	/**
	 * 查询有效的子系统列表
	 * @return
	 */
	public List<ErpSystem> getAllValidSystem();
}