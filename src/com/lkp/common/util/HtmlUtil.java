/*
 * Copyright (c) SHADANSOU 2014 All Rights Reserved
 *
 */
package com.lkp.common.util;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * <p>class function description.<p>
 *
 * create  2015年8月29日<br>
 * @author  lkp<br> 
 * @version 1.0
 * @since   1.0
 */
public class HtmlUtil {

	/**
	 * 返回所有<a href>类型的url  
	 * @param content
	 * @return
	 */
	public static List<String> getAllLinkHref(String content){
		List<String> urlList = new ArrayList<String>();
		Document doc = Jsoup.parse(content);
		Elements links = doc.select("a[href]");
        
        for (Element link : links) {
        	urlList.add(link.attr("abs:href")); 
        }
        return urlList;
	}
	
	/**
	 * 返回包含指定内容的链接
	 * @param content
	 * @param filt
	 * @return
	 */
	public static List<String> getAllHrefByFilt(String content,String filt){
		List<String> urlList = new ArrayList<String>();
		Document doc = Jsoup.parse(content);
		Elements links = doc.select("a[href]");
        for (Element link : links) {
        	String url = link.attr("abs:href");
        	if(url.contains(filt)){
            	urlList.add(link.attr("abs:href")); 
        	}
        }
        return urlList;
	}
	
	public static List<String> getPageUrlList(String origin_url,String content){
		List<String> urllist = new ArrayList<String>();
		Document doc =  Jsoup.parse(content);
		Elements eles = doc.getElementsByClass("p-skip");
		if(eles.size() == 1){
			Elements eleb = eles.get(0).getElementsByTag("b");
			if(eleb.size()==1){
				String page = eleb.get(0).text();
				System.out.println("page = "+page);
				try{
					int pageNum = Integer.parseInt(page);
					for(int i=2;i<=pageNum;i++){
						String newurl = origin_url+"&page="+i;
						urllist.add(newurl);
					}
				}catch(Exception e){
				}
			}
		}
		return urllist;
	}
	
	/**
	 * 对list类型url进行转换，如
	 * http://list.jd.com/737-794-823-0-0-0-0-0-0-0-1-1-1-1-1-72-4137-33.html
	 * 转换成http://list.jd.com/list.html?cat=737,794,823
	 * @param url
	 * @return
	 */
	public static String transferListUrl(String url){
		//http://list.jd.com/737-794-823-0-0-0-0-0-0-0-1-1-1-1-1-72-4137-33.html
		if(!url.contains("?cat=")){
			String newurl = "http://list.jd.com/list.html?cat=";
			String substring = url.substring(19);
			String[] uris = substring.split("-");
			newurl +=uris[0]+","+uris[1]+","+uris[2];
			return newurl;
		}else{
			return url;
		}
	}
	public static int getMaxPage(String content) { 
		Document doc =  Jsoup.parse(content);
		Elements eles = doc.getElementsByClass("p-skip");
		if(eles.size() == 1){
			Elements eleb = eles.get(0).getElementsByTag("b");
			if(eleb.size()==1){
				String page = eleb.get(0).text();
				System.out.println("page = "+page);
				try{
					int pageNum = Integer.parseInt(page);
					return pageNum;
				}catch(Exception e){
					return 0;
				}
				
			}
		}
		return 0;
	}
	
	public static Document getDoc(String content){
		return Jsoup.parse(content);
	}
	
	public static void main(String[] args) {
		try { } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
