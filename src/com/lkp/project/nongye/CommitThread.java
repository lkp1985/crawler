/*
 * Copyright (c) SHADANSOU 2014 All Rights Reserved
 *
 */
package com.lkp.project.nongye;

import java.util.Date;
import java.util.List;

/**
 * <p>class function description.<p>
 *
 * create  2016年3月20日<br>
 * @author  lkp<br> 
 * @version 1.0
 * @since   1.0
 */
public class CommitThread implements Runnable{
	List<DataModel>  docList;
	int index = 0;
	Date last ;
	public CommitThread(List<DataModel> _docList){
		docList = _docList;
		last = new Date();
	}
	 
	@Override
	public void run() {
		 while(true){
			 Date now = new Date();
			 if(docList.size()>10 || (now.getTime()-last.getTime())>60000){
				 last = now;
				 synchronized (docList) {
					 System.out.println("Begin commit to db");
					 //提交数据库
				}
			 }
			 try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		
	}

}
