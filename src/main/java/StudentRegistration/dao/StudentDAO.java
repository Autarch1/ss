package StudentRegistration.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import StudentRegistration.dto.StudentRequestDTO;

public class StudentDAO {
	public static Connection  con = null;
	static {
		con = MyConnection.getConnection();
	}
	
	public int studentCreate(StudentRequestDTO dto) {
		int result =0;
		String sql="INSERT INTO student(studentName,studentDOB,studentGender,studentPhone,studentEdu,studentAttend,studentPhoto)" + "VALUES(?,?,?,?,?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dto.getName());
			ps.setString(2, dto.getDOB());
			ps.setString(3, dto.getGender());
			ps.setString(4, dto.getPhone());
			ps.setString(5, dto.getEducation());
			ps.setString(6, String.join(",", dto.getAttend()));
			ps.setString(7, dto.getPhoto());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	
}
