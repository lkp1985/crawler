package com.lkp.project.meituan;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.lkp.common.net.HttpProxy;

import net.sf.json.JSONObject;

public class QYSDMain {
	private static final LinkedBlockingDeque<Runnable> taskQueue = new LinkedBlockingDeque<Runnable>(10);
	private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(5,10, 10, TimeUnit.SECONDS, taskQueue,
			new ThreadPoolExecutor.CallerRunsPolicy());
	static List<String> provinceList = new ArrayList<String>();
	static List<String> zczbList = new ArrayList<String>();
	static List<String> qytype = new ArrayList<String>();
	static List<String> qystatus = new ArrayList<String>();
	
	public static void init(){
		String[] provinces = {"110000","120000","130000","140000","150000","210000","220000","230000","310000","320000","330000","340000","350000","360000","370000","410000","420000","430000","440000","450000","460000","500000","510000","520000","530000","540000","610000","620000","630000","640000","650000","710000","810000","820000"};
		provinceList = Arrays.asList(provinces);
		
		zczbList.add("*,100");
		zczbList.add("100,500");
		zczbList.add("500,1000");
		zczbList.add("1000,5000");
		zczbList.add("5000,*");
		
		qytype.add("1000");
		qytype.add("2000");
		qytype.add("3000");
		qytype.add("4000");
		qytype.add("5000");
		qytype.add("6000");
		qytype.add("7000");
		qytype.add("8000");
		qytype.add("9000");
		
		qystatus.add("1");
		qystatus.add("2");
		qystatus.add("3");
		qystatus.add("4");
		qystatus.add("5");
		
		
		
	}
	public static void main(String[] args) {
		int num = 0;
		init();
//		while(true){
//			Proxy proxy = ProxyUtil.getProxy();
//			while(proxy==null){
//				proxy = ProxyUtil.getProxy();
//			}
//			test(proxy);
//			ProxyUtil.proxyQueue.offer(proxy);
//			num ++;
//			System.out.println("num="+num);
//		}
		MysqlUtil.init();
		while(true){
			//{"keywords":"游戏","pageNo":1,"param":"","pvn":110000,"rc":"5000,*","grt":"4000","grs":"1"}
			JSONObject paramsObj =new JSONObject();
			String condition = SearchTerm.getSearchTerm();
			paramsObj.put("rgs", condition);//搜索经营范围用rgs,搜索关键词用keywords
			for(String province: provinceList){
				paramsObj.put("pvn", province);
				for(String zczb : zczbList){
					paramsObj.put("rc", zczb);
					
					for(String type : qytype){
						paramsObj.put("grt", type);
						for(String status : qystatus){
							paramsObj.put("grs", status);
							
							
							Proxy proxy = ProxyUtil.getProxy();
							while(proxy==null){
								try {
									Thread.sleep(100);
									proxy = ProxyUtil.getProxy();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} 
							}
							executor.execute(new RequestThread(proxy,paramsObj.toString()));
							ProxyUtil.proxyQueue.offer(proxy);
						}
					}
				}
			}
			
			
			
		//	executor.execute(new RequestThread(proxy));
		}
	}
	
	public static void test(Proxy proxy){
		Map<String,String> headParams = new HashMap<String,String>();
		headParams.put("Accept", "application/json"); // 设置接收数据的格式  
		headParams.put("Content-Type", "application/json"); // 设置发送数据的格式  
		String url = "http://www.qysudu.com/qysuduApi/company/search";
		String condition = SearchTerm.getSearchTerm();
		System.out.println(Thread.currentThread().getName()+" start ,keywords= 	" +condition);
		JSONObject json = new JSONObject();
		json.put("keywords",condition);  
		json.put("pvn", "110000");
		String  body = HttpProxy.getHttpRequestContentByPost(url, proxy, json.toString(), headParams,false);
		if(body!=null && body.contains("frequentVisit")){
			int num = ProxyUtil.proxyNum.get(ProxyUtil.getProxyHost(proxy));
			System.out.println(proxy +" frequentVisit,num="+num);
		}else{
			System.out.println("body2="+body);
		}
	}
}
