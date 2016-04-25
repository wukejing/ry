package com.mv.domain.enums;

public enum PrivilegeEnum {

	menu(0,"菜单"),
	url(1,"url"),
	pageElement(2,"页面组件"),
	virtual(3,"虚拟节点");
     

	private int key;
    private String desc ;
     
    private PrivilegeEnum(int key , String desc)
     
    {
    	this.key = key ;
        this.desc = desc ;
    }

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
     
    
}
