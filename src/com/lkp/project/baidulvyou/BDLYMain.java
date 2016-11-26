package com.lkp.project.baidulvyou;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.text.translate.UnicodeUnescaper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.escape.UnicodeEscaper;
import com.lkp.common.net.HttpProxy;

import net.sf.json.JSONObject;
import sun.nio.cs.UnicodeEncoder;

public class BDLYMain {
	static String cidmap = "{\"1\":\"\u57ce\u5e02\",\"2\":\"\u53e4\u9547\",\"3\":\"\u4e61\u6751\",\"4\":\"\u6d77\u8fb9\",\"5\":\"\u6c99\u6f20\",\"6\":\"\u5c71\u5cf0\",\"7\":\"\u5ce1\u8c37\",\"8\":\"\u51b0\u5ddd\",\"9\":\"\u6e56\u6cca\",\"10\":\"\u6cb3\u6d41\",\"11\":\"\u6e29\u6cc9\",\"12\":\"\u7011\u5e03\",\"13\":\"\u8349\u539f\",\"14\":\"\u6e7f\u5730\",\"15\":\"\u81ea\u7136\u4fdd\u62a4\u533a\",\"16\":\"\u516c\u56ed\",\"17\":\"\u5c55\u9986\",\"18\":\"\u5386\u53f2\u5efa\u7b51\",\"19\":\"\u73b0\u4ee3\u5efa\u7b51\",\"20\":\"\u5386\u53f2\u9057\u5740\",\"21\":\"\u5b97\u6559\u573a\u6240\",\"22\":\"\u89c2\u666f\u53f0\",\"23\":\"\u9675\u5893\",\"24\":\"\u5b66\u6821\",\"25\":\"\u6545\u5c45\",\"26\":\"\u7eaa\u5ff5\u7891\",\"27\":\"\u5176\u4ed6\",\"28\":\"\u8d2d\u7269\u5a31\u4e50\",\"29\":\"\u4f11\u95f2\u5ea6\u5047\"}";
	private static final LinkedBlockingDeque<Runnable> taskQueue = new LinkedBlockingDeque<Runnable>(10);
	private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(5,10, 10, TimeUnit.SECONDS, taskQueue,
			new ThreadPoolExecutor.CallerRunsPolicy());
	static List<String> provinceList = new ArrayList<String>();
	static List<String> zczbList = new ArrayList<String>();
	static List<String> qytype = new ArrayList<String>();
	static List<String> qystatus = new ArrayList<String>();
	
	public static void init(){

	}
	public static void main(String[] args) { 
		String url = "https://lvyou.baidu.com/budalagong";
		url = "https://lvyou.baidu.com/wuzhizhoudao";
		
		
		String  body = HttpProxy.getHttpRequestContentByGet(url,null,null,false);
		getMapType(body);
	}
	
	
	public static String getMapType(String body){
		try{
			Document doc = Jsoup.parse(body);
			Elements eles = doc.getElementsByTag("script");
			for(Element ele :eles){
				//System.out.println("ele ="+ele.html());
				if(ele.html().contains("impression:")){
					String content = ele.html();
					//System.out.println("ele.text="+ele.text());
					
					int index = content.indexOf("impression:");
					content = content.substring(index+12);
					index = content.indexOf("\"");
					String impression = content.substring(0, index);
					impression = decodeUnicode(impression);
					System.out.println("impression  = " + impression);
					index = content.indexOf("more_desc:");
					content = content.substring(index+11);
					index = content.indexOf("\"");
					String mor_desc = content.substring(0, index);
					mor_desc = decodeUnicode(mor_desc);
					System.out.println("mor_desc  = " + mor_desc);
					
					index = content.indexOf("map_info:");
					content = content.substring(index+10);
					index = content.indexOf("\"");
					String map_info = content.substring(0, index);
					map_info = decodeUnicode(map_info);
					System.out.println("map_info  = " + map_info);
					
					index = content.indexOf("address:");
					content = content.substring(index+9);
					index = content.indexOf("\"");
					String address = content.substring(0, index);
					address = decodeUnicode(address);
					System.out.println("address  = " + address);
					

					index = content.indexOf("phone:");
					content = content.substring(index+7);
					index = content.indexOf("\"");
					String phone = content.substring(0, index);
					phone = decodeUnicode(phone);
					System.out.println("phone  = " + phone);
					
					index = content.indexOf("cids:");
					content = content.substring(index+6);
					index = content.indexOf("\"");
					String type = content.substring(0, index);
					System.out.println("type  = " + type);
					//System.out.println("content="+content);
					
					index = content.indexOf("if(false ==");
					
					
					content = content.substring(index);
					index = content.indexOf("ticket_info',{text:\"");
					if(index>0){
						content = content.substring(index+20);
						index = content.indexOf("\"");
						String ticket_info = content.substring(0, index);
						ticket_info = decodeUnicode(ticket_info);
						System.out.println("ticket_info  = " + ticket_info);
						content = content.substring(index);
					}
					
					
					
					index = content.indexOf("traffic',{text:\"");
					
					if(index>0){
						content = content.substring(index+16);
						index = content.indexOf("\"");
						String traffic = content.substring(0, index);
						traffic = decodeUnicode(traffic);
						System.out.println("traffic  = " + traffic);
						content = content.substring(index);
					}
					
					
					index = content.indexOf("bestvisittime',{text:\"");
					if(index>0){
						
						content = content.substring(index+22);
						index = content.indexOf("\"");
						String bestvisittime = content.substring(0, index);
						bestvisittime = decodeUnicode(bestvisittime);
						System.out.println("bestvisittime  = " + bestvisittime);
						content = content.substring(index);
					}
					
					index = content.indexOf("besttime',{text:\"");
					if(index>0){
						content = content.substring(index+17);
						index = content.indexOf("\"");
						String besttime = content.substring(0, index);
						besttime = decodeUnicode(besttime);
						System.out.println("besttime  = " + besttime);
						content = content.substring(index);
					}
					
					
					
					
					index = content.indexOf("open_time_desc',{text:\"");
					if(index>0){
						content = content.substring(index+23);
						index = content.indexOf("\"");
						String open_time_desc = content.substring(0, index);
						open_time_desc = decodeUnicode(open_time_desc);
						System.out.println("open_time_desc  = " + open_time_desc);
					}
					
					
					
				}
			}
			System.out.println(getType("2"));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return "";
	}
	
	public static String decodeUnicode(String theString) {      
		   
	    char aChar;      
	   
	     int len = theString.length();      
	   
	    StringBuffer outBuffer = new StringBuffer(len);      
	   
	    for (int x = 0; x < len;) {      
	   
	     aChar = theString.charAt(x++);      
	   
	     if (aChar == '\\') {      
	   
	      aChar = theString.charAt(x++);      
	   
	      if (aChar == 'u') {      
	   
	       // Read the xxxx      
	   
	       int value = 0;      
	   
	       for (int i = 0; i < 4; i++) {      
	   
	        aChar = theString.charAt(x++);      
	   
	        switch (aChar) {      
	   
	        case '0':      
	   
	        case '1':      
	   
	        case '2':      
	   
	        case '3':      
	   
	       case '4':      
	   
	        case '5':      
	   
	         case '6':      
	          case '7':      
	          case '8':      
	          case '9':      
	           value = (value << 4) + aChar - '0';      
	           break;      
	          case 'a':      
	          case 'b':      
	          case 'c':      
	          case 'd':      
	          case 'e':      
	          case 'f':      
	           value = (value << 4) + 10 + aChar - 'a';      
	          break;      
	          case 'A':      
	          case 'B':      
	          case 'C':      
	          case 'D':      
	          case 'E':      
	          case 'F':      
	           value = (value << 4) + 10 + aChar - 'A';      
	           break;      
	          default:      
	           throw new IllegalArgumentException(      
	             "Malformed   \\uxxxx   encoding.");      
	          }      
	   
	        }      
	         outBuffer.append((char) value);      
	        } else {      
	         if (aChar == 't')      
	          aChar = '\t';      
	         else if (aChar == 'r')      
	          aChar = '\r';      
	   
	         else if (aChar == 'n')      
	   
	          aChar = '\n';      
	   
	         else if (aChar == 'f')      
	   
	          aChar = '\f';      
	   
	         outBuffer.append(aChar);      
	   
	        }      
	   
	       } else     
	   
	       outBuffer.append(aChar);      
	   
	      }      
	   
	      return outBuffer.toString();      
	   
	     }    
	
	//获取旅游景点类型："1":"城市","2":"古镇","3":"乡村","4":"海边","5":"沙漠","6":"山峰","7":"峡谷","8":"冰川","9":"湖泊","10":"河流","11":"温泉","12":"瀑布","13":"草原","14":"湿地","15":"自然保护区","16":"公园","17":"展馆","18":"历史建筑","19":"现代建筑","20":"历史遗址","21":"宗教场所","22":"观景台","23":"陵墓","24":"学校","25":"故居","26":"纪念碑","27":"其他","28":"购物娱乐","29":"休闲度假"
	public static String getType(String key){
		JSONObject json = JSONObject.fromObject(cidmap);
		System.out.println("json="+json);
		System.out.println("json="+json.getString(key));
		return json.getString(key);
	}
	public static void test(Proxy proxy){
		Map<String,String> headParams = new HashMap<String,String>();
		headParams.put("Accept", "application/json"); // 璁剧疆鎺ユ敹鏁版嵁鐨勬牸寮? 
		headParams.put("Content-Type", "application/json"); // 璁剧疆鍙戦?鏁版嵁鐨勬牸寮? 
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
