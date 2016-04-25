package com.mv.domain;
import java.util.Date;

import com.mv.domain.base.BaseDomain;
import com.mv.utils.TimeUtil;

/**
 * 操作日志
 * @author Administrator
 *
 */
public class OperateLog extends BaseDomain {
	
	private static final long serialVersionUID = 8821103132313699941L;
	
	private Long id;// 编号

	/**
	 * 业务应用类型
	 * 1、后台 
	 * 2、前台
	 */
	private Integer appId;
	/**
	 * 操作人ID
	 */
	private Long operator;
	/**
	 * 操作人类型 
	 */
	private Integer operatorType;
	/**
	 * 操作人名字
	 */
	private String operatorName;
	/**
	 * 操作时间
	 */
	private Date operateTime;
	/**
	 * 操作IP地址
	 */
	private String operateIp;
	/**
	 * 执行的controller
	 */
	private String controller;
	/**
	 * 执行的action
	 */
	private String action;
	/**
	 * 操作状态 1、成功  2、失败
	 */
	private Integer status;
	/**
	 * 备注
	 */
	private String memo;
	
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Integer getAppId() {
		return appId;
	}
	public void setAppId(Integer appId) {
		this.appId = appId;
	}
	public Long getOperator() {
		return operator;
	}
	public void setOperator(Long operator) {
		this.operator = operator;
	}
	public Integer getOperatorType() {
		return operatorType;
	}
	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public String getOperateIp() {
		return operateIp;
	}
	public void setOperateIp(String operateIp) {
		this.operateIp = operateIp;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getController() {
		return controller;
	}
	public void setController(String controller) {
		this.controller = controller;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getYearMonthPart() {
		return TimeUtil.format(new Date(), "yyyyMM");
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}