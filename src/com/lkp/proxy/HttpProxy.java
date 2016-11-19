package com.lkp.proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.commons.codec.binary.Base64;

/**
 * 
 * http 及 https代理与证书设置
 * 
 * create 2014年12月12日<br>
 * 
 * @author lkp<br>
 * @version 1.0
 * @since 1.0
 */
public class HttpProxy {
	
	private static String username;
	private static String password;
	private static String authorization;
	private static String zhPattern = "[/u4e00-/u9fa5]+";
	
	public static String getResponseByPost(String url,boolean needcookie,String cookieurl,Proxy proxy) {
		System.out.println("in getResponseByPost");
		String cookie="";
		if (url.toLowerCase().startsWith("http:")) {
			if(needcookie){
				cookie = getCookieByHttp(cookieurl,proxy);
			}
			return getHttpRequestContentByPost(url,needcookie,cookie,proxy);
		} else {
			if(needcookie){
				cookie = getCookieByHttps(cookieurl);
			}
			return getHttpsRequestContentByPost(url,needcookie,cookie,proxy);
		}
	}

	public static String getResponseByPostForm(String url,String formparams,String cookie,Proxy proxy) {
		 
		if (url.toLowerCase().startsWith("http:")) { 
			return getHttpRequestContentByPostForm(url,formparams,cookie,proxy);
		} else {
			 
			return getHttpsRequestContentByPostForm(url,formparams,true,cookie,proxy);
		}
	}
	public static String getResponseByGet(String url,String cookie,Proxy proxy) { 
		if (url.toLowerCase().startsWith("http:")) {
			 
			return getHttpRequestContentByGet(url,cookie,proxy);
		} else { 
			return getHttpsRequestContentByGet(url,false,cookie,proxy);
		}
	}

	public static String getResponseByGetStatus(String url,boolean needcookie,String cookieurl,Proxy proxy) {
		String cookie =  "";
		if (url.toLowerCase().startsWith("http:")) {
			if(needcookie){
				cookie = getCookieByHttp(cookieurl,proxy);
			}
			return getHttpRequestContentByGetStatus(url , needcookie ,cookie,proxy);
		} else {
			if(needcookie){
				cookie = getCookieByHttps(cookieurl);
			}
			return getHttpsRequestContentByGetStatus(url , needcookie ,cookie);
		}
	}
	
	public static String getResponseByPostStatus(String url,boolean needcookie,String cookieurl,Proxy proxy) {
		String cookie =  "";
		if (url.toLowerCase().startsWith("http:")) {
			if(needcookie){
				cookie = getCookieByHttp(cookieurl,proxy);
			}
			return getHttpRequestContentByPostStatus(url , needcookie ,cookie,proxy);
		} else {
			if(needcookie){
				cookie = getCookieByHttps(cookieurl);
			}
			return getHttpsRequestContentByPostStatus(url , needcookie ,cookie,proxy);
		}
	}
	
	public static void setUsernameAndPwd(String _username, String _password) {
		username = _username;
		password = _password;
		authorization = username + ":" + password;
		byte[] encodedBytes = Base64.encodeBase64(authorization.getBytes());
		authorization = "Basic " + new String(encodedBytes);

	}

	private static String getHttpsRequestContentByPost(String url,boolean needcookie,String cookie,Proxy proxy) {
		System.out.println("in https post");
		String returnbody = "";
		try {
			SSLContext sc = SSLContext.getInstance("SSL");

			// 指定信任https
			sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
					new java.security.SecureRandom());
			URL console = new URL(url);
			HttpsURLConnection conn = null;
			
			
			if(proxy!=null){
				conn = (HttpsURLConnection) console
						.openConnection(proxy);
			}else{
				conn = (HttpsURLConnection) console
						.openConnection();
			}
			conn.setSSLSocketFactory(sc.getSocketFactory());
			conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
			conn.setRequestMethod("POST");
			conn.setSSLSocketFactory(sc.getSocketFactory());
			conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
			System.out.println("auth===="+authorization);
			conn.setRequestProperty("Authorization", authorization);
			conn.setDoOutput(true);
			// 设置是否从httpUrlConnection读入，默认情况下是true;
			conn.setDoInput(true);
			conn.setConnectTimeout(30000);  
			conn.setReadTimeout(30000);  
			
			
		 
			// Post 请求不能使用缓存
			
			conn.setUseCaches(false);

			// 设定传送的内容类型是可序列化的java对象
			// (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
			conn.setRequestProperty("Content-type",
					"application/x-java-serialized-object");
			conn.setFixedLengthStreamingMode(0);// 必须要这句
			if(needcookie){
				conn.setRequestProperty("Cookie", cookie);// 有网站需要将当前的session id一并上传
			}
			conn.connect();
			// conn.
			System.out.println("返回结果：" + conn.getResponseMessage());

			InputStream is = conn.getInputStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			String curLine = "";
			while ((curLine = reader.readLine()) != null) {
				returnbody += curLine;
			}
			is.close();
		} catch (Exception e) {
			//System.out.println("errorrrrrrrrrrrrrrrr:  url:"+url+" : "+ e.getMessage());
			e.printStackTrace();
			 
		}
		return returnbody;
	}

	private static String getHttpsRequestContentByPostForm(String url,String formparams,boolean needcookie,String cookie,Proxy proxy) {
		System.out.println("in https postform");
		String returnbody = "";
		try {
			SSLContext sc = SSLContext.getInstance("SSL");

			// 指定信任https
			sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
					new java.security.SecureRandom());
			
			URL console = new URL(url);
			HttpsURLConnection conn = null;
			
			if(proxy!=null){
				conn = (HttpsURLConnection) console
						.openConnection(proxy);
			}else{
				conn = (HttpsURLConnection) console
						.openConnection();
			}
			
			conn.setSSLSocketFactory(sc.getSocketFactory());
			conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
			conn.setRequestMethod("POST");
			conn.setSSLSocketFactory(sc.getSocketFactory());
			conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
			 
			byte[] data = formparams.getBytes();
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
			conn.setRequestProperty("Content-Length", String.valueOf(data.length)); 
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
			conn.setRequestProperty("Accept", "*/*");
			conn.setRequestProperty("Range", "bytes="+"");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36");
			conn.setRequestProperty("X-Anit-Forge-Code","78350827"); 
			conn.setRequestProperty("X-Anit-Forge-Token","f1583c6b-df72-40ce-98a9-2db7b32525c3"); 
			conn.setRequestProperty("X-Requested-With","XMLHttpRequest");
		//	conn.setRequestProperty("Referer","https://passport.lagou.com/login/login.html?ts=1474126171500&serviceId=lagou&service=http%253A%252F%252Fwww.lagou.com%252F&action=login&signature=C1FF5D87873B807FA0DD0E9839C9A7CC");
			
		 
			
			conn.setConnectTimeout(30000);  
			conn.setReadTimeout(30000); 
			if(cookie!=null){
				conn.setRequestProperty("Cookie", cookie);
			}
			conn.connect();
		//	conn.setRequestMethod("POST");
			
			OutputStream output = conn.getOutputStream();
			output.write(data); 
			
			
			System.out.println("返回结果：" + conn.getResponseMessage());

			InputStream is = conn.getInputStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			String curLine = "";
			while ((curLine = reader.readLine()) != null) {
				returnbody += curLine;
			}
			is.close();
		} catch (Exception e) {
			//System.out.println("errorrrrrrrrrrrrrrrr:  url:"+url+" : "+ e.getMessage());
			 
		}
		return returnbody;
	}
	
	private static String getHttpRequestContentByPost(String url,boolean needcookie,String cookie,Proxy proxy) {
		System.out.println("in http post");
		String returnBody = "";
		try {

			URL console = new URL(url);
			HttpURLConnection conn = null;
			
			
			if(proxy!=null){
				conn = (HttpURLConnection) console
						.openConnection(proxy);
			}else{
				conn = (HttpURLConnection) console
						.openConnection();
			}
			conn.setRequestMethod("POST");

			conn.setRequestProperty("Authorization", authorization);
			System.out.println("auth===="+authorization);
			// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在
			// http正文内，因此需要设为true, 默认情况下是false;
			conn.setDoOutput(true);

			// 设置是否从httpUrlConnection读入，默认情况下是true;
			conn.setDoInput(true);

			// Post 请求不能使用缓存
			conn.setUseCaches(false);

			// 设定传送的内容类型是可序列化的java对象
			// (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
			conn.setRequestProperty("Content-type",
					"application/x-java-serialized-object");

			// 设定请求的方法为"POST"，默认是GET
			conn.setRequestMethod("POST");
			conn.setFixedLengthStreamingMode(0);// 必须要这句
			conn.setConnectTimeout(30000);  
			conn.setReadTimeout(30000);  
			if(needcookie){
				conn.setRequestProperty("Cookie", cookie);// 有网站需要将当前的session id一并上传
			}
			conn.connect(); 
			System.out.println("返回结果：" + conn.getResponseMessage());

			 
			
			InputStream is = conn.getInputStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			String curLine = "";
			while ((curLine = reader.readLine()) != null) {
				returnBody += curLine;
			}
			is.close();

		} catch (Exception e) {
			System.out.println("errorrrrrrrrrrrrrrrr:  url:"+url+" : "+ e.getMessage());
			 
		}
		return returnBody;
	}

	private static String getHttpRequestContentByPostForm(String url,String formparams,String cookie,Proxy proxy) {
		String returnBody = "";
		try {
			URL console = new URL(url);
			HttpURLConnection conn = null;
			if(proxy!=null){
				conn = (HttpURLConnection) console
						.openConnection(proxy);
			}else{
				conn = (HttpURLConnection) console
						.openConnection();
			}
			byte[] data = formparams.getBytes(); 
			
			
			///
			
/*
 * HttpPost httppost = new HttpPost(url);
		httppost.setHeader("Cookie", "CookieName=CookieValue; CookieName=CookieValue; __jsluid=5f57f776f9d15d58a6f91f876053dd92; ASP.NET_SessionId=srovfd0azciqhea5zjmbvb0h; __jsl_clearance=1455890167.373|0|wot45ROwXzxIcZasvsWAvHPaftE%3D; _gsref_1241932522=http://www.court.gov.cn/zgcpwsw/; _gscu_1241932522=557987759brzvg16; _gscs_1241932522=55886811o485v616|pv:21; _gscbrs_1241932522=1");
		httppost.setHeader("Accept","XMLHttpRequest"); 
		httppost.setHeader("X-Requested-With","*"); 
//		httppost.setHeader("Referer","http://www.court.gov.cn/zgcpwsw/List/List?sorttype=1&conditions=searchWord+3+AJLX++%E6%A1%88%E4%BB%B6%E7%B1%BB%E5%9E%8B:%E8%A1%8C%E6%94%BF%E6%A1%88%E4%BB%B6");
//		httppost.setHeader("Proxy-Connection","keep-alive");
//		httppost.setHeader("Upgrade-Insecure-Requests","1");
//		httppost.setHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
//		httppost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.82 Safari/537.36");
//		httppost.setHeader("Accept-Encoding","gzip, deflate, sdch");
//		httppost.setHeader("Accept-Language","zh-CN,zh;q=0.8,en;q=0.6");
	*/
			//
			conn.setRequestProperty("Proxy-Connection","keep-alive"); 
			conn.setRequestProperty("Host","www.court.gov.cn"); 
			conn.setRequestProperty("Content-Length", "153"); 
			conn.setRequestProperty("Connection", "keep-alive"); 
			conn.setRequestProperty("Accept", "*/*"); 
			conn.setRequestProperty("Origin","http://www.court.gov.cn"); 
			conn.setRequestProperty("X-Requested-With","XMLHttpRequest"); 
			conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.82 Safari/537.36"); 
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
			conn.setRequestProperty("Referer","http://www.court.gov.cn/zgcpwsw/list/list/?sorttype=1&conditions=searchWord+4+AJLX++%E6%A1%88%E4%BB%B6%E7%B1%BB%E5%9E%8B:%E8%B5%94%E5%81%BF%E6%A1%88%E4%BB%B6&conditions=searchWord++CPRQ++%E8%A3%81%E5%88%A4%E6%97%A5%E6%9C%9F:2015-02-20%20TO%202015-03-20"); 
			conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
			conn.setRequestProperty("Accept-Encoding","gzip, deflate");  
			if(cookie!=null){
				conn.setRequestProperty("Cookie",cookie);  
			}
			//conn.setRequestProperty("Upgrade-Insecure-Requests","1");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(30000);  
			conn.setReadTimeout(30000);  
			conn.setRequestMethod("POST");
			 
			conn.connect();
			
			OutputStream output = conn.getOutputStream();
			output.write(data); 
			
		 
			System.out.println("返回结果：" + conn.getResponseMessage());

			InputStream is = conn.getInputStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is,"utf-8"));
			String curLine = "";
			while ((curLine = reader.readLine()) != null) {
				returnBody += curLine;
			}
			is.close();

		} catch (Exception e) {
			System.out.println("errorrrrrrrrrrrrrrrr:  url:"+url+" : "+ e.getMessage());
			 
			//e.printStackTrace();
		}
		return returnBody;
	}
	/**
	 * 对于请求vpn的不需要使用代理
	 * @param url
	 * @return
	 */
	public static String getHttpRequestContentByGetWithoutProxy(String url){
 
		String returnBody = "";
		try {
			URL console = new URL(url);
			HttpURLConnection conn  = (HttpURLConnection) console
					.openConnection();
			
			conn.setRequestMethod("GET");
			
			 	conn.setRequestProperty("Content-type",
					"application/x-java-serialized-object");
			 	/*
			 	 * 
			 	 * Upgrade-Insecure-Requests:1
User-Agent:Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36

			// 设定请求的方法为"POST"，默认是GET*/
		//	 conn.setRequestProperty("Upgrade-Insecure-Requests","1");
			 conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0");
			conn.setConnectTimeout(30000);  
			conn.setReadTimeout(30000);  
			conn.connect();
			//System.out.println("返回结果：" + conn.getResponseMessage());

			 
			
			InputStream is = conn.getInputStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			String curLine = "";
			while ((curLine = reader.readLine()) != null) {
				returnBody += curLine;
			}
			is.close();

		} catch (Exception e) {
			System.out.println("errorrrrrrrrrrrrrrrr:  url:"+url+" : "+ e.getMessage());
			 
			//e.printStackTrace();
		}
		return returnBody;
	
	}
	 
	private static String getHttpRequestContentByGet(String url,String cookie,Proxy proxy) {
		String returnBody = "";
		try {
			URL console = new URL(url);
			HttpURLConnection conn = null;
			if(proxy!=null){
				conn = (HttpURLConnection) console
						.openConnection(proxy);
			}else{
				conn = (HttpURLConnection) console
						.openConnection();
			}
			conn.setRequestMethod("GET");
			
//			 	conn.setRequestProperty("Content-type",
//					"application/x-java-serialized-object");
			//Accept-Language:zh-CN,zh;q=0.8,en;q=0.6
			conn.setRequestProperty("Accept-Language","zh-CN,zh;q=0.8,en;q=0.6");
		//	conn.setRequestProperty("Accept-Encoding","gzip, deflate, sdch");
			 	conn.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
			 //	conn.setRequestProperty("Host","s.weibo.com");
			//  conn.setRequestProperty("Referer", "http://219.142.101.185/jianguanfabuweb/companies.html");
			 	conn.setRequestProperty("Cache-Control","max-age=0");
			 	conn.setRequestProperty("Connection","keep-alive"); 
			 	conn.setRequestProperty("X-Requested-With","XMLHttpRequest"); 
			 	if(cookie!=null){
			 		conn.setRequestProperty("Cookie",cookie);  
			 	}
//			 	conn.setRequestProperty("If-Modified-Since","Wed, 30 Dec 2015 09:01:37 GMT");
//			 	conn.setRequestProperty("If-None-Match","");
//			 	conn.setRequestProperty("If-Modified-Since","3f5dfb0e042d11");
			 	//conn.setRequestProperty("Upgrade-Insecure-Requests","1");
			 	conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0");
			 	
			  
			// 设定请求的方法为"POST"，默认是GET
			conn.setConnectTimeout(30000);  
			conn.setReadTimeout(30000);  
//			if(needcookie){
//				conn.setRequestProperty("Cookie", cookie);// 有网站需要将当前的session id一并上传
//			}
			
			conn.connect(); 

			 
			
		 
 			
 			InputStream is = conn.getInputStream();
			
			byte[] b = new byte[20480];
			 int r_len = 0;
			 StringBuffer result = new StringBuffer();
			 while ((r_len = is.read(b)) > 0)
			  {
			       result.append(new String(b, 0, r_len));
			  } 
			 
			// System.out.println("result = " + result);
//			BufferedReader reader = new BufferedReader(
//					new InputStreamReader(is,"iso-8859-1"));
//			String curLine = "";
//			while ((curLine = reader.readLine()) != null) {
//				returnBody += curLine;
//			}
			is.close();
			return result.toString();

		} catch (Exception e) {
			System.out.println("errorrrrrrrrrrrrrrrr:  url:"+url+" : "+ e.getMessage());
		}
		return returnBody;
	}
	
	
	private static String getHttpRequestContentByGetStatus(String url,boolean needcookie,String cookie,Proxy proxy) {
		try {
			URL console = new URL(url);
			HttpURLConnection conn = null;
			if(proxy!=null){
				conn = (HttpURLConnection) console
						.openConnection(proxy);
			}else{
				conn = (HttpURLConnection) console
						.openConnection();
			}
		
			conn.setRequestMethod("GET");
			
			 	conn.setRequestProperty("Content-type",
					"application/x-java-serialized-object");

			// 设定请求的方法为"POST"，默认是GET
			conn.setConnectTimeout(30000);  
			conn.setReadTimeout(30000);  
			if(needcookie){
				conn.setRequestProperty("Cookie", cookie);// 有网站需要将当前的session id一并上传
			}
			
			conn.connect();
			System.out.println("返回结果：" + conn.getResponseCode());//.getResponseMessage());
			return conn.getResponseCode()+"";

		} catch (Exception e) {
			System.out.println("errorrrrrrrrrrrrrrrr:  url:"+url+" : "+ e.getMessage());
			 
			//e.printStackTrace();
		}
		return "1000";
	}
	
	private static String getHttpRequestContentByPostStatus(String url,boolean needcookie,String cookie,Proxy proxy){
		System.out.println("in http post");
		try {
			URL console = new URL(url);
			HttpURLConnection conn = null;
			
			
			if(proxy!=null){
				conn = (HttpURLConnection) console
						.openConnection(proxy);
			}else{
				conn = (HttpURLConnection) console
						.openConnection();
			}
			conn.setRequestMethod("POST");

			conn.setRequestProperty("Authorization", authorization);
			System.out.println("auth===="+authorization);
			// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在
			// http正文内，因此需要设为true, 默认情况下是false;
			conn.setDoOutput(true);

			// 设置是否从httpUrlConnection读入，默认情况下是true;
			conn.setDoInput(true);

			// Post 请求不能使用缓存
			conn.setUseCaches(false);

			// 设定传送的内容类型是可序列化的java对象
			// (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
			conn.setRequestProperty("Content-type",
					"application/x-java-serialized-object");

			// 设定请求的方法为"POST"，默认是GET
			conn.setRequestMethod("POST");
			conn.setFixedLengthStreamingMode(0);// 必须要这句
			conn.setConnectTimeout(30000);  
			conn.setReadTimeout(30000);  
			if(needcookie){
				conn.setRequestProperty("Cookie", cookie);// 有网站需要将当前的session id一并上传
			}
			conn.connect(); 
			System.out.println("返回结果：" + conn.getResponseCode());//.getResponseMessage());
			return conn.getResponseCode()+"";
		} catch (Exception e) {
			System.out.println("errorrrrrrrrrrrrrrrr:  url:"+url+" : "+ e.getMessage());
			e.printStackTrace();
			 
		}
		return null;
	}
	
	
	private static String getCookieByHttps(String url) {
		System.out.println("in https get");
		String cookie = "";
		try {
			SSLContext sc = SSLContext.getInstance("SSL");

			// 指定信任https
			sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
					new java.security.SecureRandom());
			URL console = new URL(url);
			HttpsURLConnection conn = (HttpsURLConnection) console
					.openConnection();
			conn.setSSLSocketFactory(sc.getSocketFactory());
			conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
			conn.setRequestMethod("GET");
			conn.setSSLSocketFactory(sc.getSocketFactory());
			conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
			 
			conn.setDoOutput(true);
			// 设置是否从httpUrlConnection读入，默认情况下是true;
			conn.setDoInput(true);

			// Post 请求不能使用缓存
			conn.setUseCaches(false);

			// 设定传送的内容类型是可序列化的java对象
			// (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
			conn.setRequestProperty("Content-type",
					"application/x-java-serialized-object");
			//conn.setFixedLengthStreamingMode(0);// GET不能要这句
			conn.setConnectTimeout(30000);  
			conn.setReadTimeout(30000);  
			conn.connect();
			cookie = conn.getHeaderField("Set-Cookie");
			String[] cookies = cookie.split(";");
			cookie = cookies[0];
		} catch (Exception e) {
			System.out.println("errorrrrrrrrrrrrrrrr:  url:"+url+" : "+ e.getMessage());
			 
		}
		System.out.println("cookie = " + cookie);
		return cookie;
	}
	private static String getCookieByHttp(String url,Proxy proxy) {
		String cookie = "";
		try {
			URL console = new URL(url);
			HttpURLConnection conn = null;
			if(proxy!=null){
				conn = (HttpURLConnection) console
						.openConnection(proxy);
			}else{
				conn = (HttpURLConnection) console
						.openConnection();
			}
			conn.setRequestMethod("GET");
			 	conn.setRequestProperty("Content-type",
					"application/x-java-serialized-object");

			// 设定请求的方法为"POST"，默认是GET
			conn.setConnectTimeout(30000);  
			conn.setReadTimeout(30000);  
			conn.connect();
			System.out.println("返回结果：" + conn.getResponseMessage());
			cookie = conn.getHeaderField("Set-Cookie");
			String[] cookies = cookie.split(";");
			cookie = cookies[0];
		} catch (Exception e) {
			System.out.println("errorrrrrrrrrrrrrrrr:  url:"+url+" : "+ e.getMessage());
			 
		}
		System.out.println("cookie = " + cookie);
		return cookie;
	}
	
	@SuppressWarnings("unused")
	private static String getHttpRequestContentByGet2(String url) {
		String str = "", tmp;
		try { 
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new URL(url).openStream()));
			while ((tmp = br.readLine()) != null) {
				str += tmp + "\r\n";
			}
		} catch (Exception e) {
			System.out.println("errorrrrrrrrrrrrrrrr:  url:"+url+" : "+ e.getMessage());
			 
		}
		return str;
	}

	private static String getHttpsRequestContentByGet(String url,boolean needcookie,String cookie,Proxy proxy) { 
		System.out.println("in https get");
		String returnbody = "";
		try {
			SSLContext sc = SSLContext.getInstance("SSL");

			// 指定信任https
			sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
					new java.security.SecureRandom());
			URL console = new URL(url);
			HttpsURLConnection conn = (HttpsURLConnection) console
					.openConnection();
			conn.setSSLSocketFactory(sc.getSocketFactory());
			conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
			conn.setRequestMethod("GET");
			conn.setSSLSocketFactory(sc.getSocketFactory());
			conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
			 
			conn.setDoOutput(true);
			// 设置是否从httpUrlConnection读入，默认情况下是true;
			conn.setDoInput(true);

			// Post 请求不能使用缓存
			conn.setUseCaches(false);

			// 设定传送的内容类型是可序列化的java对象
			// (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
			conn.setRequestProperty("Content-type",
					"application/x-java-serialized-object");
			//conn.setFixedLengthStreamingMode(0);// GET不能要这句
			conn.setConnectTimeout(30000);  
			conn.setReadTimeout(30000);  
			if(needcookie){
				conn.setRequestProperty("Cookie", cookie);// 有网站需要将当前的session id一并上传
			}
			conn.connect();
			// conn.
			System.out.println("返回结果：" + conn.getResponseMessage());

			InputStream is = conn.getInputStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			String curLine = "";
			while ((curLine = reader.readLine()) != null) {
				returnbody += curLine;
			}
			is.close();
		} catch (Exception e) {
			System.out.println("errorrrrrrrrrrrrrrrr:  url:"+url+" : "+ e.getMessage());
			 
		//	e.printStackTrace();
		}
		return returnbody;
	}
	
	private static String getHttpsRequestContentByGetStatus(String url,boolean needcookie,String cookie) { 
		System.out.println("in https post status");
		try {
			SSLContext sc = SSLContext.getInstance("SSL");

			// 指定信任https
			sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
					new java.security.SecureRandom());
			URL console = new URL(url);
			HttpsURLConnection conn = (HttpsURLConnection) console
					.openConnection();
			conn.setSSLSocketFactory(sc.getSocketFactory());
			conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
			conn.setRequestMethod("GET");
			conn.setSSLSocketFactory(sc.getSocketFactory());
			conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
			 
			conn.setDoOutput(true);
			// 设置是否从httpUrlConnection读入，默认情况下是true;
			conn.setDoInput(true);

			// Post 请求不能使用缓存
			conn.setUseCaches(false);

			// 设定传送的内容类型是可序列化的java对象
			// (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
			conn.setRequestProperty("Content-type",
					"application/x-java-serialized-object");
			conn.setConnectTimeout(30000);  
			conn.setReadTimeout(30000);  
			if(needcookie){
				conn.setRequestProperty("Cookie", cookie);// 有网站需要将当前的session id一并上传
			}
			
			conn.connect();
			System.out.println("返回结果：" + conn.getResponseCode());//.getResponseMessage());
			return conn.getResponseCode()+"";
		} catch (Exception e) {
			System.out.println("errorrrrrrrrrrrrrrrr:  url:"+url+" : "+ e.getMessage());
			 
		//	e.printStackTrace();
		}
		return "1000";
	}
	
	private static String getHttpsRequestContentByPostStatus(String url,boolean needcookie,String cookie,Proxy proxy) {
		System.out.println("in https post");
		String returnbody = "";
		try {
			SSLContext sc = SSLContext.getInstance("SSL");

			// 指定信任https
			sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
					new java.security.SecureRandom());
			URL console = new URL(url);
			HttpsURLConnection conn = null;
			
			
			if(proxy!=null){
				conn = (HttpsURLConnection) console
						.openConnection(proxy);
			}else{
				conn = (HttpsURLConnection) console
						.openConnection();
			}
			conn.setSSLSocketFactory(sc.getSocketFactory());
			conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
			conn.setRequestMethod("POST");
			conn.setSSLSocketFactory(sc.getSocketFactory());
			conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
			System.out.println("auth===="+authorization);
			conn.setRequestProperty("Authorization", authorization);
			conn.setDoOutput(true);
			// 设置是否从httpUrlConnection读入，默认情况下是true;
			conn.setDoInput(true);
			conn.setConnectTimeout(30000);  
			conn.setReadTimeout(30000);  
			
			
		 
			// Post 请求不能使用缓存
			
			conn.setUseCaches(false);

			// 设定传送的内容类型是可序列化的java对象
			// (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
			conn.setRequestProperty("Content-type",
					"application/x-java-serialized-object");
			conn.setFixedLengthStreamingMode(0);// 必须要这句
			if(needcookie){
				conn.setRequestProperty("Cookie", cookie);// 有网站需要将当前的session id一并上传
			}
			conn.connect();
			System.out.println("返回结果：" + conn.getResponseCode());//.getResponseMessage());
			return conn.getResponseCode()+"";
		} catch (Exception e) {
			System.out.println("errorrrrrrrrrrrrrrrr:  url:"+url+" : "+ e.getMessage());
			e.printStackTrace();
			 
		}
		return returnbody;
	}
	public static String encode(String str, String charset) throws UnsupportedEncodingException {
	    Pattern p = Pattern.compile(zhPattern);
	    Matcher m = p.matcher(str);
	    StringBuffer b = new StringBuffer();
	    while (m.find()) {
	      m.appendReplacement(b, URLEncoder.encode(m.group(0), charset));
	    }
	    m.appendTail(b);
	    return b.toString();
	  }
	private static URLConnection openConnection(URL localURL) throws IOException {
        URLConnection connection;
        connection = localURL.openConnection();
        return connection;
    }
	
	public static void test() throws Exception{
		String url = "http://sso.testin.cn/user.action?op=Login.login";
		String from  = "domain=real.testin.cn&email=xuwei_730@163.com&pwd=730717";
		URL localURL = new URL(url);
        
        URLConnection connection = openConnection(localURL);
        HttpURLConnection httpURLConnection = (HttpURLConnection)connection;
        
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setUseCaches(false);  
        //application/x-www-form-urlencoded
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
        httpURLConnection.setRequestProperty("Content-Length", "56");
      //  httpURLConnection.setRequestProperty("User-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.132 Safari/537.36");
    //  httpURLConnection.setRequestProperty("Content-Type", "application/x-java-serialized-object");
    //   httpURLConnection.setRequestProperty("Content-Type", "x-www-form-urlencoded");
        httpURLConnection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        
         
      
       OutputStream outputStream = null;
       OutputStreamWriter outputStreamWriter = null;
       outputStream = httpURLConnection.getOutputStream();
       outputStreamWriter = new OutputStreamWriter(outputStream);
       
       outputStreamWriter.write(from.toString());
       outputStreamWriter.flush();
       System.out.println("headers=" + httpURLConnection.getHeaderFields());
	}
	
	public static Proxy getProxy(String ip,String port){
		Proxy proxy = null;
		try{
			
			if(port!=null&& !port.trim().equals("")){
				System.out.println("set proxy ,host:"+ip+"; port:"+port);
				proxy = new Proxy(Proxy.Type.HTTP,   
			            new InetSocketAddress(ip, Integer.parseInt(port)));
			}else{
				proxy = null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return proxy;
	}
	public static void main(String[] args) throws Exception {
		test();
		//q=tomcat+中国
		String url  ="http://172.16.113.12:8080/solr/web-china/select?q=tomcat+%E4%B8%AD%E5%9B%BD&wt=json&indent=true&collection=web-china,web-foreign,noweb-china,noweb-foreign&sort=id+asc&cursorMark=*";
		String content = getHttpRequestContentByGetWithoutProxy(url);
		System.out.println("content = " + content);
	}

}


