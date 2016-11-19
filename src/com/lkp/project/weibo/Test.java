/*
 * Copyright (c) SHADANSOU 2014 All Rights Reserved
 *
 */
package com.lkp.project.weibo;

import org.apache.solr.client.solrj.response.RangeFacet.Date;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.lkp.common.util.DateUtil;
import com.lkp.common.util.HtmlUtil;

/**
 * <p>
 * class function description.
 * <p>
 *
 * create 2016骞�鏈�0鏃�br>
 * 
 * @author lkp<br>
 * @version 1.0
 * @since 1.0
 */
public class Test {
	public static void main(String[] args) {

		// String s = "aa\\n\\t\\n";
		// System.out.println("s="+s);
		// s = s.replace("\\n", "");
		// System.out.println("s="+s);
		run();

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

	public static void run2(Entity entity) {
		// http://weibo.com/u/2695651355
		String url = "http://weibo.com/u/" + entity.getSid();
		// url = "http://tool.chinaz.com/";
		//String cookie = "SINAGLOBAL=6318676043301.82.1452560762318; _s_tentry=-; Apache=4302209101151.675.1457591869487; ULV=1457591869505:22:6:4:4302209101151.675.1457591869487:1457583273563; SWB=usrmdinst_26; WBtopGlobal_register_version=8a840560e41b693d; un=280740960@qq.com; myuid=2758792962; login_sid_t=4b14e037ee2e4614cb9a4f2fbe6c6ff9; appkey=; SSOLoginState=1457592593; wvr=6; WBStore=8b23cf4ec60a636c|undefined; user_active=201603101458; user_unver=3f5f2fb4f167a93ea51d14eb5d8945ec; SUS=SID-5879083593-1457593133-GZ-bxnko-f4326c979d4836daafe77ce10701c505; SUE=es%3D880e25826d218932ebdede22e134c7ef%26ev%3Dv1%26es2%3D2f30f12e1487061e5eda4276061a7efa%26rs0%3Dm%252BvIIWSW0HAyVPI1JMn6257Tz3GTQ3of6awv%252FE5jnc2gS2tjrAj3ZcHaqjj4%252BbNP5n3C3StmmLClpyExO01VQvP2xiKP4332Euh%252FJiirGLciUq2%252Bnsb0hs42uRl%252FbxR%252BoXOWeSebGD5q2ItsY5qA1ypPbqH3tKks1yxZZuL28aE%253D%26rv%3D0; SUP=cv%3D1%26bt%3D1457593133%26et%3D1457679533%26d%3Dc909%26i%3Dc505%26us%3D1%26vf%3D0%26vt%3D0%26ac%3D0%26st%3D0%26uid%3D5879083593%26name%3D15084947675%2540163.com%26nick%3D%25E6%2596%2587%25E8%2589%25BA%25E8%258C%2583llkk%26fmp%3D%26lcp%3D; SUB=_2A2575Wt9DeTxGeNG7FsR-C3Jwj-IHXVYk9u1rDV8PUNbu9BeLW7kkW9LHesmFHnGCIkvCaGz8se3_ZopurOuhw..; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WhiVxAjUfY6dONNK5GTyXTN5JpX5Kzt; SUHB=0d4uvcW6WAJbq2; ALF=1489129132; UOR=,,login.sina.com.cn";
		String ss = com.lkp.proxy.HttpProxy.getResponseByGet(url, CookiePool.getCookie());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		write("ff.txt",ss);
		Document doc = HtmlUtil.getDoc(ss);
		Elements el = doc.getElementsByTag("script");
		for (Element ele : el) {
			if (ele.data().contains("domid\":\"Pl_Core_T8CustomTriColumn__3\"")) {
				getFans(ele.data(), entity);
				break;
			}
		}
		int index = ss.indexOf("WB_cardmore S_txt1 S_line1 clearfix");
		ss = ss.substring(index);
		index = ss.indexOf("\\/p\\/");

		String pid = ss.substring(index + 5, index + 21);
		String newurl = "http://weibo.com/p/" + pid + "/info?mod=pedit_more";
		
		ss = com.lkp.proxy.HttpProxy.getResponseByGet(newurl, CookiePool.getCookie());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("ss=  ");

		doc = HtmlUtil.getDoc(ss);
		el = doc.getElementsByTag("script");
		for (Element ele : el) {
			if (ele.data().contains("鍩烘湰淇℃伅")) {
				getBasicInfo(ele.data(), entity);
			}
		}

		// try {
		// FileWriter fw = new FileWriter("dd.txt");
		// fw.write(ss);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// Elements el = doc.getElementsByTag("script");
		// for(Element ele : el){
		//
		// if(ele.data().contains("\"pid\":\"pl_weibo_direct\"")){
		// getContentAndId(ele.data());
		// }
		// }
	}

	public static void getFans(String result, Entity entity) {
		String flag = "<strong class=\\\"W_f18\\\">";
		int index = result.indexOf(flag);
		if (index == -1) {
			flag = "<strong class=\\\"W_f16\\\">";
			index = result.indexOf(flag);
		}
		result = result.substring(index + 24);
		index = result.indexOf("<");
		String gznum = result.substring(0, index);
		System.out.println("gznum=" + gznum);
		result = result.substring(index);
		index = result.indexOf(flag);
		result = result.substring(index + 24);
		index = result.indexOf("<");
		String fsnum = result.substring(0, index);
		System.out.println("fsnum=" + fsnum);
		index = result.indexOf(flag);
		result = result.substring(index + 24);
		index = result.indexOf("<");
		String wbnum = result.substring(0, index);
		System.out.println("wbnum=" + wbnum);

		entity.setGzs(gznum);
		entity.setFans(fsnum);
		entity.setWebos(wbnum);

	}

	public static void getBasicInfo(String result, Entity entity) {
		String copyresult = result;
		int index = copyresult.indexOf("鏄电О锛�);
		result = copyresult.substring(index + 37);

		index = result.indexOf("<");
		String nname = result.substring(0, index);

		System.out.println("nname=" + nname);

		index = result.indexOf("鎵�湪鍦�);
		result = result.substring(index + 38);

		index = result.indexOf("<");
		String address = result.substring(0, index);

		System.out.println("address=" + address);

		index = result.indexOf("鎬у埆");
		result = result.substring(index + 37);

		index = result.indexOf("<");
		String sex = result.substring(0, index);

		System.out.println("sex=" + sex);

		index = result.indexOf("娉ㄥ唽鏃堕棿");
		result = result.substring(index + 103);

		index = result.indexOf("<");
		String regdate = result.substring(0, 10);

		System.out.println("regdate" + regdate);
		entity.setAddress(address);
		entity.setSex(sex);
		entity.setResdate(regdate);
		entity.setPname(nname);

	}

	public static void run() {
		String date = "2015-07-22";
		Date d = DateUtil.strToDate(date, DateUtil.DATE_FORMAT_YYYY_MM_DD);
		String date2 = "2015-08-22";
		Date d2 = DateUtil.strToDate(date2, DateUtil.DATE_FORMAT_YYYY_MM_DD);
		String baseurl = "http://s.weibo.com/weibo/%25E5%25A4%25B4%25E6%2599%2595&region=custom:11:1000&typeall=1&suball=1&Refer=g";

		for (long start = d.getTime(); start <= d2.getTime(); start = start + 86400000) {
			List<Entity> entityList = new ArrayList<Entity>();
			String newdate = DateUtil.dateTimeToStr(new Date(start), DateUtil.DATE_FORMAT_YYYY_MM_DD);
			String url = baseurl + "&timescope=custom:" + newdate + ":" + newdate;// 2015-08-15:2015-08-15";
			for (int i = 2; i <= 14; i++) {

				url = url + "&page=" + i;
				// url = "http://tool.chinaz.com/";
				String cookie = "SINAGLOBAL=6318676043301.82.1452560762318; wb_feed_unfolded_undefined=1; wb_feed_unfolded_5879083593=1; wb_feed_folded_5879083593=1; YF-Page-G0=d52660735d1ea4ed313e0beb68c05fc5; YF-Ugrow-G0=ad83bc19c1269e709f753b172bddb094; _s_tentry=login.sina.com.cn; Apache=6177465291693.807.1457657436393; ULV=1457657436405:24:8:6:6177465291693.807.1457657436393:1457608386658; YF-V5-G0=8c4aa275e8793f05bfb8641c780e617b; wb_publish_vip_5879083593=1; WBtopGlobal_register_version=8a840560e41b693d; myuid=5879083593; login_sid_t=c6ecc6379083a3ceaeb008d361a5f12f; UOR=,,login.sina.com.cn; SUS=SID-2758792962-1457657679-GZ-6iw9l-7a51a8be76b8d424caab9acc4aefc505; SUE=es%3Dba880faaad938cafe119cc3ce225c816%26ev%3Dv1%26es2%3D4acfefa9af64fb614fd633933c8173ce%26rs0%3DdnK2cVAs7ny%252BXlp0xGfz7PtUwVJQD6jjW%252FGJUF%252Bqv%252F2Izi8PJLQzyyrFVJveVv0e%252BJJ%252FmPviDx3%252B3TAQkMzmFVFWDmyyTHrL8TZBI8ZJMplfzrCxmLAS5fq1NU%252BFV8tgvIUK30576d1vkxdftTewVc7EmyQnE289awloUV0kP1w%253D%26rv%3D0; SUP=cv%3D1%26bt%3D1457657679%26et%3D1457744079%26d%3Dc909%26i%3Dc505%26us%3D1%26vf%3D0%26vt%3D0%26ac%3D0%26st%3D0%26uid%3D2758792962%26name%3D280740960%2540qq.com%26nick%3Dfffff%26fmp%3D%26lcp%3D2015-04-22%252016%253A04%253A37; SUB=_2A2575mcfDeRxGeRJ7loW-SzFzT6IHXVYkt_XrDV8PUNbuNBeLUHDkW9LHet1Fh3e63sjcALpNpJgn4eA4LH7Fg..; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WW45vfh24Z3Z47qXM5wMQ6R5JpX5K2t; SUHB=0BLpia-LSax2pj; ALF=1489193679; SSOLoginState=1457657679; un=280740960@qq.com; wvr=6; wb_publish_vip_2758792962=1; wb_feed_unfolded_2758792962=1";
				 cookie = "SINAGLOBAL=6318676043301.82.1452560762318; un=15084947675@163.com; myuid=2758792962; UOR=,,login.sina.com.cn; login_sid_t=8ab37a2f5292cdf84abf15b02f82ea53; _s_tentry=-; Apache=6291132301557.81.1457678525122; ULV=1457678525130:25:9:7:6291132301557.81.1457678525122:1457657436405; SUS=SID-5879225235-1457678319-GZ-qh28p-b7ae0200665fc47eb7859daf6046c505; SUE=es%3D30c4296a77ac3156ee0e09264343f263%26ev%3Dv1%26es2%3D32e26a38c5b296b0ed8b984d1ae61f5c%26rs0%3DqTdLWsJb8CWNdcCzjR7mCbcZxqDIrAWzcaQd8fVVifobnBCMvOFY3DQu%252FaFdIZiCQVeEmWMU8Y%252BaeZ0woxnczX%252FHA60l80b9qCUkB326zPxM6DifKPTyoSx8Wdbdlj6ybW33r35f6gs%252B9hVyiMfKb0Hjp1%252Fr6jIPdMGPs%252FT3x%252B4%253D%26rv%3D0; SUP=cv%3D1%26bt%3D1457678319%26et%3D1457764719%26d%3Dc909%26i%3Dc505%26us%3D1%26vf%3D0%26vt%3D0%26ac%3D0%26st%3D2%26uid%3D5879225235%26name%3Dxuweilkp%2540163.com%26nick%3Dfsdfsa2222%26fmp%3D%26lcp%3D; SUB=_2A2575he_DeTRGeNG7FsT8ivOyDmIHXVYkg53rDV8PUNbuNBeLWLskW9LHesRxHUL7NG7FbI3P2mL16WTM5zDMQ..; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFmp3EaB8Jz5nxFA4P-JjwJ5JpX5K2t; SUHB=0TPFtmKxF8-YRN; ALF=1489214319; SSOLoginState=1457678319; un=xuweilkp@163.com; wvr=6; SWB=usrmdinst_27; WBStore=8b23cf4ec60a636c|undefined";
				String ss = com.lkp.proxy.HttpProxy.getResponseByGet(url, CookiePool.getCookie());

				if (ss.contains("\\u60a8\\u53ef\\u4ee5\\u5c1d\\u8bd5\\u66f4\\u6362\\u5173\\u952e\\u8bcd")) {
					break;
				}
				// System.out.println("ss="+ss);
				// try {
				// FileWriter fw = new FileWriter("ee.txt");
				// fw.write(ss);
				// fw.flush();
				// } catch (IOException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				Document doc = HtmlUtil.getDoc(ss);
				Elements el = doc.getElementsByTag("script");
				for (Element ele : el) {

					if (ele.data().contains("\"pid\":\"pl_weibo_direct\"")) {

						getContentAndId(ele.data(), entityList);

					}
				}
			}
			System.out.println("entityList=" + entityList.size());
			exportXls(newdate, entityList);
			entityList.clear();
		}

	}

	public static void getContentAndId(String result, List<Entity> entityList) {
		// <em class=\"red\">
		int i = 0;
		while (result.contains("comment_txt")) {
			try {

				Entity entity = new Entity();
				String res = getMid(result);
				String mid = res.split("\\|")[0];
				String index = res.split("\\|")[1];
				result = result.substring(Integer.parseInt(index));

				res = getContent(result);
				String content = res.split("\\|")[0];
				index = res.split("\\|")[1];
				result = result.substring(Integer.parseInt(index));

				res = getPulishDate(result);
				String pubDate = res.split("\\|")[0];
				index = res.split("\\|")[1];
				result = result.substring(Integer.parseInt(index));

				res = getzfs(result);
				String zfs = res.split("\\|")[0];
				index = res.split("\\|")[1];
				if (zfs.length() == 0) {
					zfs = "0";
				}
				res = getPls(result);
				String pls = res.split("\\|")[0];
				index = res.split("\\|")[1];
				if (pls.length() == 0) {
					pls = "0";
				}

				res = getSid(result);
				String sid = res.split("\\|")[0];
				index = res.split("\\|")[1];
				result = result.substring(Integer.parseInt(index));

				System.out
						.println("sid=" + sid + ";mid= " + mid + ";content=" + content + ";zfs=" + zfs + ";pls=" + pls);
				entity.setSid(sid);
				entity.setWid(mid);
				entity.setContent(content);
				entity.setZfs(zfs);
				entity.setPls(pls);
				entity.setPubdate(pubDate);
				run2(entity);
				entityList.add(entity);
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		// p class=\"comment_txt\
	}

	public static String getPulishDate(String content) {
		int index = content.indexOf("date=\\\"");
		String pusdate = content.substring(index - 19, index - 3);
		return pusdate + "|" + (index + 6);
	}

	public static String getPls(String content) {
		// \u6536\u85cf
		int index = content.indexOf("\\u8bc4\\u8bba");
		content = content.substring(index);
		String temp = content.substring(index, index + 16);
		if (temp.contains("em")) {
			content = content.substring(index + 16);
		} else {
			content = content.substring(index + 12);
		}

		int index2 = content.indexOf("<");
		String pls = content.substring(0, index2);
		return pls + "|" + (index + 16 + index2);
	}

	public static String getzfs(String content) {
		// \u6536\u85cf
		int index = content.indexOf("\\u8f6c\\u53d1");
		content = content.substring(index + 16);
		int index2 = content.indexOf("<");
		String pls = content.substring(0, index2);
		if (pls.length() > 3) {
			pls = pls.substring(0, 2);
		}
		return pls + "|" + (index + 16 + index2);
	}

	public static String getSid(String content) {
		int index = content.indexOf("&uid=");
		String sid = content.substring(index + 5, index + 15);
		return sid + "|" + (index + 15);
	}

	public static String getMid(String content) {
		int index = content.indexOf("div mid=");
		String mid = content.substring(index + 10, index + 26);
		return mid + "|" + (index + 26);
	}

	public static String getContent(String content) {
		content = content.replaceAll("<em class=\\\\\"red\\\\\">", "");
		content = content.replaceAll("<\\\\/em>", "");
		int index = content.indexOf("p class=\\\"comment_txt\\\"");

		content = content.substring(index);

		index = content.indexOf(">");
		content = content.substring(index + 1);
		int index2 = content.indexOf("<\\/p>");
		content = content.substring(0, index2);

		content = content.replace("\\n", "");
		content = content.replace("\\t", "");
		return ascii2native(content) + "|" + (index + index2);
	}

	public static String replaceWithBlank(String str) {

		Pattern p = Pattern.compile("\\s*|\t|\r|\n");

		Matcher m = p.matcher(str);

		String finishedReplaceStr = m.replaceAll("");

		return finishedReplaceStr;

	}

	public static String ascii2native(String ascii) {
		String[] ss = ascii.split("\\\\u");
		String newasc = "";
		for (String s : ss) {
			if (s.length() >= 4) {
				newasc += "\\u" + s.substring(0, 4);
			}
		}
		int n = newasc.length() / 6;
		StringBuilder sb = new StringBuilder(n);
		for (int i = 0, j = 2; i < n; i++, j += 6) {
			String code = newasc.substring(j, j + 4);
			try {
				char ch = (char) Integer.parseInt(code, 16);
				sb.append(ch);
			} catch (Exception e) {
				System.out.println();
			}

		}
		return sb.toString();
	}

	public static void exportXls(String date, List<Entity> list) {
		List exportData = new ArrayList<Map>();
		for (Entity entity : list) {

			/*
			 * //寰崥甯愬彿id 寰崥鍗氭枃id 寰崥鏄电О 鍙戣〃鏃堕棿 杞彂閲�璇勮閲�寰崥鍐呭 娉ㄥ唽鏃堕棿 鎬у埆 鍦扮偣 绮変笣鏁�鍏虫敞鏁�寰崥鏁�
			 * 鏄惁鍔燰 璁よ瘉鍘熷洜 浜掔矇鏁�缁忓害 缁村害 鍏抽敭璇�
			 */

			Map row1 = new LinkedHashMap<String, String>();
			row1.put("1", getValueWithNull(entity.getSid()));
			row1.put("2", getValueWithNull(entity.getWid()));
			row1.put("3", getValueWithNull(entity.getPname()));
			row1.put("4", getValueWithNull(entity.getPubdate()));

			row1.put("5", getValueWithNull(entity.getZfs()));
			row1.put("6", getValueWithNull(entity.getPls()));
			row1.put("7", getValueWithNull(entity.getContent()));
			row1.put("8", getValueWithNull(entity.getResdate()));
			row1.put("9", getValueWithNull(entity.getSex()));
			row1.put("10", getValueWithNull(entity.getAddress()));
			row1.put("11", getValueWithNull(entity.getFans()));
			row1.put("12", getValueWithNull(entity.getGzs()));
			row1.put("13", getValueWithNull(entity.getWebos()));
			row1.put("14", getValueWithNull(entity.getIsV()));
			row1.put("15", getValueWithNull(entity.getAuthReson()));
			row1.put("16", getValueWithNull(entity.getEachFans()));
			row1.put("17", getValueWithNull(entity.getLongitude()));
			row1.put("18", getValueWithNull(entity.getLatitude()));
			row1.put("19", getValueWithNull(entity.getKeywords()));
			exportData.add(row1);
		}
		LinkedHashMap map = new LinkedHashMap();
		map.put("1", "寰崥璐﹀彿ID");
		map.put("2", "鍗氭枃ID");
		map.put("3", "鏄电О");
		map.put("4", "鍙戣〃鏃堕棿");
		map.put("5", "杞彂閲�);
		map.put("6", "璇勮閲�);
		map.put("7", "寰崥鍐呭");
		map.put("8", "娉ㄥ唽鏃堕棿");
		map.put("9", "鎬у埆");
		map.put("10", "鍦扮偣");
		map.put("11", "绮変笣鏁�);
		map.put("12", "鍏虫敞鏁�);
		map.put("13", "寰崥鏁�);
		map.put("14", "鏄惁鍔燰");
		map.put("15", "璁よ瘉鍘熷洜");
		map.put("16", "浜掔矇鏁�);
		map.put("17", "缁忓害");
		map.put("18", "绾害");
		map.put("19", "鍏抽敭璇�);
		String path = "D:/workspace_lkp/crawler_task/";
		String fileName = "鏂囦欢瀵煎嚭" + date;
	 File file = FileUtil.createCSVFile(exportData, map, path, fileName);

	}

	public static String getValueWithNull(Object value) {
		if (value == null) {
			return "";
		} else {
			return value.toString();
		}
	}
}
