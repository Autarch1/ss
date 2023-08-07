package StudentRegistration.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import StudentRegistration.dto.CourseRequestDTO;
import StudentRegistration.dto.CourseResponseDTO;

public class CourseDAO {
	public static Connection  con = null;
	static {
		con = MyConnection.getConnection();
	}
	
	public int courseAdd(CourseRequestDTO dto) {
		int result = 0;
		 String sql = "INSERT INTO course (courseName) VALUES (?)";
		 
		 try {
			 PreparedStatement ps = con.prepareStatement(sql);
			 ps.setString(1, dto.getCourseName());
			 
			 result = ps.executeUpdate();
		 }catch(SQLException e) {
			 System.out.println(e.getMessage());
		 }
		 
		 return result;
	}
	
	public ArrayList<CourseResponseDTO> getAllCourses(){
		ArrayList<CourseResponseDTO> resList = new ArrayList<>();
		
		String sql="SELECT * FROM course";
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				CourseResponseDTO res = new CourseResponseDTO();
				res.setCourseName(rs.getString("courseName"));
				
				resList.add(res);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return resList;
	}
	
	  
}