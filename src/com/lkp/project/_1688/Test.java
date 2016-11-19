/*
 * Copyright (c) SHADANSOU 2014 All Rights Reserved
 *
 */
package com.lkp.project._1688;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.lkp.common.net.HttpProxy;

/**
 * 乐视商城抢购
 * 
 * @author lkp
 *
 */
public class Test {
	public static FileWriter fw = null;
	public static String cookie = "hp_ab_is_marked_2016=1; hp_ab_to_page_2016=old_page; cna=bypmELmKaEYCAXH33hXAwxcN; ali_beacon_id=113.247.222.21.1476157455284.500939.8; JSESSIONID=8L78xqcv1-JZUWfFlsAiUEHGfLvD-Wbw5WzP-TO69;  tbsnid=CCzJzhq7s4x7A9IuzRPVnDxEMtVmSZUZgVUtiIjWofg6sOlEpJKl9g%3D%3D;  last_mid=b2b-198760804ffe12; unb=198760804; __last_loginid__=pengsir_2009;  _is_show_loginId_change_block_=b2b-198760804ffe12_false; _show_force_unbind_div_=b2b-198760804ffe12_false; _show_sys_unbind_div_=b2b-198760804ffe12_false; _show_user_unbind_div_=b2b-198760804ffe12_false;   alicnweb=touch_tb_at%3D1476370313095%7Clastlogonid%3Dpengsir_2009; l=AqamCWqvxmQX8YmtEd0VxnJxdhYpE-pB; isg=AjEx7Ejtwj8ZsG47r9XbsNE6QL1NZqWQCKqYxhNHuPgXOlGMW261YN9YKnmn";

	private static final LinkedBlockingDeque<Runnable> taskQueue = new LinkedBlockingDeque<Runnable>(10);
	private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 6, 10, TimeUnit.SECONDS, taskQueue,
			new ThreadPoolExecutor.CallerRunsPolicy());

	public static void main(String[] args) {
		try {
			fw = new FileWriter("D:\\data\\1688_writephonewithurl2.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// testDou();
		testTemp();
		// testGetNewUrl();
		// moidfy();
		// runUrl();
		// runContact();
		// runDetail();
		// runPhone();
	}

	public static void testDou() {
		try {
			Set<String> set = new HashSet<String>();
			BufferedReader br = new BufferedReader(new FileReader("D:\\data\\1688_newurl2.txt"));
			String line = "";
			while ((line = br.readLine()) != null) {
				set.add(line);
			}
			for (String l : set) {
				fw.write(l + "\n");
			}
			fw.flush();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void testGetNewUrl() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("D:\\data\\1688url.txt"));
			String line = "";

			// deflate,
			// sdch");
			String cookie = "hp_ab_is_marked_2016=1; hp_ab_to_page_2016=old_page; cna=bypmELmKaEYCAXH33hXAwxcN; ali_beacon_id=113.247.222.21.1476157455284.500939.8; ali_apache_track=\"c_ms=1|c_mid=b2b-198760804ffe12|c_lid=pengsir_2009\"; h_keys=\"%u6d59%u6c5f%u5929%u8d5e%u7f51%u7edc%u79d1%u6280%u6709%u9650%u516c%u53f8#%u6d59%u6c5f%u5929%u8d5e#%u6d59%u6c5f%u5929%u6e5b\"; ad_prefer=\"2016/10/13 21:23:27\"; ali_ab=175.13.249.252; JSESSIONID=8L78tCuu1-UQWWlHBIvFg5FGJS8A-kPz0ZzP-GeM; ali_apache_tracktmp=\"c_w_signed=Y\"; _ITBU_IS_FIRST_VISITED_=n; _is_show_loginId_change_block_=b2b-198760804ffe12_false; _show_force_unbind_div_=b2b-198760804ffe12_false; _show_sys_unbind_div_=b2b-198760804ffe12_false; _show_user_unbind_div_=b2b-198760804ffe12_false; _ITBU_IS_FIRST_VISITED_=*xC-i2FIYvFkGMFxGvmcT%3Apm0he64ggn%7C*xC-i2FILMFkYMFvuvC-iMwIyMNTT%3Apm0he64m7i; __cn_logon__=true; __cn_logon_id__=pengsir_2009; cn_tmp=\"Z28mC+GqtZ3ntwwhrl7an6WPLEAZ/ma9yMWpMKzMTJDYd8n641RZI2d2Do0vCo6GD4h8Upfz3BcH0ldLARGGeDmL0yfOmoVktF82vvdKCXkb+j5WvVugnNGGgViYZPBb+Y8h80CLPNvogwYXvUWTTOgqNztL0y+sFqkZ+rRy7E8cuMS34baMnfg076NASjXoH1IAIZtYetAR8MfPGILICCTUAuCYAL1Uu7iwWwaJaR5/N9XLyiKKLZY8gKu4t2V6\"; _cn_slid_=vTSAlUriVk; tbsnid=tBHNkQW1JWhPE1k7%2Bo8BcLt4PLKd25jMpOUwKqRnopw6sOlEpJKl9g%3D%3D; LoginUmid=\"QWsE%2BLUrsMo2FznQLbeom%2BLf6wbCpbc022pmSeuyfl9qJGrd38HGVQ%3D%3D\"; userID=\"lUiNWaZNBBJPSVlbqP1vMiUuHhENbj4KuyHBKEF8bIk6sOlEpJKl9g%3D%3D\"; last_mid=b2b-198760804ffe12; unb=198760804; __last_loginid__=pengsir_2009; login=\"kFeyVBJLQQI%3D\"; _csrf_token=1476447808921; alicnweb=touch_tb_at%3D1476447776912%7Clastlogonid%3Dpengsir_2009; l=AmlpQvCnEc3MEDYUqmyNADEt-R/DMV1o; isg=Aj8_wnJJW3ZTB1CF7R-NBnPAzhOGi5PG6kBG2NEM3O414F9i2fQjFr3yVAfl; __rn_alert__=false; userIDNum=\"pgVXIlOgCe4DBPJBu4sTUQ%3D%3D\"; _tmp_ck_0=\"4eBTqdpOdrR9wY0ye5VVAUcn5Gm3RcRq2%2BY0C3n%2F6kwCpUR8Lh5hSPt1tnBOhKWdC%2Fqxq1fkFI2RtKAaZhjJm%2FBWs5DAWILDVh1z7LzVgFxHElKAgdgjc5oUoXBzEu7SxdBdZGlWQBh87fEmWdqIQm2z%2FzIoiLqFd%2BWM9PxgAlsa1SbcN4L4r5xUvnlDViOqQG5%2BzlvYqeZqXPCgIQ4KntCjezozI7r5hly93xCVhDIS55MibcxQ80EwCagoBLYDoEPcGSnyAxRejCn3kz24pZ%2FhVDlyjbUdYtqG1CQBckQu5HHPE7eL5M6jAcukoSnYECQa2WFMJ5Kr3CG%2FKWR4irhzFEl6YhXzGYL%2BY7WmDHNdu3oAJmZ00pDZROc9%2FWuBgGfnwabagalE56NELxsIFoQWEOF8%2FMBgHHPbjMQmUcI8Oa52I7ehqrIvCWaA%2F2yevI26Z%2Fn7WVHPN0%2F8dLgOKXxf4Ns1XdEaBfPKTpWGfILsmBjMn9XvcHyKhe%2FaM8T6tumhMCpRfC2%2FAxgqJkmql8xrEhXhbCELfBijRC7Yv8jyTUCtopnD%2Fg%3D%3D\"; _nk_=\"2k%2BGi15Qpi1XwwWy6F3lLg%3D%3D\"";
			// headParams.put("Cookie", cookie);
			int i = 1;
			while ((line = br.readLine()) != null) {
				System.out.println("i=" + i++);
				if (line.trim().length() < 10) {
					continue;
				}
				executor.execute(new RunThread(line));
			}
			br.close();

		} catch (Exception e) {

		}
	}

	public static void testTemp() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("D:\\data\\1688_getnewurl - 副本 (5).txt"));
			String line = "";
		  
			int i = 1;
			Map<String, String> map = new HashMap<String, String>();
			
			
			while ((line = br.readLine()) != null) {
				System.out.println("i=" + i++);
				String[] lines = line.split("\\|");
				String url1 = lines[0];
				String url2 = lines[1];
				map.put(url1, url2);
			}
			br.close();
			br = new BufferedReader(new FileReader("D:\\data\\1688_getphone_new3.txt"));
			line = "";
			Map<String,String> map2 = new HashMap<String,String>();
			while((line = br.readLine())!=null){
				String[] lines = line.split("\\|");
				if(lines.length==3){
					String url1 = lines[0];
					String phone = lines[2];
				
					String url2 = map.get(url1);
					map2.put(url2, phone);
				}
				
			}
			
			br.close();
			br = new BufferedReader(new FileReader("D:\\data\\newurl2.txt"));
			line = "";
			
			while((line = br.readLine())!=null){
				
				 if(map2.get(line)!=null){
					 String url = map.get(line);
					 
					 fw.write(map2.get(line)+"\n");
					 fw.flush();
				 }else{
					 fw.write("\n");
				 }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void moidfy() {
		try {

			// BufferedReader br = new BufferedReader(new
			// FileReader("D:\\data\\1688_companydetail333.txt"));
			// String line1 = "";
			// Set<String> set = new HashSet<String>();
			// while((line1=br.readLine())!=null){
			// set.add(line1);
			// }
			// br.close();
			BufferedReader br = new BufferedReader(new FileReader("D:\\data\\1688_companydetail22.txt"));
			String line1 = "";
			while ((line1 = br.readLine()) != null) {

				if (line1.trim().length() < 10) {
					continue;
				}
				fw.write(line1 + "\n");
				// boolean find = false;
				// for(String line : set){
				// if(line1.startsWith(line)){
				// find=true;
				// break;
				// }
				// }
				// if(!find){
				// fw.write(line1+"\n");
				// }
			}
			fw.flush();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void runContact() {
		try {

			BufferedReader br = new BufferedReader(new FileReader("D:\\data\\1688url2.txt"));
			String line = "";

			int i = 1;
			while ((line = br.readLine()) != null) {

				executor.execute(new RunThread(line));
			}
			// url="http://www.lemall.com/cn/sale/heise919/miaosha.html?1474032336135&ref=cn:cn:sale:heise919:ecocp:256";

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void runUrl() {

		String url = "https://yy.1688.com/search/offer.htm?spm=0.0.0.0.iFYtkT&page=";
		// url="http://www.lemall.com/cn/sale/heise919/miaosha.html?1474032336135&ref=cn:cn:sale:heise919:ecocp:256";
		Map<String, String> headParams = new HashMap<String, String>();
		headParams.put("Host", "newhouse.sh.fang.com");
		headParams.put("Proxy-Connection", "keep-alive");
		headParams.put("Accept", "*/*");
		headParams.put("Accept-Language", "zh-CN,en-US;q=0.8,en;q=0.6");
		headParams.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		headParams.put("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36");
		headParams.put("Accept-Encoding", "gzip, deflate, sdch");// "gzip,
																	// deflate,
																	// sdch");
		headParams.put("Cookie", Test.cookie);
		// ysudu.com
		for (int i = 1; i <= 272; i++) {
			Proxy proxy = getProxy();
			System.out.println("proxy=" + proxy + ";i=" + i);
			try {
				String newurl = url + i;
				String body = HttpProxy.getHttpRequestContentByGet(newurl, proxy, headParams);
				while (body == null || body.length() < 100) {
					proxy = getProxy();
					System.out.println("proxy=" + proxy);
					newurl = url + i;
					body = HttpProxy.getHttpRequestContentByGet(newurl, proxy, headParams);
				}
				Document doc = Jsoup.parse(body);
				Elements eles = doc.getElementsByClass("title");
				for (Element ele : eles) {
					String geturl = ele.getElementsByAttribute("href").get(0).attr("href");
					if (geturl.contains("detail")) {
						System.out.println(geturl);
						fw.write(geturl + "\n");
						fw.flush();
					}
				}
				// System.out.println("body = " +body);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static void runDetail() {
		String url = "https://hzduohao8.1688.com/page/contactinfo.htm?spm=a261y.7663282.0.0.3Gs1Ia";
		// url="http://www.lemall.com/cn/sale/heise919/miaosha.html?1474032336135&ref=cn:cn:sale:heise919:ecocp:256";
		Map<String, String> headParams = new HashMap<String, String>();
		headParams.put("Host", "newhouse.sh.fang.com");
		headParams.put("Proxy-Connection", "keep-alive");
		headParams.put("Accept", "*/*");
		headParams.put("Accept-Language", "zh-CN,en-US;q=0.8,en;q=0.6");
		headParams.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		headParams.put("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36");
		headParams.put("Accept-Encoding", "gzip, deflate, sdch");// "gzip,
																	// deflate,
																	// sdch");
		headParams.put("Cookie", Test.cookie);
		// ysudu.com
		Proxy proxy = null;// getProxy();
		System.out.println("proxy=" + proxy);
		try {
			String body = HttpProxy.getHttpRequestContentByGet(url, proxy, headParams);
			System.out.println("body = " + body);
		} catch (Exception e) {
			e.printStackTrace();
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

	public static String getValueWithNull(Object value) {
		if (value == null) {
			return "";
		} else {
			return value.toString();
		}
	}

}

class RunThread implements Runnable {
	String line;
	FileWriter fw;

	public RunThread(String line) {
		this.line = line;
		fw = Test.fw;
	}

	@Override
	public void run() {

		try {

			Map<String, String> headParams = new HashMap<String, String>();
			headParams.put("Host", "detail.1688.com");
			headParams.put("Connection", "keep-alive");
			headParams.put("Pragma", "no-cache");
			headParams.put("Cache-Control", "no-cache");
			headParams.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.");
			headParams.put("Upgrade-Insecure-Requests", "1");
			headParams.put("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36");
			headParams.put("Accept-Encoding", "gzip, deflate, sdch");// "gzip,
			headParams.put("Accept-Language", "zh-CN,zh;q=0.8");

			// deflate,
			// sdch");
			String cookie = "hp_ab_is_marked_2016=1; hp_ab_to_page_2016=old_page; cna=bypmELmKaEYCAXH33hXAwxcN; ali_beacon_id=113.247.222.21.1476157455284.500939.8; ali_apache_track=\"c_ms=1|c_mid=b2b-198760804ffe12|c_lid=pengsir_2009\"; h_keys=\"%u6d59%u6c5f%u5929%u8d5e%u7f51%u7edc%u79d1%u6280%u6709%u9650%u516c%u53f8#%u6d59%u6c5f%u5929%u8d5e#%u6d59%u6c5f%u5929%u6e5b\"; ad_prefer=\"2016/10/13 21:23:27\"; ali_ab=175.13.249.252; JSESSIONID=8L78tCuu1-UQWWlHBIvFg5FGJS8A-kPz0ZzP-GeM; ali_apache_tracktmp=\"c_w_signed=Y\"; _ITBU_IS_FIRST_VISITED_=n; _is_show_loginId_change_block_=b2b-198760804ffe12_false; _show_force_unbind_div_=b2b-198760804ffe12_false; _show_sys_unbind_div_=b2b-198760804ffe12_false; _show_user_unbind_div_=b2b-198760804ffe12_false; _ITBU_IS_FIRST_VISITED_=*xC-i2FIYvFkGMFxGvmcT%3Apm0he64ggn%7C*xC-i2FILMFkYMFvuvC-iMwIyMNTT%3Apm0he64m7i; __cn_logon__=true; __cn_logon_id__=pengsir_2009; cn_tmp=\"Z28mC+GqtZ3ntwwhrl7an6WPLEAZ/ma9yMWpMKzMTJDYd8n641RZI2d2Do0vCo6GD4h8Upfz3BcH0ldLARGGeDmL0yfOmoVktF82vvdKCXkb+j5WvVugnNGGgViYZPBb+Y8h80CLPNvogwYXvUWTTOgqNztL0y+sFqkZ+rRy7E8cuMS34baMnfg076NASjXoH1IAIZtYetAR8MfPGILICCTUAuCYAL1Uu7iwWwaJaR5/N9XLyiKKLZY8gKu4t2V6\"; _cn_slid_=vTSAlUriVk; tbsnid=tBHNkQW1JWhPE1k7%2Bo8BcLt4PLKd25jMpOUwKqRnopw6sOlEpJKl9g%3D%3D; LoginUmid=\"QWsE%2BLUrsMo2FznQLbeom%2BLf6wbCpbc022pmSeuyfl9qJGrd38HGVQ%3D%3D\"; userID=\"lUiNWaZNBBJPSVlbqP1vMiUuHhENbj4KuyHBKEF8bIk6sOlEpJKl9g%3D%3D\"; last_mid=b2b-198760804ffe12; unb=198760804; __last_loginid__=pengsir_2009; login=\"kFeyVBJLQQI%3D\"; _csrf_token=1476447808921; alicnweb=touch_tb_at%3D1476447776912%7Clastlogonid%3Dpengsir_2009; l=AmlpQvCnEc3MEDYUqmyNADEt-R/DMV1o; isg=Aj8_wnJJW3ZTB1CF7R-NBnPAzhOGi5PG6kBG2NEM3O414F9i2fQjFr3yVAfl; __rn_alert__=false; userIDNum=\"pgVXIlOgCe4DBPJBu4sTUQ%3D%3D\"; _tmp_ck_0=\"4eBTqdpOdrR9wY0ye5VVAUcn5Gm3RcRq2%2BY0C3n%2F6kwCpUR8Lh5hSPt1tnBOhKWdC%2Fqxq1fkFI2RtKAaZhjJm%2FBWs5DAWILDVh1z7LzVgFxHElKAgdgjc5oUoXBzEu7SxdBdZGlWQBh87fEmWdqIQm2z%2FzIoiLqFd%2BWM9PxgAlsa1SbcN4L4r5xUvnlDViOqQG5%2BzlvYqeZqXPCgIQ4KntCjezozI7r5hly93xCVhDIS55MibcxQ80EwCagoBLYDoEPcGSnyAxRejCn3kz24pZ%2FhVDlyjbUdYtqG1CQBckQu5HHPE7eL5M6jAcukoSnYECQa2WFMJ5Kr3CG%2FKWR4irhzFEl6YhXzGYL%2BY7WmDHNdu3oAJmZ00pDZROc9%2FWuBgGfnwabagalE56NELxsIFoQWEOF8%2FMBgHHPbjMQmUcI8Oa52I7ehqrIvCWaA%2F2yevI26Z%2Fn7WVHPN0%2F8dLgOKXxf4Ns1XdEaBfPKTpWGfILsmBjMn9XvcHyKhe%2FaM8T6tumhMCpRfC2%2FAxgqJkmql8xrEhXhbCELfBijRC7Yv8jyTUCtopnD%2Fg%3D%3D\"; _nk_=\"2k%2BGi15Qpi1XwwWy6F3lLg%3D%3D\"";
			Proxy proxy = getProxy();

			String body = HttpProxy.getHttpsRequestContentByGet(line, proxy, headParams);

			Document doc = Jsoup.parse(body);
			Elements eles = doc.getElementsByAttribute("data-page-name");
			for (Element ele : eles) {
				if (ele.attr("data-page-name").equals("contactinfo")) {
					String href = ele.getElementsByTag("a").first().attr("href");
					System.out.println("href=" + href);
					fw.write(line + "|" + href + "\n");
					fw.flush();
				}
			}
			// fw.close();
		} catch (Exception e) {

		}

	}

	public static Proxy getProxy() {
		try {
			String[] ips = { "115.159.126.103", "123.206.194.143", "115.159.29.75", "119.29.241.205", "119.29.242.109",
					"119.29.244.218", "119.29.245.126", "119.29.244.202", "123.206.198.188", "115.29.165.122",
					"119.29.245.90" };
			int index = new Random().nextInt(ips.length);
			String ip = ips[index];
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, 10001));
			return proxy;
		} catch (Exception e) {
			return null;
		}

	}
}