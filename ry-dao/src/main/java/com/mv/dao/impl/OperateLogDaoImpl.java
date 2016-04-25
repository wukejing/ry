package com.mv.dao.impl;

import org.springframework.stereotype.Repository;

import com.mv.dao.OperateLogDao;
import com.mv.dao.base.BaseDaoImpl;
import com.mv.domain.OperateLog;

@Repository("operateLogDao")
public class OperateLogDaoImpl extends BaseDaoImpl<OperateLog, Long> implements OperateLogDao {
	private final static String NAMESPACE = "com.mv.dao.OperateLogDao.";

	// 返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public void createTableIfNotExists() {
		OperateLog operateLog = new OperateLog();
		this.select(getNameSpace("CREATE_TABLE"), operateLog);
	}
}
