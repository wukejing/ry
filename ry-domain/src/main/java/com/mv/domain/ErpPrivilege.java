package com.mv.domain;

import com.mv.domain.base.BaseDomain;

/**
 * erp权限实体
 * @author Administrator
 *
 */
public class ErpPrivilege extends BaseDomain {
	
	private static final long serialVersionUID = 2708233128419635701L;

	/**
	 * 上级id
	 */
	private Long pid;
	
	/**
	 * 权限名称
	 */
	private String privilegeName;
	
	/**
	 * 权限类型 0-菜单 1-url 2-页面元素
	 */
	private Integer privilegeType;
	
	/**
	 * url地址
	 */
	private String privilegeUrl;
	
	/**
	 * 权限码
	 */
	private String privilegeCode;
	
	/**
	 * 是否叶子节点 0-否 1-是
	 */
	private Integer flagLeaf;
	
	/**
	 * 图标
	 */
	private String icon;

	/**
	 * 排序字段
	 */
	private Integer orderNum;
	
	/**
	 * 所属系统ID
	 */
	private Long erpSysId;
	
	/**
	 * 备注描述
	 */
	private String remark;

	// 以下属性在数据库表erp_privilege中无对应
	/**
	 * erp系统名称
	 */
	private String erpSysName;

	/**
	 * 仅构造授权树时有用
	 */
	private String idStr;

	/**
	 * 仅构造授权树时有用
	 */
	private String pidStr;

	public Long getErpSysId() {
		return erpSysId;
	}

	public void setErpSysId(Long erpSysId) {
		this.erpSysId = erpSysId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public ErpPrivilege() {
		// 默认无参构造方法
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getPrivilegeName() {
		return privilegeName;
	}

	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}

	public Integer getPrivilegeType() {
		return privilegeType;
	}

	public void setPrivilegeType(Integer privilegeType) {
		this.privilegeType = privilegeType;
	}

	public String getPrivilegeUrl() {
		return privilegeUrl;
	}

	public void setPrivilegeUrl(String privilegeUrl) {
		this.privilegeUrl = privilegeUrl;
	}

	public String getPrivilegeCode() {
		return privilegeCode;
	}

	public void setPrivilegeCode(String privilegeCode) {
		this.privilegeCode = privilegeCode;
	}

	public Integer getFlagLeaf() {
		return flagLeaf;
	}

	public void setFlagLeaf(Integer flagLeaf) {
		this.flagLeaf = flagLeaf;
	}

	public String getErpSysName() {
		return erpSysName;
	}

	public void setErpSysName(String erpSysName) {
		this.erpSysName = erpSysName;
	}

	public String getIdStr() {
		return idStr;
	}

	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}

	public String getPidStr() {
		return pidStr;
	}

	public void setPidStr(String pidStr) {
		this.pidStr = pidStr;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}