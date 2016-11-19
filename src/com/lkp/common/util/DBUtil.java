package com.lkp.common.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 数据库工具类，以sqlite为源
 * @author lkp
 *
 */
public class DBUtil {
	
	
	public static void insertTest(){
		try{
	         //连接SQLite的JDBC
	         Class.forName("org.sqlite.JDBC");         
	         //建立一个数据库名zieckey.db的连接，如果不存在就在当前目录下创建之
	         Connection conn = DriverManager.getConnection("jdbc:sqlite:zieckey.db");         
	         Statement stat = conn.createStatement();

	         stat.executeUpdate( "create table tbl1(name varchar(20), salary int);" );//创建一个表，两列
	         stat.executeUpdate( "insert into tbl1 values('ZhangSan',8000);" ); //插入数据
	         stat.executeUpdate( "insert into tbl1 values('LiSi',7800);" );
	         stat.executeUpdate( "insert into tbl1 values('WangWu',5800);" );
	         stat.executeUpdate( "insert into tbl1 values('ZhaoLiu',9100);" ); 
	 
	         ResultSet rs = stat.executeQuery("select * from tbl1;"); //查询数据 

	         while (rs.next()) { //将查询到的数据打印出来
	             System.out.print("name = " + rs.getString("name") + " "); //列属性一
	             System.out.println("salary = " + rs.getString("salary")); //列属性二
	         }
	         rs.close();
	         conn.close(); //结束数据库的连接 
	        }
	        catch( Exception e )
	        {
	         e.printStackTrace ( );
	        }
	}
	
	public static void queryTest(){

        try {
            // The SQLite (3.3.8) Database File
            // This database has one table (pmp_countries) with 3 columns (country_id, country_code, country_name)
            // It has like 237 records of all the countries I could think of.
            String fileName = "d:/data/test.db";
            // Driver to Use
            // http://www.zentus.com/sqlitejdbc/index.html
            Class.forName("org.sqlite.JDBC");
            // Create Connection Object to SQLite Database
            // If you want to only create a database in memory, exclude the +fileName
            Connection conn = DriverManager.getConnection("jdbc:sqlite:"+fileName);
            // Create a Statement object for the database connection, dunno what this stuff does though.
            Statement stmt = conn.createStatement();
            // Create a result set object for the statement
            ResultSet rs = stmt.executeQuery("SELECT * FROM test ");
            // Iterate the result set, printing each column
            // if the column was an int, we could do rs.getInt(column name here) as well, etc.
            while (rs.next()) {
                String id   = rs.getString("id");   // Column 1 
                String name = rs.getString("name"); // Column 3
                System.out.println("ID: "+id+ " Name: "+name);

            }
            // Close the connection
            conn.close();
        }
        catch (Exception e) {
            // Print some generic debug info
            System.out.println(e.getMessage());
            System.out.println(e.toString());
        }
    
	}
    public static void main(String[] args) {}
}