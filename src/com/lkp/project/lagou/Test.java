/*
 * Copyright (c) SHADANSOU 2014 All Rights Reserved
 *
 */
package com.lkp.project.lagou;

import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import com.lkp.common.net.HttpProxy;

/**
 * <p>
 * class function description.
 * <p>
 *
 * create 2016年3月10日<br>
 * 
 * @author lkp<br>
 * @version 1.0
 * @since 1.0
 */
public class Test {

	public static void main(String[] args) {
		
		
		getUserInfo();
	}
	
	public static void getUserInfo(){
//		String url1 = "https://passport.lagou.com/login/login.html";
//		String body1 = HttpProxy.getHttpRequestContentByGet(url1, null, null);
//		
//		int start = body1.indexOf("window.X_Anti_Forge_Token = ");
//		String token = body1.substring(start+1+ "window.X_Anti_Forge_Token = ".length(),start+1+ "window.X_Anti_Forge_Token = ".length()+36);
//		start = body1.indexOf("window.X_Anti_Forge_Code = ");
//		String code = body1.substring("window.X_Anti_Forge_Code = ".length()+start+1,"window.X_Anti_Forge_Code = ".length()+start+1+8);
//		System.out.println("token="+token+";code="+code);
//		
//		
//		
//		String url = "https://passport.lagou.com/login/login.json";
//		String pwd = "pengsir2016";
//		pwd = getPwd(pwd);
//		String formparams = "username=280740960@qq.com&password="+pwd+"&isValidate=true";
//		//isValidate:true
//		/*username:280740960@qq.com
//		password:c47eeb69fa4e64971fb29cb1e9163a19*/
//		String cookie = HttpProxy.cookie;
//		
//		Map<String,String> headParams = new HashMap<String,String>();
//		headParams.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36");
//		headParams.put("X-Anit-Forge-Code",code); 
//		headParams.put("X-Anit-Forge-Token",token); 
//		headParams.put("X-Requested-With","XMLHttpRequest");
//		headParams.put("Pragma","no-cache");
//		headParams.put("Content-Length","116");
//		headParams.put("Origin","https://passport.lagou.com");
//		headParams.put("Referer","https://passport.lagou.com/login/login.html");
//		headParams.put("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
//		headParams.put(" Accept","application/json, text/javascript, */*; q=0.01");
//		/*headParams.put("Accept-Encoding","gzip, deflate");
//		headParams.put("Accept-Language","zh-CN,zh;q=0.8");*/
//		headParams.put("Cookie", cookie);
//	System.out.println("headParams =" +headParams);	
//		//headParams.put("Cookie",cookie); 
//		String ss =  HttpProxy.getHttpsRequestContentByPost(url, null, formparams, headParams);
//		 
//		System.out.println("ss="+ss);
		 
		
		
		
		HashMap headParams = new HashMap<String,String>();
		headParams.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36");
//		headParams.put("X-Anit-Forge-Code",code); 
//		headParams.put("X-Anit-Forge-Token",token); 
		headParams.put("X-Requested-With","XMLHttpRequest");
		headParams.put("Pragma","no-cache");
		headParams.put("Content-Length","116");
		headParams.put("Origin","https://passport.lagou.com");
		headParams.put("Referer","https://passport.lagou.com/login/login.html");
		headParams.put("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
		headParams.put(" Accept","application/json, text/javascript, */*; q=0.01");
	//	headParams.put("Cookie", HttpProxy.cookie);
		//7FE8E7B75246A17EDF779D2D11772562
		 headParams.put("Cookie", " JSESSIONID=82208AAD64ADA413F83AD498AECB61F5;");
		
	//	headParams.put("Cookie", " LGMOID=20160918234356-D3F57A79472F7CC71BE5B130E97C6831; ctk=1474213436; user_trace_token=20160918234358-505266950b014df3bbbc4c8d2e13738c; _gat=1; PRE_UTM=; PRE_HOST=; PRE_SITE=; PRE_LAND=http%3A%2F%2Fwww.lagou.com%2F; LGUID=20160918234358-b6deb77e-7db6-11e6-ba7b-525400f775ce; _putrc=502A488A56CB666E; login=true; unick=280740960%40qq.com; index_location_city=%E5%85%A8%E5%9B%BD; Hm_lvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1474213440; Hm_lpvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1474213511; _ga=GA1.2.2117128973.1474213440; LGSID=20160918234358-b6deb53e-7db6-11e6-ba7b-525400f775ce; LGRID=20160918234933-7e6503dc-7db7-11e6-a306-5254005c3644");
		String url1 = "http://account.lagou.com/account/cuser/userInfo.html";
//http://account.lagou.com/account/cuser/userInfo.html
		String body1 = HttpProxy.getHttpRequestContentByGet(url1, null, headParams);
		System.out.println("body1="+body1);
	}
	
	public static void Login(){

		
		String url1 = "https://passport.lagou.com/login/login.html";
		String body1 = HttpProxy.getHttpRequestContentByGet(url1, null, null);
		int start = body1.indexOf("window.X_Anti_Forge_Token = ");
		String token = body1.substring(start+1+ "window.X_Anti_Forge_Token = ".length(),start+1+ "window.X_Anti_Forge_Token = ".length()+36);
		start = body1.indexOf("window.X_Anti_Forge_Code = ");
		String code = body1.substring("window.X_Anti_Forge_Code = ".length()+start+1,"window.X_Anti_Forge_Code = ".length()+start+1+8);
		System.out.println("token="+token+";code="+code);
				
		/* if(true){
			 return;
		 }*/
		
		String url = "https://passport.lagou.com/login/login.json";
		String pwd = "pengsir2015";
		pwd = getPwd(pwd);
		String formparams = "username=280740960@qq.com&password="+pwd+"&isValidate=true";
		//isValidate:true
		/*username:280740960@qq.com
		password:c47eeb69fa4e64971fb29cb1e9163a19*/
		String cookie = HttpProxy.cookie;
		
		Map<String,String> headParams = new HashMap<String,String>();
		headParams.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36");
		headParams.put("X-Anit-Forge-Code",code); 
		headParams.put("X-Anit-Forge-Token",token); 
		headParams.put("X-Requested-With","XMLHttpRequest");
		headParams.put("Pragma","no-cache");
		headParams.put("Content-Length","116");
		headParams.put("Origin","https://passport.lagou.com");
		headParams.put("Referer","https://passport.lagou.com/login/login.html");
		headParams.put("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
		headParams.put(" Accept","application/json, text/javascript, */*; q=0.01");
		/*headParams.put("Accept-Encoding","gzip, deflate");
		headParams.put("Accept-Language","zh-CN,zh;q=0.8");*/
		headParams.put("Cookie", cookie);
	System.out.println("headParams =" +headParams);	
		//headParams.put("Cookie",cookie); 
		String ss =  HttpProxy.getHttpsRequestContentByPost(url, null, formparams, headParams);
		 
		System.out.println("ss="+ss);
		 

	
	}

	public static String getPwd(String password){
		String pwd1 = Test.MD5(password);
		System.out.println("pwd1="+pwd1);
		pwd1 = "veenike"+pwd1+"veenike";
		//veenike96e79218965eb72c92a549dd5a330112veenike
		String pwd = Test.MD5(pwd1.toLowerCase());
		return pwd.toLowerCase();
	}
	 public final static String MD5(String s) {
	        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       
	        try {
	            byte[] btInput = s.getBytes();
	            // 获得MD5摘要算法的 MessageDigest 对象
	            MessageDigest mdInst = MessageDigest.getInstance("MD5");
	            // 使用指定的字节更新摘要
	            mdInst.update(btInput);
	            // 获得密文
	            byte[] md = mdInst.digest();
	            // 把密文转换成十六进制的字符串形式
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
	            e.printStackTrace();
	            return null;
	        }
	    }
	public static void write(String file, String content) {

		try {
			FileWriter fw = new FileWriter(file);
			fw.write(content);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 

	public static String getValueWithNull(Object value) {
		if (value == null) {
			return "";
		} else {
			return value.toString();
		}
	}
}
