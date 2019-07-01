package model;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBcon {
	static Connection con;
	private DBcon() throws Exception {
		String url="jdbc:oracle:thin:@Localhost:1521:orcl";
		String user="scott";
		String pass="123456";
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		con=DriverManager.getConnection(url, user, pass);
		System.out.println("접속");
	}
	
	public static Connection getConnection() throws Exception {
		if (con==null) {//DB에 접속이 되어있지 않다면
			new DBcon();
		}
		return con;
	}
	
}
