package com.lkp.project.qichacha;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpUtils {     
    public static String getAjaxCotnent(String url)  {     
    	try{
    		  Runtime rt = Runtime.getRuntime();     
    	        Process p = rt.exec("D:\\phantomjs\\bin\\phantomjs ef5c2b8fb781963fb9d77816885f9335.js "+url);
    	        		//rt.exec("D:\\phantomjs\\bin\\phantomjs code.js "+url);//这里我的codes.js是保存在c盘下面的phantomjs目录     
    	        System.out.println("execute end");
    	        InputStream is = p.getInputStream();     
    	        BufferedReader br = new BufferedReader(new InputStreamReader(is));     
    	        StringBuffer sbf = new StringBuffer();     
    	        String tmp = "";     
    	        while((tmp = br.readLine())!=null){     
    	        	System.out.println("tmp="+tmp);
    	            sbf.append(tmp);     
    	        }     
    	        //System.out.println(sbf.toString());     
    	        return sbf.toString();     
    	}catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
      
    }     
     
    public static void main(String[] args) throws IOException {     
       String body =  getAjaxCotnent("http://www.tianyancha.com/company/182750933");     
       System.out.println("body="+body);
    }     
}    