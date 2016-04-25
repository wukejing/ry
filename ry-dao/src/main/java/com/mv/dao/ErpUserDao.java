package com.mv.dao;

import java.util.List;

import com.mv.dao.base.BaseDao;
import com.mv.domain.ErpPrivilege;
import com.mv.domain.ErpUser;

public interface ErpUserDao extends BaseDao<ErpUser, Long> {

	/**
	 * 获取用户所有权限
	 * 
	 * @param userId
	 * @return
	 */
	public List<ErpPrivilege> getUserPrivilege(Long userId);

}
