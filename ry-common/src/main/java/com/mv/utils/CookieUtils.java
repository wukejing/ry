package com.mv.utils;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie操作助手类
 */
public class CookieUtils {

	/*******************操作Cookie********************/
    /**
     * 获取指定键的Cookie
     * @param cookieName
     * @return 如果找到Cookie则返回 否则返回null
     */
    public static Cookie getCookie(HttpServletRequest request , String cookieName){
        if (StringUtils.isBlank(cookieName) || request.getCookies() == null)
            return null;
        for(Cookie cookie : request.getCookies()){
            if (cookieName.equals(cookie.getName()))
                return cookie;
        }
        return null; 
    }
    
    /**
     * 获取指定键的Cookie值
     * @param cookieName
     * @return 如果找到Cookie则返回 否则返回null
     */
    public static String getCookieValue(HttpServletRequest request , String cookieName){
        Cookie cookie = getCookie(request , cookieName);
        return cookie == null ? null : cookie.getValue();
    }
    
    /**
     * 删除指定的cookie
     * @param response
     * @param cookieName
     */
    public static void removeCookie(HttpServletResponse response , String cookieName){
    	Cookie cookie = new Cookie(cookieName, null);
    	cookie.setMaxAge(0);
    	response.addCookie(cookie);
//
//    	Cookie cookie2 = new Cookie("kkk", "aaa");
//    	cookie2.setMaxAge(-1);
//    	response.addCookie(cookie2);
    }
    
    /**
     * 保存一个对象到Cookie  Cookie只在会话内有效
     * @param response
     * @param cookieName
     * @param cookieValue
     */
    public static void setCookie(HttpServletResponse response , String cookieName,  String cookieValue){
    	if (StringUtils.isBlank(cookieName) || cookieValue == null)
    		return;
    	Cookie cookie = new Cookie(cookieName, cookieValue);
    	response.addCookie(cookie);
    }
    
    /**
     * 保存一个对象到Cookie,设置超时时间(秒), path、domain都用默认
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param expiry
     */
    public static void setCookie(HttpServletResponse response , String cookieName,  String cookieValue ,int expiry ){
        if (StringUtils.isBlank(cookieName) || cookieValue == null || expiry < 0)
            return;
        if (StringUtils.isBlank(cookieName) || cookieValue == null)
            return;
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(expiry);
        response.addCookie(cookie);
    }

    /**
     * 保存一个对象到Cookie,设置超时时间(秒), 设置domain，而path用默认
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param expiry
     * @param domain
     */
    public static void setCookieWithDomain(HttpServletResponse response , String cookieName,  String cookieValue ,int expiry, String domain){
        if (StringUtils.isBlank(cookieName) || cookieValue == null || expiry < 0)
            return;
        if (StringUtils.isBlank(cookieName) || cookieValue == null)
            return;
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setDomain(domain);
        cookie.setMaxAge(expiry);
        response.addCookie(cookie);
    }
    
    /**
     * 保存一个对象到Cookie,设置超时时间(秒), 设置path，而domain用默认
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param expiry
     * @param path
     */
    public static void setCookieWithPath(HttpServletResponse response , String cookieName,  String cookieValue ,int expiry, String path){
        if (StringUtils.isBlank(cookieName) || cookieValue == null || expiry < 0)
            return;
        if (StringUtils.isBlank(cookieName) || cookieValue == null)
            return;
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setPath(path);
        cookie.setMaxAge(expiry);
        response.addCookie(cookie);
    }

    /**
     * 保存一个对象到Cookie,设置超时时间(秒), 设置domain和path
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param expiry
     * @param domain
     * @param path
     */
    public static void setCookieWithDomainAndPath(HttpServletResponse response , String cookieName,  String cookieValue ,int expiry, String domain, String path){
        if (StringUtils.isBlank(cookieName) || cookieValue == null || expiry < 0)
            return;
        if (StringUtils.isBlank(cookieName) || cookieValue == null)
            return;
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setDomain(domain);
        cookie.setPath(path);
        cookie.setMaxAge(expiry);
        response.addCookie(cookie);
    }
}
