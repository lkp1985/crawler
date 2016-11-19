package com.lkp.project.tianyancha;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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

	/**
	 * 解析xls
	 * @return
	 */
	public static Map<String,CompanyDto> readAllResultFromExcelByXls() {
		Map<String,CompanyDto> comList = new HashMap<String,CompanyDto>();//company 表
		Map<String,CompanyDto> comList2 = new HashMap<String,CompanyDto>();// youxi表
		try{
			String path = "E:\\文档\\个人\\company\\merge";//FileUtil.getBasicDir();
			File f = new File(path);
			File[] fs = f.listFiles();
			for (File file : fs) { 
				// 获取Excel文件对象
				InputStream is = new FileInputStream(file);
				Workbook book =  Workbook.getWorkbook(is);
				Sheet sheet = book.getSheet(0);
				for (int i = 1; i < sheet.getRows(); i++) {// 取第一列的ID值
					try{
						//名称	法人	注册地	电话号码	邮箱	邮政编码	公司地址	公司状态	公司行业	工商代码	信用代码	经营范围	投资人信息	企业年报

						CompanyDto company = new CompanyDto();
						int index = 0;
						String name = sheet.getCell(index++,i).getContents();
						company.setName(name);
						comList.put(name, company); 
						company.setLegalPersonName(sheet.getCell(index++,i).getContents());
						company.setBase(sheet.getCell(index++,i).getContents());
						company.setPhoneNumber(sheet.getCell(index++,i).getContents());
						company.setEmail(sheet.getCell(index++,i).getContents());
						company.setPostcode(sheet.getCell(index++,i).getContents());
						company.setPostalAddress(sheet.getCell(index++,i).getContents());
						company.setRegStatus(sheet.getCell(index++,i).getContents());
						company.setIndustry(sheet.getCell(index++,i).getContents());
						company.setRegNumber(sheet.getCell(index++,i).getContents());
						company.setCreditCode(sheet.getCell(index++,i).getContents());
						company.setScope(sheet.getCell(index++,i).getContents());
						company.setShareholderList(sheet.getCell(index++,i).getContents());
						company.setYearReport(sheet.getCell(index++,i).getContents());
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				is.close();
			}
			
			
			
			
			//取scope的company列表
			String path2 = "E:\\文档\\个人\\company\\merge2";
			File f2 = new File(path2);
			File[] fs2 = f2.listFiles();
			for (File file : fs2) {
				// 获取Excel文件对象
				XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
		        XSSFSheet sheet = workbook.getSheetAt(0);
		        
		        
			//	HSSFWorkbook book = HSSFWorkbook.getWorkbook(stream);
			//	Sheet sheet = book.getSheet(0);
				// 行数(表头的目录不需要，从1开始)
				for (int i = 1; i < sheet.getLastRowNum()+1; i++) {// 取第一列的ID值
					//公司名	法人	注册地	状态	行业	注册号	信用号	邮编	公司地址	联系方式	邮箱	经营范围	股东信息	年报
					// 创建一个CompanyDto 用来存储每一列的值
					CompanyDto company = new CompanyDto();
					
					int index = 0;//列 
					try{ 
						String id = "";
						if(sheet.getRow(i).getCell(0)!=null){
							if(sheet.getRow(i).getCell(0).getCellType() == XSSFCell.CELL_TYPE_STRING){
								  id = sheet.getRow(i).getCell(0).getStringCellValue();
								 
							}else if(sheet.getRow(i).getCell(0).getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
								  id = sheet.getRow(i).getCell(0).getNumericCellValue()+"";
								 
							}
						
						}else{
							continue;
						}
						String content =null;
						if(sheet.getRow(i).getCell(1)!=null){
							if(sheet.getRow(i).getCell(1).getCellType() == XSSFCell.CELL_TYPE_STRING){
								content =sheet.getRow(i).getCell(1).getStringCellValue();
								 
							}else if(sheet.getRow(i).getCell(1).getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
								content =sheet.getRow(i).getCell(1).getNumericCellValue()+"";
								 
							}
						
						}else{
							continue;
						} 
						JSONObject json = JSONObject.fromObject(content);
						JSONObject companyJson = json.getJSONObject("data");
						JSONObject baseInfoJson = companyJson.getJSONObject("baseInfo"); 
						String name =  baseInfoJson.get("name")+"";
						comList2.put(name, company);
						company.setId(id);
						company.setBase(baseInfoJson.get("base")+"");
					//	company.setCreditCode(json.getString("creditCode"));
						//phoneNumber
						company.setPhoneNumber(baseInfoJson.get("phoneNumber")+"");
						company.setEmail(baseInfoJson.get("email")+"");
						
						company.setRegNumber(baseInfoJson.get("regNumber")+"");
						if(baseInfoJson.get("creditCode")!=null){
							company.setCreditCode(baseInfoJson.get("creditCode")+"");
						}
						
						company.setPostalAddress(baseInfoJson.get("regLocation")+"");
						
						company.setLegalPersonName(baseInfoJson.get("legalPersonName")+"");
						company.setIndustry(baseInfoJson.get("industry")+"");
						company.setScope(baseInfoJson.get("businessScope")+"");

						company.setName(baseInfoJson.get("name")+"");
						company.setRegStatus(baseInfoJson.get("regStatus")+"");
						company.setShareholderList(companyJson.get("investorList")+"");
						company.setYearReport(companyJson.get("annuRepYearList")+"");
						
						
					}catch(Exception e){
						e.printStackTrace();
					}
					
				} 
				
			}
			
			
			
			//取scope的company列表
//			String path2 = "E:\\文档\\个人\\company\\test2";
//			File f2 = new File(path2);
//			File[] fs2 = f2.listFiles();
//			for (File file : fs2) {
//				// 获取Excel文件对象
//				InputStream is = new FileInputStream(file);
//				Workbook book =  Workbook.getWorkbook(is);
//				Sheet sheet = book.getSheet(0);
//				for (int i = 1; i < sheet.getRows(); i++) {// 取第一列的ID值
//					try{
//						CompanyDto company = new CompanyDto();
//						String id =  sheet.getCell(0, i).getContents();
//						String content = sheet.getCell(1, i).getContents();
//						
//						JSONObject json = JSONObject.fromObject(content);
//						JSONObject companyJson = json.getJSONObject("data");
//						JSONObject baseInfoJson = companyJson.getJSONObject("baseInfo"); 
//						String name =  baseInfoJson.get("name")+"";
//						comList2.put(name, company);
//						company.setId(id);
//						company.setBase(baseInfoJson.get("base")+"");
//					//	company.setCreditCode(json.getString("creditCode"));
//						company.setRegNumber(baseInfoJson.get("regNumber")+"");
//						if(baseInfoJson.get("creditCode")!=null){
//							company.setCreditCode(baseInfoJson.get("creditCode")+"");
//						}
//						
//						company.setPostalAddress(baseInfoJson.get("regLocation")+"");
//						
//						company.setLegalPersonName(baseInfoJson.get("legalPersonName")+"");
//						company.setIndustry(baseInfoJson.get("industry")+"");
//						company.setScope(baseInfoJson.get("businessScope")+"");
//
//						company.setName(baseInfoJson.get("name")+"");
//						company.setRegStatus(baseInfoJson.get("regStatus")+"");
//						company.setShareholderList(baseInfoJson.get("investorList")+"");
//					}catch(Exception e){
//						e.printStackTrace();
//					}
//					
//				}
//		        
//		     
//				
//			}
			//合并两个companyList
//			for(String name : comList2.keySet()){
//				if(comList.containsKey(name)){
//					String scope = comList2.get(name).getScope();
//					comList.get(name).setScope(scope);//插入scope
//				}else{
//					comList.put(name, comList2.get(name));//添加
//				}
//			}
			
			for(String name : comList2.keySet()){
				if(comList.containsKey(name)){
					String yearport = comList.get(name).getYearReport();
					comList2.get(name).setYearReport(yearport);//插入scope
					System.out.println("add year success:"+name);
				} 
			}
			
			 System.out.println("after emerge ,comList.size= "+comList.size());
			 Map<String,CompanyDto> newCompany = new HashMap<String,CompanyDto>();
			 int index = 0;
			 int fileindex  =1;
			 for(String key : comList2.keySet()){
				 if(index++<50000){
					 newCompany.put(key,comList2.get(key));
				 }else{
					 writeCompany(newCompany,"newpart"+fileindex++);
					 index=0;
					 newCompany = new HashMap<String,CompanyDto>();
				 }
			 } 
			 writeCompany(newCompany,"newpart"+fileindex++);
			 
			 newCompany = new HashMap<String,CompanyDto>();
			   index = 0;
			 fileindex  =1;
			 for(String key : comList.keySet()){
				 if(index++<50000){
					 newCompany.put(key,comList.get(key));
				 }else{
					 writeCompany(newCompany,"part"+fileindex++);
					 index=0;
					 newCompany = new HashMap<String,CompanyDto>();
				 }
			 } 
			 writeCompany(newCompany,"part"+fileindex++);
		}catch(Exception e){
			e.printStackTrace();
		}
		return comList;
	}
	
	/**
	 * 解析xlsx
	 * @return
	 */
	public static Map<String,CompanyDto> readAllResultFromExcelByXlsx() {
		Map<String,CompanyDto> comList = new HashMap<String,CompanyDto>();//company 表
		Map<String,CompanyDto> comList2 = new HashMap<String,CompanyDto>();// youxi表
		try{
			String path = "E:\\文档\\个人\\company\\test1";//FileUtil.getBasicDir();
			File f = new File(path);
			File[] fs = f.listFiles();
			for (File file : fs) { 
				// 获取Excel文件对象
				XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
		        XSSFSheet sheet = workbook.getSheetAt(0);
				// 行数(表头的目录不需要，从1开始)
				for (int i = 1; i < sheet.getLastRowNum()+1; i++) {// 取第一列的ID值
					//公司名	法人	注册地	状态	行业	注册号	信用号	邮编	公司地址	联系方式	邮箱	经营范围	股东信息	年报
					// 创建一个CompanyDto 用来存储每一列的值
					CompanyDto company = new CompanyDto();
					
					int index = 0;//列 
					try{
					//公司名	法人	注册地	状态	行业	注册号	信用号	邮编	公司地址	联系方式	邮箱	经营范围	股东信息	年报
					//name	legalPersonName	base	 regStatus	 industry	regNumber	creditCode	postcode	postalAddress 	phoneNumber	email	scope	shareholderList	yearReport

					// 创建一个CompanyDto 用来存储每一列的值
					//
					
						if(sheet.getRow(i).getCell(0)==null){
							System.out.println("i=null");
							continue;
						}
						String name = sheet.getRow(i).getCell(0).getStringCellValue();
						comList.put(name, company); 
						if(sheet.getRow(i).getCell(index++)!=null){
							if(sheet.getRow(i).getCell(index-1).getCellType() == XSSFCell.CELL_TYPE_STRING){
								company.setName(sheet.getRow(i).getCell(index-1).getStringCellValue());
							}else if(sheet.getRow(i).getCell(index-1).getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
								company.setName(sheet.getRow(i).getCell(index-1).getNumericCellValue()+"");
							}
							
						}
						if(sheet.getRow(i).getCell(index++)!=null){
							if(sheet.getRow(i).getCell(index-1).getCellType() == XSSFCell.CELL_TYPE_STRING){
								company.setLegalPersonName(sheet.getRow(i).getCell(index-1).getStringCellValue());
							}else if(sheet.getRow(i).getCell(index-1).getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
								company.setLegalPersonName(sheet.getRow(i).getCell(index-1).getNumericCellValue()+"");
							}
							
						
						}
						if(sheet.getRow(i).getCell(index++)!=null){
							if(sheet.getRow(i).getCell(index-1).getCellType() == XSSFCell.CELL_TYPE_STRING){
								company.setBase(sheet.getRow(i).getCell(index-1).getStringCellValue());
							}else if(sheet.getRow(i).getCell(index-1).getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
								company.setBase(sheet.getRow(i).getCell(index-1).getNumericCellValue()+"");
							}
						}
						if(sheet.getRow(i).getCell(index++)!=null){
							if(sheet.getRow(i).getCell(index-1).getCellType() == XSSFCell.CELL_TYPE_STRING){
								company.setRegStatus(sheet.getRow(i).getCell(index-1).getStringCellValue());
							}else if(sheet.getRow(i).getCell(index-1).getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
								company.setRegStatus(sheet.getRow(i).getCell(index-1).getNumericCellValue()+"");
							}
							
							
						}
						if(sheet.getRow(i).getCell(index++)!=null){
							if(sheet.getRow(i).getCell(index-1).getCellType() == XSSFCell.CELL_TYPE_STRING){
								company.setIndustry(sheet.getRow(i).getCell(index-1).getStringCellValue());
							}else if(sheet.getRow(i).getCell(index-1).getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
								company.setIndustry(sheet.getRow(i).getCell(index-1).getNumericCellValue()+"");
							}
							
							
						}
						if(sheet.getRow(i).getCell(index++)!=null){
							if(sheet.getRow(i).getCell(index-1).getCellType() == XSSFCell.CELL_TYPE_STRING){
								company.setRegNumber(sheet.getRow(i).getCell(index-1).getStringCellValue()+"");
							}else if(sheet.getRow(i).getCell(index-1).getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
								company.setRegNumber(sheet.getRow(i).getCell(index-1).getNumericCellValue()+"");
							}
							
						}
						if(sheet.getRow(i).getCell(index++)!=null){
							if(sheet.getRow(i).getCell(index-1).getCellType() == XSSFCell.CELL_TYPE_STRING){
								company.setCreditCode(sheet.getRow(i).getCell(index-1).getStringCellValue()+"");
							}else if(sheet.getRow(i).getCell(index-1).getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
								company.setCreditCode(sheet.getRow(i).getCell(index-1).getNumericCellValue()+"");
							}
						} 
						if(sheet.getRow(i).getCell(index++)!=null){
							if(sheet.getRow(i).getCell(index-1).getCellType() == XSSFCell.CELL_TYPE_STRING){
								company.setPostcode(sheet.getRow(i).getCell(index-1).getStringCellValue()+"");
							}else if(sheet.getRow(i).getCell(index-1).getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
								company.setPostcode(sheet.getRow(i).getCell(index-1).getNumericCellValue()+"");
							}
							
							
						} 
						if(sheet.getRow(i).getCell(index++)!=null){
							if(sheet.getRow(i).getCell(index-1).getCellType() == XSSFCell.CELL_TYPE_STRING){
								company.setPostalAddress(sheet.getRow(i).getCell(index-1).getStringCellValue());
							}else if(sheet.getRow(i).getCell(index-1).getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
								
								company.setPostalAddress(sheet.getRow(i).getCell(index-1).getNumericCellValue()+"");
							}
						}
						if(sheet.getRow(i).getCell(index++)!=null){
							if(sheet.getRow(i).getCell(index-1).getCellType() == XSSFCell.CELL_TYPE_STRING){
								company.setPhoneNumber(sheet.getRow(i).getCell(index-1).getStringCellValue()+"");
							}else if(sheet.getRow(i).getCell(index-1).getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
								company.setPhoneNumber(sheet.getRow(i).getCell(index-1).getNumericCellValue()+"");
							}
						}
						if(sheet.getRow(i).getCell(index++)!=null){
							if(sheet.getRow(i).getCell(index-1).getCellType() == XSSFCell.CELL_TYPE_STRING){
								company.setEmail(sheet.getRow(i).getCell(index-1).getStringCellValue());
							}else if(sheet.getRow(i).getCell(index-1).getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
								
								company.setEmail(sheet.getRow(i).getCell(index-1).getNumericCellValue()+"");
							} 
						}
						if(sheet.getRow(i).getCell(index++)!=null){
							if(sheet.getRow(i).getCell(index-1).getCellType() == XSSFCell.CELL_TYPE_STRING){
								company.setScope(sheet.getRow(i).getCell(index-1).getStringCellValue());
							}else if(sheet.getRow(i).getCell(index-1).getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
								company.setScope(sheet.getRow(i).getCell(index-1).getNumericCellValue()+"");
							}
							
						}
						if(sheet.getRow(i).getCell(index++)!=null){
							if(sheet.getRow(i).getCell(index-1).getCellType() == XSSFCell.CELL_TYPE_STRING){
								company.setShareholderList(sheet.getRow(i).getCell(index-1).getStringCellValue());
							}else if(sheet.getRow(i).getCell(index-1).getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
								company.setShareholderList(sheet.getRow(i).getCell(index-1).getNumericCellValue()+"");
							}
							
							
						}
						if(sheet.getRow(i).getCell(index++)!=null){
							if(sheet.getRow(i).getCell(index-1).getCellType() == XSSFCell.CELL_TYPE_STRING){
								company.setYearReport(sheet.getRow(i).getCell(index-1).getStringCellValue());
							}else if(sheet.getRow(i).getCell(index-1).getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
								company.setYearReport(sheet.getRow(i).getCell(index-1).getNumericCellValue()+"");
							}
						
						}
					}catch(Exception e){
						e.printStackTrace();
						System.out.println();
					}
				} 
			}
			
			//取scope的company列表
			String path2 = "E:\\文档\\个人\\company\\test2";
			File f2 = new File(path2);
			File[] fs2 = f2.listFiles();
			for (File file : fs2) {
				// 获取Excel文件对象
				XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
		        XSSFSheet sheet = workbook.getSheetAt(0);
		        
		        
			//	HSSFWorkbook book = HSSFWorkbook.getWorkbook(stream);
			//	Sheet sheet = book.getSheet(0);
				// 行数(表头的目录不需要，从1开始)
				for (int i = 1; i < sheet.getLastRowNum()+1; i++) {// 取第一列的ID值
					//公司名	法人	注册地	状态	行业	注册号	信用号	邮编	公司地址	联系方式	邮箱	经营范围	股东信息	年报
					// 创建一个CompanyDto 用来存储每一列的值
					CompanyDto company = new CompanyDto();
					
					int index = 0;//列 
					try{ 
						String id = "";
						if(sheet.getRow(i).getCell(0)!=null){
							if(sheet.getRow(i).getCell(0).getCellType() == XSSFCell.CELL_TYPE_STRING){
								  id = sheet.getRow(i).getCell(0).getStringCellValue();
								 
							}else if(sheet.getRow(i).getCell(0).getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
								  id = sheet.getRow(i).getCell(0).getNumericCellValue()+"";
								 
							}
						
						}else{
							continue;
						}
						String content =null;
						if(sheet.getRow(i).getCell(1)!=null){
							if(sheet.getRow(i).getCell(1).getCellType() == XSSFCell.CELL_TYPE_STRING){
								content =sheet.getRow(i).getCell(1).getStringCellValue();
								 
							}else if(sheet.getRow(i).getCell(1).getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
								content =sheet.getRow(i).getCell(1).getNumericCellValue()+"";
								 
							}
						
						}else{
							continue;
						} 
						JSONObject json = JSONObject.fromObject(content);
						JSONObject companyJson = json.getJSONObject("data");
						JSONObject baseInfoJson = companyJson.getJSONObject("baseInfo"); 
						String name =  baseInfoJson.get("name")+"";
						comList2.put(name, company);
						company.setId(id);
						company.setBase(baseInfoJson.get("base")+"");
					//	company.setCreditCode(json.getString("creditCode"));
						company.setRegNumber(baseInfoJson.get("regNumber")+"");
						if(baseInfoJson.get("creditCode")!=null){
							company.setCreditCode(baseInfoJson.get("creditCode")+"");
						}
						
						company.setPostalAddress(baseInfoJson.get("regLocation")+"");
						
						company.setLegalPersonName(baseInfoJson.get("legalPersonName")+"");
						company.setIndustry(baseInfoJson.get("industry")+"");
						company.setScope(baseInfoJson.get("businessScope")+"");

						company.setName(baseInfoJson.get("name")+"");
						company.setRegStatus(baseInfoJson.get("regStatus")+"");
						company.setShareholderList(baseInfoJson.get("investorList")+"");
					}catch(Exception e){
						e.printStackTrace();
					}
					
				} 
				
			}
			//合并两个companyList
			for(String name : comList2.keySet()){
				if(comList.containsKey(name)){
					String scope = comList2.get(name).getScope();
					comList.get(name).setScope(scope);//插入scope
				}else{
					comList.put(name, comList2.get(name));//添加
				}
			}
			
			 System.out.println("after emerge ,comList.size= "+comList.size());
			 Map<String,CompanyDto> newCompany = new HashMap<String,CompanyDto>();
			 int index = 0;
			 int fileindex  =1;
			 for(String key : comList.keySet()){
				 if(index++<50000){
					 newCompany.put(key,comList.get(key));
				 }else{
					 writeCompany(newCompany,"part"+fileindex++);
					 index=0;
					 newCompany = new HashMap<String,CompanyDto>();
				 }
			 } 
			 writeCompany(newCompany,"part"+fileindex++);
		}catch(Exception e){
			e.printStackTrace();
		}
		return comList;
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
				
				cell = sheet.getCell(1,i);
				String content = cell.getContents();
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
			label = new Label(colindex++, 0, "经营范围");
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
				label = new Label(colindex++, index+ 1, company.getScope());
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

	
	public static  List<CompanyDto> getBasicInfoFromExcel(File file){
		try{
			List<CompanyDto> list = new ArrayList<CompanyDto>();
			InputStream stream = new FileInputStream(file);
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
				list.add(dto);
			}
			stream.close();
			book.close();
			return list;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static  List<CompanyDto> getDetailInfoFromExcel(File file){
		try{
			List<CompanyDto> list = new ArrayList<CompanyDto>();
			InputStream stream = new FileInputStream(file);
			Workbook book = Workbook.getWorkbook(stream);
			Sheet sheet = book.getSheet(0);
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
					if(yearInfoObj !=null && !"null".equals(yearInfoObj.toString())){
						CompanyDto dto = new CompanyDto();
						list.add(dto);
						
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
							
						}catch(Exception e){
							
						}
					}
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			stream.close();
			book.close();
			return list;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
		
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
	
	
	public static void main(String[] args) {
		MysqlUtil.init();
		String path = FileUtil.getYearDir();
		File[] fls = new File(path).listFiles();
		int total = 0;
		for(File f : fls){
		 
			System.out.println("fie="+f);
			List<CompanyDto> list = getDetailInfoFromExcel(f);
			System.out.println("list="+list.size());
			total += list.size();
			MysqlUtil.insertCompanyDetail(list);
		}
		System.out.println("total isert "+total);
	}
}
