package com.mv.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mv.domain.ErpUser;
import com.mv.domain.enums.OperatorStatusEnum;
import com.mv.domain.enums.YnEnum;
import com.mv.enums.CookieEnum;
import com.mv.service.ErpUserService;
import com.mv.utils.CookieUtils;
import com.mv.utils.Md5Utils;
import com.mv.utils.RSAUtils;
import com.mv.web.PassportConstant;
import com.mv.web.RemoteResult;

/**
 * 登录Controller
 */
@Controller
@RequestMapping(value = "/")
public class IndexController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

	@Value(value = "${man.token.private}")
	private String manToken;

	@Autowired
	private ErpUserService erpUserService;
	
	/**
	 * 默认首页
	 * @param request
	 * @param view
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="",method = {RequestMethod.GET,RequestMethod.POST})
	public String index(HttpServletRequest request,HttpServletResponse response ,Model view) throws IOException{
		
		LOGGER.info("----index-----" + request.getRequestURL() + "---" + request.getRequestURI());
		response.sendRedirect(request.getContextPath() + "/main");
		return null;
	}
	
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
			Assert.hasText(loginId , "请输入登录账号"); 
			Assert.hasText(password , "请输入登录密码!"); 
            // 验证用户名密码
            ErpUser erpUserForQuery = new ErpUser();
            erpUserForQuery.setLoginId(loginId);
            erpUserForQuery.setPassword(Md5Utils.md5(password));
            List<ErpUser> erpUsers = erpUserService.selectEntryList(erpUserForQuery);
            
            Assert.isTrue(erpUsers != null && erpUsers.size() == 1, "账号密码不正确");
            ErpUser erpUser = erpUsers.get(0);
            
			if(erpUser.getYn() != YnEnum.Y.getKey()){
				LOGGER.info("用户被冻结");
				return RemoteResult.failure("账户已被冻结，请联系管理员");
			}
			
            // 取得userId
            Long userId = erpUsers.get(0).getId();
            // 验证成功后设置加密ticket到cookie
            String ticketValuePlain = new StringBuilder().append(loginId).append(PassportConstant.TICKET_SEPARATOR).append(userId).append(PassportConstant.TICKET_SEPARATOR).append(System.currentTimeMillis()).toString();
            // key的配置
            String ticketValueSecret = RSAUtils.encryptByPrivateKey(ticketValuePlain, manToken);
            if (StringUtils.isBlank(ticketValueSecret)) {
            	LOGGER.error("ticket error, ticketValuePlain:" + ticketValuePlain);
            	return RemoteResult.failure();
            }
            // ticket存入cookie(3天) 
            // ps:多个子系统如果采用字母的二级域名方式，则此处cookie的path为/，domain默认，
            // 如果采用首域名不同的二级域名方式，则此处path默认,domain用根域，如(.dmall.com)，需要放到配置文件中
            CookieUtils.setCookie(response, CookieEnum.manTicketValue.getKey(), ticketValueSecret, 3 * 24 * 60 * 60);
            return RemoteResult.success(null);
        } catch (Exception e) {
            LOGGER.error("登录异常.loginId:" + loginId, e);
        	return RemoteResult.failure(e.getMessage());
        }
    }
    
    /**
     * 注销(退出)
     * @param response
     * @return
     * @throws Exception
     */
	@RequestMapping(value="/logout",method = {RequestMethod.GET})
	public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			CookieUtils.removeCookie(response, CookieEnum.manTicketValue.getKey());
			response.sendRedirect(request.getContextPath());
			return null;
		} catch (Exception e) {
			LOGGER.error("失败:"+e.getMessage(),e);
			return null;
		}
	}
	
	/**
	 * 欢迎页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/main")
	public String main(HttpServletRequest request) throws Exception{
		LOGGER.info("invoke main ： " );
		return "main/main";
	}

	/**
	 * 修改密码页面
	 * @param view
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/editPWD",method=RequestMethod.GET)
	public String editPWD(Model view) throws Exception{
		try {
			// 查询当前用户信息
			ErpUser erpUser = erpUserService.selectEntry(getCurrentUserId());
			view.addAttribute("erpUser",erpUser);
		} catch (Exception e) {
			LOGGER.error("失败:"+e.getMessage(),e);
			throw e;
		}finally{
		}

		return "erpUser/editPWD";
	} 

	/**
	 * 修改密码
	 * @param oldPWD
	 * @param newPWD
	 * @param view
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/updatePWD",method = {RequestMethod.POST,RequestMethod.GET},produces="application/json")
	public @ResponseBody RemoteResult updatePWD(String oldPWD,String newPWD,Model view , HttpServletResponse response) throws Exception{
    	try {
    		LOGGER.info("---" + oldPWD);
    		Assert.hasText(oldPWD, "旧密码不能为空");
    		Assert.hasText(newPWD, "新密码不能为空");

            // 验证用户名密码
            ErpUser erpUserForQuery = new ErpUser();
            erpUserForQuery.setLoginId(getCurrentLoginId());
            erpUserForQuery.setPassword(Md5Utils.md5(oldPWD));
            List<ErpUser> erpUsers = erpUserService.selectEntryList(erpUserForQuery);
            
            Assert.isTrue(erpUsers != null && erpUsers.size() == 1, "旧密码不正确");
            
            ErpUser erpUserForUpdate = new ErpUser();
    		Date now = new Date();
    		erpUserForUpdate.setId(getCurrentUserId());
    		erpUserForUpdate.setPassword(Md5Utils.md5(newPWD));
    		erpUserForUpdate.setModified(now);
			erpUserService.updateByKey(erpUserForUpdate);
			operateService.saveOperate(OperatorStatusEnum.SUCCESS,"ERP用户自行修改密码成功,user id:" + erpUserForUpdate.getId() ,getOperateLog());
			CookieUtils.removeCookie(response, CookieEnum.manTicketValue.getKey());
			return RemoteResult.success(null) ;
		} catch (Exception e) {
			LOGGER.error("失败:"+e.getMessage(),e);
			return RemoteResult.failure(e.getMessage());
		}
	}	
}
