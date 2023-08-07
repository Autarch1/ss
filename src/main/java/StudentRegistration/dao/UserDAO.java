package StudentRegistration.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import StudentRegistration.dto.UserRequestDTO;
import StudentRegistration.dto.UserResponseDTO;

public class UserDAO {
	public static Connection  con = null;
	static {
		con = MyConnection.getConnection();
	}
	public int createUser(UserRequestDTO dto) {
		int result = 0;
		String sql="INSERT INTO user(name,email,password,role)" + "VALUES(?,?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dto.getName());
			ps.setString(2, dto.getEmail());
			ps.setString(3, dto.getPassword());
			ps.setString(4, dto.getRole());
			result = ps.executeUpdate();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("sad");
		}
		
		return result;
	}
	
	public ArrayList<UserResponseDTO> getAllUsers(){
		ArrayList<UserResponseDTO> resList = new ArrayList<>();
		String sql = "select*from user where role=1";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				UserResponseDTO res = new UserResponseDTO();
				res.setName(rs.getString("name"));
				res.setEmail(rs.getString("email"));
				res.setPassword(rs.getString("password"));
				resList.add(res);
			}
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return resList;
	}
	
public UserResponseDTO getOneUser(String email) {
		
		UserResponseDTO res = new UserResponseDTO();
		
		String sql = "select*from user where email=?";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				res.setName(rs.getString("name"));;
				res.setEmail(rs.getString("email"));;
				res.setPassword(rs.getString("password"));
				return res;
			}
		}catch(SQLException e) {
			System.out.println("sads"+e.getMessage());
		}
		
		return null;
	}

public UserResponseDTO getAdmin(String email) {
	
	UserResponseDTO res = new UserResponseDTO();
	
	String sql = "select*from user where email=? and role=2";
	
	try {
		PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, email);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			res.setName(rs.getString("name"));;
			res.setEmail(rs.getString("email"));;
			res.setPassword(rs.getString("password"));
			res.setRole(rs.getString("role"));
			return res;
		}
	}catch(SQLException e) {
		System.out.println("fff"+e.getMessage());
	}
	
	return null;
}

public static int getUserId(String userEmail) {
	int id = 0;
	String sql = "select user_id from user where email=?";
	
	try {
		PreparedStatement ps =con.prepareStatement(sql);
		ps.setString(1, userEmail);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			id = rs.getInt("user_id");
		}
	}catch (SQLException e) {
		System.out.println(e.getMessage());
	}
	
	
	return id;
}
}
