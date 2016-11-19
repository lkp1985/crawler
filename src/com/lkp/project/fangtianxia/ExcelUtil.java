package com.lkp.project.fangtianxia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.lkp.project._1688.CompanyDto;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelUtil {

	public static void writeExcel(List<FangDto> companyList, String filename) {
		try {
			/*
			 * 	private String id = "";
	private String name = "";
	private String price = "";
	private String phone ="";
	private String address = "";
	private String house_type = "";//房子类型，如住宅
	private String sale_type="";//房子出售类型，如二手房
	private String fangyuan="";//房源：  新版为：写字楼、公寓等  ；
	private String salenum = "";// 旧版为： 在售128套，出租20套
			 */
			WritableWorkbook book = Workbook.createWorkbook(new File("d:\\data\\crawler\\fang\\"+filename + ".xls"));
			WritableSheet sheet = book.createSheet("fangtianxia", 0);
			Label label = new Label(0, 0, "楼盘名称");
			sheet.addCell(label);
			label = new Label(1, 0, "价格");
			sheet.addCell(label);
			label = new Label(2, 0, "电话");
			sheet.addCell(label);
			label = new Label(3, 0, "地址");
			sheet.addCell(label);
			label = new Label(4, 0, "房子类型");
			sheet.addCell(label);
			label = new Label(5, 0, "房子出售类型");
			sheet.addCell(label);
			label = new Label(6, 0, "房源");
			sheet.addCell(label);
			label = new Label(7, 0, "房源数量");
			sheet.addCell(label);
			label = new Label(8, 0, "url");
			sheet.addCell(label);
			for(int i=0 ; i<companyList.size(); i++){
				FangDto company = companyList.get(i);
				label = new Label(0 ,i+1, company.getName());
				sheet.addCell(label);
				label = new Label(1, i+1, company.getPrice());
				sheet.addCell(label);
				label = new Label(2, i+1, company.getPhone());
				sheet.addCell(label);
				label = new Label(3, i+1, company.getAddress());
				sheet.addCell(label);
				label = new Label(4, i+1, company.getHouse_type());
				sheet.addCell(label);
				label = new Label(5, i+1, company.getSale_type());
				sheet.addCell(label); 
				label = new Label(6, i+1, company.getFangyuan());
				sheet.addCell(label); 
				label = new Label(7, i+1, company.getSalenum());
				sheet.addCell(label); 
				label = new Label(8, i+1, company.getHref());
				sheet.addCell(label); 
			}
			book.write();
			book.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		try {
			 BufferedReader br = new BufferedReader(new FileReader("D:\\data\\1688_error_again.txt"));
			 String line = "";
			 List<CompanyDto> list  =new ArrayList<CompanyDto>();
			 while((line=br.readLine())!=null){
				 if(line.startsWith("http")){
					 CompanyDto company =new CompanyDto();
					 list.add(company);
					 String[] ls = line.split("\\|");
					 try{
						 company.setUrl(ls[0]);
						 company.setWwid(ls[1]);
						 if(ls.length>=3)
						 company.setPerson(ls[2]);
						 if(ls.length>=4)
						 company.setName(ls[3]);
						 if(ls.length>=5)
						 company.setAddress(ls[4]);
						 if(ls.length>=6)
							 company.setPhone(ls[5]);
						 
						
					 }catch(Exception e){
						 e.printStackTrace();
					 }
				 }
			 }
			 br.close();
			 
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
