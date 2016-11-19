/*
 * Copyright (c) SHADANSOU 2014 All Rights Reserved
 *
 */
package com.lkp.project.company;

import java.util.List;

import org.apache.solr.common.SolrInputDocument;

import com.lkp.solr.SolrUtils;

/**
 * <p>class function description.<p>
 *
 * create  2016年3月20日<br>
 * @author  lkp<br> 
 * @version 1.0
 * @since   1.0
 */
public class CommitThread implements Runnable{
	List<SolrInputDocument>  docList;
	int index = 0;
	public CommitThread(List<SolrInputDocument> _docList){
		docList = _docList;
	}
	 
	@Override
	public void run() {
		 while(true){
			 if(docList.size()>100){
				 synchronized (docList) {
					 SolrUtils.addDoc(docList);
					 docList.clear();
					 SolrUtils.commit();
					 index = index+docList.size();
					 System.out.println("commit success:"+index);
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
