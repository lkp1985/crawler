package com.lkp.project.baidulvyou;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class ProxyUtil {
	public static String[] ips = {"115.159.126.103:10001","123.206.194.143:10001","115.159.29.75:10001","119.29.241.205:10001",
			"119.29.242.109:10001","119.29.244.218:10001","119.29.245.126:10001","119.29.244.202:10001","119.29.245.69:10001",
			"119.29.245.90:10001","119.29.245.193:10001","123.206.198.232:10001","123.206.199.136:10001","123.206.198.188:10001",
			"115.29.165.122:10001","101.200.167.178:8098","104.238.147.85:9090","104.131.159.210:25","12.163.79.167:8000","192.225.175.81:3389","40.84.197.113:3000"};//备用104.131.159.210:25 

	//用于记录每个代理的访问次数与最近一次的访问时间
	static Map<String,Long> proxyTime = new HashMap<String,Long>();
	static Map<String,Integer> proxyNum = new HashMap<String,Integer>();
	static int maxRequestNum = 10;//每个IP每分钟最多访问30次
	static int intervaltime = 60000;//间隔60秒
	
	static Queue<Proxy> proxyQueue = null;
	public static void init(){
		proxyQueue = new ArrayBlockingQueue<Proxy>(100);
		for(String ipport: ips){
			String ip = ipport.split("\\:")[0];
			String port = ipport.split("\\:")[1];
			Proxy proxy  = new Proxy(Proxy.Type.HTTP,   
		            new InetSocketAddress(ip, Integer.parseInt(port)));
			proxyQueue.add(proxy);
		}
	}
	
	public static Proxy getProxy(){
		if(proxyQueue ==null){
			init();
		}
		while(proxyQueue.isEmpty()){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Proxy proxy = proxyQueue.poll();
		if(proxy == null){
			return null;
		}
		String ipport = getProxyHost(proxy);
		
		if(proxyNum.containsKey(ipport)){
			int num = proxyNum.get(ipport);
			if(num< maxRequestNum){
				proxyNum.put(ipport, num+1);
				proxyTime.put(ipport, System.currentTimeMillis());
				return proxy;
			}else{//已经达到最大次数，再判断间隔时间是否已经有1分钟
				if(proxyTime.containsKey(ipport)){
					long lasttime = proxyTime.get(ipport);
					if(System.currentTimeMillis() - lasttime > intervaltime){
						proxyNum.put(ipport, 1);
						proxyTime.put(ipport, System.currentTimeMillis());
						return proxy;
					}else{
						proxyQueue.offer(proxy);
						return null;
					}
				}else{ 
					proxyTime.put(ipport, System.currentTimeMillis());
					proxyQueue.offer(proxy);
					 
					return null;
				}
			}
		}else{
			proxyNum.put(ipport, 1);
			proxyTime.put(ipport, System.currentTimeMillis());
			return proxy;
		}
	}
	
	public static String getProxyHost(Proxy proxy){
		InetSocketAddress    address =(InetSocketAddress)proxy.address();
		String ip = address.getHostString();
		String port = address.getPort()+"";
		String ipport = ip+":"+port;
		return ipport;
	}
	 
	public static void main(String[] args) {
		
		init();
		while(true){
			Proxy proxy = getProxy();
			if(proxy == null){
				continue;
			}
			new Thread(new TestThread(proxy)).start();
		}
	}
}

class TestThread implements Runnable{
	Proxy proxy ;
	public TestThread(Proxy proxy){
		this.proxy = proxy;
	}
	@Override
	public void run() {
		if(proxy!=null){
			System.out.println(proxy);
			ProxyUtil.proxyQueue.offer(this.proxy);
		}
		
	}
	
}
