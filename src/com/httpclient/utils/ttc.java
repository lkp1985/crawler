package com.httpclient.utils;

import java.io.UnsupportedEncodingException;
import java.net.Proxy;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.httpclient.utils.beans.Detail;
import com.httpclient.utils.beans.FinalResult;
import com.httpclient.utils.beans.Tongji;
import com.lkp.common.net.HttpProxy;

/**
 * ����� ����ʵ��
 * 
 * @author mdc
 * @date 2016��9��2��
 */
public class ttc {

	public static String[] $SoGou0$ = { "6", "b", "t", "f", "2", "z", "l", "5", "w", "h", "q", "i", "s", "e", "c", "p",
			"m", "u", "9", "8", "y", "k", "j", "r", "x", "n", "-", "0", "3", "4", "d", "1", "a", "o", "7", "v", "g" };
	public static String[] $SoGou1$ = { "1", "8", "o", "s", "z", "u", "n", "v", "m", "b", "9", "f", "d", "7", "h", "c",
			"p", "y", "2", "0", "3", "j", "-", "i", "l", "k", "t", "q", "4", "6", "r", "a", "w", "5", "e", "x", "g" };
	public static String[] $SoGou2$ = { "s", "6", "h", "0", "p", "g", "3", "n", "m", "y", "l", "d", "x", "e", "a", "k",
			"z", "u", "f", "4", "r", "b", "-", "7", "o", "c", "i", "8", "v", "2", "1", "9", "q", "w", "t", "j", "5" };
	public static String[] $SoGou3$ = { "x", "7", "0", "d", "i", "g", "a", "c", "t", "h", "u", "p", "f", "6", "v", "e",
			"q", "4", "b", "5", "k", "w", "9", "s", "-", "j", "l", "y", "3", "o", "n", "z", "m", "2", "1", "r", "8" };
	public static String[] $SoGou4$ = { "z", "j", "3", "l", "1", "u", "s", "4", "5", "g", "c", "h", "7", "o", "t", "2",
			"k", "a", "-", "e", "x", "y", "b", "n", "8", "i", "6", "q", "p", "0", "d", "r", "v", "m", "w", "f", "9" };
	public static String[] $SoGou5$ = { "j", "h", "p", "x", "3", "d", "6", "5", "8", "k", "t", "l", "z", "b", "4", "n",
			"r", "v", "y", "m", "g", "a", "0", "1", "c", "9", "-", "2", "7", "q", "e", "w", "u", "s", "f", "o", "i" };
	public static String[] $SoGou6$ = { "8", "q", "-", "u", "d", "k", "7", "t", "z", "4", "x", "f", "v", "w", "p", "2",
			"e", "9", "o", "m", "5", "g", "1", "j", "i", "n", "6", "3", "r", "l", "b", "h", "y", "c", "a", "s", "0" };
	public static String[] $SoGou7$ = { "d", "4", "9", "m", "o", "i", "5", "k", "q", "n", "c", "s", "6", "b", "j", "y",
			"x", "l", "a", "v", "3", "t", "u", "h", "-", "r", "z", "2", "0", "7", "g", "p", "8", "f", "1", "w", "e" };
	public static String[] $SoGou8$ = { "7", "-", "g", "x", "6", "5", "n", "u", "q", "z", "w", "t", "m", "0", "h", "o",
			"y", "p", "i", "f", "k", "s", "9", "l", "r", "1", "2", "v", "4", "e", "8", "c", "b", "a", "d", "j", "3" };
	public static String[] $SoGou9$ = { "1", "t", "8", "z", "o", "f", "l", "5", "2", "y", "q", "9", "p", "g", "r", "x",
			"e", "s", "d", "4", "n", "b", "u", "a", "m", "c", "h", "j", "3", "v", "i", "0", "-", "w", "7", "k", "6" };

	/**
	 * ��ASCII���Unicode��תΪ�ַ� ����Բ��ɴ�ӡ�ַ�
	 */
	public static String fromCharCode(String[] chars) {

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < chars.length; i++) {
			int code = Integer.parseInt(chars[i]);

			if (0x4E00 <= code && code <= 0x9FA5) {
				builder.append((char) Integer.parseInt((chars[i]), 16));
			} else if (code >= 32) {
				builder.append((char) code);
			}
		}

		return builder.toString();
	}

	public static Map<String, String> getHeaders() {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "application/json, text/plain, */*");
		headers.put("Accept-Encoding", "gzip, deflate, sdch");
		headers.put("Cache-Control", "max-age=0");
		headers.put("CheckError", "false");
		headers.put("Connection", "keep-alive");
		headers.put("Host", "www.tianyancha.com");
		headers.put("Tyc-From", "normal");
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.82 Safari/537.36");
		return headers;
	}

	/**
	 * ������������
	 * 
	 * @author mdc
	 * @date 2016��9��3��
	 * @param string
	 * @return
	 */
	public static String encode(String string) {
		try {
			return URLEncoder.encode(string, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return string;
		}
	}

	public static void main(String[] args) {

		// ��һ����ѯ�б�
		//String keyword = encode("����"); // ��ʼ��ѯ���ݣ���̬�޸ģ�
		String keyword = "1000003253";
		// 1����ȡcookie ���ݲ��޸� Cookie
		String url = "http://www.tianyancha.com/tongji/" + keyword + ".json?random=" + new Date().getTime();
		System.out.println("�����ַ===" + url);
		HttpUtils util = HttpUtils.get(url).setHeaders(getHeaders());
		ResponseWrap resp = util.execute();
		System.out.println("��ȡ��������===" + resp.getString());
		Tongji bean = resp.getJson(Tongji.class);
		System.out.println("ת��ΪBean===" + bean);

		String script = fromCharCode(bean.getData().getV().split(","));
		System.out.println("��ȡ�ű�����===" + script);

		String token = script.substring(script.indexOf("token=") + "token=".length(), script.indexOf(";path="));
		System.out.println("����token==" + token);

		String[] wtfFnReturn = script.substring(script.indexOf("return'") + "return'".length(), script.indexOf("'}}"))
				.split(",");

		String[] $SoGou$ = { "j", "p", "7", "o", "3", "w", "0", "-", "2", "e", "9", "z", "f", "m", "b", "k", "u", "8",
				"g", "s", "a", "1", "d", "y", "r", "i", "h", "q", "l", "n", "v", "6", "5", "c", "x", "4", "t" };
		StringBuilder _utm = new StringBuilder();
		for (int i = 0; i < wtfFnReturn.length; i++) {
			_utm.append($SoGou$[Integer.parseInt(wtfFnReturn[i])]);
		}

		System.out.println("����_utm===" + _utm);

		Map<String, String> headers = getHeaders();
		String credence = "token=" + token + "; _utm=" + _utm;
		headers.put("Cookie",
				"TYCID=f872552ce66f4902a43afd903894eeee; tnet=42.159.236.8; RTYCID=a54c421fc34d44c7b63154eaf3782d09; Hm_lvt_e92c8d65d92d534b0fc290df538b4758=1473843346,1473843958,1473843975; Hm_lpvt_e92c8d65d92d534b0fc290df538b4758=1473843975; _pk_id.1.e431=65f91552c041537e.1473843346.1.1473843975.1473843346.; _pk_ses.1.e431=*;"
						+ credence);
		// 2:��ȡ�б�
		url = "http://www.tianyancha.com/search/" + keyword + ".json?&pn=1";
		System.out.println("�����ַ=== " + url);
		url = "http://www.tianyancha.com/company/"+keyword+".json";
		//url = "http://www.tianyancha.com/";
		Proxy proxy = HttpProxy.getProxy();
		String body = HttpProxy.getHttpRequestContentByGet(url, null, headers,true);
		util = HttpUtils.get(url, util).setHeaders(headers);
		resp = util.execute();
		// resp.shutdown(); //�ر�����
		System.out.println("��������Ӧ״̬��:" + resp.getStatusLine());

		int statusCode = resp.getStatusLine().getStatusCode();
		if (statusCode != 200) {
			System.err.println("����ʧ��,״̬��:" + statusCode);
			return;
		}

		System.out.println("��ȡ�����ս��===" + resp.getString());

		FinalResult finalBen = resp.getJson(FinalResult.class);
		System.out.println("���ս������===" + finalBen);

		System.out.println("���ս���б�===");
		Detail[] details = finalBen.getData();

		for (int i = 0; i < details.length; i++) {
			if (details[i].getType() == 1) {
				System.out.println(details[i]);
			}
		}

		System.out.println("!!!!�������!!!!");

		// �ڶ�����ϸ��ѯ
		String key = "142083219";// ĳ�������ID ��ʶ ����̬�޸ģ�

		// 1:��ȡ�޸�Cookie ���ݲ��޸�Cookie
		long number = new Date().getTime();
		String str = Long.toString(number).substring(0, 10);
		headers.put("Cookie",
				"TYCID=f872552ce66f4902a43afd903894eeee; tnet=42.159.236.8; RTYCID=a54c421fc34d44c7b63154eaf3782d09; Hm_lvt_e92c8d65d92d534b0fc290df538b4758=1473843346,1473843958,1473843975; Hm_lpvt_e92c8d65d92d534b0fc290df538b4758="
						+ str);
		url = "http://www.tianyancha.com/tongji/" + key + ".json?random=" + number;
		System.out.println("��ȡCookie�����ַ===" + url);

		HttpUtils util1 = HttpUtils.get(url).setHeaders(getHeaders());
		resp = util1.execute();
		bean = resp.getJson(Tongji.class);
		System.out.println("ת��ΪBean===" + bean);

		script = fromCharCode(bean.getData().getV().split(","));
		System.out.println("��ȡ�ű�����===" + script);

		token = script.substring(script.indexOf("token=") + "token=".length(), script.indexOf(";path="));
		System.out.println("����token==" + token);
		wtfFnReturn = script.substring(script.indexOf("return'") + "return'".length(), script.indexOf("'}}"))
				.split(",");

		List<String[]> liststr = new ArrayList<String[]>();
		liststr.add($SoGou0$);
		liststr.add($SoGou1$);
		liststr.add($SoGou2$);
		liststr.add($SoGou3$);
		liststr.add($SoGou4$);
		liststr.add($SoGou5$);
		liststr.add($SoGou6$);
		liststr.add($SoGou7$);
		liststr.add($SoGou8$);
		liststr.add($SoGou9$);
		// �ı�����cookie utm��SoGou����,�����������ŵ����ȡȡ��һ��$SoGou$���飬�������� utm ���
		if (key == null || key.equals(""))
			return;
		String index = key.codePointAt(0) + "";
		char[] indexChars = index.toCharArray();
		char indexChar = indexChars.length > 1 ? indexChars[1] : indexChars[0];// ���code�����Ǵ�����λ���ģ���ȡ����Ϊ1�����֣�����ȡ����Ϊ0������
		System.out.println("����	$SoGou$���������index:" + indexChar);
		$SoGou$ = liststr.get(Integer.parseInt(String.valueOf(indexChar)));

		_utm = new StringBuilder();
		for (int i = 0; i < wtfFnReturn.length; i++) {
			_utm.append($SoGou$[Integer.parseInt(wtfFnReturn[i])]);
		}

		System.out.println("����_utm===" + _utm);

		headers = getHeaders();
		credence = "token=" + token + "; _utm=" + _utm;
		headers.put("CheckError", "check");
		headers.put("Referer", "http://www.tianyancha.com/company/" + key);
		headers.put("Cookie",
				"TYCID=f872552ce66f4902a43afd903894eeee; tnet=42.159.236.8; RTYCID=a54c421fc34d44c7b63154eaf3782d09; Hm_lvt_e92c8d65d92d534b0fc290df538b4758=1473843346,1473843958,1473843975; Hm_lpvt_e92c8d65d92d534b0fc290df538b4758=1473844133; _pk_id.1.e431=65f91552c041537e.1473843346.1."
						+ str + ".1473843346.; _pk_ses.1.e431=*;" + credence);

		// 2:�޸�Cookie ��������������
		url = "http://www.tianyancha.com/company/" + key + ".json";
		
		
		  body = HttpProxy.getHttpRequestContentByGet(url, null, headers);
		System.out.println("body="+body);
		System.out.println("���������ַ===" + url);
		resp = HttpUtils.get(url, util1).setHeaders(headers).execute();
		System.out.println("�����������Ӧ״̬��:" + resp.getStatusLine());
		statusCode = resp.getStatusLine().getStatusCode();
		if (statusCode != 200) {
			System.err.println("��������ʧ��,״̬��:" + statusCode);
			return;
		}
		System.out.println("��ȡ���������ս��===" + resp.getString());
		resp.shutdown();// �ر�����

	}

	
	public static String getDetail(String key){
		try{
			//String key="1000032629";//ĳ�������ID ��ʶ ����̬�޸ģ�
			
			//1:��ȡ�޸�Cookie  ���ݲ��޸�Cookie
			long number= new Date().getTime();
			String str=Long.toString(number).substring(0, 10);
			Map<String, String> headers = getHeaders();
			headers.put("Cookie", "TYCID=f872552ce66f4902a43afd903894eeee; tnet=42.159.236.8; RTYCID=a54c421fc34d44c7b63154eaf3782d09; Hm_lvt_e92c8d65d92d534b0fc290df538b4758=1473843346,1473843958,1473843975; Hm_lpvt_e92c8d65d92d534b0fc290df538b4758="+str);
			String url = "http://www.tianyancha.com/tongji/"+key+".json?random=" +number;
			//System.out.println("��ȡCookie�����ַ===" + url);
			
			 HttpUtils util1 = HttpUtils.get(url).setHeaders(getHeaders());
			 ResponseWrap resp = util1.execute();
			 Tongji bean = resp.getJson(Tongji.class);
		//	System.out.println("ת��ΪBean===" + bean);
			
			String script = fromCharCode(bean.getData().getV().split(","));
		//	System.out.println("��ȡ�ű�����===" + script);
			
			 String token = script.substring(script.indexOf("token=") + "token=".length(), script.indexOf(";path="));
		//	 System.out.println("����token==" + token);
			 String[] wtfFnReturn = script.substring(script.indexOf("return'") + "return'".length(), script.indexOf("'}}")).split(",");
			 
			 	List<String[]> liststr=new ArrayList<String[]>();
				liststr.add($SoGou0$);
				liststr.add($SoGou1$);
				liststr.add($SoGou2$);
				liststr.add($SoGou3$);
				liststr.add($SoGou4$);
				liststr.add($SoGou5$);
				liststr.add($SoGou6$);
				liststr.add($SoGou7$);
				liststr.add($SoGou8$);
				liststr.add($SoGou9$);
				//�ı�����cookie utm��SoGou����,�����������ŵ����ȡȡ��һ��$SoGou$���飬�������� utm ���
				if(key==null||key.equals(""))return null;
				String index=key.codePointAt(0)+"";
				char[] indexChars= index.toCharArray();
				char indexChar=indexChars.length>1?indexChars[1]:indexChars[0];//���code�����Ǵ�����λ���ģ���ȡ����Ϊ1�����֣�����ȡ����Ϊ0������
		//		System.out.println("����	$SoGou$���������index:"+indexChar);
				String[] $SoGou$=liststr.get(Integer.parseInt(String.valueOf(indexChar)));
				
			StringBuilder  _utm = new StringBuilder();
			for (int i = 0; i < wtfFnReturn.length; i++) {
				_utm.append($SoGou$[Integer.parseInt(wtfFnReturn[i])]);
			}

		//	System.out.println("����_utm===" + _utm);
			
			
			headers = getHeaders();
 		String credence = "token=" + token + "; _utm=" + _utm;
			headers.put("CheckError", "check");
			headers.put("Referer", "http://www.tianyancha.com/company/"+key);
			headers.put("Cookie", "TYCID=f872552ce66f4902a43afd903894eeee; tnet=42.159.236.8; RTYCID=a54c421fc34d44c7b63154eaf3782d09; Hm_lvt_e92c8d65d92d534b0fc290df538b4758=1473843346,1473843958,1473843975; Hm_lpvt_e92c8d65d92d534b0fc290df538b4758=1473844133; _pk_id.1.e431=65f91552c041537e.1473843346.1."+str+".1473843346.; _pk_ses.1.e431=*;" + credence);
			
		
			//2:�޸�Cookie ��������������
			url = "http://www.tianyancha.com/company/"+key+".json";
	//		System.out.println("���������ַ===" + url);
			resp = HttpUtils.get(url, util1).setHeaders(headers).execute();
		//	System.out.println("�����������Ӧ״̬��:" + resp.getStatusLine());
			int statusCode = resp.getStatusLine().getStatusCode();
			if(statusCode != 200) {
				System.err.println("��������ʧ��,״̬��:" + statusCode);
				return null ;
			}
	//		System.out.println("��ȡ���������ս��===" + resp.getString());
			resp.shutdown();//�ر�����
	//	 System.out.println("��ȡ�����ս��===" + resp.getString());
			return resp.getString();
		}catch(Exception e){
			
		}
		return null;
	}
	public static String getToken(String key) {
		// �ڶ�����ϸ��ѯ
		// String key="142083219";//ĳ�������ID ��ʶ ����̬�޸ģ�
		Map<String, String> headers = getHeaders();
		// 1:��ȡ�޸�Cookie ���ݲ��޸�Cookie
		long number = new Date().getTime();
		String str = Long.toString(number).substring(0, 10);
		headers.put("Cookie",
				"TYCID=f872552ce66f4902a43afd903894eeee; tnet=42.159.236.8; RTYCID=a54c421fc34d44c7b63154eaf3782d09; Hm_lvt_e92c8d65d92d534b0fc290df538b4758=1473843346,1473843958,1473843975; Hm_lpvt_e92c8d65d92d534b0fc290df538b4758="
						+ str);
		String url = "http://www.tianyancha.com/tongji/" + key + ".json?random=" + number;
		System.out.println("��ȡCookie�����ַ===" + url);

		HttpUtils util1 = HttpUtils.get(url).setHeaders(getHeaders());
		ResponseWrap resp = util1.execute();

		Tongji bean = resp.getJson(Tongji.class);
		System.out.println("ת��ΪBean===" + bean);

		String script = fromCharCode(bean.getData().getV().split(","));
		System.out.println("��ȡ�ű�����===" + script);

		String token = script.substring(script.indexOf("token=") + "token=".length(), script.indexOf(";path="));
		System.out.println("����token==" + token);
		String[] wtfFnReturn = script.substring(script.indexOf("return'") + "return'".length(), script.indexOf("'}}"))
				.split(",");

		List<String[]> liststr = new ArrayList<String[]>();
		liststr.add($SoGou0$);
		liststr.add($SoGou1$);
		liststr.add($SoGou2$);
		liststr.add($SoGou3$);
		liststr.add($SoGou4$);
		liststr.add($SoGou5$);
		liststr.add($SoGou6$);
		liststr.add($SoGou7$);
		liststr.add($SoGou8$);
		liststr.add($SoGou9$);
		// �ı�����cookie utm��SoGou����,�����������ŵ����ȡȡ��һ��$SoGou$���飬�������� utm ���
		if (key == null || key.equals(""))
			return null;
		String index = key.codePointAt(0) + "";
		char[] indexChars = index.toCharArray();
		char indexChar = indexChars.length > 1 ? indexChars[1] : indexChars[0];// ���code�����Ǵ�����λ���ģ���ȡ����Ϊ1�����֣�����ȡ����Ϊ0������
		System.out.println("����	$SoGou$���������index:" + indexChar);
		String[] $SoGou$ = liststr.get(Integer.parseInt(String.valueOf(indexChar)));

		StringBuilder _utm = new StringBuilder();
		for (int i = 0; i < wtfFnReturn.length; i++) {
			_utm.append($SoGou$[Integer.parseInt(wtfFnReturn[i])]);
		}

		System.out.println("����_utm===" + _utm);

		headers = getHeaders();
		String credence = "token=" + token + "; _utm=" + _utm;
		headers.put("CheckError", "check");
		headers.put("Referer", "http://www.tianyancha.com/company/" + key);
		String cookie = "TYCID=f872552ce66f4902a43afd903894eeee; tnet=42.159.236.8; RTYCID=a54c421fc34d44c7b63154eaf3782d09; Hm_lvt_e92c8d65d92d534b0fc290df538b4758=1473843346,1473843958,1473843975; Hm_lpvt_e92c8d65d92d534b0fc290df538b4758=1473844133; _pk_id.1.e431=65f91552c041537e.1473843346.1."
				+ str + ".1473843346.; _pk_ses.1.e431=*;" + credence;
		
		// headers.put("Cookie", "TYCID=f872552ce66f4902a43afd903894eeee;
		// tnet=42.159.236.8; RTYCID=a54c421fc34d44c7b63154eaf3782d09;
		// Hm_lvt_e92c8d65d92d534b0fc290df538b4758=1473843346,1473843958,1473843975;
		// Hm_lpvt_e92c8d65d92d534b0fc290df538b4758=1473844133;
		// _pk_id.1.e431=65f91552c041537e.1473843346.1."+str+".1473843346.;
		// _pk_ses.1.e431=*;" + credence);
		return cookie;
	}
}
