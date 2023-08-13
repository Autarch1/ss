package StudentRegistration.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import StudentRegistration.dao.UserDAO;
import StudentRegistration.dto.UserRequestDTO;
import StudentRegistration.dto.UserResponseDTO;
import StudentRegistration.model.UserBean;

/**
 * Servlet implementation class StudentAccount
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDAO dao = new UserDAO();
		HttpSession session = request.getSession();
		ArrayList<UserResponseDTO> resList = dao.getAllUsers();

		
		UserBean ub = new UserBean();
		
		ub.setName(request.getParameter("name"));
		ub.setEmail(request.getParameter("email"));
		ub.setPassword(request.getParameter("password"));
		ub.setConfirmPassword(request.getParameter("confirmPassword"));
		ub.setRole(request.getParameter("role"));
		
		boolean isDuplicate = false;
		boolean isSamePsw =false;

		
		
		if(ub.getName().equals("") || ub.getEmail().equals("") || ub.getPassword().equals("") || ub.getConfirmPassword().equals("") || ub.getRole().equals("")) {
			request.setAttribute("error1", "Field Cannot be blank");
			request.setAttribute("sb", ub);
			request.getRequestDispatcher("Register.jsp").forward(request, response);
			return;
		}
		if(ub.getPassword().equals(ub.getConfirmPassword())) {
			isSamePsw = true;
			for(UserResponseDTO res : resList) {
				if(res.getEmail().equals(ub.getEmail())) {
					isDuplicate = true;
					request.setAttribute("sameEmail", "Email Already Exist");
					request.getRequestDispatcher("Register.jsp").forward(request, response);

				}
				
			}
			
			if(!isDuplicate) {
				UserRequestDTO dto = new UserRequestDTO();
				dto.setName(ub.getName());
				dto.setEmail(ub.getEmail());
				dto.setPassword(ub.getPassword());
				dto.setRole(ub.getRole());
				
				int i = dao.createUser(dto);
				if(i > 0) {
					response.sendRedirect("Login.jsp");
				}else {
					request.setAttribute("insertError", "Insert Failed!");
					request.setAttribute("sb", ub);
					request.getRequestDispatcher("Register.jsp").forward(request, response);
				}
				session.setAttribute("registerUser", ub);
				
			}
		}
		
		
		if(!isSamePsw) {
			request.setAttribute("error", "Password doesn't match");
			request.setAttribute("sb", ub);
			System.out.println("1234");

			request.getRequestDispatcher("Register.jsp").forward(request, response);
		}
		

		
		
		
		
		
	}
}


