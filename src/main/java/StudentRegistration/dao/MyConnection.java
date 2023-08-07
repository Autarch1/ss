package StudentRegistration.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
	static Connection con = null;
	public static Connection getConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			 con = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_form", "root","rootroot");
			System.out.println("Connecting......");
			
		}catch(ClassNotFoundException e) {
			System.out.println("Driver Class Not Found ");
		}catch(SQLException e) {
			System.out.println("DataBase Not Found ");
		}
		return con;
	}
	

}
