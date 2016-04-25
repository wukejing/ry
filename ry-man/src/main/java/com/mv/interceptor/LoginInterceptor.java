package com.mv.interceptor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mv.enums.CookieEnum;
import com.mv.utils.CookieUtils;
import com.mv.utils.RSAUtils;
import com.mv.web.LoginContext;
import com.mv.web.PassportConstant;

/**
 * 登录拦截器
 */
public class LoginInterceptor implements HandlerInterceptor{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginInterceptor.class);

	/**
	 * man加密解密key
	 */
	private String token;
	
	/**
	 * erp的登录地址
	 */
	private String erpLoginUrl;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object obj) throws Exception {
		// 获取请求的url
		String url = request.getRequestURL().toString();
		LOGGER.info("url=" + url);
		// 如果为空，默认为main首页，为了登录后回跳到首页
		if(StringUtils.isBlank(url)){
			url = request.getContextPath() +"/main" ;
		}
		
		// cookie中获取加密的ticket
		String ticketValueSecret = CookieUtils.getCookieValue(request, CookieEnum.manTicketValue.getKey());
		String ticketValuePlain = RSAUtils.decryptByPublicKey(ticketValueSecret, token);
		if (StringUtils.isBlank(ticketValuePlain)) {
			LOGGER.error("用户未登录");
			response.sendRedirect(getRedirectUrl(request));
			return false;
		}
		String[] ticketValuePlainArr = ticketValuePlain.split(PassportConstant.TICKET_SEPARATOR);
		
		long loginTime = NumberUtils.toLong(ticketValuePlainArr[2]);
		long now = System.currentTimeMillis();
		long ms = 1000*60*60*24*3 ;
		//cookie有效期最多3天
		if((now - loginTime) > ms){
			LOGGER.error("用户cookie已过期");
			response.sendRedirect(getRedirectUrl(request));
			return false;
		}
		
		// 设置上下文
		LoginContext lc = new LoginContext();
		lc.setLoginId(ticketValuePlainArr[0]);
		lc.setUserId(NumberUtils.toLong(ticketValuePlainArr[1]));
		lc.setLoginTime(new Date(loginTime));
		request.setAttribute("login_context", lc);
		return true ;
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
			return erpLoginUrl + "?goto=" + URLEncoder.encode(url + "?" + queryString, "utf-8");
		}
		return erpLoginUrl + "?goto=" + URLEncoder.encode(url, "utf-8");
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object obj, Exception e)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object obj, ModelAndView mav) throws Exception {
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setErpLoginUrl(String erpLoginUrl) {
		this.erpLoginUrl = erpLoginUrl;
	}
	
	
}
