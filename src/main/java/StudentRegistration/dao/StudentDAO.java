package StudentRegistration.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import StudentRegistration.dto.StudentRequestDTO;
import StudentRegistration.dto.StudentResponseDTO;
import StudentRegistration.dto.UserResponseDTO;

public class StudentDAO {
	public static Connection  con = null;
	static {
		con = MyConnection.getConnection();
	}
	
	public int studentCreate(StudentRequestDTO dto) {
		int result =0;
		
		int studentCount = getStudentCount();
		
		int nextSequence = studentCount + 1;
		
		String formattedStudentId = String.format("STU%03d", nextSequence);
		String sql="INSERT INTO student(student_id,studentName,studentDOB,studentGender,studentPhone,studentEdu,studentAttend,studentPhoto,user_id)" + "VALUES(?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, formattedStudentId);
			ps.setString(2, dto.getName());
			ps.setString(3, dto.getDob());
			ps.setString(4, dto.getGender());
			ps.setString(5, dto.getPhone());
			ps.setString(6, dto.getEducation());
			ps.setString(7, String.join(",", dto.getAttend()));
			ps.setString(8, dto.getPhoto());
			ps.setInt(9, dto.getUser_id());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	public ArrayList<StudentResponseDTO> getAllStudents(){
		ArrayList<StudentResponseDTO> resList = new ArrayList<>();
		String sql = "select * from student ";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				StudentResponseDTO	res = new StudentResponseDTO();
				res.setStudId(rs.getString("student_id"));
				res.setName(rs.getString("studentName"));
				res.setDob(rs.getString("studentDOB"));
				res.setGender(rs.getString("studentGender"));
				res.setPhone(rs.getString("studentPhone"));
				res.setEducation(rs.getString("studentEdu"));
                res.setAttend(rs.getString("studentAttend").split(","));
                res.setPhoto(rs.getString("studentPhoto"));
                res.setUser_id(rs.getInt("user_id"));
                resList.add(res);

			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return resList;
	}
	
	public int getStudentCount() {
		int count =0;
		String sql = "SELECT COUNT(*) FROM student";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				count=rs.getInt(1);
				
				
			}
		}catch (SQLException e) {
			
			System.out.println(e.getMessage());
		}
		
		return count;
	}
	
	
public StudentResponseDTO getOneStudent(String id) {
		
	StudentResponseDTO res = new StudentResponseDTO();
		
		String sql = "select * from student where student_id=?";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				res.setStudId(rs.getString("student_id"));
				res.setName(rs.getString("studentName"));
				res.setDob(rs.getString("studentDOB"));
				res.setGender(rs.getString("studentGender"));
				res.setPhone(rs.getString("studentPhone"));
				res.setEducation(rs.getString("studentEdu"));
                res.setAttend(rs.getString("studentAttend").split(","));
                res.setPhoto(rs.getString("studentPhoto"));
                
				return res;
			}
		}catch(SQLException e) {
			System.out.println("sads"+e.getMessage());
		}
		
		return null;
	}


	public int updatedStudent(StudentRequestDTO dto) {
		int result = 0;
		String  sql = "UPDATE student SET studentName=?,studentDOB=?,studentGender=?,studentPhone=?,studentEdu=?,studentAttend=?,studentPhoto=? WHERE student_id=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dto.getName());
			ps.setString(2, dto.getDob());
			ps.setString(3, dto.getGender());
			ps.setString(4, dto.getPhone());
			ps.setString(5, dto.getEducation());
			ps.setString(6, String.join(",", dto.getAttend()));
			ps.setString(7, dto.getPhoto());
			ps.setString(8, dto.getStudId() );
			result =ps.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return result;
	}

	public int deleteStudent(String id) {
	    int result = 0;
	    String sql = "DELETE FROM student WHERE student_id= ?";
	    try {
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setString(1, id);
	        result = ps.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	    return result;
	}	
	
	
	
	

	    public ArrayList<StudentResponseDTO> searchStudents(String studentID, String studentName, String attend) {
	        ArrayList<StudentResponseDTO> resultList = new ArrayList<>();
	        
	        try (Connection con = MyConnection.getConnection()) {
	            String sql = "SELECT * FROM student WHERE student_id LIKE ? AND UPPER(studentName) LIKE ? AND studentAttend = ?";
	            PreparedStatement ps = con.prepareStatement(sql);
	            ps.setString(1, studentID + "%");
	            ps.setString(2, studentName.toUpperCase() + "%");
	            ps.setString(3, attend);
	            ResultSet rs = ps.executeQuery();
	            while (rs.next()) {
	                StudentResponseDTO student = new StudentResponseDTO();
	                resultList.add(student);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        
	        return resultList;
	    }
	}

	

