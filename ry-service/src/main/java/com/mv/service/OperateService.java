package com.mv.service;

import com.mv.domain.OperateLog;
import com.mv.domain.enums.OperatorStatusEnum;
import com.mv.service.base.BaseService;

public interface OperateService extends BaseService<OperateLog, Long> {

	public void saveOperate(OperatorStatusEnum status, String memo, OperateLog operatorLog);

}
