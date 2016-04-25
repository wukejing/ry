package com.mv.service.impl;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;

import com.mv.dao.ErpPrivilegeDao;
import com.mv.dao.ErpUserDao;
import com.mv.dao.base.BaseDao;
import com.mv.domain.ErpPrivilege;
import com.mv.service.ErpPrivilegeService;
import com.mv.service.base.BaseServiceImpl;
import com.mv.web.RemoteResult;

@Service("erpPrivilegeService")
public class ErpPrivilegeServiceImpl extends BaseServiceImpl<ErpPrivilege, Long> implements ErpPrivilegeService {

	@Resource
	private ErpPrivilegeDao erpPrivilegeDao;

	@Resource
	private ErpUserDao erpUserDao;

	public BaseDao<ErpPrivilege, Long> getDao() {
		return erpPrivilegeDao;
	}

	private static final String URL_FAVICON = "/favicon.ico";

	/**
	 * URL鉴权
	 * 
	 * @param url
	 * @param userId
	 * @return
	 * @throws IOException
	 */
	public RemoteResult validateUrl(String url, Long userId) {

		String loginId = String.valueOf(userId);
		try {
			Assert.hasText(loginId, "登录ID为空");
			Assert.hasText(url, "url为空");
			// 忽略favicon.ico
			if (url.toLowerCase().endsWith(URL_FAVICON)) {
				return RemoteResult.success("鉴权通过");
			}
			// ANT规则查询
			PathMatcher pm = new AntPathMatcher();
			LOGGER.info("请求URL:" + url);
			List<ErpPrivilege> privilegeList = erpUserDao.getUserPrivilege(NumberUtils.toLong(loginId));
			if (CollectionUtils.isEmpty(privilegeList)) {
				LOGGER.error("用户没有任何权限：");
				return RemoteResult.create(0, "-1", null);
			}
			for (ErpPrivilege privilege : privilegeList) {
				String fullUrl = privilege.getPrivilegeUrl();
				if (pm.match(fullUrl, url)) {
					return RemoteResult.success("鉴权通过");
				}
			}
			LOGGER.error("非法访问请求，user：url:" + url);
			return RemoteResult.create(0, "-2", null);
		} catch (RuntimeException re) {
			LOGGER.error("验证URL合法性失败", re);
			return RemoteResult.failure("验证URL合法性失败");
		}

	}

	/**
	 * 资源码鉴权
	 * 
	 * @param code
	 * @param userId
	 * @return
	 * @throws IOException
	 */
	public RemoteResult validateCode(String code, Long userId) {
		try {
			Assert.notNull(userId, "用户id为空");
			Assert.hasText(code, "资源码为空");
			List<ErpPrivilege> privilegeList = erpUserDao.getUserPrivilege(userId);
			if (CollectionUtils.isEmpty(privilegeList)) {
				LOGGER.error("用户没有任何权限：");
				return RemoteResult.create(0, "-1", null);
			}
			boolean hasPrivilege = false;
			for (ErpPrivilege privilege : privilegeList) {
				if (privilege.getPrivilegeCode().equals(code)) {
					hasPrivilege = true;
					// if(privilege.getPrivilegeType() ==
					// PrivilegeEnum.menu.getKey()){
					// //将菜单ID设置到cookie中，以便页面显示菜单选中状态
					// CookieUtils.setCookie(response, CookieEnum.menu.getKey(),
					// "menu_" + privilege.getId());
					// }
					break;
				}
			}
			if (!hasPrivilege) {
				LOGGER.error("非法访问请求，code:" + code);
				return RemoteResult.create(0, "-2", null);
			}
			return RemoteResult.success("鉴权通过");
		} catch (RuntimeException re) {
			LOGGER.error("验证资源码合法性失败", re);
			return RemoteResult.failure("验证资源码合法性失败");
		}

	}
}