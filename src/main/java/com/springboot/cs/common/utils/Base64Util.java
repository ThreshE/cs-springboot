package com.springboot.cs.common.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author yyw
 * @Description: BASE64加密方法
 * @date 2018/8/1511:28
 */
public class Base64Util {
	
	/**
	 *  加密
	 * @param str
	 */
	public static String encode(String str){
		
		try {
			BASE64Encoder encoder = new BASE64Encoder();
			String encodeStr = encoder.encode(str.getBytes("UTF-8"));
			return encodeStr;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	
	/**	
	 * 	解密
	 * @param str
	 * @return
	 */
	public static String decode(String str) {
		
		try {
			
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] b = decoder.decodeBuffer(str);
			return new String(b,"utf-8");
			
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	

	
	public static void main(String[] args) {

		String str=null;
		str=String.format("Hi,%s", "小超");
		str=String.format("活动:%s被取消", "踏青");
		System.out.println(str);
	}
}
