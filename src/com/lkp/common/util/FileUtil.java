/*
 * Copyright (c) SHADANSOU 2014 All Rights Reserved
 *
 */
package com.lkp.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Set;
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
		} catch (Exception e) {
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
