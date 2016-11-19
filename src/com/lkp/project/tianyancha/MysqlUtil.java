package com.lkp.project.tianyancha;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
 
 
public class MysqlUtil {
	static String url = "jdbc:mysql://192.168.0.102:3306/tyc?"
            + "user=root&password=root&useUnicode=true&characterEncoding=UTF8";
	static Connection conn = null;
	static Statement stmt = null;
	/*
	 * {"id":144746435,"name":"重庆市钢龙投资股份有限公司","type":1,"base":"重庆",
	 * "legalPersonName":"王贵宾","estiblishTime":null,"regCapital":null,
	 * "regStatus":"存续","score":"93","orginalScore":"9394","historyNames":null,
	 * "categoryCode":"1901","industry":"商务服务业","humanNames":"","trademarks":null,
	 * "usedBondName":"","bondName":"","bondNum":"","bondType":"","newtestName":"",
	 * "regNumber":"500102000051642","orgNumber":"","creditCode":"","businessScope":null,"contantMap":null}
	 */
	static String insert_sql1 = "replace into company(id,name,legalPersonName,base,regStatus,industry,regNumber,"
			+ "creditCode,scope,postalAddress) values (?,?,?,?,?,?,?,?,?,?)";
	static String update_sql1 = "update   company name=?,legalPersonName=?,base=?,regStatus=?,industry=?,regNumber=?,"
			+ "creditCode=?,scope=?,postalAddress=? where id=?";
	static String insert_sql2 = "update company"
			+ " set postcode=?,phoneNumber=?,email=?,postalAddress=?,shareholderList=?,"
			+ "yearReport=?  where id=?";
	static String queryLimit10 = "select * from company where readstatus=0 limit 5";
	static String queryLimit10_2 = "select * from youxi where readstatus=0 limit 20"; 
	static String queryLimit10_3 = "select * from youxi where readstatus=2 limit 20"; 
	static String updateReadStatusSql = "update company set readstatus=? , scope=? where id= ?";//每次有结果返回，就该数值置为1，表示该数据已经拿到经营范围
	static String updateReadStatusSql2 = "update company set readstatus=?  where id= ?";//每取一次就临时将readstatus=2,表示已经取但可能还没有抓的数据
	static String updateYXReadStatusSql2 = "update youxi set readstatus=?  where id= ?";//每取一次就临时将readstatus=2,表示已经取但可能还没有抓的数据
	static String updateYXReadStatusSql3 = "update youxi set readstatus=?  where id= ?";//每取一次就临时将readstatus=4,表示已经取出并已写到Company表
	
	static String insert_youxi = "insert  IGNORE  into youxi(id,readstatus) values (?,?)";
	static String insert_youxi2 = "replace into youxi(id,content,readstatus) values (?,?,?)";
	
	static int total = 0;
	public static void init(){
		try{
			 Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
	            System.out.println("成功加载MySQL驱动程序");
	            // 一个Connection代表一个数据库连接
	            conn = DriverManager.getConnection(url);
	            conn.createStatement();
	            conn.setAutoCommit(false);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static String getKeywords(){
		try{
			String condition = "";
			stmt = conn.createStatement();
			String sql = " select word from dic where length = 2 and readstatus = 0 limit 1";
			ResultSet rs = stmt.executeQuery(sql);	
			String sql2 = "update dic set readstatus=?  where word= ?";
			PreparedStatement psts = conn.prepareStatement(sql2);
			while(rs.next()){
				String word = rs.getString("word");   
				condition+=word;
				psts.setInt(1, 1); //1表示已读
				psts.setString(2, word);
				psts.addBatch();
			}
			psts.executeBatch();
			conn.commit();
			return condition;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 插入公司名称与ID,及公司基本信息baseinfo
	 */
	public static void insertBasic(List<CompanyBasic> list){
		try{
			String sql = "replace into basic(id,name,basicinfo) values ( ?,?,?)";
			PreparedStatement psts = conn.prepareStatement(sql);
			for(CompanyBasic basic : list){
				psts.setString(1, basic.getId());
				psts.setString(2, basic.getName());
				psts.setString(3, basic.getBase()); 
				psts.addBatch();
			}
			psts.executeBatch();
			conn.commit();
			total = total+list.size();
			System.out.println("insert to mysql "+ list.size()+" record success ,total:" + total);
		}catch(Exception e){
			e.printStackTrace();
		}
		 
	}
	public static void insertDic(){
		try{
			BufferedReader br = new BufferedReader(new FileReader("百度分词词库2.txt"));
			String line = "";
			stmt = conn.createStatement();
			String sql = "insert into dic(word,readstatus,length) values (?, ?, ?)";
			PreparedStatement psts = conn.prepareStatement(sql);
			int index = 1;
			while((line = br.readLine())!=null){
				System.out.println("line="+line+";index="+index);
				psts.setString(1, line);
				psts.setInt(2, 0);
				psts.setInt(3, line.length());
				psts.addBatch();
				if(index++%100 ==0){
					psts.executeBatch();
					conn.commit();  // 提交
					psts.clearBatch();
				}
			}
			psts.executeBatch();
			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void insertOrUpdateCompanyBasic(List<CompanyDto> companyList){
		try{
			PreparedStatement psts = conn.prepareStatement(insert_sql1);

			PreparedStatement psts2 = conn.prepareStatement(update_sql1);
			for(CompanyDto dto : companyList){
					String id = dto.getId();
				
					int index = 1;
					String sql1 = "select * from company where id="+id;
					
					Statement state = conn.createStatement();
					ResultSet rs = state.executeQuery(sql1);
					rs.last(); // 将光标移动到最后一行     
					int count = rs.getRow(); // 得到当前行号，即结果集记录数  
				 
					 
					
					if(count > 0){//更新
						System.out.println("update id="+id);
						psts2.setString(index++, dto.getName());
						psts2.setString(index++, dto.getLegalPersonName());
						psts2.setString(index++, dto.getBase());
						psts2.setString(index++, dto.getRegStatus());
						psts2.setString(index++, dto.getIndustry());
						psts2.setString(index++, dto.getRegNumber());
						psts2.setString(index++, dto.getCreditCode());
						psts2.setString(index++, dto.getScope());
						psts2.setString(index++, dto.getPostalAddress());
						psts2.setString(index++, dto.getId());
						psts2.addBatch();          // 加入批量处理
					}else{//插入
						System.out.println("insert  id="+id);
						psts.setString(index++, dto.getId());
			            psts.setString(index++, dto.getName());
			            psts.setString(index++, dto.getLegalPersonName());
			            psts.setString(index++, dto.getBase());
			            psts.setString(index++, dto.getRegStatus());
			            psts.setString(index++, dto.getIndustry());
			            psts.setString(index++, dto.getRegNumber());
			            psts.setString(index++, dto.getCreditCode());
			            psts.setString(index++, dto.getScope());
			            psts.setString(index++, dto.getPostalAddress());
			            psts.addBatch();          // 加入批量处理
					}
					
				 
					
		           
			}
	        psts.executeBatch(); // 执行批量处理
	        psts2.executeBatch(); // 执行批量处理
	        conn.commit();  // 提交
	        total = total + companyList.size();
	        System.out.println("insert or update  " + companyList.size()+" success,total = "+total);
	       // conn.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	//从youxi表中读出公司列表
	public static Map<String,String> getCompanyList(){
		Map<String,String> companyMap = new HashMap<String,String>();
		try{
			stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(queryLimit10_3);
			PreparedStatement psts = conn.prepareStatement(updateYXReadStatusSql3);
			while(rs.next()){
				
				String id = rs.getString("id");  
				String content = rs.getString("content");
				companyMap.put(id, content);
				psts.setInt(1, 4); //4表示读出来
				psts.setString(2, id);
				psts.addBatch();
			}
			psts.executeBatch();
			conn.commit();
		}catch(Exception e){
			e.printStackTrace();
		}
		return companyMap;
	}
	
	
	public static List<String> getYouxiId(){
		List<String> idlist = new ArrayList<String>();
		try{
			stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(queryLimit10_2);
			PreparedStatement psts = conn.prepareStatement(updateYXReadStatusSql2);
			while(rs.next()){
				
				String id = rs.getString("id");  
				idlist.add(id);
				psts.setInt(1, 1); //1表示已读，2表示已经请求完成
				psts.setString(2, id);
				psts.addBatch();
			}
			psts.executeBatch();
			conn.commit();
		}catch(Exception e){
			e.printStackTrace();
		}
		return idlist;
	}
	
	
	public static void insertYouxi(List<String> idlist){
		try{
			PreparedStatement psts = conn.prepareStatement(insert_youxi);
			for(String id : idlist){
				psts.setString(1, id);
				psts.setInt(2, 0); 
				psts.addBatch();
			}
		
			int[] results = psts.executeBatch();
			conn.commit();
			for(int num : results){
				total = total+num;
			}
		//	total = total+idlist.size();
			System.out.println("insert   success,total=	" + total);	
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	public static void insertYouxi(String id ,String content){
		try{
			PreparedStatement psts = conn.prepareStatement(insert_youxi2);
			psts.setString(1, id);
			psts.setString(2, content); 
			psts.setInt(3, 2); 
			psts.execute(); 
			conn.commit();
			total = total+1;
			System.out.println("insert :"+id+" success,total=	" + total);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	public static void insertScope(Map<String,String> map){
		try{
			PreparedStatement psts = conn.prepareStatement(updateReadStatusSql);
			for(String id : map.keySet()){
				String href = map.get(id);
				psts.setInt(1, 1);
				psts.setString(2, href);
				psts.setString(3, id);
				psts.addBatch();
			}
			psts.executeBatch();
			conn.commit();
			System.out.println("insert success :"+map.size());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public static Map<String,String > getCompanyQueryCondition(){
		Map<String,String> map = new HashMap<String,String>();
		try{
			stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(queryLimit10);
			PreparedStatement psts = conn.prepareStatement(updateReadStatusSql2);
			while(rs.next()){
				
				String id = rs.getString("id");
			//	System.out.println("id="+id);
				String name = rs.getString("name");
				map.put(id, name);
				psts.setInt(1, 2); 
				psts.setString(2, id);
				psts.addBatch();
			}
			psts.executeBatch();
			conn.commit();
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
	
	public static Map<String,String > getCompanyQueryCondition2(){
		Map<String,String> map = new HashMap<String,String>();
		try{
			stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(queryLimit10);
			PreparedStatement psts = conn.prepareStatement(updateReadStatusSql2);
			while(rs.next()){
				
				String id = rs.getString("id");
			//	System.out.println("id="+id);
				String regNumber = rs.getString("regNumber");
				map.put(id, regNumber);
				psts.setInt(1, 2); 
				psts.setString(2, id);
				psts.addBatch();
			}
			psts.executeBatch();
			conn.commit();
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
	
	public static void insertCompanyBasic(List<CompanyDto> companyList){
		try{
			PreparedStatement psts = conn.prepareStatement(insert_sql1);
			for(CompanyDto dto : companyList){
					int index=1;
		            psts.setString(index++, dto.getName());
		            psts.setString(index++, dto.getLegalPersonName());
		            psts.setString(index++, dto.getBase());
		            psts.setString(index++, dto.getRegStatus());
		            psts.setString(index++, dto.getIndustry());
		            psts.setString(index++, dto.getRegNumber());
		            psts.setString(index++, dto.getCreditCode());
		            psts.setString(index++, dto.getScope());
		            psts.setString(index++, dto.getPostalAddress());
					 psts.setString(index++, dto.getId());
		            psts.addBatch();          // 加入批量处理
		           
			}
	        psts.executeBatch(); // 执行批量处理
	        conn.commit();  // 提交
	        System.out.println("insert  " + companyList.size()+" success");
	       // conn.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	
	
	public static void insertCompanyDetail(List<CompanyDto> companyList){
		try{
			int total = 0;
			PreparedStatement psts = conn.prepareStatement(insert_sql2);
			for(CompanyDto dto : companyList){
				if(dto.getId() ==null){
					continue;
				}
				total ++;
				int index = 1;
		            psts.setString(index++, dto.getPostcode());
		            psts.setString(index++, dto.getPhoneNumber());
		            psts.setString(index++, dto.getEmail());
		            psts.setString(index++, dto.getPostalAddress());
		            psts.setString(index++, dto.getShareholderList());
		            psts.setString(index++, dto.getYearReport()); 
					 psts.setString(index++, dto.getId());
		            psts.addBatch();          // 加入批量处理
		            
			}
	        psts.executeBatch(); // 执行批量处理
	        conn.commit();  // 提交
	        System.out.println("insert     " + total +" success");
	       // conn.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	 
	
    public static void main(String[] args) throws Exception { 
    	init();
    	getKeywords();
    }
 
}