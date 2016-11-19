/*
 * Copyright (c) SHADANSOU 2014 All Rights Reserved
 *
 */
package com.lkp.project.tianyancha;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Proxy;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

import com.httpclient.utils.ttc;
import com.lkp.common.net.HttpProxy;
import com.mysql.jdbc.MySQLConnection;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 乐视商城抢购
 * 
 * @author lkp
 *
 */
public class TYCMain { 
	private static final Log LOGGER = LogFactory.getLog(TYCMain.class);
	private static final LinkedBlockingDeque<Runnable> taskQueue = new LinkedBlockingDeque<Runnable>(10);
	private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 6, 20, TimeUnit.SECONDS, taskQueue,
			new ThreadPoolExecutor.CallerRunsPolicy());

	public static String cookie = "pgv_pvi=605905920; tj2_lc=5fa15b2620c541ee165a1aa1fb6cebd8; COOKIE_CPS_ID=C_yiqifa_6611_NzU4MDYwfDAwODY5MzA0OTIyMmQ5ZjAwNzNj; COOKIE_USER_CITY=; COOKIE_THIRDPARTY_ID=233296357; COOKIE_TOKEN_ID_N=9a4b80b7339dce42e11c54dbe8d9535c; COOKIE_USER_IP=10.183.100.91; COOKIE_USER_NAME=97477c2980103f022e6c5e495e21d318; COOKIE_NICKNAME=150xxxxx675_516_358; COOKIE_USER_LEVEL_ID=1; COOKIE_SESSION_ID=2495EC11CB9530433170F6EC4B13BAED; COOKIE_TOKEN_ID=7cccb8417be6216870534f1225d4567d; COOKIE_TOKEN_DATE=1475244238337; COOKIE_USER_ID=2233296357; COOKIE_USER_TYPE=1; COOKIE_LOGIN_TYPE=5; COOKIE_USER_INFO=150xxxxx675_516_358%5E28*****60%40qq.com%5E150*****675%5E233296357%5Ehttp%3A%2F%2Fi1.letvimg.com%2Flc06_user%2F201605%2F09%2F15%2F00%2F1923203311462777250.jpg%2Chttp%3A%2F%2Fi1.letvimg.com%2Flc04_user%2F201605%2F09%2F15%2F00%2F1923203311462777256_200_200.jpg%2Chttp%3A%2F%2Fi1.letvimg.com%2Flc04_user%2F201605%2F09%2F15%2F00%2F1923203311462777256_70_70.jpg%2Chttp%3A%2F%2Fi1.letvimg.com%2Flc04_user%2F201605%2F09%2F15%2F00%2F1923203311462777256_50_50.jpg%5E; webtrekk_cookie_record=1; pgv_si=s6365890560; tj_sg=1; sourceUrl=http%3A//www.lemall.com/product/products-pid-GWGT400143.html%3Fref%3Dcn%3Acn%3Asale%3Aguoqingjie%3A327; COOKIE_CHECK_TIME=1475285754015; __xsptplus104=104.5.1475283197.1475286098.47%234%7C%7C%7C%7C%7C%23%23YzxAgxXuaFiSPLt7IoLh7EZAVpJ0Jy76%23; tj_sid=3e5d0b154f1ff3056ba560303cce4a7f; tj_lc=1EE98D50C5010A0DA4C43A0D5CDD84D6274D1A18";
	public static String keywords = "游戏";
	static Map<String, String> headParams = new HashMap<String, String>();
	public static int successnum = 0;

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
	 //	insertCompanyFromScope();
		//  mergeResult();
		while(true){
			runBasic2();
		}
		  
		// new Thread(new RunThread()).start();
		// runYearReport();
		//getDetail2();
	//  getDetail();
		// SeleniumUtil.init();
		// MysqlUtil.init();
		// BaduUtil.init();
		// for(int i=0;i<5;i++)
		// executor.execute(new RunThread());
		// PhoneUtil.init();
		// QYSDUtil.init();

		// for(int i=0;i<3;i++){
		// executor.execute(new RunThread());
		// }
	}

	/**
	 * 合并几次的结果
	 */
	public static void mergeResult(){
		ExcelUtil.readAllResultFromExcelByXls();
	}
	
	public static void insertCompanyFromScope(){
		MysqlUtil.init();
		while(true){
			Map<String,String> companyMap = MysqlUtil.getCompanyList();
			Map<String, String> newmap = new HashMap<String, String>();
			// System.out.println("companyMap-="+companyMap);
			List<CompanyDto> companyList =new ArrayList<CompanyDto>();
			for (String id : companyMap.keySet()) { 
				String content = companyMap.get(id);
				try{
					JSONObject json = JSONObject.fromObject(content);
					JSONObject companyJson = json.getJSONObject("data");
					JSONObject baseInfoJson = companyJson.getJSONObject("baseInfo");
					CompanyDto company = new CompanyDto();
					companyList.add(company);
					company.setId(id);
					company.setBase(baseInfoJson.get("base")+"");
				//	company.setCreditCode(json.getString("creditCode"));
					company.setRegNumber(baseInfoJson.get("regNumber")+"");
					if(baseInfoJson.get("creditCode")!=null){
						company.setCreditCode(baseInfoJson.get("creditCode")+"");
					}
					
					company.setPostalAddress(baseInfoJson.get("regLocation")+"");
					
					company.setLegalPersonName(baseInfoJson.get("legalPersonName")+"");
					company.setIndustry(baseInfoJson.get("industry")+"");
					company.setScope(baseInfoJson.get("businessScope")+"");

					company.setName(baseInfoJson.get("name")+"");
					company.setRegStatus(baseInfoJson.get("regStatus")+"");
					company.setShareholderList(baseInfoJson.get("investorList")+"");
				}catch(Exception e){
					System.err.println(id+" error:"+e.getMessage());
				}
			}
			
			MysqlUtil.insertOrUpdateCompanyBasic(companyList);
		}
	}
	public static void getDetail2() {
		int total = 0;
		MysqlUtil.init();
		while(true){
			List<String> idlist = MysqlUtil.getYouxiId();
			Map<String, String> newmap = new HashMap<String, String>();
			// System.out.println("companyMap-="+companyMap);
			for (String id : idlist) {
				if(total++>200){
					try {
						Thread.sleep(50000);
						total =0;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				 executor.execute(new RunThread(id));
			}
		}
		
	}
	public static void getDetail3() {
	 int total = 0;
		String path = FileUtil.getBasicDir();
		File f = new File(path);
		File[] fs = f.listFiles();
	 
		for (File file : fs) {
			System.out.println("file=" + file.getName());
			LOGGER.info("file=" + file.getName());
			JSONArray array = new JSONArray();
			List<String> idList = ExcelUtil.readIdFromExcel(file.getName());
			Set<String> readIds = FileUtil.getReadIds();
			System.out.println("idList.size=" + idList.size());
			for(String id : idList){
				if(readIds.contains(id)){
					continue;
				}
				if(total++>=500){
					try {
						Thread.sleep(60000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					total = 0;
				}
				executor.execute(new RunThread3(id));
				FileUtil.writeId(id);
			}
		}
		
	}
	public static void getDetail() {
		MysqlUtil.init();
		String path = FileUtil.getBasicDir();
		File f = new File(path);
		File[] fs = f.listFiles();
		int total = 0;
		for (File file : fs) {
			System.out.println("file=" + file.getName());
			LOGGER.info("file=" + file.getName());
			JSONArray array = new JSONArray();
			List<String> idList = ExcelUtil.readIdFromExcel(file.getName());
			Set<String> idSet = new HashSet<String>();
			for(String id : idList){
				idSet.add(id);
			}
			System.out.println("idList.size=" + idList.size()+",idset.size="+idSet.size());
		 
			int index = 0;
			MysqlUtil.insertYouxi(idList);
			 
		}
	}

	public static void getJYScope2() {
	}

	// 从qysd
	public static void getJYScope4() {
		Map<String, String> companyMap = MysqlUtil.getCompanyQueryCondition2();
		Map<String, String> newmap = new HashMap<String, String>();
		// System.out.println("companyMap-="+companyMap);
		for (String id : companyMap.keySet()) {
			String regcode = companyMap.get(id);
			if (regcode == null) {
				continue;
			}

			String scope = QYSDUtil.getJYScope(regcode);
			if (scope == null || scope.trim().length() == 0 || scope.equals("null")) {
				System.out.println(regcode + " 获取scope失败");
			} else {
				newmap.put(id, scope);
				System.out.println("regcode=" + regcode + ";scope = " + scope);
			}
		}
		successnum += newmap.size();
		System.out.println("successnum=" + successnum);
		if (newmap.size() > 0) {
			MysqlUtil.insertScope(newmap);
		}
	}

	// 通过手机
	public static void getJYScope3() {
		Map<String, String> companyMap = MysqlUtil.getCompanyQueryCondition();
		Map<String, String> newmap = new HashMap<String, String>();
		// System.out.println("companyMap-="+companyMap);
		for (String id : companyMap.keySet()) {
			String name = companyMap.get(id);
			String scope = PhoneUtil.getJYScope(id);
			if (scope == null || scope.trim().length() == 0) {
				System.out.println("获取scope失败");
			} else {
				newmap.put(id, scope);
				System.out.println("name=" + name + ";scope = " + scope);
			}
		}
		successnum += newmap.size();
		System.out.println("successnum=" + successnum);
		MysqlUtil.insertScope(newmap);
	}

	public static void getJYScope() {

		Map<String, String> companyMap = MysqlUtil.getCompanyQueryCondition();
		Map<String, String> newmap = new HashMap<String, String>();
		System.out.println("companyMap-=" + companyMap);
		for (String id : companyMap.keySet()) {
			String name = companyMap.get(id);
			if (name == null) {
				continue;
			}
			if (name.contains("...")) {
				name = name.replace("...", "");
			}
			String scope = SeleniumUtil.getJYScope(name);
			if (scope == null || scope.trim().length() == 0) {
				System.out.println("获取scope失败");
			} else {
				newmap.put(id, scope);
				System.out.println("name=" + name + ";scope = " + scope);
			}

		}
		MysqlUtil.insertScope(newmap);
	}

	public static void writerCompanyInfo() {
		Map<String, CompanyDto> companyMap = readCompanyInfo();
		ExcelUtil.writeCompany(companyMap, "企业信息_jx");
	}

	public static void runYearReport() {
		try {
			String path = FileUtil.getBasicDir();
			File f = new File(path);
			File[] fs = f.listFiles();
			for (File file : fs) {
				System.out.println("file=" + file.getName());
				LOGGER.info("file=" + file.getName());
				JSONArray array = new JSONArray();
				List<String> idList = ExcelUtil.readIdFromExcel(file.getName());
				System.out.println("idList.size=" + idList.size());
				int index = 0;
				for (String id : idList) {
					System.out.println("id=" + id + "total " + idList.size() + ",index=" + index++);
					JSONObject obj = getYearReport(id);
					array.add(obj);
				}
				String year = FileUtil.getYearDir();
				ExcelUtil.writeExcel(array, year + File.separator + "年报_" + file.getName());
				ExcelUtil.ExcelFlushAndClose();
				System.out.println(file + " writer year " + array.size() + " success");
				array.clear();
			}
			ExcelUtil.ExcelFlushAndClose();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static Map<String, CompanyDto> readCompanyInfo() {
		String file1 = "D:\\workspace_lkp\\crawler_task\\data\\电子商务_jx.xls";
		String file2 = "D:\\workspace_lkp\\crawler_task\\年报_电子商务_jx.xls";
		Map<String, CompanyDto> companyMap = ExcelUtil.readCompanyInfoFromExcel(file1, file2);
		System.out.println("comapnyMap=" + companyMap.size());
		return companyMap;

	}

	public static JSONObject getYearReport(String id) {

		headParams = new HashMap<String, String>();
		headParams.put("Host", "www.tianyancha.com");
		headParams.put("Proxy-Connection", "keep-alive");
		headParams.put("Accept-Language", "zh-CN,en-US;q=0.8,en;q=0.6");
		headParams.put("Accept", "application/json, text/plain, */*");
		headParams.put("Tyc-From", "normal");
		headParams.put("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36");
		// headParams.put("Cookie",
		// "aliyungf_tc=AQAAAI9MqwqvEAEAJvgNr0DaX7ORPL9e;
		// TYCID=07de69cab21b4ad184a50b0138a48227; tnet=175.13.248.38;
		// Hm_lvt_e92c8d65d92d534b0fc290df538b4758=1477063168;
		// Hm_lpvt_e92c8d65d92d534b0fc290df538b4758=1477063226;
		// _pk_id.1.e431=80c78942252a35f0.1477063171.1.1477063225.1477063171.;
		// _pk_ses.1.e431=*; token=1cf83a0b3d674aeab17ee3251ceb27b8;
		// _utm=7734a92759cf4e3a8b065ef3b7b1c4dc;
		// RTYCID=8aacba5a4b71411dbfb283a2031683ce");
		headParams.put("Accept-Encoding", "gzip, deflate, sdch");// "gzip,
																	// deflate,
																	// sdch");
		JSONObject reportObj = new JSONObject();
		JSONObject yearObj = new JSONObject();
		for (int i = 2015; i < 2016; i++) {
			try {
				String url = "http://www.tianyancha.com/annualreport/newReport.json?id=" + id + "&year=" + i;
				LOGGER.info("url = " + url);
				// http://www.tianyancha.com/annualreport/newReport.json?id=271079645&year=2015
				String body = HttpProxy.getHttpRequestContentByGet(url, null, headParams);
				// int time = new Random().nextInt(1);
				// Thread.sleep(1000*time);
				JSONObject obj = JSONObject.fromObject(body);
				LOGGER.info(id + " get yearreport success");
				if (body != null && !body.contains("null")) {
					System.out.println("get year succwss");
				} else {
					System.out.println("get year fail");
				}
				Object data = obj.get("data");
				yearObj.put(i, data);
			} catch (Exception e) {
				LOGGER.error("getYearReport error:" + e.getMessage());
				// System.out.println("getYearReport error:"+e.getMessage());
			}
		}
		reportObj.put("id", id);
		reportObj.put("content", yearObj);
		return reportObj;
	}

	public static void runBasic() {

		try {
			List<String> areaList = Utils.getProvinceCodeList();
			for (String area : areaList) {
				ExcelUtil.ExcelFlushAndClose();
				List<String> codeList = Utils.getPrimInduCodeList();
				int index = 0;
				for (String code : codeList) {
					List<String> zczbList = Utils.getzibenList();
					for(String zczb : zczbList){
						int page = getPage(area, code);
						LOGGER.info("area=" + area + ";code=" + code + ",page=" + page + ";keywords=" + keywords);
						for (int i = 2; i <= page; i++) {
							String newkeywords = URLEncoder.encode(keywords);
							String url = "http://" + area + ".tianyancha.com/search/" + newkeywords + ".json?&pn=" + i
									+ "&category=" + code + "&type=scope"+"&"+zczb;
							LOGGER.info("url=" + url);
							url += "&base=" + area;
							Proxy proxy = null;// HttpProxy. getProxy();
							String body = HttpProxy.getHttpRequestContentByGet(url, proxy, headParams);
							int ind = 0;
							while (body == null || body.length() < 10) {
								// LOGGER.info("proxy:"+proxy+" error,headers= "
								// +HttpProxy.headers);
								if (ind++ > 2) {
									break;
								}
								Utils.dama2();
								// proxy = HttpProxy. getProxy();
								body = HttpProxy.getHttpRequestContentByGet(url, proxy, headParams);
							}
							try {
								LOGGER.info("page=" + index++);
								JSONObject obj = JSONObject.fromObject(body);
								JSONArray content = obj.getJSONArray("data");
								ExcelUtil.writeExcel(content, keywords + "_" + area);
								// DBUtil.insertCompanyList(content);
							} catch (Exception e) {
								LOGGER.info("error:" + e.getMessage());
								// e.printStackTrace();
							}
						}
					}
					
					
				}
			}
			ExcelUtil.ExcelFlushAndClose();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 根据关键词按地区查信息
	 * */
	public static void runBasic2() {
		try {
			String area = "hun";
			
			List<String> codeList = Utils.getPrimInduCodeList();
			int index = 0;
			keywords = MysqlUtil.getKeywords();
			System.out.println("keywords="+keywords);
			for (String code : codeList) {
				List<String> zczbList = Utils.getzibenList();
				for(String zczb : zczbList){
					int page = getPage(area, code);
					if(page==0){
						return;
					}
					Thread.sleep(500);
					LOGGER.info("area=" + area + ";code=" + code + ",page=" + page + ";keywords=" + keywords);
					for (int i = 2; i <= page; i++) {
						String newkeywords = URLEncoder.encode(keywords);
						String url = "http://" + area + ".tianyancha.com/search/" + newkeywords + ".json?&pn=" + i
								+ "&category=" + code + "&"+zczb;//&type=scope"+
						LOGGER.info("url=" + url);
						url += "&base=" + area;
						Proxy proxy = null;// HttpProxy. getProxy();
						String body = HttpProxy.getHttpRequestContentByGet(url, proxy, headParams);
						int ind = 0;
						while (body == null || body.length() < 10) {
							// LOGGER.info("proxy:"+proxy+" error,headers= "
							// +HttpProxy.headers);
							if (ind++ > 2) {
								break;
							}
							Utils.dama2();
							// proxy = HttpProxy. getProxy();
							body = HttpProxy.getHttpRequestContentByGet(url, proxy, headParams);
							Thread.sleep(500);
						}
						
						try {
							LOGGER.info("page=" + index++);
							insertCompanyToMysql(body);
						} catch (Exception e) {
							LOGGER.info("error:" + e.getMessage());
							// e.printStackTrace();
						}
//						try {
//							LOGGER.info("page=" + index++);
//							JSONObject obj = JSONObject.fromObject(body);
//							JSONArray content = obj.getJSONArray("data");
//							if (content.size() > 0) {
//								for (Object company : content) {
//									JSONObject comObj = JSONObject.fromObject(company);
//									
//								}
//							}
//						} catch (Exception e) {
//							LOGGER.info("error:" + e.getMessage());
//							// e.printStackTrace();
//						}
					}
				}
				
				
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static int getPage(String area, String code) {
		try {
			/// house/ajaxrequest/arealist.php
			String url = "";

			if (area != null) {
				url = "http://" + area + ".tianyancha.com/search/" + keywords + ".json" + "?&pn=1&category=" + code;
			} else {
				url = "http://www.tianyancha.com/search/" + keywords + ".json" + "?&pn=1&category=" + code;
			}
			url = url + "&base=" + area ;//+ "&type=scope";
			LOGGER.info("url=" + url);
			Proxy proxy = null;// HttpProxy. getProxy();

			System.out.println("head=" + headParams);
			String body = HttpProxy.getHttpRequestContentByGet(url, proxy, headParams);
			
			// LOGGER.info("body="+body);
			int index = 0;
			while (body == null || body.length() < 10) {
				if (index++ > 2) {
					break;
				}
				Utils.dama2();

				body = HttpProxy.getHttpRequestContentByGet(url, proxy, headParams);
			}
			try {
				System.out.println("body=" + body);
				
				// LOGGER.info("pagenum="+pagenum);
				//ExcelUtil.writeExcel(content, keywords + "_" + area);
				// DBUtil.insertCompanyList(content);
				return insertCompanyToMysql(body);
			} catch (Exception e) {
				// e.printStackTrace();
			}
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;

	}

	
	public static  int insertCompanyToMysql(String body){
		try{
			JSONObject obj = JSONObject.fromObject(body);

			int pagenum = obj.getInt("totalPage");
			JSONArray content = obj.getJSONArray("data");
			
			System.out.println("content = " + content);
			List<CompanyBasic> list= new ArrayList<CompanyBasic>();
			for(Object base :content){
				JSONObject baseObj = JSONObject.fromObject(base);
				if(baseObj.containsKey("regStatus") || baseObj.containsKey("estiblishTime")|| baseObj.containsKey("industry")){
					String id = baseObj.getString("id");
					String name = baseObj.getString("name");
					name = name.replace("<em>", "");
					name = name.replace("</em>", "");
					CompanyBasic company = new CompanyBasic();
					company.setId(id);
					company.setName(name);
					company.setBase(base.toString());
					list.add(company);
				}
				
			}
			MysqlUtil.insertBasic(list);
			return pagenum;
		}catch(Exception e){
			//e.printStackTrace();
		}
		return 0;
	}
	public static void test() {

		try {
			/// house/ajaxrequest/arealist.php
			String url = "http://www.tianyancha.com/search/游戏.json?&pn=2&category=19";
			Map<String, String> headParams = new HashMap<String, String>();
			headParams.put("Host", "www.tianyancha.com");
			headParams.put("Proxy-Connection", "keep-alive");
			headParams.put("Accept", "*/*");
			headParams.put("Accept-Language", "zh-CN,en-US;q=0.8,en;q=0.6");
			headParams.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			headParams.put("loop", "null");
			headParams.put("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36");
			headParams.put("Accept-Encoding", "gzip, deflate");// "gzip,
																// deflate,
																// sdch");
			// headParams.put("Cookie", Test.cookie);

			// url =
			// "http://newhouse.sh.fang.com/house/s/b260000%2C65000-b93/?ctm=1.sh.xf_search.page.3";
			Proxy proxy = null;// HttpProxy. getProxy();
			String body = HttpProxy.getHttpRequestContentByGet(url, proxy, headParams);
			// LOGGER.info("body="+body);

			try {
				JSONObject obj = JSONObject.fromObject(body);

				int pagenum = obj.getInt("totalPage");
				JSONArray content = obj.getJSONArray("data");
				// LOGGER.info("pagenum="+pagenum);//+",data="+content);
				DBUtil.insertCompanyList(content);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 */
	public static Map<String, String> getAddressList() {
		try {
			/// house/ajaxrequest/arealist.php
			String url = "http://newhouse.fang.com/";
			Map<String, String> headParams = new HashMap<String, String>();
			headParams.put("Host", "newhouse.fang.com");
			headParams.put("Proxy-Connection", "keep-alive");
			headParams.put("Accept", "*/*");
			headParams.put("Accept-Language", "zh-CN,en-US;q=0.8,en;q=0.6");
			headParams.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			headParams.put("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36");
			headParams.put("Accept-Encoding", "gzip, deflate");// "gzip,
																// deflate,
																// sdch");
			// headParams.put("Cookie", Test.cookie);

			// url =
			// "http://newhouse.sh.fang.com/house/s/b260000%2C65000-b93/?ctm=1.sh.xf_search.page.3";
			Proxy proxy = HttpProxy.getProxy();
			String body = HttpProxy.getHttpRequestContentByGet(url, proxy, headParams);
			LOGGER.info("body=" + body);
			Document doc = Jsoup.parse(body);
			Elements eles = doc.getElementsByClass("city20141104nr");
			Map<String, String> addressUrlMap = new HashMap<String, String>();

			for (Element ele : eles) {
				Elements aeles = ele.getElementsByTag("a");
				for (Element aele : aeles) {
					addressUrlMap.put(aele.text(), aele.attr("href"));
				}
			}
			LOGGER.info("urls.size=" + addressUrlMap.size());
			LOGGER.info("urls=" + addressUrlMap);
			return addressUrlMap;
		} catch (Exception e) {
			return null;
		}
	}

	public static void runList(String address, String mainurl) {
		try {

			Map<String, String> headParams = new HashMap<String, String>();
			headParams.put("Host", "newhouse.fang.com");
			headParams.put("Proxy-Connection", "keep-alive");
			headParams.put("Accept", "*/*");
			headParams.put("Accept-Language", "zh-CN,en-US;q=0.8,en;q=0.6");
			headParams.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			headParams.put("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36");
			headParams.put("Accept-Encoding", "gzip, deflate");// "gzip,
																// deflate,
																// sdch");
			headParams.put("Cookie", TYCMain.cookie);
			String url = mainurl + "/house/s/b2";
			// "http://newhouse.sh.fang.com/house/s/b2";
			// url =
			// "http://newhouse.sh.fang.com/house/s/b260000%2C65000-b93/?ctm=1.sh.xf_search.page.3";
			int durl = 3000;
			for (int i = 0; i < 70; i++) {
				String condition = "";

				if (i == 69) {
					condition = i * durl + "%2c" + (i + 300) * durl;// 最后一次跨度大一点
				} else {
					condition = i * durl + "%2c" + (i + 1) * durl;
				}

				String filename = address + "_" + i * durl + "_" + (i + 1) * durl;
				String newurl = url + condition;
				LOGGER.info("newurl = " + newurl);
				Proxy proxy = HttpProxy.getProxy();
				String body = HttpProxy.getHttpRequestContentByGet(newurl, proxy, headParams);
				int num = 0;
				if (body != null && body.length() > 100) {
					Document doc = Jsoup.parse(body);
					Elements eles = doc.getElementsByTag("strong");
					try {
						for (Element ele : eles) {
							String value = ele.text();
							if (StringUtils.isNumeric(value)) {
								LOGGER.info("value = " + value);
								num = Integer.parseInt(value);
								break;
							}
						}
					} catch (Exception e) {
					}
				}
				if (num > 0) {
					int pageall = 0;
					if (num % 20 == 0) {
						pageall = num / 20;
					} else {
						pageall = num / 20 + 1;
					}
					filename = filename + "_" + pageall;
					// executor.execute(new RunThread(newurl, pageall,
					// filename));
				}
			}
		} catch (Exception e) {

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

class RunThread implements Runnable {

	private static final Log LOGGER = LogFactory.getLog(RunThread.class);
	private String id;
	public RunThread(String id) {
		this.id = id;
	}

	@Override
	public void run() {
		try {
			String body =null;
			int index = 0;
			while (body == null) {
			//	id="1000003253";
				//Utils.dama2();
				body = ttc.getDetail(id);
				if (body == null || body.length() < 10) {
					System.out.println("begin dama");
					Utils.dama2();
					Utils.dama2(); 
					boolean flag = Utils.dama2();
					System.out.println("dama :"+flag);
					body = ttc.getDetail(id);
				}
				System.out.println("body=" + body);
				
				if(index++<5){
					break;
				}
				
			}
			if(body!=null && body.length()>10){
				System.out.println("get "+id+" success");
				MysqlUtil.insertYouxi(id, body);
			}
		} catch (Exception e) {

		}

	}

}


class RunThread3 implements Runnable {

	private static final Log LOGGER = LogFactory.getLog(RunThread.class);
	private String id;
	public RunThread3(String id) {
		this.id = id;
	}

	@Override
	public void run() {
		try {
			String body =null;
			int index = 0;
			while (body == null) {
			//	id="1000003253";
				
				body = ttc.getDetail(id);
				if (body == null || body.length() < 10) {
					System.out.println("begin dama");
					Utils.dama2();
					Utils.dama2();
					Utils.dama2();
					Utils.dama2(); 
					boolean flag = Utils.dama2();
					System.out.println("dama :"+flag);
					body = ttc.getDetail(id);
				}
				System.out.println("body=" + body);
				
				if(index++<5){
					break;
				}
				
			}
			if(body!=null && body.length()>10){
				System.out.println("get "+id+" success");
				//MysqlUtil.insertYouxi(id, body);
				FileUtil.writeResult(id, body);
			}
		} catch (Exception e) {

		}

	}

}