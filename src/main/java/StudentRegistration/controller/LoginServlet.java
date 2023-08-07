package StudentRegistration.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import StudentRegistration.dao.UserDAO;
import StudentRegistration.dto.UserResponseDTO;
import StudentRegistration.model.UserBean;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		
		 String email = request.getParameter("email");
	      String password = request.getParameter("password");
          HttpSession session = request.getSession();


	        UserDAO dao = new UserDAO();
	        UserResponseDTO user = dao.getOneUser(email);
//	        UserResponseDTO admin = dao.getAdmin(email);
//	        
//	        if (admin != null && admin.getRole().equals(2)) {
//	            session.setAttribute("isLoggedIn", true);
//	            session.setAttribute("userRole", "admin");
//	            response.sendRedirect("Welcome.jsp");
//	        }
	        
	        if (user != null && user.getPassword().equals(password)) {
	            session.setAttribute("currentUser", user);
	            response.sendRedirect("Welcome.jsp");
	        } else {
	        	System.out.println("sadad");
	        	request.setAttribute("loginError", "Invalid email or password.");
	            request.getRequestDispatcher("Login.jsp").forward(request, response);
	        }
	        
	        session.setAttribute("isLoggedIn", "LogIn");
	        
	      
	}
}

