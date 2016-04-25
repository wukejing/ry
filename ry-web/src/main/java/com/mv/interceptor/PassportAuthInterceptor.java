package com.mv.interceptor;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mv.web.PassportConstant;
import com.mv.web.PassportContext;

/**
 * 验证 userId 是否存在，如果不存在:
 * 1、同步请求返回登录页面，并把原地址作为参数传入登录页面，参数名为 goto
 */
public class PassportAuthInterceptor implements HandlerInterceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(PassportAuthInterceptor.class);

	/**
	 * 跳过的urls
	 */
	private Set<String> allowUrls;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
		// 获取请求的url
		String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");
		// 排除不拦截的url(配置在spring-mvc.xml中)
		if (null != allowUrls) {
			for (String allowUrl : allowUrls) {
				if (StringUtils.startsWith(requestUrl, allowUrl)) {
					LOGGER.info("不拦截的URL:" + requestUrl);
					return true;
				}
			}
		}

		// 取得passport上下文
		Object context = request.getAttribute(PassportConstant.PASSPORT_CONTEXT_KEY);
		// 验证上下文中是否存在userId
		if (context != null) {
			Long userId = ((PassportContext) context).getUserId();
			if (userId != null && userId.longValue() > 0) {
				LOGGER.info("----->" + userId);
				return true;
			}
		}

		// 验证失败后判断是否ajax请求，如果是ajax请求返回json，如果是同步请求跳转到登录页
		if (isAjaxRequest(request)) {
			ajaxResponse(response);
		} else {
			String redirectUrl = getRedirectUrl(request);
			LOGGER.info("redirectUrl:" + redirectUrl);
			response.sendRedirect(redirectUrl);
		}
		return false;
	}

	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

	}

	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
	}

	/**
	 * 判断是否是ajax请求
	 * @param request
	 * @return
	 */
	private boolean isAjaxRequest(HttpServletRequest request) {
		String xRequestedWith = request.getHeader("X-Requested-With");
		if (xRequestedWith != null) {
			LOGGER.info("X-Requested-With:" + xRequestedWith + ",User-Agent:" + request.getHeader("User-Agent"));
			if (xRequestedWith.equalsIgnoreCase("XMLHttpRequest")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 返回登录验证失败json
	 * 
	 * @param response
	 */
	private void ajaxResponse(HttpServletResponse response) {
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write("{\"message\":\"notLogin\",\"code\":\"9000\"}");
		} catch (Exception e) {
			LOGGER.error("--ajaxResponse error--", e);
		} finally {
			if (writer != null)
				try {
					writer.close();
				} catch (Exception e) {
					LOGGER.error("--ajaxResponse close writer error--", e);
				}
		}
	}

	/**
	 * 取得重定向到登录页的url，参数中传入被拦截器拦截的目标url
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String getRedirectUrl(HttpServletRequest request) throws UnsupportedEncodingException {
		// 取得目标url
		String url = request.getRequestURL().toString();
		// 取得请求参数
		String queryString = request.getQueryString();
		// 返回带目标url(有参数则带参数)的登陆页跳转地址
		if (StringUtils.isNotBlank(queryString)) {
			return request.getContextPath() + "/login.html?goto=" + URLEncoder.encode(url + "?" + queryString, "utf-8");
		}
		return request.getContextPath() + "/login.html?goto=" + URLEncoder.encode(url, "utf-8");
	}

	public void setAllowUrls(Set<String> allowUrls) {
		this.allowUrls = allowUrls;
	}
}
