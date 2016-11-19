package com.lkp.project.tianyancha;

import java.util.HashMap;
import java.util.Map;

import com.lkp.common.net.HttpProxy;

import net.sf.json.JSONObject;

public class PhoneUtil {
	static Map<String,String> headParams = new HashMap<String,String>();
	public static String getJYScope(String id){
		String url  = "http://api.tianyancha.com/services/v3/t/details/wapCompany/"+id;
		try{
			  Thread.sleep(1000);
				String  body = HttpProxy.getHttpRequestContentByGet(url, null,headParams,false);
				if(body ==null || body.length()<100){
					body = HttpProxy.getHttpRequestContentByGet(url, null,headParams,false);
				}
			JSONObject obj = JSONObject.fromObject(body);
			String scope = obj.getJSONObject("data").getJSONObject("baseInfo").getString("businessScope");
			return scope;
			
		}catch(Exception e){
			System.out.println("get scope error:"+e.getMessage());
			return "";
		}
		
	}
	
	public static void init(){
		headParams.put("Host","api.tianyancha.com");
		//headParams.put("Proxy-Connection","keep-alive");  
	 	headParams.put("Content-Type","application/json");
//		headParams.put("User-Agent",
//				"Dalvik/2.1.0 (Linux; U; Android 6.0; H60-L01 Build/HDH60-L01)");
		//headParams.put("Cookie", "aliyungf_tc=AQAAAI9MqwqvEAEAJvgNr0DaX7ORPL9e; TYCID=07de69cab21b4ad184a50b0138a48227; tnet=175.13.248.38; Hm_lvt_e92c8d65d92d534b0fc290df538b4758=1477063168; Hm_lpvt_e92c8d65d92d534b0fc290df538b4758=1477063226; _pk_id.1.e431=80c78942252a35f0.1477063171.1.1477063225.1477063171.; _pk_ses.1.e431=*; token=1cf83a0b3d674aeab17ee3251ceb27b8; _utm=7734a92759cf4e3a8b065ef3b7b1c4dc; RTYCID=8aacba5a4b71411dbfb283a2031683ce");
		headParams.put("Accept-Encoding","gzip");// "gzip, deflate, sdch");
		headParams.put("Authorization","gvxsNL63M5TI8MpVxhlC7leyr5tIGpHYw6ZozW2/pDgHXU6Li/efPwMs5o9UeFMK");
		//gvxsNL63M5TI8MpVxhlC7rWMDyJ0b68KZaK6RR8SErpCeCSaKJFMqcr8aKMolQQy
		headParams.put("version","Android 2.1.4"); 
		headParams.put("If-Modified-Since","Sat, 29 Oct 2016 03:00:18 GMT+00:00"); 
	
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
}
