package com.mv.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mv.web.PassportContext;

public class BaseController {

    /**
     * 请求上下文
     */
    private HttpServletRequest request;
    /**
     * 应答上下文
     */
    private HttpServletResponse response;
    
    /**
     * 获取当前登录用户userId
     * 
     * @return
     */
    public Long getCurrentUserId() {
        if (request.getAttribute("passport_context") == null) {
            return null;
        }
        PassportContext ctx = (PassportContext) request.getAttribute("passport_context");
        return ctx.getUserId();
    }

    /**
     * 获取当前登录用户loginId
     * 
     * @return
     */
    public String getCurrentLoginId() {
        if (request.getAttribute("passport_context") == null) {
            return null;
        }
        PassportContext ctx = (PassportContext) request.getAttribute("passport_context");
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
    
}
