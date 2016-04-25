package com.mv.service;

import java.io.IOException;

import com.mv.domain.ErpPrivilege;
import com.mv.service.base.BaseService;
import com.mv.web.RemoteResult;

public interface ErpPrivilegeService extends BaseService<ErpPrivilege, Long> {

	/**
	 * URL鉴权
	 * @param url
	 * @param userId
	 * @return
	 * @throws IOException
	 */
	public RemoteResult validateUrl(String url, Long userId);
	
	/**
	 * 资源码鉴权
	 * @param code
	 * @param userId
	 * @return
	 * @throws IOException
	 */
	public RemoteResult validateCode(String code, Long userId);
}