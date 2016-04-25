package com.mv.dao;

import com.mv.dao.base.BaseDao;
import com.mv.domain.OperateLog;

public interface OperateLogDao extends BaseDao<OperateLog, Long> {
	// 自定义扩展
	public void createTableIfNotExists();
}
