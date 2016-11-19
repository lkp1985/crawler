/*
 * Copyright (c) SHADANSOU 2014 All Rights Reserved
 *
 */
package com.lkp.project.weibo;

/**
 * <p>class function description.<p>
 *
 * create  2016年3月10日<br>
 * @author  lkp<br> 
 * @version 1.0
 * @since   1.0
 */
public class Entity {
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getWid() {
		return wid;
	}
	public void setWid(String wid) {
		this.wid = wid;
	}
	public String getFans() {
		try{
			Integer.parseInt(fans);
		}catch(Exception e){
			return "0";
		}
		return fans;
	}
	public void setFans(String fans) {
		this.fans = fans;
	}
	public String getGzs() {
		try{
			Integer.parseInt(gzs);
		}catch(Exception e){
			return "0";
		}
		return gzs;
	}
	public void setGzs(String gzs) {
		this.gzs = gzs;
	}
	public String getWebos() {
		try{
			Integer.parseInt(webos);
		}catch(Exception e){
			return "0";
		}
		return webos;
	}
	public void setWebos(String webos) {
		this.webos = webos;
	}
	public String getZfs() {
		try{
			Integer.parseInt(zfs);
		}catch(Exception e){
			return "0";
		}
		return zfs;
	}
	public void setZfs(String zfs) {
		this.zfs = zfs;
	}
	public String getPls() {
		try{
			Integer.parseInt(pls);
		}catch(Exception e){
			return "0";
		}
		return pls;
	}
	public void setPls(String pls) {
		this.pls = pls;
	}
	public String getResdate() {
		return resdate;
	}
	public void setResdate(String resdate) {
		this.resdate = resdate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPubdate() {
		return pubdate;
	}
	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	/**
	 * @return the isV
	 */
	public String getIsV() {
		return isV;
	}
	/**
	 * @param isV the isV to set
	 */
	public void setIsV(String isV) {
		this.isV = isV;
	}
	/**
	 * @return the authReson
	 */
	public String getAuthReson() {
		return authReson;
	}
	/**
	 * @param authReson the authReson to set
	 */
	public void setAuthReson(String authReson) {
		this.authReson = authReson;
	}
	/**
	 * @return the eachFans
	 */
	public String getEachFans() {
		return eachFans;
	}
	/**
	 * @param eachFans the eachFans to set
	 */
	public void setEachFans(String eachFans) {
		this.eachFans = eachFans;
	}
	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	private String sid;//微博ID
	private String wid;//博文ID
	private String fans;
	private String gzs;
	private String webos;
	private String zfs;//转发数
	private String pls;//评论数
	
	private String resdate;
	private String content;
	private String pname;//昵称
	private String address;
	private String sex;
	private String pubdate;
	private String keywords;//关键词
	private String isV;
	private String authReson;
	private String eachFans;
	private String longitude;
	private String latitude;
	
	/*
	 * map.put("15", "认证原因");
			map.put("16", "互粉数");
			map.put("17", "经度");
			map.put("18", "纬度");
			map.put("19", "关键词");
			
	 */
	
}
