package com.lkp.project.tianyancha;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lkp.common.net.HttpProxy;

import net.sf.json.JSONObject;

public class QYSDUtil {
	static Map<String,Long> proxyTime = new HashMap<String,Long>();
	static Map<String,Integer> proxyNum = new HashMap<String,Integer>();
	static Map<String,Integer> proxyNumAll = new HashMap<String,Integer>();
	static 	Map<String, String> params = new HashMap<String, String>();
	static Set<String> unvalidIp = new HashSet<String>();
	private static final Log LOGGER = LogFactory.getLog(QYSDUtil.class);
	
	public static String getCompanyList(String key){
		try{
			Proxy proxy  =getProxy();
			String url = "http://www.qysudu.com/qysuduApi/company/detail?id=";
			
			
		}catch(Exception e){
			
		}
		return "";
	}
	public static String getJYScope(String  id){
		try{
			if(id==null || id.trim().length() ==0){
				return "";
			}
			String url = "http://www.qysudu.com/qysuduApi/company/detail?id="+id;
			System.out.println("等待请求条件");
			Proxy proxy = null;
			String ipport = "";
			while(true){
				proxy = HttpProxy.getProxy();
				InetSocketAddress    address =(InetSocketAddress)proxy.address();
				String ip = address.getHostString();
				String port = address.getPort()+"";
				ipport = ip+":"+port;
//				if(unvalidIp.contains(ipport)){
//					continue;
//				}
				if(!proxyNum.containsKey(ipport)){
					proxyNum.put(ipport, 1);
					proxyTime.put(ipport, System.currentTimeMillis());
					break;
				}
				int num = proxyNum.get(ipport);
				if(num>=29){
					long time = proxyTime.get(ipport);
					if(System.currentTimeMillis() - time < 61000){
						continue;
					}else{
						proxyNum.put(ipport, 0);
						proxyTime.put(ipport, System.currentTimeMillis());
						break;
					}
				}else{
					proxyNum.put(ipport, num+1);
					proxyTime.put(ipport, System.currentTimeMillis());
					break;
				}
			}
			System.out.println("proxy="+proxy);
			if(!proxyNumAll.containsKey(ipport)){
				proxyNumAll.put(ipport, 1);
			}else{
				int num = proxyNumAll.get(ipport);
				proxyNumAll.put(ipport, num+1);
			}
			String body = 	HttpProxy.getHttpRequestContentByGet(url,proxy , params,false);
			
			if(body!=null && body.length()>100){
				JSONObject obj = JSONObject.fromObject(body).getJSONObject("company");
				String scope = obj.getString("rgs");
				System.out.println("scope="+scope);
				System.out.println("请求条件成功");
				return scope;
			}else if(body!=null && body.contains("frequentVisit")){
				System.out.println("请求频繁,"+proxy+" ," + body);
				unvalidIp.add(ipport);
				LOGGER.error(proxy+" 请求频繁");
			}
			else{
				System.out.println("请求失败,"+proxy+" ," + body);
				System.out.println("error body="+body);
				//unvalidIp.add(ipport);
				return "";
			}
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return "";
	}
	
	public static String getJYScope2(String  id){
		try{
			String url = "http://www.qysudu.com/qysuduApi/company/detail?id="+id;
			System.out.println("等待请求条件 ");
			Proxy proxy = null;
			String ipport = "";
			proxy = getProxy();
			String body = 	HttpProxy.getHttpRequestContentByGet(url,proxy , params,false);
			System.out.println("请求条件成功");
			if(body!=null && body.length()>100){
				JSONObject obj = JSONObject.fromObject(body).getJSONObject("company");
				String scope = obj.getString("rgs");
				System.out.println("scope="+scope);
				return scope;
			}else if(body!=null && body.contains("frequentVisit")){
				System.out.println(proxy+" " + body);
			}
			else{
				System.out.println("error body ="+body);
				return "";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	public static void init(){

//		 for(String ip : HttpProxy.ips2){
//			 proxyTime.put(ip, System.currentTimeMillis()); 
//			 proxyNum.put(ip,0);
//			 proxyNumAll.put(ip, 0);
//		 }
		params.put("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
		// params.put("Accept-Encoding","gzip, deflate, sdch");
		params.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		// params.put("Host","s.weibo.com");
		// params.put("Referer",
		// "http://www.court.gov.cn/zgcpwsw/List/List?sorttype=1&conditions=searchWord+4+AJLX++%E6%A1%88%E4%BB%B6%E7%B1%BB%E5%9E%8B:%E8%B5%94%E5%81%BF%E6%A1%88%E4%BB%B6");
		params.put("Cache-Control", "max-age=0");
		params.put("Connection", "keep-alive");
		// conn.setInstanceFollowRedirects(false);
		// params.put("If-Modified-Since","Wed, 30 Dec 2015 09:01:37 GMT");
		// params.put("If-None-Match","");
		// params.put("If-Modified-Since","3f5dfb0e042d11");
		params.put("Upgrade-Insecure-Requests", "1");
		params.put("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.82 Safari/537.36");
	}
	
	public static Proxy getProxy(){
		try{
			Proxy proxy =  null;
			String ipport = null;
			while(true){
				proxy = HttpProxy.getProxy2();
				InetSocketAddress    address =(InetSocketAddress)proxy.address();
				String ip = address.getHostString();
				String port = address.getPort()+"";
				ipport = ip+":"+port;
//				if(unvalidIp.contains(ipport)){
//					continue;
//				}
				if(!proxyNum.containsKey(ipport)){
					proxyNum.put(ipport, 1);
					proxyTime.put(ipport, System.currentTimeMillis());
					break;
				}
				int num = proxyNum.get(ipport);
				if(num>=55){
					long time = proxyTime.get(ipport);
					if(System.currentTimeMillis() - time < 61000){
						continue;
					}else{
						proxyNum.put(ipport, 0);
						proxyTime.put(ipport, System.currentTimeMillis());
						break;
					}
				}else{
					proxyNum.put(ipport, num+1);
					proxyTime.put(ipport, System.currentTimeMillis());
					break;
				}
			}
			if(!proxyNumAll.containsKey(ipport)){
				proxyNumAll.put(ipport, 1);
			}else{
				int num = proxyNumAll.get(ipport);
				proxyNumAll.put(ipport, num+1);
			}
			System.out.println(ipport+" num :"+proxyNum.get(ipport)+"; all "+proxyNumAll.get(ipport));
			return proxy;
		}catch(Exception e){
			
		}
		return null;
	}
	public static void main(String[] args) {
		
//		Proxy proxy = HttpProxy.getProxy();
//		System.out.println("proxy="+proxy);
//		InetSocketAddress    address =(InetSocketAddress)proxy.address();
//		System.out.println("ip="+address.getHostString());
//		Proxy proxy2 = HttpProxy.getProxy();
//		System.out.println("proxy2="+proxy);
//		Proxy proxy3 = HttpProxy.getProxy();
//		System.out.println(proxy==proxy2);
		init();
		String condition = "210244000033522";
		System.out.println(getJYScope(condition));
		
		
	}

	
	
}
