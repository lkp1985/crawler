package com.lkp.project.qichacha;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ExcelUtil {

	static String file = "";
	static WritableWorkbook book = null;
	static WritableSheet sheet = null;
	static int total = 0;
	static int i = 0;
	static int readIndex = 0;
	private static final Log LOGGER = LogFactory.getLog(ExcelUtil.class);
	/**
	 * 
	 * @param file1
	 *            :基本信息excel
	 * @param file2:年报excel
	 * @return
	 */
	public static Map<String, CompanyDto> readCompanyInfoFromExcel(String file1, String file2) {
		
		Map<String, CompanyDto> companyMap = new HashMap<String, CompanyDto>();
		try {
			InputStream stream = new FileInputStream(file1);
			Workbook book = Workbook.getWorkbook(stream);
			
			Sheet sheet = book.getSheet(0);
			for (int i = 1; i < sheet.getRows(); i++) {// 取第一列的ID值
				Cell cell = sheet.getCell(1, i);
				String content = cell.getContents();
				JSONObject baseInfo = JSONObject.fromObject(content);
				CompanyDto dto = new CompanyDto();
				dto.setId(baseInfo.get("id") + "");
				dto.setBase(baseInfo.get("base") + "");
				String name = baseInfo.get("name") + "";
				name = name.replace("<em>", "");
				name = name.replace("</em>", "");
				dto.setName(name);
				dto.setIndustry(baseInfo.get("industry") + "");
				dto.setLegalPersonName(baseInfo.get("legalPersonName") + "");
				dto.setRegStatus(baseInfo.get("regStatus") + "");
				dto.setCreditCode(baseInfo.get("creditCode") + "");
				dto.setRegNumber(baseInfo.get("regNumber") + "");
				companyMap.put(baseInfo.get("id") + "", dto);
			}
			stream.close();
			book.close();
			// 拿年报信息
			stream = new FileInputStream(file2);
			book = Workbook.getWorkbook(stream);
			sheet = book.getSheet(0);
			for (int i = 1; i < sheet.getRows(); i++) {
				try{
					Cell cell = sheet.getCell(0, i);
					String id = cell.getContents();
					cell = sheet.getCell(1, i);
					String content = cell.getContents();
					JSONObject yearInfo = JSONObject.fromObject(JSONObject.fromObject(content).get("content"));
					JSONObject yearInfoObj = null;
					if(yearInfo.containsKey("2015")){
						try{
							yearInfoObj = 	JSONObject.fromObject(JSONObject.fromObject(yearInfo.get("2015")).get("baseInfo"));
						}catch(Exception e){
							
						}
					}
					if(yearInfoObj !=null){
						CompanyDto dto = companyMap.get(id);
						if(dto==null){
							System.out.println("id:"+id+" have no yearreport or have no info");
							LOGGER.error("id:"+id+" have no yearreport or have no info");
							continue;
						}
						
						try{
							dto.setYearReport(yearInfoObj+"");
							dto.setPostcode(yearInfoObj.get("postcode") + "");
							dto.setPhoneNumber(yearInfoObj.get("phoneNumber") + "");
							dto.setEmail(yearInfoObj.get("email") + "");
							dto.setPostalAddress(yearInfoObj.get("postalAddress") + "");
							dto.setId(id);
							//List<ShareHolder> shareList =new ArrayList<ShareHolder>();
							JSONArray array  = JSONArray.fromObject(JSONObject.fromObject(yearInfo.get("2015")).get("shareholderList"));
							dto.setShareholderList(array+"");
//							for(Object obj : array){
//								JSONObject shareObj = JSONObject.fromObject(obj);
//								ShareHolder shareHolder = new ShareHolder();
//								shareHolder.setInvestorName(shareObj.get("investorName")+"");
//								shareHolder.setPaidAmount(shareObj.get("paidAmount")+"");
//								shareHolder.setSubscribeTime(shareObj.get("subscribeTime")+"");
//								shareList.add(shareHolder);
//							}
//							dto.setShareholderList(shareList);
						}catch(Exception e){
							
						}
					}
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			stream.close();
			book.close();
			
			
			//对比两个dto
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return companyMap;
	}

	public static List<String> readIdFromExcel(String filename) {

		try {
			System.out.println("filename=" + filename);
			filename = FileUtil.getBasicDir() +File.separator+ filename;
			System.out.println("filename=" + filename);
			InputStream stream = new FileInputStream(filename);
			List<String> idList = new ArrayList<String>();
			// 获取Excel文件对象
			Workbook book = Workbook.getWorkbook(stream);
			Sheet sheet = book.getSheet(0);
			// 行数(表头的目录不需要，从1开始)
			for (int i = 1; i < sheet.getRows(); i++) {// 取第一列的ID值
				// 创建一个数组 用来存储每一列的值
				Cell cell = sheet.getCell(0, i);
				// 列数
				String id = cell.getContents();
				idList.add(id);
			}
			book.close();
			stream.close();
			return idList;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	
	public static void writeCompany(Map<String,CompanyDto> companyMap,String filename) {
		try{
			WritableWorkbook book = Workbook.createWorkbook(new File(filename + ".xls"));
			WritableSheet sheet = book.createSheet("企业信息", 0);
			/*
			 * private String id;// tyc的ID号，183761655
	private String legalPersonName;// 法人，陈猛
	private String base;// 注册地
	private String name;/// 公司名,如江西泓泰企业集团有限公司
	private String regStatus;// 公司状态，如开业、注销
	private String industry;// 公司行业，如商务服务业
	private String regNumber;// 360000520000154
	private String creditCode;// 91360000612447241H
	
	
	private String postcode;
	private String postalAddress;//南昌市高新开发区高新六路116号
	private String phoneNumber;//电话号码
	private String email;//
	private String website;
			 */
			int colindex  =1 ;
			Label label = new Label(0, 0, "名称");
			sheet.addCell(label);
			label = new Label(colindex++, 0, "法人");
			sheet.addCell(label);
			label = new Label(colindex++, 0, "注册地");
			sheet.addCell(label);
			label = new Label(colindex++, 0, "电话号码");
			sheet.addCell(label);
			label = new Label(colindex++, 0, "邮箱");
			sheet.addCell(label);
			label = new Label(colindex++, 0, "邮政编码");
			sheet.addCell(label);
			label = new Label(colindex++, 0, "公司地址");
			sheet.addCell(label);
			label = new Label(colindex++, 0, "公司状态");
			sheet.addCell(label);
			label = new Label(colindex++, 0, "公司行业");
			sheet.addCell(label);
			label = new Label(colindex++, 0, "工商代码");
			sheet.addCell(label);
			label = new Label(colindex++, 0, "信用代码");
			sheet.addCell(label); 
			label = new Label(colindex++, 0, "投资人信息");
			sheet.addCell(label);
			label = new Label(colindex++, 0, "企业年报");
			sheet.addCell(label);
			
			int index = 0;
			
			for(String key : companyMap.keySet()){
				CompanyDto company = companyMap.get(key);
				colindex  =0 ;
				label = new Label(colindex++, index+ 1, company.getName());
				sheet.addCell(label);
				label = new Label(colindex++, index+ 1, company.getLegalPersonName());
				sheet.addCell(label);
				label = new Label(colindex++, index+ 1, company.getBase());
				sheet.addCell(label);
				label = new Label(colindex++, index+ 1, company.getPhoneNumber());
				sheet.addCell(label);
				label = new Label(colindex++, index+ 1, company.getEmail());
				sheet.addCell(label);
				label = new Label(colindex++, index+ 1, company.getPostcode());
				sheet.addCell(label);
				label = new Label(colindex++, index+ 1, company.getPostalAddress());
				sheet.addCell(label);
				label = new Label(colindex++, index+ 1, company.getRegStatus());
				sheet.addCell(label);
				label = new Label(colindex++, index+ 1, company.getIndustry());
				sheet.addCell(label);
				label = new Label(colindex++, index+ 1, company.getRegNumber());
				sheet.addCell(label);
				label = new Label(colindex++, index+ 1, company.getCreditCode());
				sheet.addCell(label);
				label = new Label(colindex++, index+ 1, company.getShareholderList());
				sheet.addCell(label);
				label = new Label(colindex++, index+ 1, company.getYearReport());
				index = index+1;
				sheet.addCell(label);
			}
			book.write();
			book.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}
	public static void writeExcel(JSONArray companyArray, String filename) {
		try {
			if (file.equals(filename)) {

			} else {
				file = filename;
				i = 0;
				if (book != null) {
					ExcelFlushAndClose();
				}

				book = Workbook.createWorkbook(new File(filename + ".xls"));
				sheet = book.createSheet("tianyancha", 0);
				Label label = new Label(0, 0, "id");
				sheet.addCell(label);
				label = new Label(1, 0, "content");
				sheet.addCell(label);
			}

			if (companyArray.size() > 0) {
				for (Object company : companyArray) {
					JSONObject comObj = JSONObject.fromObject(company);
					String id = comObj.getString("id");
					Label label = new Label(0, i + 1, id);
					sheet.addCell(label);
					label = new Label(1, i + 1, comObj.toString());
					sheet.addCell(label);

					i++;

				}
				total += companyArray.size();
				System.out.println("total write " + total + " record");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void ExcelFlushAndClose() {
		try {
			if (book != null) {
				book.write();
				book.close();
			}
		} catch (Exception e) {
			try {
				book.close();
			} catch (Exception ee) {
				e.printStackTrace();
			}
			//e.printStackTrace();
		}

	}
}
