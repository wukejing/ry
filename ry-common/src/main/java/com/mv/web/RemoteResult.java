package com.mv.web;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

public class RemoteResult implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String code;

	//0：成功;	1：系统异常	2：业务异常
	private int mainCode;
	private String msg;
	private Object data;
	
	public RemoteResult() {
	}

	public RemoteResult(String code, String result) {
		this.code = code;
		this.msg = result;
	}
	
	public RemoteResult(String code, String result, Object data) {
		this.code = code;
		this.msg = result;
		this.data = data;
	}

    public RemoteResult(int mainCode,String code, String result) {
        this.mainCode = mainCode;
        this.code = code;
        this.msg = result;
    }

    public RemoteResult(int mainCode,String code, String result, Object data) {
        this.mainCode = mainCode;
        this.code = code;
        this.msg = result;
        this.data = data;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getResult() {
		return msg;
	}

	public void setResult(String result) {
		this.msg = result;
	}
	
	@SuppressWarnings("unchecked")
	public <E> E getData() {
		return (E) data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}

	public int getMainCode() {
		return mainCode;
	}

	public void setMainCode(int mainCode) {
		this.mainCode = mainCode;
	}
	
	public static RemoteResult create(String code,String result,Object obj) {
		return new RemoteResult(code, result , obj);
	}
	
	public static RemoteResult create(String code,String result) {
		return create(code, result , null);
	}

	public static RemoteResult create(int mainCode,String code,String result,Object obj) {
		return new RemoteResult(mainCode,code, result , obj);
	}

	public static RemoteResult create(int mainCode,String code,String result) {
		return create(mainCode,code, result , null);
	}
	
	public static RemoteResult success(String msg) {
		return create(0,"0000", msg);
	}
	
	public static RemoteResult success(String msg , Object obj) {
		return create(0,"0000", msg , obj);
	}

	public static RemoteResult failure() {
		return failure("操作失败!");
	}
	
	public static RemoteResult failure(String msg) {
		return create(1,"-1", msg);
	}
	
	public static RemoteResult failure(Exception ex) {
		return failure("系统异常:"+ex.getMessage(),ex);
	}
	
	public static RemoteResult failure(String message, Exception ex) {
		if(ex == null) {
			return failure();
		}
		RemoteResult msg = failure(message);
		try {
			StringWriter sw = new StringWriter();
			ex.printStackTrace(new PrintWriter(sw));
			msg.setData(sw.toString());
		} catch (Exception e) {
		}
		return msg;
	}
	
	@Override
	public String toString() {
		return "TradeHttpResult [code=" + code + ", msg=" + msg + ", data=" + data
				+ "]";
	}

}
