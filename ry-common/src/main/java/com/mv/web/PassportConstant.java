package com.mv.web;

/**
 * passport常量
 */
public class PassportConstant {
    
    public static final String TICKET_SEPARATOR = "##";
    
    /**
     * Cookie登录过期时间 秒
     */
    public static final int LOGIN_EXPIRES_TIME = 365 * 24 * 60 * 60;
    
    /**
     * Cookie昵称过期时间 秒
     */
    public static final int NICKNAME_EXPIRES_TIME = 365 * 24 * 60 * 60;
    
    /**
     * 更新ticket时间 毫秒
     */
    public static final long UPDATE_TICKET_TIME = 10 * 60 * 1000;
    
    /**
     * 服务器 登录过期时间 毫秒
     */
    public static final long LOGIN_EXPIRES_TIME_SERVER = 365 * 24 * 60 * 60 * 1000;
    
    public static final String PASSPORT_CONTEXT_KEY = "passport_context";
    
    public static final String PASSPORT_SID_KEY = "sid";
    
    
}
