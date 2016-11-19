/*
 * Copyright (c) SHADANSOU 2014 All Rights Reserved
 *
 */
package com.lkp.project.fangtianxia;

import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.lkp.common.net.HttpProxy; 

/**
 * 乐视商城抢购
 * @author lkp
 *
 */
public class FangMain {
	private static final LinkedBlockingDeque<Runnable> taskQueue = new LinkedBlockingDeque<Runnable>(10);
	private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 6, 10, TimeUnit.SECONDS, taskQueue,
			new ThreadPoolExecutor.CallerRunsPolicy());
			
	public static String cookie = "pgv_pvi=605905920; tj2_lc=5fa15b2620c541ee165a1aa1fb6cebd8; COOKIE_CPS_ID=C_yiqifa_6611_NzU4MDYwfDAwODY5MzA0OTIyMmQ5ZjAwNzNj; COOKIE_USER_CITY=; COOKIE_THIRDPARTY_ID=233296357; COOKIE_TOKEN_ID_N=9a4b80b7339dce42e11c54dbe8d9535c; COOKIE_USER_IP=10.183.100.91; COOKIE_USER_NAME=97477c2980103f022e6c5e495e21d318; COOKIE_NICKNAME=150xxxxx675_516_358; COOKIE_USER_LEVEL_ID=1; COOKIE_SESSION_ID=2495EC11CB9530433170F6EC4B13BAED; COOKIE_TOKEN_ID=7cccb8417be6216870534f1225d4567d; COOKIE_TOKEN_DATE=1475244238337; COOKIE_USER_ID=2233296357; COOKIE_USER_TYPE=1; COOKIE_LOGIN_TYPE=5; COOKIE_USER_INFO=150xxxxx675_516_358%5E28*****60%40qq.com%5E150*****675%5E233296357%5Ehttp%3A%2F%2Fi1.letvimg.com%2Flc06_user%2F201605%2F09%2F15%2F00%2F1923203311462777250.jpg%2Chttp%3A%2F%2Fi1.letvimg.com%2Flc04_user%2F201605%2F09%2F15%2F00%2F1923203311462777256_200_200.jpg%2Chttp%3A%2F%2Fi1.letvimg.com%2Flc04_user%2F201605%2F09%2F15%2F00%2F1923203311462777256_70_70.jpg%2Chttp%3A%2F%2Fi1.letvimg.com%2Flc04_user%2F201605%2F09%2F15%2F00%2F1923203311462777256_50_50.jpg%5E; webtrekk_cookie_record=1; pgv_si=s6365890560; tj_sg=1; sourceUrl=http%3A//www.lemall.com/product/products-pid-GWGT400143.html%3Fref%3Dcn%3Acn%3Asale%3Aguoqingjie%3A327; COOKIE_CHECK_TIME=1475285754015; __xsptplus104=104.5.1475283197.1475286098.47%234%7C%7C%7C%7C%7C%23%23YzxAgxXuaFiSPLt7IoLh7EZAVpJ0Jy76%23; tj_sid=3e5d0b154f1ff3056ba560303cce4a7f; tj_lc=1EE98D50C5010A0DA4C43A0D5CDD84D6274D1A18";
	public static void main(String[] args) {
	 
		
 	 
 	  Map<String,String> addressUlrMap = getAddressList();
 	  for(String key : addressUlrMap.keySet()){
 		  	runList(key,addressUlrMap.get(key));
 	  }
// 	 	for(String address : addressList){
// 	 		runList(address);
// 	 	}
	}
	
	
	/**
	 * 
	 */
	public static Map<String,String> getAddressList(){
		try{
///house/ajaxrequest/arealist.php
			String url = "http://newhouse.fang.com/";
			Map<String,String> headParams = new HashMap<String,String>();
			headParams.put("Host","newhouse.fang.com");
			headParams.put("Proxy-Connection","keep-alive");
			headParams.put("Accept","*/*");
			headParams.put("Accept-Language","zh-CN,en-US;q=0.8,en;q=0.6");
			headParams.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			headParams.put("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36");
			headParams.put("Accept-Encoding","gzip, deflate");// "gzip, deflate, sdch");
			//headParams.put("Cookie", Test.cookie);
		 
	     //   url = "http://newhouse.sh.fang.com/house/s/b260000%2C65000-b93/?ctm=1.sh.xf_search.page.3";
			Proxy proxy  =  getProxy();
			String  body = HttpProxy.getHttpRequestContentByGet(url, proxy,headParams);
			System.out.println("body="+body);
			Document doc = Jsoup.parse(body);
			Elements eles = doc.getElementsByClass("city20141104nr");
			Map<String,String> addressUrlMap = new HashMap<String,String>();
			
			for(Element ele : eles){
				Elements aeles = ele.getElementsByTag("a");
				for(Element aele : aeles){
					addressUrlMap.put(aele.text(), aele.attr("href"));
				}
			}
			System.out.println("urls.size="+addressUrlMap.size());
			System.out.println("urls="+addressUrlMap);
			return addressUrlMap;
		}catch(Exception e){
			return null;
		}
	}
	public static void runList(String address,String mainurl){ 
		try{
			
			Map<String,String> headParams = new HashMap<String,String>();
			headParams.put("Host","newhouse.fang.com");
			headParams.put("Proxy-Connection","keep-alive");
			headParams.put("Accept","*/*");
			headParams.put("Accept-Language","zh-CN,en-US;q=0.8,en;q=0.6");
			headParams.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			headParams.put("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36");
			headParams.put("Accept-Encoding","gzip, deflate");// "gzip, deflate, sdch");
			headParams.put("Cookie", FangMain.cookie);
			String url = mainurl+"/house/s/b2";
					//"http://newhouse.sh.fang.com/house/s/b2";
	     //   url = "http://newhouse.sh.fang.com/house/s/b260000%2C65000-b93/?ctm=1.sh.xf_search.page.3";
			int durl = 3000;
			for(int i=0;i<70;i++){
				String condition = "";
				
				if(i==69){
					condition = i*durl+"%2c"+(i+300)*durl;//最后一次跨度大一点
				}else{
					condition = i*durl+"%2c"+(i+1)*durl;
				}
				
				String filename = address+"_"+ i*durl+"_"+(i+1)*durl;
				String newurl = url+condition;
				System.out.println("newurl = "+newurl); 
				Proxy proxy  =  getProxy();
				String  body = HttpProxy.getHttpRequestContentByGet(newurl, proxy, headParams);
			 
				int num = 0;
				if(body!=null && body.length()>100){
					Document doc = Jsoup.parse(body);
					
					
					try{ 
						Elements eles = doc.getElementsByClass("page");
						num =Integer.parseInt(eles.first().getElementsByClass("fl").first().getElementsByTag("strong").text());
						System.out.println("num="+num);
					}catch(Exception e){
					}
				}
				if(num>0){
					int pageall = 0;
					if(num%20==0){
						pageall = num/20;
					}else{
						pageall = num/20+1;
					}
					filename = filename+"_"+pageall;
					executor.execute(new RunThread(newurl,pageall,filename));
				}
			} 
		}catch(Exception e){
			
		}
		
	}

	public static Proxy getProxy() {
		try {
			String[] ips = { "115.159.126.103", "123.206.194.143", "115.159.29.75", "119.29.241.205", "119.29.242.109",
					"119.29.244.218", "119.29.245.126", "119.29.244.202" };
			int index = new Random().nextInt(ips.length);
			String ip = ips[index];
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, 10001));
			return proxy;
		} catch (Exception e) {
			return null;
		}

	}
	public static void write(String file, String content) {

		try {
			FileWriter fw = new FileWriter(file);
			fw.write(content);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 
 
	public static String getValueWithNull(Object value) {
		if (value == null) {
			return "";
		} else {
			return value.toString();
		}
	}
	
	 
}


class RunThread implements Runnable{
	private static final Log LOGGER = LogFactory.getLog(RunThread.class);
	FileWriter fw = null;
	String url = null;
	int pageall = 0;
	String filename;
	public RunThread(String url,int pageall,String filename){
		this.url = url;
		this.pageall = pageall;
		this.filename=filename;
		try {
	  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 	@Override
	public void run() { 
	 		LOGGER.info("url="+url+";pageall="+pageall);
	 	 
	 		Map<String,String> headParams = new HashMap<String,String>();
			headParams.put("Host","newhouse.sh.fang.com");
			headParams.put("Proxy-Connection","keep-alive");
			headParams.put("Accept","*/*");
			headParams.put("Accept-Language","zh-CN,en-US;q=0.8,en;q=0.6");
			headParams.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			headParams.put("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36");
			headParams.put("Accept-Encoding","gzip, deflate");// "gzip, deflate, sdch");
			//headParams.put("Cookie", Test.cookie);
			
			List<FangDto> list = new ArrayList<FangDto>();
	 		for(int j=1;j<=pageall;j++){
				String newurl2 = url+"-b9"+j;
				System.out.println("newurl2="+newurl2);
				Proxy proxy  =  getProxy();
				String body = HttpProxy.getHttpRequestContentByGet(newurl2, proxy, headParams);
				// System.out.println("body = "+body);
				   if(body!=null && body.length()>100){
					   try{
						   
						   Document doc = Jsoup.parse(body);
						   
						   //第一种方式
						   Elements eles = doc.getElementsByClass("sslalone");
						   if(eles!=null && eles.size()>0){
							 
							   for(Element ele : eles){
								   FangDto dto = new FangDto();
								   list.add(dto);
								  Elements deles = ele.getElementsByTag("dd");
								  if(deles.size()>=3	){
									  Element ele1 = deles.get(0);
									  try{
										  String name = ele1.getElementsByTag("h4").first().getElementsByAttribute("href").text();
										  String type= ele1.getElementsByTag("h4").first().getElementsByTag("span").text();
										  String price = ele1.getElementsByTag("h5").first().getElementsByTag("span").text();
										  System.out.println("name="+name+";price="+price+",type="+type);
										  dto.setName(name);
										  dto.setHouse_type(type);
										  dto.setPrice(price);
									  }catch(Exception e){
										  
									  }
									  try{
										  Element ele2 = deles.get(1);
										  Elements spaneles = ele2.getElementsByClass("fl").first().getElementsByTag("span");
										  if(spaneles.size()>=2){
											  Element spanele = spaneles.get(0);
											  String saelnum =   spanele.getElementsByTag("a").text();
											  Element spanele2 = spaneles.get(1);
											  String chuqunum = spanele2.getElementsByTag("a").text();
											  System.out.println("在售:"+saelnum+";chuqunum="+chuqunum);
											  dto.setSalenum("在售:"+saelnum+";  出租:"+chuqunum);
										  }
									  }catch(Exception e){
										  
									  }
									
									  try{
										  String address = ele.getElementsByClass("add").first().getElementsByTag("a").text();
										  System.out.println("address="+address);
										  dto.setAddress(address);
									  }catch(Exception e){
										  
									  }
									  
									  try{
										  String house_saletype =  ele.getElementsByClass("esfLp").first().text();
										  System.out.println("house_saletype="+house_saletype);
										  dto.setSale_type(house_saletype);
									  }catch(Exception e){
										  
									  }
									  
									  
								  }
								  
							   }
						   }
						   
						   //第二种方式
						   eles = doc.getElementsByClass("nlc_details");
						   if(eles!=null && eles.size()>0){
							   for(Element ele : eles){
								   FangDto dto = new FangDto();
								   list.add(dto);
								   Elements aeles  = ele.getElementsByClass("nlcd_name").first().getElementsByTag("a");
								   String name = aeles.first().text();
								   String href = aeles.first().attr("href");
								   dto.setName(name);
								   dto.setHref(href);
								 System.out.println("name="+name+";href="+href);
								 
								 aeles  =ele.getElementsByClass("nhouse_price").first().getElementsByTag("span");
								 String price = aeles.first().text();
								 System.out.println("price = "+price);
								 dto.setPrice(price);
								   aeles  = ele.getElementsByClass("house_type").first().getElementsByTag("a");
								   String type = "";
								   for(Element aele : aeles){
									   type+=aele.text()+" ";
								   }
								   System.out.println("type="+type);
								   type += ele.getElementsByClass("house_type").text();
								   System.out.println("type="+type);
								   dto.setHouse_type(type);
								   aeles  = ele.getElementsByClass("address");
								   String address = aeles.first().getElementsByAttribute("href").first().getElementsByAttribute("title").attr("title");
								   System.out.println("address="+address);
								   dto.setAddress(address);
								   aeles  = ele.getElementsByClass("tel");
								   String phone = aeles.first().getElementsByTag("p"	).first().text();
								   System.out.println("phone="+phone);
								   dto.setPhone(phone);
								   String fangyuan = "";
								   aeles  = ele.getElementsByClass("fangyuan").first().getElementsByAttribute("href");
								   for(Element aele : aeles){
									   fangyuan+=aele.text()+" ";
								   }
								   System.out.println("fanyuan="+fangyuan);
								   dto.setFangyuan(fangyuan);
								   
							   }
						   }
						   
					   }catch(Exception e){
						   
					   }
					   
				   }
				
			}
	 		ExcelUtil.writeExcel(list, filename);
	 	}
	
	 	
	public Proxy getProxy(){
		try{
			String[] ips = {"115.159.126.103","123.206.194.143","115.159.29.75","119.29.241.205","119.29.242.109",
					"119.29.244.218","119.29.245.126","119.29.244.202","119.29.245.69","119.29.245.193","119.29.245.90"
					,"123.206.199.136","123.206.198.232","123.206.198.188","115.29.165.122","115.159.126.103"};
			int index = new Random().nextInt(ips.length);
			String ip = ips[index];
			Proxy proxy  = new Proxy(Proxy.Type.HTTP,   
		            new InetSocketAddress(ip, 10001));
			return proxy;
		}catch(Exception e){
			return null;
		}
		
	}
}