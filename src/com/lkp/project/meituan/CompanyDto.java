package com.lkp.project.meituan;

import java.util.List;

public class CompanyDto {

	private String id;// tyc的ID号，183761655
	private String legalPersonName;// 法人，陈猛
	private String base;// 注册地
	private String regGov;//注册机关
	private String regMoney;//注册资金
	private String name;/// 公司名,如江西泓泰企业集团有限公司
	private String regStatus;// 公司状态，如开业、注销
	private String industry;// 公司行业，如商务服务业
	private String regNumber;// 360000520000154
	private String creditCode;// 91360000612447241H
	
	private String scope ;//经营范围
	private String postcode;
	private String postalAddress;//南昌市高新开发区高新六路116号
	private String phoneNumber;//电话号码
	private String email;//
	private String website;
	private String shareholderList;//股东列表
	private String yearReport;//年报
	// basicinfo
	/*
	 * 
	 * {"id":183761655,"name":"江西泓泰企业集团有限公司","type":1,"base":"江西",
	 * "legalPersonName":"陈猛","estiblishTime":null,"regCapital":null,"regStatus"
	 * :"开业","score":"98",
	 * "orginalScore":"9837","historyNames":null,"categoryCode":"1901",
	 * "industry":"商务服务业",
	 * "humanNames":"","trademarks":null,"usedBondName":"","bondName":"",
	 * "bondNum":"","
	 * bondType":"","newtestName":"","regNumber":"360000520000154","orgNumber":"","
	 * creditCode":"91360000612447241H","businessScope":null,"contantMap":null}
	 */

	// yearreport
	
	
	/*
	 * {"baseInfo":{"reportYear":"2014","companyName":"江西泓泰企业集团有限公司",
	 * "creditCode":"91360000612447241H","regNumber":"360000520000154",
	 * "phoneNumber":"88162211","postcode":"330096","postalAddress":
	 * "南昌市高新开发区高新六路116号","email":"25887430@qq.com","manageState":"开业",
	 * "employeeNum":"","operatorName":"","totalAssets":"","totalEquity":"",
	 * "totalSales":"","totalProfit":"","primeBusProfit":"","retainedProfit":"",
	 * "totalTax":"","totalLiability":""},"changeRecordList":[],
	 * "equityChangeInfoList":[],"outGuaranteeInfoList":[{"creditor":
	 * "中国银行股份有限公司安义支行","obligor":"江西鑫隆泰建材工业有限公司","creditoType":"合同",
	 * "creditoAmount":"200","creditoTerm":"2014年09月28日-2015年09月27日",
	 * "guaranteeTerm":"期限","guaranteeWay":"连带保证","guaranteeScope":""}],
	 * "outboundInvestmentList":[{"outcompanyName":
	 * "<a href=\"http://www.tianyancha.com/company/809170386\" target=\"_blank\">南昌泓泰置业发展有限公司</a>"
	 * ,"regNum":"360123210000267","creditCode":""},{"outcompanyName":
	 * "<a href=\"http://www.tianyancha.com/company/244612657\" target=\"_blank\">江西雅丽泰建材股份有限公司</a>"
	 * ,"regNum":"360100119401753","creditCode":""},{"outcompanyName":
	 * "<a href=\"http://www.tianyancha.com/company/1669646614\" target=\"_blank\">江西泓泰环境景观工程有限公司</a>"
	 * ,"regNum":"360100219402164","creditCode":""},{"outcompanyName":
	 * "<a href=\"http://www.tianyancha.com/company/2324643848\" target=\"_blank\">南昌溪霞湖度假村有限责任公司</a>"
	 * ,"regNum":"360122110090970","creditCode":""},{"outcompanyName":
	 * "<a href=\"http://www.tianyancha.com/company/2320314123\" target=\"_blank\">南昌沁田农庄有限公司</a>"
	 * ,"regNum":"360123210007280","creditCode":""},{"outcompanyName":
	 * "<a href=\"http://www.tianyancha.com/company/244612950\" target=\"_blank\">江西益佰年投资有限公司</a>"
	 * ,"regNum":"360123110001075","creditCode":""},{"outcompanyName":
	 * "<a href=\"http://www.tianyancha.com/company/826764211\" target=\"_blank\">南昌益佰兔电子商务有限公司</a>"
	 * ,"regNum":"360106210009664","creditCode":""},{"outcompanyName":
	 * "<a href=\"http://www.tianyancha.com/company/950721734\" target=\"_blank\">江西佰年互联科技股份有限公司</a>"
	 * ,"regNum":"360106110003492","creditCode":""},{"outcompanyName":
	 * "<a href=\"http://www.tianyancha.com/company/662945424\" target=\"_blank\">南昌泓泰国际教育咨询有限公司</a>"
	 * ,"regNum":"360106110003870","creditCode":""},{"outcompanyName":
	 * "<a href=\"http://www.tianyancha.com/company/808964964\" target=\"_blank\">江西樱花新材料有限公司</a>"
	 * ,"regNum":"360123110000808","creditCode":""},{"outcompanyName":
	 * "<a href=\"http://www.tianyancha.com/company/237080896\" target=\"_blank\">江西阳光高氟科技有限公司</a>"
	 * ,"regNum":"360123210008410","creditCode":""},{"outcompanyName":
	 * "<a href=\"http://www.tianyancha.com/company/183759614\" target=\"_blank\">四川雅丽泰节能建材有限公司</a>"
	 * ,"regNum":"510131000006989","creditCode":""}],"shareholderList":[{
	 * "investorName":"江西省金合控股集团有限公司","subscribeAmount":"800.47","subscribeTime"
	 * :"2011-08-15","subscribeType":"货币","paidAmount":"800.47","paidTime":
	 * "2011-08-15","paidType":"货币"},{"investorName":"江西泓泰实业投资有限责任公司",
	 * "subscribeAmount":"4720.03","subscribeTime":"2011-08-15","subscribeType":
	 * "货币","paidAmount":"4720.03","paidTime":"2011-08-15","paidType":"货币"},{
	 * "investorName":"台湾泓泰工业有限公司","subscribeAmount":"2365.93","subscribeTime":
	 * "1995-02-27","subscribeType":"货币","paidAmount":"2365.93","paidTime":
	 * "1995-02-27","paidType":"货币"}],"webInfoList":[{"webType":"网站","name":
	 * "江西泓泰企业集团有限公司","website":"http://www.hongtai.cn/"}
	 */
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLegalPersonName() {
		return legalPersonName;
	}
	public void setLegalPersonName(String legalPersonName) {
		this.legalPersonName = legalPersonName;
	}
	public String getBase() {
		return base;
	}
	public void setBase(String base) {
		this.base = base;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRegStatus() {
		return regStatus;
	}
	public void setRegStatus(String regStatus) {
		this.regStatus = regStatus;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getRegNumber() {
		return regNumber;
	}
	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}
	public String getCreditCode() {
		return creditCode;
	}
	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getPostalAddress() {
		return postalAddress;
	}
	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	 
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getYearReport() {
		return yearReport;
	}
	public void setYearReport(String yearReport) {
		this.yearReport = yearReport;
	}
	public String getShareholderList() {
		return shareholderList;
	}
	public void setShareholderList(String shareholderList) {
		this.shareholderList = shareholderList;
	}
	public String getRegGov() {
		return regGov;
	}
	public void setRegGov(String regGov) {
		this.regGov = regGov;
	}
	public String getRegMoney() {
		return regMoney;
	}
	public void setRegMoney(String regMoney) {
		this.regMoney = regMoney;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	
}

//股东信息
class ShareHolder{
	private String investorName;
	private String paidAmount;
	private String subscribeTime;
	public String getInvestorName() {
		return investorName;
	}
	public void setInvestorName(String investorName) {
		this.investorName = investorName;
	}
	public String getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(String paidAmount) {
		this.paidAmount = paidAmount;
	}
	public String getSubscribeTime() {
		return subscribeTime;
	}
	public void setSubscribeTime(String subscribeTime) {
		this.subscribeTime = subscribeTime;
	}
	 
	

}