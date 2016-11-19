/*
 * Copyright (c) SHADANSOU 2014 All Rights Reserved
 *
 */
package com.lkp.project.tianyancha;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import net.sf.json.JSONObject;
/**
 * 
 * <p>class function description.<p>
 *
 * create  2016Äê3ÔÂ14ÈÕ<br>
 * @author  lkp<br> 
 * @version 1.0
 * @since   1.0
 */
public class FileUtil {
	static Properties p = new Properties();

	static String cfgPath;
	static String outpath;
	static String input;
	static String keyspath;
	static String countrypath;
	static String provincepath;
	static String citypath;
	static BufferedWriter writer = null;
	static BufferedWriter outputwriter = null;
	static BufferedReader reader = null;
	static Set<String> keyset;
	static OutputStreamWriter fileWriter =null;
	static FileWriter idWriter;
	static BufferedReader idReader ;
	static {
		// URL url = FileUtil.class
		// .getProtectionDomain().getCodeSource().getLocation();
		String currpath = System.getProperty("user.dir");
		// System.out.println("currpath=="+currpath);
		cfgPath = currpath + File.separator + "cfg" + File.separator + "cfg.properties"; 
		// System.out.println("cfgpath="+cfgPath);
		// String path = url.getPath();
		try {
			InputStream inputStream = new FileInputStream(cfgPath);
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
			p.load(bf);
			
//			fileWriter = new FileWriter(new File("/home/test.txt"),true);
//			br = new BufferedReader(new FileReader("/home/data/ids.txt"));
//			idWriter =  new FileWriter(new File("/home/data/ids.txt"),true); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Set<String> getReadIds(){
		try{
			String file = "/home/data/ids.txt";
		//	file="d://data//logs//ids.txt";
			Set<String> ids = new HashSet<String>();
			if(idReader ==null){
				idReader = new BufferedReader(new FileReader(file));
			}
			String line = "";
			while((line =idReader.readLine())!=null){
				ids.add(line);
			}
			idReader.close();
			return ids;
		}catch(Exception e){
			e.printStackTrace();
		}
		return new HashSet<String>();
	}
	
	public static void writeId(String id){
		try{
			String file = "/home/data/ids.txt";
		//	file="d://data//logs//ids.txt";
			if(idWriter==null){
				idWriter =  new FileWriter(new File(file),true); 
			}
			idWriter.write(id+"\n");
			idWriter.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void writeResult(String id,String content){
		try{
			String file = "/home/data/test.txt";
		//	file="d://data//logs//test.txt";
			if(fileWriter ==null){
				fileWriter = new OutputStreamWriter(new FileOutputStream(file,true),"UTF-8");
			//	fileWriter = new FileWriter(new File(file),true);
			}
			JSONObject obj  = new JSONObject();
			obj.put(id, content);
			fileWriter.write(obj.toString()+"\n");
			fileWriter.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static String getInputFile() {
		File f = new File(outpath);
		for(String fname : f.list()){
			return fname;
		}
		return null;
	}
	 

	public static String getBasicDir(){
		String filedir = p.getProperty("basic.dir");
		return filedir;
	}
	public static String getYearDir(){
		String filedir = p.getProperty("year.dir");
		return filedir;
	}
	public static String getDbPath() {
		
		String dbpath = p.getProperty("db");
		return dbpath;
	}
	public static String getKeywords() {
		String keywords = p.getProperty("keywords");
		return keywords;
	}
	
	
	public static void save() {
		try {
			FileOutputStream fos = new FileOutputStream(cfgPath);
			p.setProperty("bbb", "333444");
			p.store(fos, "");
			fos.flush();
			fos.close();
			System.out.println("store properties success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	public static void writeoutput(String message) {
		try {
			FileWriter fw = new FileWriter("");
			if (outputwriter == null) {
				String filepath = FileUtil.getInputFile();
				outputwriter = new BufferedWriter(new FileWriter(new File(filepath)));
			}
			outputwriter.write(message + "\n");
			outputwriter.flush();
		} catch (Exception e) {

		}
	}

	public static String getValueWithNull(Object value){
		if(value == null){
			return "";
		}else{
			return value.toString();
		}
	}
	public static void main(String[] args) {
	}
}
