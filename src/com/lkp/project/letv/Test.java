/*
 * Copyright (c) SHADANSOU 2014 All Rights Reserved
 *
 */
package com.lkp.project.letv;

import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.lkp.common.net.HttpProxy;
import com.lkp.common.util.DateUtil;

/**
 * 乐视商城抢购
 * @author lkp
 *
 */
public class Test {
	public static String cookie = "pgv_pvi=605905920; tj2_lc=5fa15b2620c541ee165a1aa1fb6cebd8; COOKIE_CPS_ID=C_yiqifa_6611_NzU4MDYwfDAwODY5MzA0OTIyMmQ5ZjAwNzNj; COOKIE_USER_CITY=; COOKIE_THIRDPARTY_ID=233296357; COOKIE_TOKEN_ID_N=9a4b80b7339dce42e11c54dbe8d9535c; COOKIE_USER_IP=10.183.100.91; COOKIE_USER_NAME=97477c2980103f022e6c5e495e21d318; COOKIE_NICKNAME=150xxxxx675_516_358; COOKIE_USER_LEVEL_ID=1; COOKIE_SESSION_ID=2495EC11CB9530433170F6EC4B13BAED; COOKIE_TOKEN_ID=7cccb8417be6216870534f1225d4567d; COOKIE_TOKEN_DATE=1475244238337; COOKIE_USER_ID=2233296357; COOKIE_USER_TYPE=1; COOKIE_LOGIN_TYPE=5; COOKIE_USER_INFO=150xxxxx675_516_358%5E28*****60%40qq.com%5E150*****675%5E233296357%5Ehttp%3A%2F%2Fi1.letvimg.com%2Flc06_user%2F201605%2F09%2F15%2F00%2F1923203311462777250.jpg%2Chttp%3A%2F%2Fi1.letvimg.com%2Flc04_user%2F201605%2F09%2F15%2F00%2F1923203311462777256_200_200.jpg%2Chttp%3A%2F%2Fi1.letvimg.com%2Flc04_user%2F201605%2F09%2F15%2F00%2F1923203311462777256_70_70.jpg%2Chttp%3A%2F%2Fi1.letvimg.com%2Flc04_user%2F201605%2F09%2F15%2F00%2F1923203311462777256_50_50.jpg%5E; webtrekk_cookie_record=1; pgv_si=s6365890560; tj_sg=1; sourceUrl=http%3A//www.lemall.com/product/products-pid-GWGT400143.html%3Fref%3Dcn%3Acn%3Asale%3Aguoqingjie%3A327; COOKIE_CHECK_TIME=1475285754015; __xsptplus104=104.5.1475283197.1475286098.47%234%7C%7C%7C%7C%7C%23%23YzxAgxXuaFiSPLt7IoLh7EZAVpJ0Jy76%23; tj_sid=3e5d0b154f1ff3056ba560303cce4a7f; tj_lc=1EE98D50C5010A0DA4C43A0D5CDD84D6274D1A18";
	public static void main(String[] args) {
		runTv();
		//runPhone();
	}
	public static void runTv(){
		 
		 Date[] ds = new Date[7];
		 for(int i=1;i<=7;i++){
			 ds[i-1] = DateUtil.strToDate("2016-10-0"+i+" 10:00:00", DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
		 }
		 for(int j=0;j<1;j++){
				new Thread(new RunThread()).start();
			}
			while(true){
			 
				for(int i=0;i<ds.length;i++){
					if( (ds[i].getTime() - System.currentTimeMillis()<2000 && ds[i].getTime() - System.currentTimeMillis()>0)){
						for(int j=0;j<15;j++){
							new Thread(new RunThread()).start();
						}
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				try {
					System.out.println("还没到抢购时间，请等待:"+new Date());
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
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
	int sum =  10;
	 	@Override
	public void run() {
	 	int i=0;
		while(true){//(i++<sum){
			String url = "http://g.go.lemall.com/v4/api/web/rush.jsonp?rushId=8352&pid=600200002868_X00000002679&timestamp=1475286372246&callback=callResult&_=1475286374063";
			//	url="http://www.lemall.com/cn/sale/heise919/miaosha.html?1474032336135&ref=cn:cn:sale:heise919:ecocp:256";
				Map<String,String> headParams = new HashMap<String,String>();
				headParams.put("Host","g.go.lemall.com");
				headParams.put("Proxy-Connection","keep-alive");
			//	headParams.put("Accept","*/*");
				headParams.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
				headParams.put("User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36");
				headParams.put("Accept-Encoding","gzip, deflate, sdch");// "gzip, deflate, sdch");
				headParams.put("Cookie", Test.cookie);
				//ysudu.com
				
				Proxy proxy  = getProxy();
				System.out.println("proxy="+proxy);
				try{
					String  body = HttpProxy.getHttpRequestContentByGet(url, proxy, headParams);
					System.out.println("body = " +body);
				}catch(Exception e){
					e.printStackTrace();
				}
		}
		
		
	}
	
	 	
	public Proxy getProxy(){
		try{
			String[] ips = {"115.159.126.103","123.206.194.143","115.159.29.75","119.29.241.205","119.29.242.109","119.29.244.218","119.29.245.126","119.29.244.202"};
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