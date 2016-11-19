package com.lkp.project.court;

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
