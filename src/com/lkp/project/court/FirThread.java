package com.lkp.project.court;

import java.io.FileWriter;

import com.lkp.common.net.HttpTools;

public class FirThread implements Runnable{
	String cookie  = "";
	FileWriter writer = null;
	 
	public FirThread(String _cookie,FileWriter _wirter) {
		cookie = _cookie;
		writer = _wirter;
	}
	@Override
	public void run() {
		while(true){
			String line = CommonUtil.queue.poll();
			String url = "http://www.court.gov.cn/zgcpwsw/content/content?DocID="+line;//9ec22c93-f176-47e6-b449-d36fc9ece4c0";
			try {
//CookieValue; CookieName=CookieValue; _gscu_1049835508=5594722827xhyh0717; _pk_id.1.cf4c=3f0de15842e55247.1455947834.3.1456018299.1456018257.; __jsluid=4fef5efded1deab4c09a4ce525f034be; ASP.NET_SessionId=y0uwlbuanixiywlnpjf0rmsc; _gsref_1241932522=http://www.court.gov.cn/zgcpwsw/; _gscu_1241932522=557987759brzvg16; _gscs_1241932522=t56154415icaw5612|pv:13; _gscbrs_1241932522=1; __jsl_clearance=1456156344.255|0|i2xuGAO2TZTcfEgXO%2BXZS05QnB0%3D
				String res = HttpTools.getHttpResponse(url, "utf-8",cookie);
				System.out.println("res="+res);
				//Thread.sleep(1000);
				String publishDate = CommonUtil.getPublishDate(res);
				System.out.println("date="+publishDate);
				writer.write(line+"  "+publishDate+"\n");
				writer.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
