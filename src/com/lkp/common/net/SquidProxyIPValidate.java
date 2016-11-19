/*
 * Copyright (c) SHADANSOU 2014 All Rights Reserved
 *
 */
package com.lkp.common.net;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.Proxy;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;

import com.httpclient.utils.HttpUtils;
import com.httpclient.utils.ttc;

/**
 * squid ip 代理验证
 * @author lkp
 *
 */
public class SquidProxyIPValidate {
	private static final Log logger = LogFactory
			.getLog(SquidProxyIPValidate.class); 
	 private static final LinkedBlockingDeque<Runnable> taskQueue = new LinkedBlockingDeque<Runnable>(10);
	private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(15, 30, 60, TimeUnit.SECONDS, taskQueue,
               new ThreadPoolExecutor.CallerRunsPolicy());

 
 
	public static void main(String[] args) {
		try{
			String path = "/home/sds/data/idexport.txt";
			path = "d://data//logs//proxyid3.txt";
			BufferedReader br = new BufferedReader(new FileReader(path));///home/sds/data/idexport.txt
			String line = "";
			int i = 0;
			while((line=br.readLine())!=null){
			//	line = "185.89.216.101||32787";
				String[] lines = line.split("\\|");
				if(lines.length==3){
					String ip = lines[0];
					String port = lines[2]; 
					HttpHost proxy = HttpProxy.getProxy(ip	,port); 
					if(i++%100 ==0){
						System.out.println("i="+i);
					}
					executor.execute( new SquidProxyIPValidate().new RunThread(proxy));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		 
	}
	
	
	class RunThread implements Runnable{
		HttpHost proxy;
		public RunThread(HttpHost proxy){
			this.proxy = proxy;
		}
		@Override
		public void run() { 
			try{
				boolean flag = true;
				for(int i=0;i<1;i++){
					//659002058006547
					HttpUtils.proxy = proxy;
					String body = ttc.getDetail("1275375711");
							//HttpProxy.getHttpRequestContentByGet("http://www.tianyancha.com/", proxy,null);
					if(body.length()<100){
						flag= false;
						break;
					}
					System.out.println("body="+body);
				}
				if(flag)
				System.out.println("proxy======="+proxy);
			}catch(Exception e){
				
			}
		} 
	}
}


