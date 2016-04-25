package com.mv.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UrlPathHelper;

import com.alibaba.fastjson.JSON;
import com.mv.nb.common.annotation.WmPermission;
import com.mv.service.ErpPrivilegeService;
import com.mv.web.LoginContext;
import com.mv.web.RemoteResult;

/**
 * 权限拦截器
 */
public class AuthInterceptor implements HandlerInterceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthInterceptor.class);

	/**
	 * 是否开发模式，默认为开发模式
	 */
	private boolean devMode = false;

	/**
	 * 拒绝访问页面的地址
	 */
	private String erpAccessDeniedUrl;

	/**
	 * favicon.ico
	 */
	private static final String URL_FAVICON = "/favicon.ico";

	@Resource
	private ErpPrivilegeService erpPrivilegeService;

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception e)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView mav)
			throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		LoginContext lc = (LoginContext) request.getAttribute("login_context");
		Long userId = lc.getUserId();
		LOGGER.info("userId: " + userId);
		// 注意：此处为临时方案，调用erpPrivilegeService的方法验证权限，多系统整合时应改为调用erp系统的dubbo接口校验权限
		// 校验资源码
		if (obj instanceof HandlerMethod) {
			WmPermission annotation = (WmPermission) ((HandlerMethod) obj).getMethodAnnotation(WmPermission.class);
			if (annotation != null) {
				// 根据开发模式决定选用何种资源码
				String code = devMode ? annotation.testCode() : annotation.code();
				if (StringUtils.isNotBlank(code)) {
					LOGGER.info("code:" + code);
					RemoteResult codeResult = erpPrivilegeService.validateCode(code, userId);
					if (codeResult.getMainCode() == 0 && "0000".equals(codeResult.getCode())) {
						LOGGER.debug("鉴权通过");
						return true;
					} else {
						LOGGER.error("鉴权失败:" + codeResult.getResult());
						// response.sendRedirect(redirectUrl);
						sendErroMsg(request, response);
						return false;
					}
				}
			}
		}
		// 若未配置资源码，则校验URL
		UrlPathHelper uph = new UrlPathHelper();
		// String serverName = request.getServerName() +
		// request.getContextPath();

		String path = uph.getLookupPathForRequest(request);
		if (path.equals(URL_FAVICON)) {
			// 忽略/favicon.ico
			return true;
		}
		// String url = serverName + path ;
		LOGGER.info("look up:" + path);
		RemoteResult httpResult = erpPrivilegeService.validateUrl(path, userId);
		if (httpResult.getMainCode() == 0 && "0000".equals(httpResult.getCode())) {
			LOGGER.debug("鉴权通过");
			return true;
		} else {
			LOGGER.error("鉴权失败:" + httpResult.getResult());
			// response.sendRedirect(redirectUrl);
			sendErroMsg(request, response);
			return false;
		}
	}

	/**
	 * 鉴权失败，返回错误信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void sendErroMsg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String requestType = request.getHeader("X-Requested-With");
		LOGGER.info("requestType:" + requestType);
		String msg = "";
		if ("XMLHttpRequest".equals(requestType)) {
			// AJAX请求
			msg = JSON.toJSON(RemoteResult.failure("权限不足，拒绝访问")).toString();
			response.setHeader("Content-type", "application/json;charset=UTF-8");
			response.getOutputStream().write(msg.getBytes("UTF-8"));
		} else {
			// 普通请求
			response.sendRedirect(erpAccessDeniedUrl);
		}
	}

	public boolean isDevMode() {
		return devMode;
	}

	public void setDevMode(boolean devMode) {
		this.devMode = devMode;
	}

	public void setErpAccessDeniedUrl(String erpAccessDeniedUrl) {
		this.erpAccessDeniedUrl = erpAccessDeniedUrl;
	}

}
