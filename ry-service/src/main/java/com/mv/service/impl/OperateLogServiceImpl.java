package com.mv.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mv.dao.OperateLogDao;
import com.mv.domain.OperateLog;
import com.mv.domain.enums.OperatorStatusEnum;
import com.mv.service.OperateService;
import com.mv.service.base.BaseServiceImpl;

@Service("operateLogService")
public class OperateLogServiceImpl extends BaseServiceImpl<OperateLog, Long> implements OperateService {
	private static final Logger LOGGER = LoggerFactory.getLogger(OperateLogServiceImpl.class);

	@Resource
	private OperateLogDao operateLogDao;

	public OperateLogDao getDao() {
		return operateLogDao;
	}

	public void saveOperate(OperatorStatusEnum status, String memo, OperateLog operatorLog) {
		try {
			// create table if not exsits
			getDao().createTableIfNotExists();
			operatorLog.setMemo(memo);
			operatorLog.setStatus(status.getKey());
			operateLogDao.insertEntry(operatorLog);
		} catch (Exception e) {
			LOGGER.error("插入日志失败" + operatorLog, e);
		}
	}
}
