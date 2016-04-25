package com.mv.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mv.enums.CookieEnum;
import com.mv.utils.CookieUtils;
import com.mv.utils.RSAUtils;
import com.mv.web.PassportConstant;
import com.mv.web.RemoteResult;

/**
 * 登录Controller
 */
@Controller
@RequestMapping(value = "/")
public class LoginController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	@Value(value = "${passport.token.private}")
	private String passportToken;
	
    /**
     * 登录
     * 
     * @param request
     * @param view
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "doLogin", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
    public RemoteResult doLogin( HttpServletRequest request, HttpServletResponse response, String loginId, String password) throws IOException {
        try {
            // 用户名，密码不为空
            if (StringUtils.isBlank(loginId) || StringUtils.isBlank(password)) {
            	return RemoteResult.failure();
            }
            // Todo:验证用户名密码
            
            // Todo:取得userId
            Long userId = 555L;
            // 验证成功后设置加密ticket到cookie
            String ticketValuePlain = new StringBuilder().append(loginId).append(PassportConstant.TICKET_SEPARATOR).append(userId).append(PassportConstant.TICKET_SEPARATOR).append(System.currentTimeMillis()).toString();
            String ticketValueSecret = RSAUtils.encryptByPrivateKey(ticketValuePlain, passportToken);
            if (StringUtils.isBlank(ticketValueSecret)) {
            	LOGGER.error("ticket error, ticketValuePlain:" + ticketValuePlain);
            	return RemoteResult.failure();
            }
            // ticket存入cookie(1年)
            CookieUtils.setCookie(response, CookieEnum.ticketValue.getKey(), ticketValueSecret, PassportConstant.LOGIN_EXPIRES_TIME);
            return RemoteResult.success(null);
        } catch (Exception e) {
            LOGGER.error("登录异常.loginId:" + loginId, e);
        	return RemoteResult.failure();
        }
    }
    
    /**
     * 注销(退出)
     * 
     * @param request
     * @param view
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "doLogout", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
    public RemoteResult doLogout(HttpServletResponse response) throws IOException {
        try {
        	CookieUtils.removeCookie(response, CookieEnum.ticketValue.getKey());;
            return RemoteResult.success(null);
        } catch (Exception e) {
            LOGGER.error("注销异常", e);
        	return RemoteResult.failure();
        }
        
    }
}
