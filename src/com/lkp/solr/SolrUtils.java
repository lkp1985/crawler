package com.lkp.solr;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SolrUtils {
	static SolrServer server = new HttpSolrServer("http://localhost:8983/solr");
	 
	public static SolrDocumentList getDocs(SolrQuery query){
		try {
			return server.query(query).getResults();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public static boolean addDoc(List<SolrInputDocument> docList){
		try{
			server.add(docList);
			//server.commit();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	public static void commit(){
		try {
			server.commit();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static boolean addDoc(SolrInputDocument doc){
		try{
			server.add(doc);
			server.commit();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	public static void main(String[] args) {
		try{
			SolrQuery query = new SolrQuery();
			query.setQuery("id:\"2014-11-12T16:00:00.000Z\"");
			QueryResponse res = server.query(query);	
			SolrDocumentList list = res.getResults();
			Object obj = list.get(0).getFieldValue("content");
			String result = obj.toString();
			
			result = result.substring(1,result.length()-1);
			result = result.replace("\\\"", "\"");
			String[] results = result.split("\\}\\,\\{");
			for(String re : results){
				if(re.contains("Count")){
					continue;
				}
				String[] sss = re.split("\\\"\\,\\\"");
				for(int i=0;i<sss.length;i++){
					if(sss[i].equals("")){
						System.out.println();
					}
				}
				
			}
			 System.out.println("result="+result);
			JSONArray array = JSONArray.fromObject(result);
			System.out.println(array.size());
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
