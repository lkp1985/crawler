/*
 * Copyright (c) SHADANSOU 2014 All Rights Reserved
 *
 */
package com.lkp.project.nongye;

import java.io.FileReader;
import java.net.Proxy;
import java.util.List;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.lkp.getproxyip.ProxyIPHolder;
import com.lkp.proxy.HttpProxy;

/**
 * <p>
 * class function description.
 * <p>
 *
 * create 2016年3月20日<br>
 * 
 * @author lkp<br>
 * @version 1.0
 * @since 1.0
 */
public class MainThread implements Runnable {

	TaskQueue urlqueue;
	List<DataModel> docList;

	public MainThread(TaskQueue _urlqueue, List<DataModel> _docList) {
		urlqueue = _urlqueue;
		docList = _docList;
	}

	@Override
	public void run() {
		System.out.println("线程启动。……。。……。……。………");
		while (!urlqueue.isEmpty()) {
			String url = urlqueue.poll();
			int ind = url.indexOf("&itemid=");
			System.out.println("url = " + url);
			String itemid = url.substring(ind + 8);
			String cookie = null;
//			String proxyIP = ProxyIPHolder.getProxyIP();
//			System.out.println("proxyIP==" + proxyIP);
//			while (proxyIP == null) {
//				try {
//					Thread.sleep(100);
//					proxyIP = ProxyIPHolder.getProxyIP();
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			String ip = proxyIP.split("\\|")[0];
//			String port = proxyIP.split("\\|")[1];
//			Proxy proxy = HttpProxy.getProxy(ip, port);
			Proxy proxy = null;
			String ss = com.lkp.proxy.HttpProxy.getResponseByGet(url, cookie,proxy);
//			while (ss.length() < 100) {
//				System.out.println("begin return get " + url);
//				proxyIP = ProxyIPHolder.getProxyIP();
//				ip = proxyIP.split("\\|")[0];
//				port = proxyIP.split("\\|")[1];
//				proxy = HttpProxy.getProxy(ip, port);
//				ss = com.lkp.proxy.HttpProxy.getResponseByGet(url, cookie,proxy);
//			}
			System.out.println("get success ");
			// System.out.println("ss="+ss);
			Document doc = Jsoup.parse(ss);

			// System.out.println("ss="+doc);
			// 公司地址、传真、公司电话、邮箱、联系人、部门职位、手机号码、
			Elements eles = doc.getElementsByClass("i_user");
			if (eles.size() == 1) {
				DataModel dataModel = new DataModel();
				Elements tables = eles.get(0).getElementsByTag("table");
				if (tables.size() == 1) {
					Elements trs = tables.get(0).getElementsByTag("tr");
					for (Element tr : trs) {
						Elements tds = tr.getElementsByTag("td");
						if (tds.get(0).getElementsByTag("img").size() == 1) {
							String user_id = tds.get(1).getElementsByTag("strong").get(0).text();
							String company = tds.get(1).getElementsByTag("a").get(0).text();
							System.out.println("user_id=" + user_id + ";company=" + company);
							//solrDoc.setProductid(user_id);
							dataModel.setCompanyname(company);
						}
						if (tds.get(0).text().contains("公司地址")) {
							String address = tds.get(1).text();
							System.out.println("address=" + address);
							dataModel.setCompanyaddress( address);
						} else if (tds.get(0).text().contains("电子邮件")) {
							Elements as = tds.get(1).getElementsByTag("a");
							if (as.size() == 1) {
								String email = as.get(0).attr("data-cfemail");
								email = getEmail(email);
								System.out.println("email=" + email);
								dataModel.setEmail( email);
							}
						} else if (tds.get(0).text().contains("传真")) {
							String fax = tds.get(1).text();
							System.out.println("fax=" + fax);
							dataModel.setFax( fax);
						} else if (tds.get(0).text().contains("邮政编码")) {
							String post = tds.get(1).text();
							System.out.println("post=" + post);
							dataModel.setPost(post);
						} else if (tds.get(0).text().contains("公司电话")) {
							String companyphone = tds.get(1).text();
							System.out.println("companyphone=" + companyphone);
							//solrDoc.setPhone(companyphone);
						} else if (tds.get(0).text().contains("联 系 人")) {
							String people = tds.get(1).text();
							System.out.println("people=" + people);
							dataModel.setContactname(people);
						} else if (tds.get(0).text().contains("部门职位")) {
							String position = tds.get(1).text();
							System.out.println("position=" + position);
							dataModel.setPosition(position);
						} else if (tds.get(0).text().contains("手机号码")) {
							String telephone = tds.get(1).text();
							System.out.println("phone=" + telephone);
							dataModel.setPhone(telephone);
						}
					}
				}
				dataModel.setProductid(itemid);
				docList.add(dataModel);
				System.out.println("doclist.size=" + docList.size());
			}
		}
	}

	public String getEmail(String email) {
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		try {
			String js = "function test(){		var a = '" + email
					+ "';for(e='',r='0x'+a.substr(0,2)|0,n=2;a.length-n>0;n+=2)e+='%'+('0'+('0x'+a.substr(n,2)^r).toString(16)).slice(-2);return decodeURIComponent(e);	}";
			// engine.eval("function test(){ var a =
			// '750c1b01174d4d354447435b161a18';for(e='',r='0x'+a.substr(0,2)|0,n=2;a.length-n;n+=2)e+='%'+('0'+('0x'+a.substr(n,2)^r).toString(16)).slice(-2);}");
			engine.eval(js);
			// engine.eval("function test(){ var a =
			// '750c1b01174d4d354447435b161a18';var i =
			// 1;for(e='',r='0x'+a.substr(0,2)|0,n=2;a.length-n>0;n+=2)i=i+1
			// }");

			Invocable inv = (Invocable) engine;
			String value = String.valueOf(inv.invokeFunction("test"));
			System.out.println("value=" + value);
			return value;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		try {
			FileReader reader = new FileReader("D:\\workspace_lkp\\crawler_task\\test.js");
			engine.eval(reader);
			reader.close();

			Invocable inv = (Invocable) engine;
			String value = String.valueOf(inv.invokeFunction("encryptString"));
			System.out.println("value=" + value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
