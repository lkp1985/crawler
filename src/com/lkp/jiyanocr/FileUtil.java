/*
 * Copyright (c) SHADANSOU 2014 All Rights Reserved
 *
 */
package com.lkp.jiyanocr;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
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
		outpath = currpath + File.separator + "output" + File.separator;
		input = currpath + File.separator + "input" + File.separator;
		File temp = new File(outpath);
		if (!temp.exists()) {
			temp.mkdirs();
		}
		keyspath = currpath + File.separator + "cfg" + File.separator + "key.txt";
		countrypath = currpath + File.separator + "cfg" + File.separator + "country.txt";
		provincepath = currpath + File.separator + "cfg" + File.separator + "province.txt";
		citypath = currpath + File.separator + "cfg" + File.separator + "city.txt";
		// System.out.println("cfgpath="+cfgPath);
		// String path = url.getPath();
		try {
			p.load(new FileReader(new File(cfgPath)));

			// System.out.println("path="+path);

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
	public static void deleteOutputFile() {
		File f = new File(outpath);
		for(String fname : f.list()){
			File temp =new File(fname);
			temp.delete();
		} 
	}
	public static void deleteInputFile() {
		File f = new File(input);
		for(String fname : f.list()){
			File temp =new File(fname);
			temp.delete();
		} 
	}
	public static void write(String x) {
		deleteInputFile();
		File f = new File(outpath+"x.txt");
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
