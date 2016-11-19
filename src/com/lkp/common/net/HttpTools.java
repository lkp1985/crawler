package com.lkp.common.net;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class HttpTools {
	public static void main(String[] args) {
		try {
			
			String body = getHttpsResponse("http://www.court.gov.cn/zgcpwsw/", "utf-8");
			System.out.println("body="+body);
			body = getHttpResponse("http://channel.jd.com/electronic.html", "gbk",null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String postHttpResponse(String url,String charset) throws Exception{
		String body = "";
		HttpClient httpclient = HttpClientBuilder.create().build();
		 
		HttpPost httppost = new HttpPost(url);
		httppost.setHeader("Cookie", "CookieName=CookieValue; CookieName=CookieValue; __jsluid=5f57f776f9d15d58a6f91f876053dd92; ASP.NET_SessionId=srovfd0azciqhea5zjmbvb0h; __jsl_clearance=1455890167.373|0|wot45ROwXzxIcZasvsWAvHPaftE%3D; _gsref_1241932522=http://www.court.gov.cn/zgcpwsw/; _gscu_1241932522=557987759brzvg16; _gscs_1241932522=55886811o485v616|pv:21; _gscbrs_1241932522=1");
		httppost.setHeader("Accept","XMLHttpRequest"); 
		httppost.setHeader("X-Requested-With","*/*"); 
		httppost.setHeader("Referer","http://www.court.gov.cn/zgcpwsw/List/List?sorttype=1&conditions=searchWord+3+AJLX++%E6%A1%88%E4%BB%B6%E7%B1%BB%E5%9E%8B:%E8%A1%8C%E6%94%BF%E6%A1%88%E4%BB%B6");
		httppost.setHeader("Proxy-Connection","keep-alive");
		httppost.setHeader("Upgrade-Insecure-Requests","1");
		httppost.setHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
		httppost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.82 Safari/537.36");
		httppost.setHeader("Accept-Encoding","gzip, deflate, sdch");
		httppost.setHeader("Accept-Language","zh-CN,zh;q=0.8,en;q=0.6");
	
		
		/*
		 * Param:案件类型:赔偿案件
Index:1
Page:5
Order:法院层级
Direction:asc*/
//
//		JSONObject json = new JSONObject();
//		json.put("Param", "案件类型:赔偿案件");
//		json.put("Index", "1");
//		json.put("Order", "法院层级");
//		json.put("Direction","asc");
//		
//		StringEntity s = new StringEntity(json.toString());
//		httppost.setEntity(s);
		
		
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
        nvps.add(new BasicNameValuePair("Param", "案件类型:赔偿案件"));  
        nvps.add(new BasicNameValuePair("Index", "1"));    
        nvps.add(new BasicNameValuePair("Order", "法院层级"));    
        nvps.add(new BasicNameValuePair("Direction","asc"));  
        httppost.setEntity(new UrlEncodedFormEntity(nvps));
		
		 
		HttpResponse response = httpclient.execute(httppost);
		
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream instream = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					instream, charset));
			String line = reader.readLine();
			
			while (line != null) {
				body += line;
				line = reader.readLine();
			}
		}
		return body; 
	}

	public static String getHttpResponse(String url,String charset,String cookie) throws Exception{
		String body = "";
		HttpClient httpclient = HttpClientBuilder.create().build();
		 
		HttpGet httpget = new HttpGet(url);
		if(cookie!=null){
			httpget.setHeader("Cookie", cookie);
		}
		httpget.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		httpget.setHeader("Upgrade-Insecure-Requests","1");
		httpget.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.82 Safari/537.36");
		httpget.setHeader("Accept-Encoding","gzip, deflate, sdch");
		httpget.setHeader("Accept-Language","zh-CN,zh;q=0.8,en;q=0.6");
		/**
		  * Proxy-Connection: keep-alive
Cache-Control: max-age=0
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp, ;q=0.8
Upgrade-Insecure-Requests: 1
User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.82 Safari/537.36
Accept-Encoding: gzip, deflate, sdch
Accept-Language: zh-CN,zh;q=0.8,en;q=0.6
Cookie: CookieName=CookieValue; CookieName=CookieValue; __jsluid=5f57f776f9d15d58a6f91f876053dd92; __jsl_clearance=1455886593.121|0|4u5X7Q6mF87y5PPyMZFPlaS7d2A%3D; 
		 
		/**
	  *  HttpGet resumeSearchGet = new HttpGet(resumeSearchUrl);
            resumeSearchGet.setHeader("Referer", "http://ehire.51job.com/Navigate.aspx?ShowTips=11&PwdComplexity=N");
            resumeSearchGet.setHeader("Accept", "image/gif, image/jpeg, image/pjpeg, application/x-shockwave-flash, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, application/xaml+xml, application/x-ms-xbap, application/x-ms-application, ");
            resumeSearchGet.setHeader("Accept-Language","zh-cn");
            resumeSearchGet.setHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; InfoPath.2; .NET4.0C; .NET4.0E)");
             
	  */
		  
		HttpResponse response = httpclient.execute(httpget);
	
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream instream = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					instream, charset));
			String line = reader.readLine();
			
			while (line != null) {
				body += line; 
				line = reader.readLine();
			}
		}
		return body;
	}
	public static String getHttpsResponse(String url,String charset) throws Exception{
		String body = "";
		HttpClient httpclient =  createSSLClientDefault();
		HttpGet httpget = new HttpGet(url);
		HttpResponse response = httpclient.execute(httpget);
		for(Header header : response.getAllHeaders()){
			System.out.println("name="+header.getName()+";value="+header.getValue());
		}
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream instream = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					instream, charset));
			String line = reader.readLine();
			
			while (line != null) {
				body += line;
				line = reader.readLine();
			}
		}
		return body;
	}
	public static void testHttp() throws Exception {
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpGet httpget = new HttpGet("http://channel.jd.com/electronic.html");
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream instream = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					instream, "gbk"));
			String line = reader.readLine();
			while (line != null) {
				System.out.println(line);
				line = reader.readLine();
			}
		}
	}

	public static void testHttps() throws Exception {
		HttpClient httpclient =  createSSLClientDefault();
		HttpGet httpget = new HttpGet("http://www.court.gov.cn/zgcpwsw/");
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream instream = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					instream, "utf-8"));
			String line = reader.readLine();
			while (line != null) {
				System.out.println(line);
				line = reader.readLine();
			}
		}
	}

	public static CloseableHttpClient createSSLClientDefault() {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(
					null, new TrustStrategy() {
						// 淇′换鎵?湁
						public boolean isTrusted(X509Certificate[] chain,
								String authType) throws CertificateException {
							return true;
						}
					}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					sslContext);
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
}
