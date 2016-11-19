package com.lkp.court;

import java.io.FileWriter;
import java.util.Date;

import org.apache.solr.common.SolrInputDocument;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.lkp.common.net.HttpTools;
import com.lkp.common.util.CommonUtil;
import com.lkp.common.util.DateUtil;
import com.lkp.solr.SolrUtils;

import net.sf.json.JSONArray;

public class ParseThread implements Runnable{
	String startDate = "";
	String endDate = "";
	String error = "";
	public ParseThread(String start ,String end,String errorfile) {
		this.startDate = start;
		this.endDate = end;
		this.error = errorfile;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void run() {
		while(true){
			try{
				 CommonUtil.runByDate(startDate, endDate, error);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	 
	}
}
