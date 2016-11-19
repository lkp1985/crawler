/*
 * Copyright (c) SHADANSOU 2014 All Rights Reserved
 *
 */
package com.lkp.project.donghang;

import java.io.FileWriter;
import java.io.IOException;

/**
 * <p>
 * class function description.
 * <p>
 *
 * create 2016年3月10日<br>
 * 
 * @author lkp<br>
 * @version 1.0
 * @since 1.0
 */
public class Test {

	public static void main(String[] args) {

		String url = "http://movieapp.baidu.com/na/app/dispatcher?mockup=0";
		String cookie = "_session_id=10b877a8a681120804505454fb9ff694";
		// url = "http://tool.chinaz.com/";
		//String cookie = "SINAGLOBAL=6318676043301.82.1452560762318; _s_tentry=-; Apache=4302209101151.675.1457591869487; ULV=1457591869505:22:6:4:4302209101151.675.1457591869487:1457583273563; SWB=usrmdinst_26; WBtopGlobal_register_version=8a840560e41b693d; un=280740960@qq.com; myuid=2758792962; login_sid_t=4b14e037ee2e4614cb9a4f2fbe6c6ff9; appkey=; SSOLoginState=1457592593; wvr=6; WBStore=8b23cf4ec60a636c|undefined; user_active=201603101458; user_unver=3f5f2fb4f167a93ea51d14eb5d8945ec; SUS=SID-5879083593-1457593133-GZ-bxnko-f4326c979d4836daafe77ce10701c505; SUE=es%3D880e25826d218932ebdede22e134c7ef%26ev%3Dv1%26es2%3D2f30f12e1487061e5eda4276061a7efa%26rs0%3Dm%252BvIIWSW0HAyVPI1JMn6257Tz3GTQ3of6awv%252FE5jnc2gS2tjrAj3ZcHaqjj4%252BbNP5n3C3StmmLClpyExO01VQvP2xiKP4332Euh%252FJiirGLciUq2%252Bnsb0hs42uRl%252FbxR%252BoXOWeSebGD5q2ItsY5qA1ypPbqH3tKks1yxZZuL28aE%253D%26rv%3D0; SUP=cv%3D1%26bt%3D1457593133%26et%3D1457679533%26d%3Dc909%26i%3Dc505%26us%3D1%26vf%3D0%26vt%3D0%26ac%3D0%26st%3D0%26uid%3D5879083593%26name%3D15084947675%2540163.com%26nick%3D%25E6%2596%2587%25E8%2589%25BA%25E8%258C%2583llkk%26fmp%3D%26lcp%3D; SUB=_2A2575Wt9DeTxGeNG7FsR-C3Jwj-IHXVYk9u1rDV8PUNbu9BeLW7kkW9LHesmFHnGCIkvCaGz8se3_ZopurOuhw..; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WhiVxAjUfY6dONNK5GTyXTN5JpX5Kzt; SUHB=0d4uvcW6WAJbq2; ALF=1489129132; UOR=,,login.sina.com.cn";
		String ss = com.lkp.proxy.HttpProxy.getResponseByPost(url, needcookie, cookieurl).getResponseByGet(url, cookie);
		System.out.println("ss="+ss);

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
