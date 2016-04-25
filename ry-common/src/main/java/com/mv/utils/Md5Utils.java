/**
 * 
 */
package com.mv.utils;

import java.security.MessageDigest;

/**
 * @author shevliu
 *
 */
public class Md5Utils {

	 public static String md5(String origString) {  
		 char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',  
	                'a', 'b', 'c', 'd', 'e', 'f' };  
	        try {  
	            byte[] strTemp = origString.getBytes("utf-8");  
	            //如果输入“SHA”，就是实现SHA加密。  
	            MessageDigest mdTemp = MessageDigest.getInstance("MD5");   
	            mdTemp.update(strTemp);  
	            byte[] md = mdTemp.digest();  
	            int j = md.length;  
	            char str[] = new char[j * 2];  
	            int k = 0;  
	            for (int i = 0; i < j; i++) {  
	                byte byte0 = md[i];  
	                str[k++] = hexDigits[byte0 >>> 4 & 0xf];  
	                str[k++] = hexDigits[byte0 & 0xf];  
	            }  
	            return new String(str);  
	        } catch (Exception e) {  
	            return null;  
	        }  
	    }  

}
