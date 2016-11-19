package com.lkp.project.tianyancha;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.lkp.common.net.HttpProxy;

public class BaduUtil {
	static 	Map<String, String> params = new HashMap<String, String>();
	static String incondition  =" inurl:www.tianyancha.com";
	public static void init(){
	

		params.put("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
		// params.put("Accept-Encoding","gzip, deflate, sdch");
		params.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		// params.put("Host","s.weibo.com");
		// params.put("Referer",
		// "http://www.court.gov.cn/zgcpwsw/List/List?sorttype=1&conditions=searchWord+4+AJLX++%E6%A1%88%E4%BB%B6%E7%B1%BB%E5%9E%8B:%E8%B5%94%E5%81%BF%E6%A1%88%E4%BB%B6");
		params.put("Cache-Control", "max-age=0");
		params.put("Connection", "keep-alive");
		// conn.setInstanceFollowRedirects(false);
		// params.put("If-Modified-Since","Wed, 30 Dec 2015 09:01:37 GMT");
		// params.put("If-None-Match","");
		// params.put("If-Modified-Since","3f5dfb0e042d11");
		params.put("Upgrade-Insecure-Requests", "1");
		params.put("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.82 Safari/537.36");
	}
	public static void main(String[] args) {
		init();
		String condition = "220200200122094";
		System.out.println(getJYScope(condition));
		
		
	}

	
	public static String getJYScope(String code){
		try{
			String condition = incondition +" " + code;
			condition = URLEncoder.encode(condition);
			String url = "https://www.baidu.com/s?wd="+condition;
			System.out.println("等待请求条件");
			String body = 	HttpProxy.getHttpRequestContentByGet(url, HttpProxy.getProxy(), params,false);
			System.out.println("请求条件成功");
		//	System.out.println("body="+body);
			Document doc = Jsoup.parse(body);
			Elements eles = doc.getElementsByTag("a");
			String newbody = "";
			for(Element ele :eles){
				if(ele.attr("href").contains("tp://cache.baiducontent.com/")){
					String href = ele.attr("href");
					System.out.println("等待请求经营范围");
					newbody = 	HttpProxy.getHttpRequestContentByGet(href, HttpProxy.getProxy(), params,false,"gbk");
					System.out.println("请求经营范围成功");
					break;
						
				 
				}
			}
			
			if(newbody!=null && newbody.length()>100){
			//	System.out.println("newbody="+newbody);
				doc = Jsoup.parse(newbody);
				eles = doc.getElementsByAttribute("ng-if ");
				if(eles.size()==0){
					eles = doc.getElementsByClass("break-word");
					if(eles.size()>0){
						Elements neweles = eles.get(0).getElementsByClass("ng-binding");
						if(neweles.size()>0){
							String scope = neweles.get(0).text();
							return scope;
						}
					}
				}else{
					for(Element ele : eles){
					if(ele.attr("ng-if").equals("company.baseInfo.businessScope")){
						System.out.println("经营范围:"+ele.text());
						String scope = ele.text();
						scope = scope.replace("经营范围:经营范围：", "");
						return scope;
					}
				}
				}
				
			}			
		}catch(Exception e){
			
		}
		return "";
	}
}
