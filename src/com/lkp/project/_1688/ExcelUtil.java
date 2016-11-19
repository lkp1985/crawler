package com.lkp.project._1688;

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

	public static void writeExcel(List<CompanyDto> companyList, String filename) {
		try {
			WritableWorkbook book = Workbook.createWorkbook(new File(filename + ".xls"));
			WritableSheet sheet = book.createSheet("1688", 0);
			Label label = new Label(0, 0, "url");
			sheet.addCell(label);
			label = new Label(1, 0, "wwid");
			sheet.addCell(label);
			label = new Label(2, 0, "person");
			sheet.addCell(label);
			label = new Label(3, 0, "company");
			sheet.addCell(label);
			label = new Label(4, 0, "address");
			sheet.addCell(label);
			label = new Label(5, 0, "phone");
			sheet.addCell(label);
			for(int i=0 ; i<companyList.size(); i++){
				CompanyDto company = companyList.get(i);
				label = new Label(0 ,i+1, company.getUrl());
				sheet.addCell(label);
				label = new Label(1, i+1, company.getWwid());
				sheet.addCell(label);
				label = new Label(2, i+1, company.getPerson());
				sheet.addCell(label);
				label = new Label(3, i+1, company.getName());
				sheet.addCell(label);
				label = new Label(4, i+1, company.getAddress());
				sheet.addCell(label);
				label = new Label(5, i+1, company.getPhone());
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
			 
			 writeExcel(list, "1688_error");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
