package com.mv.interceptor;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mv.enums.CookieEnum;
import com.mv.utils.CookieUtils;
import com.mv.utils.RSAUtils;
import com.mv.web.PassportConstant;
import com.mv.web.PassportContext;

/**
 * 该拦截器负责把cookie中的passport信息(loginId、userId)写入上下文
 */
public class PassportInterceptor implements HandlerInterceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(PassportInterceptor.class);

	/**
	 * passport的加密解密key
	 */
	private String token;

	/**
	 * 跳过的urls
	 */
	private Set<String> allowUrls;

	/**
	 * 拦截请求
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		// 获取请求的url
		String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");
		LOGGER.info("url=" + requestUrl);
		// 排除不拦截的url(配置在spring-mvc.xml中，一般不用配置，因为此拦截器不会阻挡请求)
		if (allowUrls != null && allowUrls.contains(requestUrl)) {
			LOGGER.info("不拦截的URL:" + requestUrl);
			return true;
		}
		// cookie中获取加密的ticket
		String ticketValueSecret = CookieUtils.getCookieValue(request, CookieEnum.ticketValue.getKey());
		// 如果cookie中获取不到，则从请求参数中获取(用于flash上传组件无法提交cookie等情况)
		if (StringUtils.isBlank(ticketValueSecret)) {
			ticketValueSecret = request.getParameter(PassportConstant.PASSPORT_SID_KEY);
			LOGGER.info("获取SID:" + ticketValueSecret);
		}
		// 为空直接返回
		if (StringUtils.isBlank(ticketValueSecret)) {
			LOGGER.info("Cookie 为空，用户未登录");
			return true;
		}
		// 解密ticket,如果失败直接返回 
		String ticketValuePlain = RSAUtils.decryptByPublicKey(ticketValueSecret, token);
		if (StringUtils.isBlank(ticketValuePlain)) {
			LOGGER.warn("解密加密串失败,ticketValueSecret:" + ticketValueSecret + ",token:" + token);
			CookieUtils.removeCookie(response, CookieEnum.ticketValue.getKey());
			return true;
		}
		String[] ticketValuePlainArr = ticketValuePlain.split(PassportConstant.TICKET_SEPARATOR);
		// ticket解密后有3个参数loginId、userId、timestamp
		if (ArrayUtils.getLength(ticketValuePlainArr) != 3) {
			LOGGER.error("解密后格式不正确,ticketValueSecret:" + ticketValueSecret + ",ticketValuePlain:" + ticketValuePlain);
			CookieUtils.removeCookie(response, CookieEnum.ticketValue.getKey());
			return true;
		}
		// 1 获取ticket中的loginId
		String loginId = ticketValuePlainArr[0];
		// 2 获取ticket中的userId
		long userId = NumberUtils.toLong(ticketValuePlainArr[1]);
		// 3 获取ticket中的timestamp
		// long timestamp = NumberUtils.toLong(ticketValuePlainArr[2]);
		// long intervalTime = DateUtils.getServerTime() - timestamp;

		// 3 验证timestamp(1一个月)
		// if (intervalTime > PassportConstant.LOGIN_EXPIRES_TIME_SERVER) {
		// LOGGER.info("Cookie 已过期,ticketValueSecret:" + ticketValueSecret +
		// ",ticketValuePlain:" + ticketValuePlain);
		// CookieUtils.removeCookie(response, CookieEnum.ticketValue.getKey(),
		// domain);
		// return true;
		// }
		// // 4 如果验证通过，根据loginId和当前时间重新生成ticket，更新到cookie中
		// // 提供远程接口加密，10分钟更新一次，避免泄露加密KEY
		// if (intervalTime > PassportConstant.UPDATE_TICKET_TIME) {
		// LOGGER.info("更新ticket.loginId:" + loginId);
		// ticketValueSecret = refresh(ticketValueSecret);
		// if (StringUtils.isNotBlank(ticketValueSecret)) {
		// CookieUtils.setCookie(response, CookieEnum.ticketValue.getKey(),
		// ticketValueSecret, "/", domain, PassportConstant.LOGIN_EXPIRES_TIME);
		// LOGGER.info("更新ticket成功.loginId:" + loginId);
		// }
		// LOGGER.error("更新ticket失败.loginId:" + loginId);
		// }
		// 5 将用户信息放入上下文
		PassportContext ctx = new PassportContext();
		ctx.setLoginId(loginId);
		ctx.setUserId(userId);
		request.setAttribute(PassportConstant.PASSPORT_CONTEXT_KEY, ctx);
		return true;
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception e) throws Exception {
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView mav) throws Exception {
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setAllowUrls(Set<String> allowUrls) {
		this.allowUrls = allowUrls;
	}

}
