package com.lkp.proxyutils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import com.lkp.common.net.HttpProxy;
import com.lkp.solr.SolrUtils;

public class ProxyUtil {
	static List<Proxy> validProxyList = new CopyOnWriteArrayList<>();
	static List<Proxy> unvalidProxyList = new CopyOnWriteArrayList<>(); 
	
	static String proxyids = "206.251.244.74||9090,159.203.222.32||2222,52.30.166.188||9999,162.244.32.131||10443,206.251.244.73||9090,50.97.251.245||9080,50.97.251.245||9090,199.180.134.162||9999,5.9.126.130||32768,185.22.52.6||9000,130.180.68.6||8000,177.0.214.166||9080,31.148.219.150||10443,213.95.148.66||9160,213.95.148.67||9160,5.9.105.47||32768,81.4.120.227||10243,190.143.136.82||80,114.108.234.191||5000,195.123.181.11||443,125.192.102.218||8080,81.4.120.227||10000,177.0.214.198||9080,189.72.104.125||9090,81.4.120.227||9999,144.76.125.51||9090,5.154.190.189||10443,5.154.190.199||10443,78.181.209.104||81,188.120.234.1||10000,187.5.135.95||9080,81.4.120.227||10443,49.129.189.114||8080,79.165.180.243||443,213.95.148.68||9090,134.168.55.226||8080,63.223.108.12||9999,62.108.36.173||443,";
	
	
	public static void initProxy(){
		while(true){
			try{
				BufferedReader br = new BufferedReader(new FileReader("D:\\workspace_lkp\\crawler_task\\ip.txt"));
				String line = null;
				proxyids = "";
				while((line=br.readLine())!=null){
					proxyids += line+",";
				}
				String[] ids = proxyids.split(",");
				System.out.println("ids.size="+ids.length);
				for(String id : ids){
					String ip  = id.split(":")[0];
					String port = id.split(":")[1];
					Proxy proxy  = new Proxy(Proxy.Type.HTTP,   
				            new InetSocketAddress(ip, Integer.parseInt(port)));
					new Thread(new ValidProxyThread(validProxyList,unvalidProxyList,proxy)).start(); 
				}
				
				while(validProxyList.size()+unvalidProxyList.size() < ids.length){
					System.out.println("all size = " + (validProxyList.size()+unvalidProxyList.size()));
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("valid proxy:"+validProxyList);
				System.out.println("unvalid proxy:"+unvalidProxyList);
			
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public static Proxy getProxy(){
		int size = validProxyList.size();
		if(size<=0){
			return null;
		}
		int index = new Random().nextInt(size);
		Proxy proxy = validProxyList.remove(index);
		return proxy;
	}
	public static Proxy getTorProxyFromSolr(){
		SolrQuery query = new SolrQuery();
		query.setQuery("appservice:tor-socks");
		query.setRows(100);
		  query.setParam("collection", "finger-us,finger-us-new,finger-foreign,finger-foreign-new");
	/*	SolrServer server = 
				
				new HttpSolrServer("http://172.16.111.10:8080/solr");*/
		SolrDocumentList docList = SolrUtils.getDocs(query);
		try {
			//docList = server.query(query).getResults();
			 
			System.out.println("docList.size="+docList.size());
			for(SolrDocument doc : docList){
					String id = doc.getFieldValue("id").toString();
					proxyids+=	id+",";
					/*String ip  = id.split("\\|\\|")[0];
					String port = id.split("\\|\\|")[1];
					System.out.println("port="+port+";ip="+ip);
					Proxy proxy  = new Proxy(Proxy.Type.HTTP,   
				            new InetSocketAddress(ip, Integer.parseInt(port)));*/
			}
			System.out.println("proxyids="+proxyids);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		initProxy();
		Proxy proxy = getProxy();
		System.out.println("host="+proxy);
	}

	
	
}


class ValidProxyThread implements Runnable{
	List<Proxy> validProxy;
	List<Proxy> unvalidProxy;
	Proxy currentProxy ;
	int reqesttime = 5;//连续请求次数，有一次成功则表示代理可用。
	ValidProxyThread(List<Proxy> validProxy,List<Proxy> unvalidProxy,Proxy proxy){
		this.validProxy = validProxy;
		this.unvalidProxy = unvalidProxy;
		currentProxy = proxy;
	}
	@Override
	public void run() {
		try{
			boolean valid = false;
			for(int i=0; i< reqesttime; i++){
				String body = HttpProxy.getHttpRequestContentByGet("https://www.baidu.com", currentProxy, "utf-8");
				if(body.length()<100){
					 continue;
				}else{
					valid = true;
					break;
				}
		
			}
			if(valid){
				if(!validProxy.contains(currentProxy)){
					validProxy.add(currentProxy);
				}
				if(unvalidProxy.contains(currentProxy)){
					unvalidProxy.remove(currentProxy);
				}
				 
				
			}else{
				if(!unvalidProxy.contains(currentProxy)){
					unvalidProxy.add(currentProxy);
				}
				if(validProxy.contains(currentProxy)){
					validProxy.remove(currentProxy);
				}
			}
			System.out.println("ValidProxyThread.class,validProxy.size="+validProxy.size()+",unvalidProxy.size="+unvalidProxy.size());
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
}
 