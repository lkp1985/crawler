package com.lkp.project.qichacha;

import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;

import com.lkp.common.net.HttpProxy;
import com.lkp.proxyutils.ProxyUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Test {
	static Map<String,String> headParams = new HashMap<String,String>();
	static int succes_num = 0;
	static int failed_num = 0;
	static  int last_num = 0;
	public static void init(){
		headParams.put("Host","www.qichacha.com");
		headParams.put("Proxy-Connection","keep-alive"); 
		headParams.put("Accept-Language","zh-CN,en-US;q=0.8,en;q=0.6"); 
		headParams.put("Accept","text/html, */*; q=0.01");
		headParams.put("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36");
		headParams.put("Accept-Encoding","gzip, deflate, sdch");// "gzip, deflate, sdch");
		headParams.put("X-Requested-With", "XMLHttpRequest");
	}
	public static void main(String[] args) {
		init();
		getBasic();
	//	getDetail();
	}
	
	
	public static void getBasic(){
		 headParams.put("Cookie", "gr_user_id=8b6c04f8-b925-4889-a123-07678024471f; _uab_collina=147679118423651115003228; _umdata=2FB0BDB3C12E491D2879EBE0AA45A28CCFA1E49D866EEB32183BD853B2859D6D6B487FE605689DDE572CF40C52C717640C8EF870054B02247B285B320E057A8E195FBC6DB29DCBA79291E6E31207D42161250241100F5FE45A2F1A70063A450CAE055F494B9CB8DC; PHPSESSID=gr0jp91jhvv3i9h7t3215skmf4; gr_session_id_9c1eb7420511f8b2=578c33b8-5d42-4a80-ba51-d25a73140c4f; CNZZDATA1254842228=872728790-1476788608-http%253A%252F%252Fwww.ixy360.com%252F%7C1477312434");
		
		for(int i=0;i<10000;i++){
			System.out.println("i="+i);
			 Proxy proxy =   HttpProxy.getProxy();
			String url = "http://www.qichacha.com/search?key=430103000074318&index=0";
			String  body = HttpProxy.getHttpRequestContentByGet(url, proxy,headParams);
			// System.out.println("bodi="+body);
			if(body !=null &&body.contains("长沙裕")){
				System.out.println("true:"+succes_num++);
				last_num = i;
			}else{
				System.out.println("false:"+failed_num++);
			}
			System.out.println("last_num="+last_num);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void getDetail(){

		//headParams.put("Cookie", "gr_user_id=5fdfd9f0-d6b3-4545-ba35-7e7e23ae9e9d; _uab_collina=147702805846834657329541; _umdata=0712F33290AB8A6D74269EB4325E3232DF794B2175989774DB8B595A5A48EC20023CF43CCA109831B4E8AD8FB34A7D3D715CE8EC996AEB7D81C02452920B5FB6374348CE6D6DECE427CF55119832B88F9E088430CED0122FC22653CC86DAF638; PHPSESSID=95gp8fqsuhjo5hllonf7iea4b1; gr_session_id_9c1eb7420511f8b2=6c3d6e30-1007-43c0-b3fc-efb5635d8bbe; CNZZDATA1254842228=1754438472-1477026227-null%7C1477285434");
		
		String url = "http://www.qichacha.com/firm_c7a5dc4302047b63cb0d96d1f18a59fe.shtml";
		for(int i=0;i<100;i++){
			Proxy proxy =   HttpProxy.getProxy();
		//	System.out.println("proxy="+proxy);
			System.out.println("i="+i);
			String  body = HttpProxy.getHttpRequestContentByGet(url, proxy,headParams);

		//	System.out.println("body="+body);
			if(body!=null &&body.contains("裕邦")){
				System.out.println("true");
			}else{
				System.out.println("false");
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	 
}
