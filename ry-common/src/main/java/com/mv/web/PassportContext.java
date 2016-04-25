package com.mv.web;

/**
 * Created by wukejing on 15/12/27.
 */
public class PassportContext {
    
    private String loginId;
    
    private Long userId;
    
    /**
     * 微信登录的OpenID
     */
    private String openId;
    
    public String getOpenId() {
        return openId;
    }
    
    public void setOpenId(String openId) {
        this.openId = openId;
    }
    
    public String getLoginId() {
        return loginId;
    }
    
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
}
