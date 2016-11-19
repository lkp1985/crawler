/*
 * Copyright (c) SHADANSOU 2014 All Rights Reserved
 *
 */
package com.lkp.project.qichacha;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Proxy;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.lkp.common.net.HttpProxy;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 乐视商城抢购
 * 
 * @author lkp
 *
 */
public class CompanyMain {
	private static final Log LOGGER = LogFactory.getLog(CompanyMain.class);
	private static final LinkedBlockingDeque<Runnable> taskQueue = new LinkedBlockingDeque<Runnable>(10);
	private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 6, 10, TimeUnit.SECONDS, taskQueue,
			new ThreadPoolExecutor.CallerRunsPolicy());

	public static String cookie = "pgv_pvi=605905920; tj2_lc=5fa15b2620c541ee165a1aa1fb6cebd8; COOKIE_CPS_ID=C_yiqifa_6611_NzU4MDYwfDAwODY5MzA0OTIyMmQ5ZjAwNzNj; COOKIE_USER_CITY=; COOKIE_THIRDPARTY_ID=233296357; COOKIE_TOKEN_ID_N=9a4b80b7339dce42e11c54dbe8d9535c; COOKIE_USER_IP=10.183.100.91; COOKIE_USER_NAME=97477c2980103f022e6c5e495e21d318; COOKIE_NICKNAME=150xxxxx675_516_358; COOKIE_USER_LEVEL_ID=1; COOKIE_SESSION_ID=2495EC11CB9530433170F6EC4B13BAED; COOKIE_TOKEN_ID=7cccb8417be6216870534f1225d4567d; COOKIE_TOKEN_DATE=1475244238337; COOKIE_USER_ID=2233296357; COOKIE_USER_TYPE=1; COOKIE_LOGIN_TYPE=5; COOKIE_USER_INFO=150xxxxx675_516_358%5E28*****60%40qq.com%5E150*****675%5E233296357%5Ehttp%3A%2F%2Fi1.letvimg.com%2Flc06_user%2F201605%2F09%2F15%2F00%2F1923203311462777250.jpg%2Chttp%3A%2F%2Fi1.letvimg.com%2Flc04_user%2F201605%2F09%2F15%2F00%2F1923203311462777256_200_200.jpg%2Chttp%3A%2F%2Fi1.letvimg.com%2Flc04_user%2F201605%2F09%2F15%2F00%2F1923203311462777256_70_70.jpg%2Chttp%3A%2F%2Fi1.letvimg.com%2Flc04_user%2F201605%2F09%2F15%2F00%2F1923203311462777256_50_50.jpg%5E; webtrekk_cookie_record=1; pgv_si=s6365890560; tj_sg=1; sourceUrl=http%3A//www.lemall.com/product/products-pid-GWGT400143.html%3Fref%3Dcn%3Acn%3Asale%3Aguoqingjie%3A327; COOKIE_CHECK_TIME=1475285754015; __xsptplus104=104.5.1475283197.1475286098.47%234%7C%7C%7C%7C%7C%23%23YzxAgxXuaFiSPLt7IoLh7EZAVpJ0Jy76%23; tj_sid=3e5d0b154f1ff3056ba560303cce4a7f; tj_lc=1EE98D50C5010A0DA4C43A0D5CDD84D6274D1A18";
	public static String keywords = "游戏";
	static Map<String, String> headParams = new HashMap<String, String>();

	public static void init() {
		keywords = FileUtil.getKeywords();
		System.out.println("keyowrds=" + keywords);
		headParams = new HashMap<String, String>();
		headParams.put("Host", "www.tianyancha.com");
		headParams.put("Proxy-Connection", "keep-alive");
		headParams.put("Accept", "*/*");
		headParams.put("Accept-Language", "zh-CN,en-US;q=0.8,en;q=0.6");
		headParams.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		headParams.put("loop", "null");
		headParams.put("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36");
		headParams.put("Accept-Encoding", "gzip, deflate");// "gzip, deflate,
															// sdch");
	}

	public static void main(String[] args) {
		init();
		MysqlUtil.init();
		while(true){
			Map<String,String> conditionMap = MysqlUtil.getCompanyQueryCondition();
			Map<String,String> newmap = new HashMap<String,String>();
			for(String id : conditionMap.keySet()){
				String href = getDetailHref(id,conditionMap.get(id));
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(href!=null){
					newmap.put(id,href);
				}
			}
			MysqlUtil.insertHref(newmap);
		}
	
	}
 
	public static String getDetailHref(String id,String condition){
		 headParams.put("Cookie", "PHPSESSID=8rtq4njdhmt2nqpg04ej7341n3; ");
			

		try{ 
			 Proxy proxy =   HttpProxy.getProxy();
			 System.out.println("condition="+condition);
			String url = "http://www.qichacha.com/search?key="+condition+"&index=2";
			String  body = HttpProxy.getHttpRequestContentByGet(url, proxy,headParams);
		 System.out.println("body  ="+body);
		    Document doc = 	Jsoup.parse(body);
		    Elements eles = doc.getElementsByClass("tp2_tit");
		   String href =  eles.first().getElementsByAttribute("href").first().attr("href");
		   System.out.println("id="+id+",href="+href);
		   
		   return href;
			// System.out.println("bodi="+body);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
class RunThread implements Runnable {
	private static final Log LOGGER = LogFactory.getLog(RunThread.class);
	FileWriter fw = null;
	String url = null;
	int pageall = 0;
	String filename;

	public RunThread(String url, int pageall, String filename) {
		this.url = url;
		this.pageall = pageall;
		this.filename = filename;
		try {

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

	}

}