package com.lkp.project.qichacha;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
 
 
public class MysqlUtil {
	static String url = "jdbc:mysql://192.168.0.105:3306/test?"
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
			+ "creditCode) values (?,?,?,?,?,?,?,?)";
	static String insert_sql2 = "update company"
			+ " set postcode=?,phoneNumber=?,email=?,postalAddress=?,shareholderList=?,"
			+ "yearReport=?  where id=?";
	static String queryLimit10 = "select * from company where readstatus=0 limit 10";
	static String updateReadStatusSql = "update company set readstatus=? , qcchref=? where id= ?";
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
	
	public static void insertHref(Map<String,String> map){
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
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static Map<String,String > getCompanyQueryCondition(){
		Map<String,String> map = new HashMap<String,String>();
		try{
			stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(queryLimit10);
			while(rs.next()){
				String id = rs.getString("id");
				System.out.println("id="+id);
				String name = rs.getString("name");
				map.put(id, name);
			}
			//psts.executeBatch();
			//conn.commit();
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
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