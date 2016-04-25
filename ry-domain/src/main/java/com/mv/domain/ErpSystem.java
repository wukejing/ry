package com.mv.domain;

import com.mv.domain.base.BaseDomain;

/**
 * erp子系统信息
 * @author Administrator
 *
 */
public class ErpSystem extends BaseDomain {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 系统名称
	 */
	private String sysName;

	/**
	 * 图标
	 */
	private String icon ;
	
	/**
	 * 系统域名、前缀
	 */
	private String sysDomain;
	
	/**
	 * 系统负责人
	 */
	private String sysOwner;
	
	/**
	 * 系统备注
	 */
	private String remark ;

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getSysDomain() {
		return sysDomain;
	}

	public void setSysDomain(String sysDomain) {
		this.sysDomain = sysDomain;
	}

	public String getSysOwner() {
		return sysOwner;
	}

	public void setSysOwner(String sysOwner) {
		this.sysOwner = sysOwner;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}