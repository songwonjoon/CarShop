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
		System.out.println("����");
	}
	
	public static Connection getConnection() throws Exception {
		if (con==null) {//DB�� ������ �Ǿ����� �ʴٸ�
			new DBcon();
		}
		return con;
	}
	
}
