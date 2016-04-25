/**
 * 
 */
package com.mv.utils;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AES加解密工具类
 * @author perry liu
 * 2015年1月28日
 */
public class AESUtils {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AESUtils.class);

	 /** 
     * 加密 
     *  
     * @param encryptStr 
     * @return 
     */  
    public static byte[] encrypt(byte[] src, String key) throws Exception {  
        Cipher cipher = Cipher.getInstance("AES");  
        SecretKeySpec securekey = new SecretKeySpec(key.getBytes(), "AES");  
        cipher.init(Cipher.ENCRYPT_MODE, securekey);//设置密钥和加密形式  
        return cipher.doFinal(src);  
    }  
  
    /** 
     * 解密 
     *  
     * @param decryptStr 
     * @return 
     * @throws Exception 
     */  
    public static byte[] decrypt(byte[] src, String key)  throws Exception  {  
        Cipher cipher = Cipher.getInstance("AES");  
        SecretKeySpec securekey = new SecretKeySpec(key.getBytes(), "AES");//设置加密Key  
        cipher.init(Cipher.DECRYPT_MODE, securekey);//设置密钥和解密形式  
        return cipher.doFinal(src);  
    }  
      
    /** 
     * 二行制转十六进制字符串 
     *  
     * @param b 
     * @return 
     */  
    public static String byte2hex(byte[] b) {  
        String hs = "";  
        String stmp = "";  
        for (int n = 0; n < b.length; n++) {  
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));  
            if (stmp.length() == 1)  
                hs = hs + "0" + stmp;  
            else  
                hs = hs + stmp;  
        }  
        return hs.toUpperCase();  
    }  
  
    public static byte[] hex2byte(byte[] b) {  
        if ((b.length % 2) != 0)  
            throw new IllegalArgumentException("长度不是偶数");  
        byte[] b2 = new byte[b.length / 2];  
        for (int n = 0; n < b.length; n += 2) {  
            String item = new String(b, n, 2);  
            b2[n / 2] = (byte) Integer.parseInt(item, 16);  
        }  
        return b2;  
    }  
      
    /** 
     * 解密 
     *  
     * @param data 
     * @return 
     * @throws Exception 
     */  
    public final static String decrypt(String data,String key) {  
        try {  
            return new String(decrypt(hex2byte(data.getBytes()),  
                    key));  
        } catch (Exception e) {  
        	LOGGER.error("解密失败", e);
        	throw new RuntimeException("解密失败" , e);
        }  
    }  
  
    /** 
     * 加密 
     *  
     * @param data 
     * @return 
     * @throws Exception 
     */  
    public final static String encrypt(String data,String key) {  
        try {  
            return byte2hex(encrypt(data.getBytes(), key));  
        } catch (Exception e) {  
        	LOGGER.error("加密失败", e);
        	throw new RuntimeException("加密失败" , e);
        }  
    }  
      
    
    public static void main(String args[]){
    	String str = AESUtils.encrypt("app_id=1&format=json&method=abc&timestamp=1&version=2.0&sign=d888cb4eed17344d363466b2ba9ecf25&name=bbbbb", "1111111122222222");
    	System.out.println("str:" + str);
    	System.out.println(System.currentTimeMillis());
    }
}
