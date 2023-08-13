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
	        ArrayList<UserResponseDTO> user= dao.getAllUsers();
	        UserResponseDTO admin = dao.getAdmin();
	        boolean isAdmin = false;
	        boolean isUser = false;
	        
	        System.out.println(email);
	        
	        if(email.equals("") || password.equals("")) {
	        	System.out.println("a kone htae ha lee lar");
	        	request.setAttribute("Blank", "Fill all the data");
	        	request.getRequestDispatcher("Login.jsp").forward(request, response);
	        	return;
	        }
	        if(admin.getEmail().equals(email) && admin.getPassword().equals(password)) {
	        	 isAdmin = true;
	        	 session.setAttribute("isAdmin", isAdmin);
	        	 session.setAttribute("currentUser", admin);
		   	     session.setAttribute("isLoggedIn", "Login");
	        	 request.getRequestDispatcher("CourseRegistration.jsp").forward(request, response);
	        	 
	        }
	        if(!isAdmin) {
	        	for(UserResponseDTO res : user) {
	        		if(res.getEmail().equals(email) && res.getPassword().equals(password)) {
	        			isUser = true;
	    	            session.setAttribute("currentUser", res);	        			
	        		}
	        	}
	        	if(!isUser) {
		        	System.out.println("whyyyy");
		        	request.setAttribute("loginError", "Invlaid email or password");
		        	request.getRequestDispatcher("Login.jsp").forward(request, response);
		        	return;
		        }
	        	   session.setAttribute("isUser", isUser);
	   	        session.setAttribute("isLoggedIn", "Login");
	   	        request.getRequestDispatcher("Welcome.jsp").forward(request, response);
	   	        
	        }
	        
	     

	}
}

