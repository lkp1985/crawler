package com.lkp.project.company;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.lkp.common.net.HttpProxy;

import net.sf.json.JSONObject;

public class RequestThread implements Runnable{

	
	Proxy proxy;
	int time = 5;
	String url = "http://58.56.21.242:88/qysuduApi/company/search";
	public RequestThread(Proxy proxy) {
		// TODO Auto-generated constructor stub
		this.proxy = proxy;
	}
	@Override
	public void run() {
		System.out.println("new request start,proxy="+proxy);
		int requestTimes = 0;
		while(true){
			try{
				while(MainThread.urlqueue.isEmpty()){
					Thread.sleep(3000);
				}
				String params = MainThread.urlqueue.poll();
				
				for(int i=0;i<time ; i++){
					requestTimes ++;
					 
					JSONObject json = new JSONObject();
					json.put("name", "公司"); 
					json.put("pageNo", 6);
					json.put("param", "13467694635");
					json.put("grs", "2");
					json.put("grt", "1000");
					json.put("rc", "*,100");
					json.put("pvn", 430000);
					Map<String,String> headParams = new HashMap<String,String>();
					headParams.put("Content-Type", "application/json");
					headParams.put("Content-Length", "99");
					headParams.put("User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36");
					headParams.put("Host","www.qysudu.com");
					headParams.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
					headParams.put("access_token","06e2c1a0769d42adadf1188a5c45d988");
					headParams.put("app_key","f139b0d83ae843dfaaff750198b48770");
					headParams.put("userInfo","355c07c4d28e4af88d9b41abc14a3e41");
					headParams.put("host","58.56.21.242:88");
					System.out.println(Thread.currentThread() +",MainThread.proxyRequestTimes.get(key)="+MainThread.proxyRequestTimes.get(proxy));
					if(MainThread.proxyRequestTimes.get(proxy)>=50){
						
						while((System.currentTimeMillis() -
								MainThread.proxyLastRequestTime.get(proxy))<60000 ||
								MainThread.proxyRequestTimes.get(proxy)>=50){
							Thread.sleep(10000);
						}
					}
					System.out.println("begin request");
					MainThread.proxyLastRequestTime.put(proxy, System.currentTimeMillis());
					MainThread.proxyRequestTimes.put(proxy, 1);
					String ss = HttpProxy.getHttpRequestContentByPost(url, proxy, json.toString(), headParams);
					if(ss.length()>100){
						System.out.println("proxy="+proxy+";params = " +params+";ss="+ss);
						break;
					}
				}
				if(requestTimes>=59){
					Thread.sleep(60000);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
		
	}

	public static void main(String[] args) {
		String url = "http://58.56.21.242:88/qysuduApi/company/search";
		Proxy proxy   = new Proxy(Proxy.Type.SOCKS,   
	            new InetSocketAddress("114.108.234.191",5000 ));
		JSONObject json = new JSONObject();
		json.put("name", "公司"); 
		json.put("pageNo", 6);
		json.put("param", "13467694635");
		json.put("grs", "2");
		json.put("grt", "1000");
		json.put("rc", "*,100");
		json.put("pvn", 430000);
		Map<String,String> headParams = new HashMap<String,String>();
		headParams.put("Content-Type", "application/json");
		headParams.put("Content-Length", "99");
		headParams.put("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36");
		headParams.put("Host","www.qysudu.com");
		headParams.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");

		String ss = HttpProxy.getHttpRequestContentByPost(url, proxy, json.toString(), headParams);
		if(ss.length()>100){
			System.out.println("ss="+ss);
			 
		}

	}
}
