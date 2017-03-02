package com.lkp.project.baidulvyou;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.lkp.common.net.HttpProxy;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class BDLYMain {
	private static final LinkedBlockingDeque<Runnable> taskQueue = new LinkedBlockingDeque<Runnable>(10);
	private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 10, TimeUnit.SECONDS, taskQueue,
			new ThreadPoolExecutor.CallerRunsPolicy());
	static List<String> provinceList = new ArrayList<String>();
	static List<String> zczbList = new ArrayList<String>();
	static List<String> qytype = new ArrayList<String>();
	static List<String> qystatus = new ArrayList<String>();

	public static void init() {

	}

	public static void main(String[] args) {
		// String url = "https://lvyou.baidu.com/budalagong";
		// url = "https://lvyou.baidu.com/budalagong";
		//
		//
		// String body =
		// HttpProxy.getHttpRequestContentByGet(url,null,null,false);
		// getBasicInfo(body);

		MysqlUtil.init();
// 	 List<String> list = getChinaLyList();
////	// System.out.println("list = " + list);
// 	 	MysqlUtil.insertJingdianUrl(list);

		List<Proxy> proxyList = HttpProxy.getProxy4();
		//executor.execute(new RunBdlyBasicThread(proxyList.get(0)));
		for(Proxy proxy :proxyList){
			System.out.println("proxy="+proxy);
			executor.execute(new RunBdlyBasicThread(proxy));
		}
		
		

		// String url =
		// "http://lvyou.baidu.com/destination/ajax/jingdian?format=ajax&cid=0&playid=0&seasonid=5&surl=beijing&pn=1&rn=18";

	}

	public static  List<String> getLyList() {
		List<String> list = new ArrayList<String>();
		String url = "https://lvyou.baidu.com/destination/ajax/top";
		String body = HttpProxy.getHttpRequestContentByGet(url, null, null, false);

		System.out.println("body=" + body);
		Document doc = Jsoup.parse(body);
		Elements eles = doc.getElementsByTag("record");
		for (Element ele : eles) {
			String jdurl = ele.attr("url");
			jdurl = jdurl.substring(1, jdurl.length() - 1);
			list.add(jdurl);
		}
		return list;
	}

	public  static List<String> getChinaLyList() {
		List<String> list = new ArrayList<String>();
		String url = "https://lvyou.baidu.com/destination/ajax/top";
		String body = HttpProxy.getHttpRequestContentByGet(url, null, null, false);

		System.out.println("body=" + body);
		Document doc = Jsoup.parse(body);
		Elements eles = doc.getElementsByTag("china");
		if(eles.size()>0){
			Element chinaele = eles.first();
			Elements chinaeles = chinaele.getElementsByTag("record");
			for (Element ele : chinaeles) {
				String jdurl = ele.attr("url");
				jdurl = jdurl.substring(1, jdurl.length() - 1);
				list.add(jdurl);
			}
		}
		
		return list;
	}
	public static void runCommend(){
		
	}
	
	

	public static void test(Proxy proxy) {
		Map<String, String> headParams = new HashMap<String, String>();
		headParams.put("Accept", "application/json"); // 设置接收数据的格�?
		headParams.put("Content-Type", "application/json"); // 设置发�?数据的格�?
		String url = "http://www.qysudu.com/qysuduApi/company/search";
		String condition = SearchTerm.getSearchTerm();
		System.out.println(Thread.currentThread().getName() + " start ,keywords= 	" + condition);
		JSONObject json = new JSONObject();
		json.put("keywords", condition);
		json.put("pvn", "110000");
		String body = HttpProxy.getHttpRequestContentByPost(url, proxy, json.toString(), headParams, false);
		if (body != null && body.contains("frequentVisit")) {
			int num = ProxyUtil.proxyNum.get(ProxyUtil.getProxyHost(proxy));
			System.out.println(proxy + " frequentVisit,num=" + num);
		} else {
			System.out.println("body2=" + body);
		}
	}
}


class RunBdlyBasicThread implements  Runnable {
	static String cidmap = "{\"1\":\"\u57ce\u5e02\",\"2\":\"\u53e4\u9547\",\"3\":\"\u4e61\u6751\",\"4\":\"\u6d77\u8fb9\",\"5\":\"\u6c99\u6f20\",\"6\":\"\u5c71\u5cf0\",\"7\":\"\u5ce1\u8c37\",\"8\":\"\u51b0\u5ddd\",\"9\":\"\u6e56\u6cca\",\"10\":\"\u6cb3\u6d41\",\"11\":\"\u6e29\u6cc9\",\"12\":\"\u7011\u5e03\",\"13\":\"\u8349\u539f\",\"14\":\"\u6e7f\u5730\",\"15\":\"\u81ea\u7136\u4fdd\u62a4\u533a\",\"16\":\"\u516c\u56ed\",\"17\":\"\u5c55\u9986\",\"18\":\"\u5386\u53f2\u5efa\u7b51\",\"19\":\"\u73b0\u4ee3\u5efa\u7b51\",\"20\":\"\u5386\u53f2\u9057\u5740\",\"21\":\"\u5b97\u6559\u573a\u6240\",\"22\":\"\u89c2\u666f\u53f0\",\"23\":\"\u9675\u5893\",\"24\":\"\u5b66\u6821\",\"25\":\"\u6545\u5c45\",\"26\":\"\u7eaa\u5ff5\u7891\",\"27\":\"\u5176\u4ed6\",\"28\":\"\u8d2d\u7269\u5a31\u4e50\",\"29\":\"\u4f11\u95f2\u5ea6\u5047\"}";
	
	Proxy proxy =null;
	String jingdian;
	public   RunBdlyBasicThread(Proxy proxy){
		this.proxy = proxy; 
	}
	public void run() {
//		String baseurl = "http://lvyou.baidu.com/" + jingdian+"/remark";
//		
//		String body = HttpProxy.getHttpRequestContentByGet(baseurl, proxy, null, false);
		
		//runBasic();
		runRemark();
	}
	
	
	public void runRemark(){
		while (true) {
			List<String> idlist = MysqlUtil.getSourceUrlList2();
			if(idlist==null || idlist.isEmpty()	){
				try {
					Thread.sleep(10000);
					continue;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for (String sid : idlist) {
				try{
					if(sid == null){
						continue;
					}
					System.out.println("remark id="+sid);
					String baseurl = "http://lvyou.baidu.com/user/ajax/remark/getsceneremarklist?xid=" + sid+"&score=0&pn=1&rn=15";
					String body = HttpProxy.getHttpRequestContentByGet(baseurl, proxy, null, false);
					MysqlUtil.insertRemark(sid, 0, body);
					JSONObject obj = JSONObject.fromObject(body);
					JSONObject data = obj.getJSONObject("data");
					int total = data.getInt("total");
					int page = 0;
					if(total%15 == 0){
						page = total/15;
					}else{
						page = total/15+1;
					}
					for(int i=1;i<= page;i++){
						baseurl = "http://lvyou.baidu.com/user/ajax/remark/getsceneremarklist?xid=" + sid+"&score=0&pn="+i+"&rn=15";
						body = HttpProxy.getHttpRequestContentByGet(baseurl, proxy, null, false);
						MysqlUtil.insertRemark(sid, i, body);
					}
				}catch(Exception e){
					
				}
				
				
			}
		}
	}
	
	 
	public   void runBasic(){
		while (true) {
			try{
				List<String> urllist = MysqlUtil.getSourceUrlList();
				for (String url : urllist) {
					try { 
						System.out.println("begin get basic url = " +url);
						String baseurl = "http://lvyou.baidu.com/" + url;
						String body = HttpProxy.getHttpRequestContentByGet(baseurl, proxy, null, false);
						BdlyDto dto = getBasicInfo(body);
						dto.setUrl(url);
						List<BdlyDto> dtoList = new ArrayList<BdlyDto>();
						dtoList.add(dto);
						MysqlUtil.insertJingdianBasic(dtoList);

						String formaturl = "http://lvyou.baidu.com/destination/ajax/jingdian?surl=" + url + "&pn=1&rn=18";
 
						body = HttpProxy.getHttpRequestContentByGet(formaturl, proxy, null, false);

						System.out.println("body=" + body);

						int page = getJiandianPages(body);
						if(page==0){
							continue;
						}
						getJingdianList(body);
						for (int i = 2; i <= page; i++) {
							formaturl = "http://lvyou.baidu.com/destination/ajax/jingdian?surl=" + url + "&pn=" + i + "&rn=18";

							System.out.println("formaturl=" + formaturl);
							body = HttpProxy.getHttpRequestContentByGet(formaturl, proxy, null, false);
							getJingdianList(body);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}catch(Exception e){
				
			}
			
		}
	}
	
	
	public   int getJiandianPages(String body) {
		List<String> jdlist = new ArrayList<String>();
		try {
			JSONObject json = JSONObject.fromObject(body);
			JSONObject data = json.getJSONObject("data");
			String total = data.getString("scene_total");
			int num = Integer.parseInt(total);

			int page = 1;
			if (num % 18 == 0) {
				page = num / 18;
			} else {
				page = num / 18 + 1;
			}

			return page;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public   List<String> getJingdianList(String body) {
		List<String> jdlist = new ArrayList<String>();
		try {
			JSONObject json = JSONObject.fromObject(body);
			JSONObject data = json.getJSONObject("data");
			JSONArray scene_path_array = data.getJSONArray("scene_path");
			String path = "";
			for (Object obj : scene_path_array) {
				JSONObject pathobj = JSONObject.fromObject(obj);
				path += pathobj.getString("sname");
			}

			JSONArray jdarray = data.getJSONArray("scene_list");

			for (Object obj : jdarray) {
				JSONObject jdobj = JSONObject.fromObject(obj);
				System.out.println("jdobj=" + jdobj);
				String url = jdobj.getString("surl");
				String sname = jdobj.getString("sname");
				System.out.println("sname=" + path + sname);
				MysqlUtil.insertJingdianUrl(url, path + sname);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return jdlist;
	}

	
	public   BdlyDto getBasicInfo(String body) {
		try {
			BdlyDto dto = new BdlyDto();
			Document doc = Jsoup.parse(body);

			Elements aeles = doc.getElementsByAttributeValue("pb-id", "destAllviewNavClick");
			if (aeles != null && !aeles.isEmpty()) {
				String href = aeles.get(0).attr("href");
				if (href != null && href.endsWith("/")) {
					href = href.substring(0, href.length() - 1);
				}
				int index = href.indexOf("/jingdian");
				href = href.substring(1, index);
				// href = "https://lvyou.baidu.com"+href;

				MysqlUtil.insertJingdianUrl(href, "");
			}

			Elements scoreeles = doc.getElementsByClass("main-score");
			if(scoreeles.size()>0){
				String score  = scoreeles.get(0).text();
				System.out.println("score = " + score);
				if(score!=null && score.contains("分") &&  score.contains("点评") ){
					String[] scores = score.split("");
					if(scores.length == 2){
						String scorestr = scores[0];
						String comment  = scores[1];
						int index = scorestr.indexOf("分");
						if(index>0){
							scorestr = scorestr.substring(0, index);
						}
						index = comment.indexOf("条点评");
						if(index>0){
							comment = comment.substring(0, index);
						}
						dto.setScore(scorestr);
						dto.setCommentnum(comment);
						System.out.println("scorestr="+scorestr+",comment="+comment);
						
					}
				}
			}
			
			Elements points = doc.getElementsByClass("point-rank");
			if(points.size()>0){
				String point  = points.get(0).text();
				System.out.println("point = " +point);
				dto.setPoint(point);
			}
			
			//point-rank
			Elements eles = doc.getElementsByTag("script");
			for (Element ele : eles) {
				// System.out.println("ele ="+ele.html());
				if (ele.html().contains("impression:")) {
					String content = ele.html();
					// System.out.println("ele.text="+ele.text());

					int index = content.indexOf("sid:\"");
					content = content.substring(index + 5);
					index = content.indexOf("\",");
					String sid = content.substring(0, index);
					System.out.println("sid="+sid);
					dto.setSid(sid);
					
					index = content.indexOf("sname:\"");
					if(index>0){
						content = content.substring(index + 7);
						index = content.indexOf("\",");
						String sname = content.substring(0, index);
						sname = decodeUnicode(sname);
						System.out.println("sname"+sname);
						dto.setName(sname);
						
					} 
					index = content.indexOf("impression:");
					content = content.substring(index + 12);
					index = content.indexOf("\"");
					String impression = content.substring(0, index);
					impression = decodeUnicode(impression);
					dto.setImpression(impression);
					System.out.println("impression  = " + impression);
					index = content.indexOf("more_desc:");
					content = content.substring(index + 11);
					index = content.indexOf("\"");
					String mor_desc = content.substring(0, index);
					mor_desc = decodeUnicode(mor_desc);
					System.out.println("mor_desc  = " + mor_desc);
					dto.setMor_desc(mor_desc);
					index = content.indexOf("map_info:");
					content = content.substring(index + 10);
					index = content.indexOf("\"");
					String map_info = content.substring(0, index);
					map_info = decodeUnicode(map_info);
					System.out.println("map_info  = " + map_info);
					dto.setMap_info(map_info);
					index = content.indexOf("address:");
					content = content.substring(index + 9);
					index = content.indexOf("\"");
					String address = content.substring(0, index);
					address = decodeUnicode(address);
					System.out.println("address  = " + address);
					dto.setAddress(address);

					index = content.indexOf("phone:");
					content = content.substring(index + 7);
					index = content.indexOf("\"");
					String phone = content.substring(0, index);
					phone = decodeUnicode(phone);
					System.out.println("phone  = " + phone);
					dto.setPhone(phone);
					index = content.indexOf("cids:");
					content = content.substring(index + 6);
					index = content.indexOf("\"");
					String type = content.substring(0, index);
					System.out.println("type  = " + type);
					// System.out.println("content="+content);
					String jdtype = getType(type);
					dto.setLytype(jdtype);
					index = content.indexOf("if(false ==");

					if (index > 0) {
						content = content.substring(index);
						index = content.indexOf("ticket_info',{text:\"");
						if (index > 0) {
							content = content.substring(index + 20);
							index = content.indexOf("\"");
							String ticket_info = content.substring(0, index);
							ticket_info = decodeUnicode(ticket_info);
							System.out.println("ticket_info  = " + ticket_info);
							dto.setTicket_info(ticket_info);
							content = content.substring(index);
						}
					}

					index = content.indexOf("traffic',{text:\"");

					if (index > 0) {
						content = content.substring(index + 16);
						index = content.indexOf("\"");
						String traffic = content.substring(0, index);
						traffic = decodeUnicode(traffic);
						System.out.println("traffic  = " + traffic);
						dto.setTraffic(traffic);
						content = content.substring(index);
					}

					index = content.indexOf("bestvisittime',{text:\"");
					if (index > 0) {

						content = content.substring(index + 22);
						index = content.indexOf("\"");
						String bestvisittime = content.substring(0, index);
						bestvisittime = decodeUnicode(bestvisittime);
						System.out.println("bestvisittime  = " + bestvisittime);
						dto.setBestvisittime(bestvisittime);
						content = content.substring(index);
					}

					index = content.indexOf("besttime',{text:\"");
					if (index > 0) {
						content = content.substring(index + 17);
						index = content.indexOf("\"");
						String besttime = content.substring(0, index);
						besttime = decodeUnicode(besttime);
						System.out.println("besttime  = " + besttime);
						dto.setBesttime(besttime);
						content = content.substring(index);
					}

					index = content.indexOf("open_time_desc',{text:\"");
					if (index > 0) {
						content = content.substring(index + 23);
						index = content.indexOf("\"");
						String open_time_desc = content.substring(0, index);
						open_time_desc = decodeUnicode(open_time_desc);
						dto.setOpen_time_desc(open_time_desc);
						System.out.println("open_time_desc  = " + open_time_desc);
					}
					break;
				}
			}
			return dto;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String decodeUnicode(String theString) {
		try {
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
								throw new IllegalArgumentException("Malformed   \\uxxxx   encoding.");
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

		} catch (Exception e) {

		}

		return theString;

	}

	// ��ȡ���ξ������ͣ�"1":"����","2":"����","3":"���","4":"����","5":"ɳĮ","6":"ɽ��","7":"Ͽ��","8":"��","9":"����","10":"����","11":"��Ȫ","12":"�ٲ�","13":"��ԭ","14":"ʪ��","15":"��Ȼ������","16":"��԰","17":"չ��","18":"��ʷ����","19":"�ִ���","20":"��ʷ��ַ","21":"�ڽ̳���","22":"�۾�̨","23":"��Ĺ","24":"ѧУ","25":"�ʾ�","26":"���","27":"����","28":"��������","29":"���жȼ�"
	public static String getType(String key) {
		try {
			JSONObject json = JSONObject.fromObject(cidmap);
			return json.getString(key);
		} catch (Exception e) {

		}
		return "";
	}
}