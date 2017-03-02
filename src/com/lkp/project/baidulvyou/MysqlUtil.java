package com.lkp.project.baidulvyou;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
 
 
 
public class MysqlUtil {
	static int total = 0;
	static String url = "jdbc:mysql://localhost:3306/crawler?"
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
	static String insert_sql1 = "insert ignore  into sourceurl_china(url,name,readstatus) values (?,?,?)";
	static String insert_sql2 = "replace into bdly_china(sid,url,sname,address,phone,price,besttime,bestvisittime,"
			+ "traffic,map_info,impression,more_desc,type,ticket_info,open_time_desc,score,commentnum,point,readstatus) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	static String insert_sql3 = "replace into remark(sid,page,remark) values (?,?,?)";
	static String queryLimit5_2 = "select * from sid where readstatus=0 limit 5";
	static String queryLimit5 = "select * from sourceurl_china where readstatus=0 limit 5";
	static String updateReadStatusSql = "update sourceurl_china set readstatus=?  where url= ?";//每次有结果返回，就该数值置为1，表示该数据已经开始去拿基本信息
	static String updateReadStatusSql2 = "update sid set readstatus=?  where sid= ?";//每次有结果返回，就该数值置为1，表示该数据已经开始去拿评论
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
	 
	
	public static void insertJingdianUrl(List<String> urlList){
		try{
			PreparedStatement psts = conn.prepareStatement(insert_sql1);
			for(String url : urlList){
				psts.setString(1, url); 
				psts.setString(2, ""); 
				psts.setInt(3, 0); 
				psts.addBatch();
			}
			psts.executeBatch();
			conn.commit();
			System.out.println("insert url :"+urlList+" success");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void insertJingdianUrl(String url,String name){
		try{
			PreparedStatement psts = conn.prepareStatement(insert_sql1);
			psts.setString(1, url); 
			psts.setString(2, name); 
			psts.setInt(3, 0); 
			psts.addBatch();
			psts.executeBatch();
			conn.commit();
			System.out.println("insert url :"+url+" success");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	 
	
	public static List<String> getSourceUrlList(){
		List<String> urlList = new ArrayList<String>();
		try{
			stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(queryLimit5);
			PreparedStatement psts = conn.prepareStatement(updateReadStatusSql);
			while(rs.next()){
				String url = rs.getString("url");
				urlList.add(url);
				psts.setInt(1, 1); 
				psts.setString(2, url);
				psts.addBatch();
			}
			psts.executeBatch();
			conn.commit();
		}catch(Exception e){
			e.printStackTrace();
		}
		return urlList;
	}
	 
	public static List<String> getSourceUrlList2(){
		List<String> urlList = new ArrayList<String>();
		try{
			stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(queryLimit5_2);
			PreparedStatement psts = conn.prepareStatement(updateReadStatusSql2);
			while(rs.next()){
				String url = rs.getString("sid");
				if(url!=null){
					urlList.add(url);
					 
				}
				
				psts.setInt(1, 1); 
				psts.setString(2, url);
				psts.addBatch();
			}
			 
			psts.executeBatch();
			conn.commit();
		}catch(Exception e){
			e.printStackTrace();
		}
		return urlList;
	}
	
	public static void insertRemark(String url,int page,String remark){
		try{
			//url,address,phone,price,besttime,bestvistime,traffic,map_info,impression,more_desc,
			//type,ticket_info,open_time_desc
			PreparedStatement psts = conn.prepareStatement(insert_sql3);
			int index=1;
			psts.setString(index++, url);
			psts.setInt(index++, page);
			psts.setString(index++, remark);
			psts.execute();
			conn.commit();
			total++;
//			if(total%10000==0){
//				conn.close();
//				 conn = DriverManager.getConnection(url);
//		            conn.createStatement();
//		            conn.setAutoCommit(false);
//			}
			System.out.println("insert remark  " + url +" success");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void insertJingdianBasic(List<BdlyDto> list){
		try{
			//url,address,phone,price,besttime,bestvistime,traffic,map_info,impression,more_desc,
			//type,ticket_info,open_time_desc
			PreparedStatement psts = conn.prepareStatement(insert_sql2);
			for(BdlyDto dto : list){ 
				int index=1;
				psts.setString(index++, dto.getSid());
				psts.setString(index++, dto.getUrl());
				psts.setString(index++, dto.getName());
				psts.setString(index++, dto.getAddress());
				psts.setString(index++, dto.getPhone());
				psts.setString(index++, dto.getPrice());
				psts.setString(index++, dto.getBesttime());
				psts.setString(index++, dto.getBestvisittime());
				psts.setString(index++, dto.getTraffic());
				psts.setString(index++, dto.getMap_info());
				psts.setString(index++, dto.getImpression());
				psts.setString(index++, dto.getMor_desc());
				psts.setString(index++, dto.getLytype());
				psts.setString(index++, dto.getTicket_info());
				psts.setString(index++, dto.getOpen_time_desc());
				psts.setString(index++, dto.getScore());
				psts.setString(index++, dto.getCommentnum());
				psts.setString(index++, dto.getPoint());
				psts.setInt(index++,0);
				psts.addBatch();
			}
			int total = 0;
			int[] result_num = psts.executeBatch();
			for(int num : result_num){
				total += num;
			}
			conn.commit();
			System.out.println("insert bdlyinfo  " + total +" success");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
    public static void main(String[] args) throws Exception {
        Connection conn = null;
        String sql;
        String url = "jdbc:mysql://localhost:3306/test?"
                + "user=root&password=root&useUnicode=true&characterEncoding=UTF8";
 
        try {
            // 之所以要使用下面这条语句，是因为要使用MySQL的驱动，所以我们要把它驱动起来，
            // 可以通过Class.forName把它加载进去，也可以通过初始化来驱动起来，下面三种形式都可以
          
            sql = "create table student(NO char(20),name varchar(20),primary key(NO))";
            int result = stmt.executeUpdate(sql);// executeUpdate语句会返回一个受影响的行数，如果返回-1就没有成功
            if (result != -1) {
                System.out.println("创建数据表成功");
                sql = "insert into student(NO,name) values('2012001','陶伟基')";
                result = stmt.executeUpdate(sql);
                sql = "insert into student(NO,name) values('2012002','周小俊')";
                result = stmt.executeUpdate(sql);
                sql = "select * from student";
                ResultSet rs = stmt.executeQuery(sql);// executeQuery会返回结果的集合，否则返回空值
                System.out.println("学号\t姓名");
                while (rs.next()) {
                    System.out
                            .println(rs.getString(1) + "\t" + rs.getString(2));// 入如果返回的是int类型可以用getInt()
                }
            }
        } catch (SQLException e) {
            System.out.println("MySQL操作错误");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
 
    }
 
}