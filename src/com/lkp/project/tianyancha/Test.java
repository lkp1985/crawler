package com.lkp.project.tianyancha;

import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;

import com.lkp.common.net.HttpProxy;
import com.lkp.proxyutils.ProxyUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Test {
	static Map<String,String> headParams = new HashMap<String,String>();
	
	public static void init(){
		headParams.put("Host","api.tianyancha.com");
		//headParams.put("Proxy-Connection","keep-alive");  
	 	headParams.put("Content-Type","application/json");
//		headParams.put("User-Agent",
//				"Dalvik/2.1.0 (Linux; U; Android 6.0; H60-L01 Build/HDH60-L01)");
		//headParams.put("Cookie", "aliyungf_tc=AQAAAI9MqwqvEAEAJvgNr0DaX7ORPL9e; TYCID=07de69cab21b4ad184a50b0138a48227; tnet=175.13.248.38; Hm_lvt_e92c8d65d92d534b0fc290df538b4758=1477063168; Hm_lpvt_e92c8d65d92d534b0fc290df538b4758=1477063226; _pk_id.1.e431=80c78942252a35f0.1477063171.1.1477063225.1477063171.; _pk_ses.1.e431=*; token=1cf83a0b3d674aeab17ee3251ceb27b8; _utm=7734a92759cf4e3a8b065ef3b7b1c4dc; RTYCID=8aacba5a4b71411dbfb283a2031683ce");
		headParams.put("Accept-Encoding","gzip");// "gzip, deflate, sdch");
		headParams.put("Authorization","gvxsNL63M5TI8MpVxhlC7pN03BC6tmbH+x4cpjQBKeXW2oAeGaY0bJsBThcdB0YC");
		headParams.put("version","Android 2.1.4"); 
		headParams.put("If-Modified-Since","Sat, 29 Oct 2016 02:23:18 GMT+00:00"); 
	
	}
	public static void main(String[] args) {
		init();
		String url  = "http://api.tianyancha.com/services/v3/t/details/wapCompany/2353434382";
		String  body = HttpProxy.getHttpRequestContentByGet(url, null,headParams,false);
		try{
			System.out.println("body="+body);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void readCompanyInfo(){
		String file1="D:\\workspace_lkp\\crawler_task\\data\\电子商务_jx.xls";
		String file2="D:\\workspace_lkp\\crawler_task\\年报_电子商务_jx.xls";
		Map<String,CompanyDto> companyMap = ExcelUtil.readCompanyInfoFromExcel(file1, file2);
		System.out.println("comapnyMap="+companyMap.size());
		
	}
	public static void getYearReport(String id){
		String url  ="http://www.tianyancha.com/annualreport/newReport.json?id="+id+"&year=2015";
		System.out.println("url = " + url);
	//	headParams.put("Cookie", "RTYCID=822c4346c49040ce9a767364f02f79e2; tnet=175.13.248.38; TYCID=9c5067c89f3c4ea2a1f07092422bd699; aliyungf_tc=AQAAAJmiugkCvAAAJvgNr3UKCJkzYUlG; _pk_ref.1.e431=%5B%22%22%2C%22%22%2C1477147108%2C%22http%3A%2F%2Fantirobot.tianyancha.com%2Fcaptcha%2Fverify%3Freturn_url%3Dhttp%3A%2F%2Fwww.tianyancha.com%2Fcompany%2F25693490%26rnd%3DH551jD2kB4J5Jy3-9mRWkQ%3D%3D%22%5D; token=b7cdf047f8b84028a773e9d1baa87778; _utm=3f23efce825f47a2b224bd7df16992c3; _pk_id.1.e431=b0fe203c140cfde0.1476969406.14.1477147240.1477147108.; _pk_ses.1.e431=*; Hm_lvt_e92c8d65d92d534b0fc290df538b4758=1477093722,1477123331,1477144440,1477147177; Hm_lpvt_e92c8d65d92d534b0fc290df538b4758=1477147241");
		JSONArray array = new JSONArray();
		Proxy proxy = HttpProxy.getProxy();
		System.out.println(proxy);
		String  body = HttpProxy.getHttpRequestContentByGet(url, proxy,headParams);
		try{
			JSONObject obj = JSONObject.fromObject(body);
			Object data = obj.get("data");
			
		 
		}catch(Exception e){
			
		}
		System.out.println("body="+body);
	}
	public static void getDetail(String id){
		id = "182750933";
		String url  = "http://www.tianyancha.com/company/"+id+".json";
		String  body = HttpProxy.getHttpRequestContentByGet(url, null,headParams);
		
		System.out.println("body="+body);
	}
}
