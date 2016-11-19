/*
 * Copyright (c) SHADANSOU 2014 All Rights Reserved
 *
 */
package com.lkp.project.nongye;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.script.Invocable;
import javax.script.ScriptEngine;

import com.lkp.getproxyip.ProxyIpGet;

/**
 * <p>
 * class function description.
 * <p>
 *
 * create 2016年3月10日<br>
 * 
 * @author lkp<br>
 * @version 1.0
 * @since 1.0
 */
public class Test {

	public static void main(String[] args) {
		
 		
		//getEmail(email);
		String url = "http://www.nongcun5.com/sell/search.php?list=0&kw=&fields=0&fromdate=20080101&todate=20160322&catid=0&typeid=&areaid=0&minprice=&maxprice=&order=0&x=36&y=21&page=";
		TaskQueue urlqueue = new TaskQueue(); 
//		FileWriter fw = null;
//		try {
//			fw = new FileWriter("D:\\workspace_lkp\\crawler_task\\url.txt");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		for(int i=1;i<=259;i++){
//			String newurl = url+i;
//			String rsp = HttpProxy.getResponseByGet(newurl, null);
//			Document doc = Jsoup.parse(rsp);
//			Elements eles = doc.getElementsByTag("a");
//			for(Element ele : eles){
//				String href = ele.attr("href");
//				if(href.contains("www.nongcun5.com/member/chat.php?touser")){
//					System.out.println("href="+href);
//					urlqueue.add(href);
//					try {
//						fw.write(href+"\n");
//						fw.flush();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			}
//		}
//		try {
//			fw.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		try{
			BufferedReader br = new BufferedReader(new FileReader("D:\\workspace_lkp\\crawler_task\\url.txt"));
			String href = null;
			while((href=br.readLine())!=null){
				urlqueue.add(href);
			}
			br.close();
		}catch(Exception e){
			
		}
		ProxyIpGet ipGet = new ProxyIpGet();
 		new Thread(ipGet).start();
	 //	String rsp = HttpProxy.getResponseByGet(url, null);
	//	System.out.println("rsp="+rsp);
		List<DataModel> docList = new ArrayList<DataModel>();
		//new Thread(new CommitThread(docList)).start();
//		
//		
//		
//		for(int i=1;i<=27511;i++){//27511;i++){
//			String newurl = url+"&PageIndex="+i; 
//			urlqueue.add(newurl);
//		}
		for(int i=0;i<1;i++){
			new Thread(new MainThread(urlqueue,docList)).start();
		}
		 
		 
		

	}

	
	private static void testUsingJDKClasses(ScriptEngine engine)    throws Exception {   
	    // Packages是脚本语言里的一个全局变量,专用于访问JDK的package   
	    String js = "function a(){		var a = \"750c1b01174d4d354447435b161a18\";for(e='',r='0x'+a.substr(0,2)|0,n=2;a.length-n;n+=2)e+='%'+('0'+('0x'+a.substr(n,2)^r).toString(16)).slice(-2);	}";   
	    engine.eval(js);   
	    // Invocable 接口: 允许java平台调用脚本程序中的函数或方法   
	    Invocable inv = (Invocable) engine;   
	    // invokeFunction()中的第一个参数就是被调用的脚本程序中的函数，第二个参数是传递给被调用函数的参数；   
	    inv.invokeFunction("doSwing", "Scripting Swing");   
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
