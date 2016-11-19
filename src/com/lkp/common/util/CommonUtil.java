package com.lkp.common.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.solr.common.SolrInputDocument;

import com.lkp.common.net.HttpTools;
import com.lkp.solr.SolrUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

public class CommonUtil {

	/**
	 * 获取审判长
	 * @param res
	 * @return
	 */
	public static String getSPZ(String res){
		try{
			int index1 = res.indexOf(">审　判　长");
			if(index1==-1){
				index1 = res.indexOf(">审判长");
				String spz2 = res.substring(index1+1,index1+1+20);
				int index22 = spz2.indexOf("<");
				String spzsub2 = spz2.substring(3,index22);
				System.out.println("spzsub2="+spzsub2.trim());
				return spzsub2;
			}
			String spz = res.substring(index1+1,index1+1+20);
			int index2 = spz.indexOf("<");
			String spzsub = spz.substring(6,index2);
			System.out.println("spzsub="+spzsub);
			return spzsub;
		}catch(Exception e){
			return "";
		}
		
	}
	
	/**
	 * 获取代理审判员
	 * @param res
	 * @return
	 */
	public static String getSPY(String res){
		try{
			int index1 = res.indexOf(">审判员");
			String spz = res.substring(index1+1,index1+1+20);
			int index2 = spz.indexOf("<");
			String spzsub = spz.substring(4,index2);
			System.out.println("审判员="+spzsub);
			
			index1 = res.indexOf(spzsub);
			res = res.substring(index1+spzsub.length());
			index1 = res.indexOf(">审判员");
			if(index1!=-1){
				spz = res.substring(index1+1,index1+1+20);
				  index2 = spz.indexOf("<");
				 String spzsub2 = spz.substring(4,index2);
				 spzsub2 = trim(spzsub2);
				 spzsub = trim(spzsub);
				System.out.println("审判员2="+spzsub2);
				spzsub +=" "+spzsub2;
				return spzsub;
			}else{
				return trim(spzsub);
			}
			
		}catch(Exception e){
			return "";
		}
	}
	
	
	
	/**
	 * 获取代理审判员
	 * @param res
	 * @return
	 */
	public static String getProxySPY(String res){
		try{
			int index1 = res.indexOf(">代理审判员");
			String spz = res.substring(index1+1,index1+1+20);
			int index2 = spz.indexOf("<");
			String spzsub = spz.substring(6,index2);
			System.out.println("审判员="+spzsub);
			
			index1 = res.indexOf(spzsub);
			res = res.substring(index1+spzsub.length());
			index1 = res.indexOf(">代理审判员");
			if(index1!=-1){
				spz = res.substring(index1+1,index1+1+20);
				  index2 = spz.indexOf("<");
				 String spzsub2 = spz.substring(6,index2);
				 spzsub2 = trim(spzsub2);
				 spzsub = trim(spzsub);
				System.out.println("审判员2="+spzsub2);
				spzsub +=" "+spzsub2;
				return spzsub;
			}else{
				return trim(spzsub);
			}
			
		}catch(Exception e){
			return "";
		}
	}
	/**
	 * 获取书记员
	 * @param res
	 * @return
	 */
	public static String getSJY(String res){
		try{
			int index1 = res.indexOf(">书　记　员");
			if(index1==-1){
				index1 = res.indexOf(">书记员");
				String sjy2 = res.substring(index1+1,index1+1+20);
				int index22 = sjy2.indexOf("<");
				String sjysub2 = sjy2.substring(3,index22);
				System.out.println("sjysub2="+sjysub2.trim());
				return sjysub2;
			}
			String sjy = res.substring(index1+1,index1+1+20);
			int index2 = sjy.indexOf("<");
			String sjysub = sjy.substring(6,index2);
			System.out.println("sjysub="+sjysub);
			return sjysub;
		}catch(Exception e){
			return "";
		}
		
	}
	
	/**
	 * 获取浏览量
	 * @param res
	 * @return
	 */
	public static String getLookCount(String res){
		//"{\"TotalCount\":\"41\"}"
		try{
			int index1 = 19;
			int index2 = res.indexOf("}");
			String count = res.substring(index1,index2-2);
			System.out.println("count="+count);
			return count;
		}catch(Exception e){
			e.printStackTrace();
			return "0";
		}
	}
	
	public static String getPublishDate(String res){
		try{
			if(res.contains("上传日期")){
				int index = res.indexOf("Date(");
				res = res.substring(index);
				int index2 = res.indexOf(")");
				String date = res.substring(5,index2);
				System.out.println("date="+date);
				String newdate = DateUtil.dateTimeToStr(new Date(Long.parseLong(date)), DateUtil.DATE_FORMAT_YYYY_MM_DD);
				return newdate;
				//"上传日期":"\/Date(1403702683000)\/"
				
			}else{
				
			}
			return "";
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
	
	}
	public static JSONObject getContent(String res){
		try{
			String result = res.substring(1,res.length()-1);
			result = result.replace("\\\"", "\"");
			String[] results = result.split("\\}\\,\\{");
			JSONObject resultJson = new JSONObject();
			JSONArray array = new JSONArray();
			for(String re : results){
				if(re.contains("Count")){
					re = re.substring(11);
					int index = re.indexOf("\"");
					String numstr = re.substring(0,index);
					try{
						long num = Long.parseLong(numstr);
						resultJson.put("total", num);
					}catch(Exception e){
						resultJson.put("total", 0);
						return resultJson;
					}
					
				}else if(re.contains("裁判要旨段原文")){
					JSONObject json = new JSONObject();
					String[] sss = re.split("\",\"",-1);
					for(int i=0;i<sss.length;i++){
						if(sss[i].contains("DocContent")){
							String content = sss[i].substring(13);
							json.put("content", content); 
						}else if(sss[i].contains("案件类型")){
							String content = sss[i].substring(7);
							json.put("casetype", content); 
						}else if(sss[i].contains("裁判日期")){
							String content = sss[i].substring(7);
							json.put("chargedate", content); 
						}else if(sss[i].contains("案件名称")){
							String content = sss[i].substring(7);
							json.put("casename", content); 
						}else if(sss[i].contains("文书ID")){
							String content = sss[i].substring(7);
							json.put("docid", content); 
						}else if(sss[i].contains("案号")){
							String content = sss[i].substring(5);
							json.put("caseno", content); 
						} else if(sss[i].contains("法院名称")){
							String content = sss[i].substring(7,sss[i].length()-1);
							json.put("courtname", content); 
						} else{
							
						}
					}
					array.add(json);
				}else if(re.contains("DocContent")){
					JSONObject json = new JSONObject();
					String[] sss = re.split("\",\"",-1);
					for(int i=0;i<sss.length;i++){
						if(sss[i].contains("DocContent")){
							String content = sss[i].substring(13);
							json.put("content", content); 
						}else if(sss[i].contains("案件类型")){
							String content = sss[i].substring(7);
							json.put("casetype", content); 
						}else if(sss[i].contains("裁判日期")){
							String content = sss[i].substring(7);
							json.put("chargedate", content); 
						}else if(sss[i].contains("案件名称")){
							String content = sss[i].substring(7);
							json.put("casename", content); 
						}else if(sss[i].contains("文书ID")){
							String content = sss[i].substring(7);
							json.put("docid", content); 
						}else if(sss[i].contains("案号")){
							String content = sss[i].substring(5);
							json.put("caseno", content); 
						} else if(sss[i].contains("法院名称")){
							String content = sss[i].substring(7,sss[i].length()-1);
							json.put("courtname", content); 
						} else{
							
						}
					}
					array.add(json);
				}else{
					
				}
			}
			resultJson.put("list",array);
			return resultJson;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	public static String trim(String args){
		int index = 0;
		while(args.contains(" ")&&index++<20){
			args  = args.replaceAll(" ", "");
		}
		return args;
	}
	
	public static void runByDate(String begindate,String enddate,String errorfile){
		FileWriter errorWriter = null;
		try {
			errorWriter = new FileWriter("D:\\workspace_lkp\\crawler_task\\output\\"+errorfile+".txt");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		String firsturl = "http://www.court.gov.cn/";
		try {
			//String resss = HttpTools.getHttpsResponse(firsturl, "utf-8");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String url = "http://www.court.gov.cn/zgcpwsw/List/ListContent";
		String cookie = "CookieName=CookieValue; CookieName=CookieValue; PHPSESSID=4iiif7rb2np0i0tugm7tq079e1; __jsluid=77fd4bec6367c3c28bfb60c53c23c888; ASP.NET_SessionId=nov4voeavo421hcask3y2qda; __jsl_clearance=1456017994.872|0|k42L8ZEHa78l7A4KjyxnDnjdaMc%3D; _gscu_1049835508=55947827xhyh0717; _gscs_1049835508=56018257mg41o817|pv:2; _gscbrs_1049835508=1; _pk_id.1.cf4c=3f0de15842e55247.1455947834.3.1456018299.1456018257.; _pk_ses.1.cf4c=*; _gscu_1241932522=557987759brzvg16; _gscs_1241932522=t56018255nw4yjy16|pv:4; _gscbrs_1241932522=1; _gsref_1241932522=http://www.court.gov.cn/zgcpwsw/";
		String formparams = "Param=%E6%A1%88%E4%BB%B6%E7%B1%BB%E5%9E%8B%3A%E8%B5%94%E5%81%BF%E6%A1%88%E4%BB%B6%2C%E8%A3%81%E5%88%A4%E6%97%A5%E6%9C%9F%3A";
		String page= "&Page=20";
		String order="&Order=%E6%B3%95%E9%99%A2%E5%B1%82%E7%BA%A7";
		String direct = "&Direction=asc";
		String index="&Index=";
		String startDateStr = begindate; 
		
		int sum = 0;
		Date startDate = DateUtil.strToDate(startDateStr, DateUtil.DATE_FORMAT_YYYY_MM_DD);
		Date endDate = DateUtil.strToDate(enddate, DateUtil.DATE_FORMAT_YYYY_MM_DD);
		Long dual = 1728000000l;//以10天为一段往下翻
		for(long time = (startDate.getTime()+dual);time < endDate.getTime()+dual;time=time+dual){
			System.out.println("startDate="+startDateStr);
			String endDateStr = DateUtil.dateToStr(new Date(time), DateUtil.DATE_FORMAT_YYYY_MM_DD);
			try{
				String queryParams = formparams +startDateStr+"+TO+"+endDateStr+page+order+direct+index+"1";
				
				String res = com.lkp.proxy.HttpProxy.getResponseByPostForm(url, queryParams, cookie);
				//System.out.println("res="+res);
				JSONObject resultJson = getContent(res);
				if(resultJson == null || resultJson.get("total") instanceof JSONNull){
					System.out.println("total is null");
					startDateStr = endDateStr;
					continue;
				}
				long total = 0;
				try{
					total = resultJson.getLong("total");
				}catch(Exception e){
					startDateStr = endDateStr;
					continue;
				}
				System.out.println("total="+total);
				if(total == 0){
					startDateStr = endDateStr;
					continue;
				}
				if(total>500){
					errorWriter.write(startDateStr+"_"+endDateStr+" exceed 500");
					errorWriter.flush();
					startDateStr = endDateStr;
					continue;
				}else{
					sum += total;
					for(int i=1;i<=total/20+1;i++){
						queryParams = formparams +startDateStr+"+TO+"+endDateStr+page+order+direct+index+i;
						res = com.lkp.proxy.HttpProxy.getResponseByPostForm(url, queryParams, cookie);
						resultJson = getContent(res);
						JSONArray array = resultJson.getJSONArray("list");
						for(Object obj : array){
							JSONObject json = (JSONObject)obj;
							
							String docid = json.getString("docid");
							String url2 = "http://www.court.gov.cn/zgcpwsw/content/content?DocID="+docid;//9ec22c93-f176-47e6-b449-d36fc9ece4c0";
							
						//	res = com.lkp.proxy.HttpProxy.getResponseByGet(url2, cookie2);
							
							try {
								res = HttpTools.getHttpResponse(url2, "utf-8",cookie);
								String publishDate = CommonUtil.getPublishDate(res);
								System.out.println("date="+publishDate);
								json.put("publishdate", publishDate);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							url2 = "http://www.court.gov.cn/zgcpwsw/Content/GetClickCount";
							String formparams2 = "docId="+docid;
							res = com.lkp.proxy.HttpProxy.getResponseByPostForm(url2, formparams2,cookie);
							System.out.println("res="+res);
							String looknum = CommonUtil.getLookCount(res);
							json.put("looknum", looknum);
							
							String suburl = docid.substring(0,4);
							String url3 = "http://www.court.gov.cn/zgcpwsw/Html_Pages/"+suburl+"/"+docid+".js";//25a91429-5be7-49fd-a398-05c2fe96f003.js";
							res = com.lkp.proxy.HttpProxy.getResponseByGet(url3, cookie);
							String sjy = CommonUtil.getSJY(res);
							String spz = CommonUtil.getSPZ(res);
							String spy = CommonUtil.getSPY(res);
							String prspy = CommonUtil.getProxySPY(res);
							
							json.put("spz", spz);
							json.put("spy", spy);
							json.put("sjy", sjy);
							json.put("prspy", prspy);
							
							//System.out.println("json="+json);
						}
						//提交solr
						List<SolrInputDocument> docList = new ArrayList<SolrInputDocument>();
						for(Object obj : array){
							JSONObject json = (JSONObject)obj;
							SolrInputDocument doc = new SolrInputDocument();
							doc.addField("id", json.get("docid"));
							doc.addField("name", json.get("casename"));
							doc.addField("no", json.get("caseno"));
							doc.addField("court", json.get("courtname"));
							doc.addField("chartdate", json.get("chargedate"));
							doc.addField("insertdate", DateUtil.dateToStr(new Date(), DateUtil.DATE_FORMAT_YYYY));
							doc.addField("publishdate", json.get("publishdate"));
							doc.addField("times", json.get("looknum"));
							doc.addField("content", json.get("content"));
							doc.addField("presidingjudge", json.get("spz"));
							doc.addField("presidingor", json.get("spy"));
							doc.addField("proxyor", json.get("prspy"));
							doc.addField("recorder", json.get("sjy"));
							doc.addField("listurl", "http://www.court.gov.cn/zgcpwsw/List/List?sorttype=1&conditions=searchWord+4+AJLX++%E6%A1%88%E4%BB%B6%E7%B1%BB%E5%9E%8B:%E8%B5%94%E5%81%BF%E6%A1%88%E4%BB%B6");
							doc.addField("detailurl", "http://www.court.gov.cn/zgcpwsw/content/content?DocID="+json.get("docid"));
							docList.add(doc);
						}
						Thread.sleep(100);
						boolean flag = SolrUtils.addDoc(docList);
						docList.clear();
						if(flag){
							System.out.println("commit success");
							System.out.println("sum = "+sum);
						}
					}
					
					
				}

				
			}catch(Exception e){
				e.printStackTrace();
			}
			startDateStr = endDateStr;
		}
	}
	public static void main(String[] args) {
		FileWriter errorWriter = null;
		try {
			errorWriter = new FileWriter("D:\\workspace_lkp\\crawler_task\\output\\error.txt");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		String firsturl = "http://www.court.gov.cn/";
		try {
			//String resss = HttpTools.getHttpsResponse(firsturl, "utf-8");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String url = "http://www.court.gov.cn/zgcpwsw/List/ListContent";
		String cookie = "CookieValue; CookieName=CookieValue; PHPSESSID=4iiif7rb2np0i0tugm7tq079e1; _gscu_1049835508=55947827xhyh0717; _pk_id.1.cf4c=3f0de15842e55247.1455947834.2.1455977845.1455977845.; __jsluid=77fd4bec6367c3c28bfb60c53c23c888; ASP.NET_SessionId=nov4voeavo421hcask3y2qda; _gscu_1241932522=557987759brzvg16; _gscs_1241932522=t55982198owf4c326|pv:14; _gscbrs_1241932522=1; __jsl_clearance=1455983562.377|0|kt4l2n2eDJBnMRHt5HzkSiv6XgI%3D";
		String formparams = "Param=%E6%A1%88%E4%BB%B6%E7%B1%BB%E5%9E%8B%3A%E8%B5%94%E5%81%BF%E6%A1%88%E4%BB%B6%2C%E8%A3%81%E5%88%A4%E6%97%A5%E6%9C%9F%3A";
		String page= "&Page=20";
		String order="&Order=%E6%B3%95%E9%99%A2%E5%B1%82%E7%BA%A7";
		String direct = "&Direction=asc";
		String index="&Index=";
		String startDateStr = "2015-01-01"; 
		
		int sum = 0;
		Date startDate = DateUtil.strToDate(startDateStr, DateUtil.DATE_FORMAT_YYYY_MM_DD);
		Date endDate = DateUtil.strToDate("2016-02-20", DateUtil.DATE_FORMAT_YYYY_MM_DD);
		Long dual = 1728000000l;//以10天为一段往下翻
		for(long time = (startDate.getTime()+dual);time < endDate.getTime()+dual;time=time+dual){
			System.out.println("startDate="+startDateStr);
			String endDateStr = DateUtil.dateToStr(new Date(time), DateUtil.DATE_FORMAT_YYYY_MM_DD);
			try{
				String queryParams = formparams +startDateStr+"+TO+"+endDateStr+page+order+direct+index+"1";
				
				String res = com.lkp.proxy.HttpProxy.getResponseByPostForm(url, queryParams, cookie);
				//System.out.println("res="+res);
				JSONObject resultJson = getContent(res);
				if(resultJson == null || resultJson.get("total") instanceof JSONNull){
					System.out.println("total is null");
					startDateStr = endDateStr;
					continue;
				}
				long total = 0;
				try{
					total = resultJson.getLong("total");
				}catch(Exception e){
					startDateStr = endDateStr;
					continue;
				}
				System.out.println("total="+total);
				if(total == 0){
					startDateStr = endDateStr;
					continue;
				}
				if(total>500){
					errorWriter.write(startDateStr+"_"+endDateStr+" exceed 500");
					errorWriter.flush();
					startDateStr = endDateStr;
					continue;
				}else{
					sum += total;
					for(int i=1;i<=total/20+1;i++){
						queryParams = formparams +startDateStr+"+TO+"+endDateStr+page+order+direct+index+i;
						res = com.lkp.proxy.HttpProxy.getResponseByPostForm(url, queryParams, cookie);
						resultJson = getContent(res);
						JSONArray array = resultJson.getJSONArray("list");
						for(Object obj : array){
							JSONObject json = (JSONObject)obj;
							
							String docid = json.getString("docid");
							String url2 = "http://www.court.gov.cn/zgcpwsw/content/content?DocID="+docid;//9ec22c93-f176-47e6-b449-d36fc9ece4c0";
							
						//	res = com.lkp.proxy.HttpProxy.getResponseByGet(url2, cookie2);
							
							try {
								res = HttpTools.getHttpResponse(url2, "utf-8",cookie);
								String publishDate = CommonUtil.getPublishDate(res);
								System.out.println("date="+publishDate);
								json.put("publishdate", publishDate);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							url2 = "http://www.court.gov.cn/zgcpwsw/Content/GetClickCount";
							String formparams2 = "docId="+docid;
							res = com.lkp.proxy.HttpProxy.getResponseByPostForm(url2, formparams2,cookie);
							System.out.println("res="+res);
							String looknum = CommonUtil.getLookCount(res);
							json.put("looknum", looknum);
							
							String suburl = docid.substring(0,4);
							String url3 = "http://www.court.gov.cn/zgcpwsw/Html_Pages/"+suburl+"/"+docid+".js";//25a91429-5be7-49fd-a398-05c2fe96f003.js";
							res = com.lkp.proxy.HttpProxy.getResponseByGet(url3, cookie);
							String sjy = CommonUtil.getSJY(res);
							String spz = CommonUtil.getSPZ(res);
							String spy = CommonUtil.getSPY(res);
							String prspy = CommonUtil.getProxySPY(res);
							
							json.put("spz", spz);
							json.put("spy", spy);
							json.put("sjy", sjy);
							json.put("prspy", prspy);
							
							//System.out.println("json="+json);
						}
						//提交solr
						List<SolrInputDocument> docList = new ArrayList<SolrInputDocument>();
						for(Object obj : array){
							JSONObject json = (JSONObject)obj;
							SolrInputDocument doc = new SolrInputDocument();
							doc.addField("id", json.get("docid"));
							doc.addField("name", json.get("casename"));
							doc.addField("no", json.get("caseno"));
							doc.addField("court", json.get("courtname"));
							doc.addField("chartdate", json.get("chargedate"));
							doc.addField("insertdate", DateUtil.dateToStr(new Date(), DateUtil.DATE_FORMAT_YYYY));
							doc.addField("publishdate", json.get("publishdate"));
							doc.addField("times", json.get("looknum"));
							doc.addField("content", json.get("content"));
							doc.addField("presidingjudge", json.get("spz"));
							doc.addField("presidingor", json.get("spy"));
							doc.addField("proxyor", json.get("prspy"));
							doc.addField("recorder", json.get("sjy"));
							doc.addField("listurl", "http://www.court.gov.cn/zgcpwsw/List/List?sorttype=1&conditions=searchWord+4+AJLX++%E6%A1%88%E4%BB%B6%E7%B1%BB%E5%9E%8B:%E8%B5%94%E5%81%BF%E6%A1%88%E4%BB%B6");
							doc.addField("detailurl", "http://www.court.gov.cn/zgcpwsw/content/content?DocID="+json.get("docid"));
							docList.add(doc);
						}
						boolean flag = SolrUtils.addDoc(docList);
						docList.clear();
						if(flag){
							System.out.println("commit success");
							System.out.println("sum = "+sum);
						}
					}
					
					
				}

				
			}catch(Exception e){
				e.printStackTrace();
			}
			startDateStr = endDateStr;
		}
		
		
		
		
		
		/*
		 * Param=%E6%A1%88%E4%BB%B6%E7%B1%BB%E5%9E%8B%3A%E8%B5%94%E5%81%BF%E6%A1%88%E4%BB%B6&Index=1&Page=5&Order=%E6%B3%95%E9%99%A2%E5%B1%82%E7%BA%A7&Direction=asc
		 */
		
//		String url = "http://www.court.gov.cn/zgcpwsw/content/content?DocID=ce86e0fb-438f-48c2-8ac1-3968e698de88";
//		String cookie = "";
//		String res = com.lkp.proxy.HttpProxy.getResponseByGet(url, cookie);
//		String date = getPublishDate(res);
//		System.out.println("date="+date);
//		String url = "http://www.court.gov.cn/zgcpwsw/Content/GetClickCount";
//		String formparams = "docId=16b3128f-3bfb-40c5-a6d0-c98d64c746f6";
		//String res = com.lkp.proxy.HttpProxy.getResponseByPostForm(url, formparams, cookie);
//		System.out.println("res="+res);
//		String count = getCount(res);
		
		/**
		 * 16b3128f-3bfb-40c5-a6d0-c98d64c746f6.js
		 * a8b745f3-43ac-402c-99bf-68b9a9cae635.js
		 * efe9c3ed-b647-11e3-84e9-5cf3fc0c2c18.js
		 */
//		String jsurl = "http://www.court.gov.cn/zgcpwsw/Html_Pages/ce86/ce86e0fb-438f-48c2-8ac1-3968e698de88.js";
//		String jsres = com.lkp.proxy.HttpProxy.getResponseByGet(jsurl, false, null);
//		String spz = getSPZ(jsres);
//		String proxyspy = getProxySPY(jsres);
//		String spy = getSPY(jsres);
//		String sjy = getSJY(jsres);
//		System.out.println("sjy="+sjy);
		
	}
}
