package com.mv.service;

import java.util.List;

import com.mv.domain.ErpPrivilege;
import com.mv.domain.ErpUser;
import com.mv.service.base.BaseService;

public interface ErpUserService extends BaseService<ErpUser, Long> {

	public void save(ErpUser erpUser);

	public void update(ErpUser erpUser);

	/**
	 * 获取用户权限
	 * 
	 * @param userId
	 * @return
	 */
	public List<ErpPrivilege> getUserPrivilege(Long userId);
}