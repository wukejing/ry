package com.mv.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.mv.domain.OperateLog;
import com.mv.domain.enums.OperatorStatusEnum;
import com.mv.service.OperateService;
import com.mv.web.LoginContext;

public class BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);
    
    /**
     * 请求上下文
     */
    private HttpServletRequest request;
    /**
     * 应答上下文
     */
    private HttpServletResponse response;

	@Resource
	protected OperateService operateService;

	/**
	 * 获取当前登录用户userId
	 * 
	 * @return
	 */
	public Long getCurrentUserId() {
		if (request.getAttribute("login_context") == null) {
			return null;
		}
		LoginContext ctx = (LoginContext) request.getAttribute("login_context");
		return ctx.getUserId();
	}

	/**
	 * 获取当前登录用户loginId
	 * 
	 * @return
	 */
	public String getCurrentLoginId() {
		if (request.getAttribute("login_context") == null) {
			return null;
		}
		LoginContext ctx = (LoginContext) request.getAttribute("login_context");
		return ctx.getLoginId();
	}

	/**
	 * 设置NoCacheHeader
	 * 
	 * @param response
	 */
	public void setNoCacheHeader() {
		// Http 1.0 header
		response.setDateHeader("Expires", 0);
		response.addHeader("Pragma", "no-cache");
		// Http 1.1 header
		response.setHeader("Cache-Control", "no-cache");
	}

	public OperateLog getOperateLog() {
		OperateLog operateLog = new OperateLog();
		try {
			operateLog.setController(this.getClass().getSimpleName());
	        operateLog.setAction(request.getPathInfo());
	        operateLog.setOperateIp(this.getRemoteIp());
	        operateLog.setOperatorType(1);
	        operateLog.setOperator(getCurrentUserId());
	        operateLog.setOperatorName(getCurrentLoginId());
	        operateLog.setOperateTime(new Date());
	        operateLog.setAppId(1);
		} catch(Exception e) {
			operateLog.setStatus(OperatorStatusEnum.FAILURE.getKey());
		} 
		return operateLog;
	}

    /**
     * 获取远程访问IP
     * @return
     */
    protected String getRemoteIp(){
        try {
			//第一步首先获取代理类传过来的用户真实IP,如果有，直接返回
//			String ip = request.getHeader("proxy-send-client-ip");
//			if(ip != null && ip.length() > 0){
//				logger.info("proxy-send-client-ip=" + ip);
//				return ip;
//			}
			//接下来，获取Squid透传过来的ip
			String ip = request.getHeader("x-forwarded-for");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("X-Real-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
			LOGGER.info("proxy-ip=" + ip);
			if (! (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) ){
				int dotIdx = ip.indexOf(",");
				if(dotIdx == -1){
					//squid
					//透传过来的IP以一个空格分隔，第二个固定为客户的真实IP
					String[] ipToken = ip.split(" ");
					if(ipToken.length > 1){
						return ipToken[1];
					}
				}else{
					//squid,nginx
					//透传过来的IP以一个,分隔，第一个固定为客户的真实IP
					String[] ipToken = ip.split(",");
					if(ipToken.length > 1){
						return ipToken[0];
					}
				}
				LOGGER.info("user ip=" + ip);
				return ip;
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return "";
        
        
    }
    @ModelAttribute
    public void setRequest(HttpServletRequest request , HttpServletResponse response){
    	this.request = request ;
    	this.response = response ;
    }
}
