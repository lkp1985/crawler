package com.lkp.project.qichacha;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.lkp.common.net.HttpProxy;

import net.sf.json.JSONObject;

public class Utils {

	public static void main(String[] args) {
		test2();
//	 dama2();
//		 String url = "http://www.tianyancha.com/act/create.json";
//		String json = "";
//		 JSONObject obj = new JSONObject();
//		 obj.put("tid", "TYCID=c9218d7449af44fc9ba9da2f7ad6a8b5;");
//		 json = obj.toString();
//		try {
//			httpPostWithJSON(url,json);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

private static final String APPLICATION_JSON = "application/json";
    
    private static final String CONTENT_TYPE_TEXT_JSON = "application/json";

    
    public static void test2(){}
public static void httpPostWithJSON(String url, String json) throws Exception {
        // 将JSON进行UTF-8编码,以便传输中文
        String encoderJson = URLEncoder.encode(json, HTTP.UTF_8);
        
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
        httpPost.addHeader("Accept", APPLICATION_JSON);
        StringEntity se = new StringEntity(encoderJson);
   //     se.setContentType(CONTENT_TYPE_TEXT_JSON);
     se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
        httpPost.setEntity(se);
        HttpResponse httpResponse  = httpClient.execute(httpPost);
        System.out.println("status="+httpResponse.getStatusLine().getStatusCode());
        HttpEntity httpEntity = httpResponse.getEntity();  
      String   result = EntityUtils.toString(httpEntity);//取出应答字符串  
        System.out.println("result="+result);
    }
	 public static boolean dama2(){
		 try{

	    		String url = "http://www.tianyancha.com/act/create.json";
	   		 JSONObject obj = new JSONObject();
	   		 obj.put("tid", "TYCID=c9218d7449af44fc9ba9da2f7ad6a8b5;");

	        	HttpPost httppost = new HttpPost(url);
	    	 
//	    		httppost.setHeader("content-type", "text/x-gwt-rpc; charset=UTF-8");
//	    		httppost.setHeader("origin", "https://adwords.google.com");
//	    		httppost.setHeader("referer", "httc=6524324568");
//	    		httppost.setHeader("x-gwt-module-base", "https:/lanner/");
//	    		httppost.setHeader("x-gwt-permutation", "2BF16164A");
//	    		httppost.setHeader("dnt", "1");
	    		
	        	httppost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
	        	httppost.addHeader("Accept", APPLICATION_JSON);
	            
	            
	    		httppost.setEntity(new StringEntity(obj.toString()));
	    		/*nvps = new ArrayList<NameValuePair>();
	    		nvps.add(new BasicNameValuePair("_", query));
	    		httppost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));*/
	    		DefaultHttpClient httpClient = new DefaultHttpClient();
	    		HttpResponse response = httpClient.execute(httppost);
	    		 int code = response.getStatusLine().getStatusCode();
	    	//	System.out.println("Login6 form get: " + code);
	    		 HttpEntity httpEntity = response.getEntity();  
	    	      String   result = EntityUtils.toString(httpEntity);//取出应答字符串  
	    	  //      System.out.println("result="+result);
	    	        JSONObject resultobj = JSONObject.fromObject(result);
	    	        if(resultobj.get("state").equals("ok")){
	    	        	return true;
	    	        }
	    	
		 }catch(Exception e){
			 System.out.println("dama2 error:"+e.getMessage());
			// e.printStackTrace();
		 }
		 return false;
	 }
	public static boolean dama() {
		try {
			String url = "http://antirobot.tianyancha.com/captcha/getGtCaptcha";
			Map<String, String> headParams = new HashMap<String, String>();

			headParams = new HashMap<String, String>();
			headParams.put("Host", "antirobot.tianyancha.com");
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
			String body = HttpProxy.getHttpRequestContentByGet(url, null, headParams,false);
			System.out.println("body=" + body);
			JSONObject obj = JSONObject.fromObject(body);
			String gt = obj.getString("gt");
			String challenge = obj.getString("challenge");
			
			System.out.println("gt="+gt+";challenge="+challenge);
			
			String validateurl = "http://api.ashx.cn/geetest?gt="+gt+"&challenge="+challenge+"&Referer={Referer}&User=64005d5e-ef3c-4032-b544-bf8ee1e1c69b";
			
			 body = HttpProxy.getHttpRequestContentByGet(validateurl, null, null,false);
			 System.out.println("validate body="+body);
			 if(body!=null && body.length()>10){
				 JSONObject validateObj = JSONObject.fromObject(body);
				 String success = validateObj.getString("Success");
				 if(success.equals("success")){
					 challenge = validateObj.getString("Challenge");
					 String validate =  validateObj.getString("Validate");
					 
					 System.out.println("challenge = " + challenge+";validate="+validate);
					 
					 //
					 
					 String posturl = "http://antirobot.tianyancha.com/captcha/checkGtCaptcha";
					 //return_url=http://www.tianyancha.com/search?key=电信增值&
					 String formparams = "geetest_challenge="+challenge
					 		+ "&geetest_validate="+validate
					 		+ "&geetest_seccode="+validate+"|jordan";
					 formparams +="&rnd=KVpCtBQ468Lc3uLuM6jZ8w==";
					 System.out.println("formparams="+formparams);
					 
				//	 headParams.put("Cookie","SESSION=c051fcd7-c6d3-4419-aa45-07d4e49462bd;");
					 String postbody = HttpProxy.getHttpRequestContentByPost(posturl, null, formparams, headParams,false);
					 if(postbody!=null && postbody.length()>10){
						 return true;
					 }
					 System.out.println("postbody="+postbody);
				 }
				 
			 } 
			/*
			 * http://api.ashx.cn/geetest?gt={gt}&challenge={challenge}&Referer={Referer}&User=64005d5e-ef3c-4032-b544-bf8ee1e1c69b
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public static List<String> getPrimInduCodeList() {
		List<String> primInduCodeList = new ArrayList<String>();
		String str = "[{primInduCode:\"19\",secList:[{secnduName:\"商务服务业\",secInduCode:\"1901\"},{secnduName:\"租赁业\",secInduCode:\"1900\"}],primInduName:\"租赁和商务服务业\"},{primInduCode:\"17\",secList:[{secnduName:\"公共设施管理业\",secInduCode:\"1702\"},{secnduName:\"生态保护和环境治理业\",secInduCode:\"1700\"}],primInduName:\"水利、环境和公共设施管理业\"},{primInduCode:\"16\",secList:[{secnduName:\"黑色金属矿采选业\",secInduCode:\"1606\"},{secnduName:\"煤炭开采和洗选业\",secInduCode:\"1605\"},{secnduName:\"非金属矿采选业\",secInduCode:\"1603\"},{secnduName:\"有色金属矿采选业\",secInduCode:\"1601\"}],primInduName:\"采矿业\"},{primInduCode:\"15\",secList:[{secnduName:\"零售业\",secInduCode:\"1501\"},{secnduName:\"批发业\",secInduCode:\"1500\"}],primInduName:\"批发和零售业\"},{primInduCode:\"14\",secList:[{secnduName:\"装卸搬运和运输代理业\",secInduCode:\"1407\"},{secnduName:\"水上运输业\",secInduCode:\"1405\"},{secnduName:\"道路运输业\",secInduCode:\"1403\"}],primInduName:\"交通运输、仓储和邮政业\"},{primInduCode:\"13\",secList:[{secnduName:\"建筑装饰和其他建筑业\",secInduCode:\"1303\"},{secnduName:\"建筑安装业\",secInduCode:\"1302\"},{secnduName:\"土木工程建筑业\",secInduCode:\"1300\"}],primInduName:\"建筑业\"},{primInduCode:\"12\",secList:[{secnduName:\"保险业\",secInduCode:\"1200\"}],primInduName:\"金融业\"},{primInduCode:\"11\",secList:[{secnduName:\"燃气生产和供应业\",secInduCode:\"1102\"},{secnduName:\"水的生产和供应业\",secInduCode:\"1101\"},{secnduName:\"电力、热力生产和供应业\",secInduCode:\"1100\"}],primInduName:\"电力、热力、燃气及水生产和供应业\"},{primInduCode:\"10\",secList:[{secnduName:\"餐饮业\",secInduCode:\"1001\"},{secnduName:\"住宿业\",secInduCode:\"1000\"}],primInduName:\"住宿和餐饮业\"},{primInduCode:\"29\",secList:[{secnduName:\"有色金属冶炼和压延加工业\",secInduCode:\"2929\"},{secnduName:\"铁路、船舶、航空航天和其他运输设备制造业\",secInduCode:\"2928\"},{secnduName:\"家具制造业\",secInduCode:\"2927\"},{secnduName:\"其他制造业\",secInduCode:\"2926\"},{secnduName:\"木材加工和木、竹、藤、棕、草制品业\",secInduCode:\"2925\"},{secnduName:\"仪器仪表制造业\",secInduCode:\"2922\"},{secnduName:\"皮革、毛皮、羽毛及其制品和制鞋业\",secInduCode:\"2920\"},{secnduName:\"农副食品加工业\",secInduCode:\"2919\"},{secnduName:\"医药制造业\",secInduCode:\"2918\"},{secnduName:\"金属制品业\",secInduCode:\"2917\"},{secnduName:\"汽车制造业\",secInduCode:\"2916\"},{secnduName:\"食品制造业\",secInduCode:\"2915\"},{secnduName:\"黑色金属冶炼和压延加工业\",secInduCode:\"2914\"},{secnduName:\"非金属矿物制品业\",secInduCode:\"2913\"},{secnduName:\"化学原料和化学制品制造业\",secInduCode:\"2911\"},{secnduName:\"废弃资源综合利用业\",secInduCode:\"2910\"},{secnduName:\"文教、工美、体育和娱乐用品制造业\",secInduCode:\"2909\"},{secnduName:\"通用设备制造业\",secInduCode:\"2908\"},{secnduName:\"专用设备制造业\",secInduCode:\"2907\"},{secnduName:\"纺织业\",secInduCode:\"2906\"},{secnduName:\"造纸和纸制品业\",secInduCode:\"2905\"},{secnduName:\"橡胶和塑料制品业\",secInduCode:\"2904\"},{secnduName:\"石油加工、炼焦和核燃料加工业\",secInduCode:\"2903\"},{secnduName:\"印刷和记录媒介复制业\",secInduCode:\"2902\"},{secnduName:\"电气机械和器材制造业\",secInduCode:\"2901\"},{secnduName:\"酒、饮料和精制茶制造业\",secInduCode:\"2900\"},{secnduName:\"计算机、通信和其他电子设备制造业\",secInduCode:\"2930\"}],primInduName:\"制造业\"},{primInduCode:\"28\",secList:[{secnduName:\"房地产业\",secInduCode:\"2800\"}],primInduName:\"房地产业\"},{primInduCode:\"27\",secList:[{secnduName:\"广播、电视、电影和影视录音制作业\",secInduCode:\"2704\"},{secnduName:\"娱乐业\",secInduCode:\"2703\"},{secnduName:\"文化艺术业\",secInduCode:\"2702\"},{secnduName:\"新闻和出版业\",secInduCode:\"2701\"},{secnduName:\"体育\",secInduCode:\"2700\"}],primInduName:\"文化、体育和娱乐业\"},{primInduCode:\"26\",secList:[{secnduName:\"专业技术服务业\",secInduCode:\"2602\"},{secnduName:\"研究和试验发展\",secInduCode:\"2601\"},{secnduName:\"科技推广和应用服务业\",secInduCode:\"2600\"}],primInduName:\"科学研究和技术服务业\"},{primInduCode:\"25\",secList:[{secnduName:\"教育\",secInduCode:\"2500\"}],primInduName:\"教育\"},{primInduCode:\"24\",secList:[{secnduName:\"卫生\",secInduCode:\"2401\"}],primInduName:\"卫生和社会工作\"},{primInduCode:\"22\",secList:[{secnduName:\"农业\",secInduCode:\"2204\"},{secnduName:\"渔业\",secInduCode:\"2203\"},{secnduName:\"林业\",secInduCode:\"2202\"},{secnduName:\"畜牧业\",secInduCode:\"2201\"},{secnduName:\"农、林、牧、渔服务业\",secInduCode:\"2200\"}],primInduName:\"农、林、牧、渔业\"},{primInduCode:\"21\",secList:[{secnduName:\"电信、广播电视和卫星传输服务\",secInduCode:\"2102\"},{secnduName:\"互联网和相关服务\",secInduCode:\"2100\"}],primInduName:\"信息传输、软件和信息技术服务业\"},{primInduCode:\"20\",secList:[{secnduName:\"机动车、电子产品和日用产品修理业\",secInduCode:\"2002\"},{secnduName:\"其他服务业\",secInduCode:\"2001\"},{secnduName:\"居民服务业\",secInduCode:\"2000\"}],primInduName:\"居民服务、修理和其他服务业\"}]";
		String[] strs = str.split("primInduCode:\"");
		//System.out.println("strs=" + strs);
		for (String stri : strs) {
			if (stri.length() > 4) {
				String num = stri.substring(0, 2);
				primInduCodeList.add(num);
			}
		}
		return primInduCodeList;
	}

	public static List<String> getProvinceCodeList() {
		List<String> provinceCodeList = new ArrayList<String>();
	 
		String[] strs ={"xj","jx","ln","han","js","gd","snx","heb","hub","jl","nx","tj","fj","bj","zj","hen","sx","hun","gz","hlj","gx","sd","sc","qh","xz","sh","lyg","nmg","cq","yn","gs","ah"};
		 
		for (String stri : strs) { 
			provinceCodeList.add(stri);
		}
		return provinceCodeList;
	}

	// 获取资本
	public static List<String> getzibenList() {
		List<String> zibenList = new ArrayList<String>();
		zibenList.add("or0100");
		zibenList.add("or5001000");
		zibenList.add("or200500");
		zibenList.add("or100200");
		zibenList.add("or1000");
		return zibenList;
	}

	public static List<String> getzctimeList() {
		List<String> timeList = new ArrayList<String>();
		timeList.add("oe15");
		timeList.add("oe510");
		timeList.add("oe1015");
		timeList.add("oe01");
		timeList.add("oe015");

		return timeList;
	}

}
