package com.lkp.project.meituan;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lkp.common.net.HttpProxy;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class RequestThread implements Runnable{
	Map<String,String> headParams = new HashMap<String,String>();
	Proxy proxy ;
	String params ;
	public  RequestThread(Proxy proxy,String params) {
		headParams.put("Accept", "application/json"); // 设置接收数据的格式  
		headParams.put("Content-Type", "application/json"); // 设置发送数据的格式  
		this.proxy = proxy;
		this.params  = params;
	}
	@Override
	public void run() {
		try{
			System.out.println(Thread.currentThread().getName()+" start ,param="+params+",proxy="+proxy);
			String url = "http://www.qysudu.com/qysuduApi/company/search";
			String  body = HttpProxy.getHttpRequestContentByPost(url, proxy, params, headParams,false);
			if(body!=null && body.contains("frequentVisit")){
				int num = ProxyUtil.proxyNum.get(ProxyUtil.getProxyHost(proxy));
				System.out.println(proxy +" frequentVisit,num="+num);
			}else{
				//解析成company对象
				System.out.println("body2="+body);
				JSONObject bodyJson = JSONObject.fromObject(body);
				JSONArray array = bodyJson.getJSONArray("data");
			
				List<CompanyDto> companyList = new ArrayList<CompanyDto>();
				for(Object obj : array){
					CompanyDto company = new CompanyDto();
					companyList.add(company);
					JSONObject objJson = JSONObject.fromObject(obj);
					company.setId(objJson.getString("id"));
					company.setBase(objJson.getString("addr"));
					company.setName(objJson.getString("name"));
					company.setLegalPersonName(objJson.getString("lp"));
					company.setCreditCode(objJson.getString("nsc"));
					company.setRegNumber(objJson.getString("rgc"));
					company.setRegStatus(objJson.getString("stt"));
					company.setRegGov(objJson.getString("gov"));
					company.setRegMoney(objJson.getString("rc"));
					company.setScope(objJson.getString("rgs"));
					company.setShareholderList(objJson.getString("kp")); 
				}
				MysqlUtil.insertCompanyBasic(companyList);
			}
		}catch(Exception e	){
			e.printStackTrace();
		} 
	}
}
