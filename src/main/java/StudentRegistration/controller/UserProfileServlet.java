package StudentRegistration.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import StudentRegistration.dao.UserDAO;
import StudentRegistration.dto.UserRequestDTO;
import StudentRegistration.dto.UserResponseDTO;

/**
 * Servlet implementation class UserProfileServlet
 */
@WebServlet("/UserProfileServlet")
public class UserProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserDAO dao = new UserDAO();
	HttpSession session;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		if(  session.getAttribute("isAdmin") != null) {
			String id = request.getParameter("id");
			UserResponseDTO oneUser =dao.getOneUser(Integer.valueOf(id));
			System.out.println(oneUser.getUser_id());
			request.setAttribute("oneUser",  oneUser);
			request.getRequestDispatcher("UserUpdate.jsp").forward(request, response);

		}
		if (session.getAttribute("isUser") != null) {
			request.getRequestDispatcher("UserUpdate.jsp").forward(request, response);

		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
//	      ArrayList<UserResponseDTO> resList = dao.getAllUsers();
		  UserResponseDTO currentUser = (UserResponseDTO) session.getAttribute("currentUser");
		    int userId = dao.getUserId(currentUser.getEmail());
		    
		    if (session.getAttribute("isLoggedIn") == null) {
				  request.getRequestDispatcher("Login.jsp").forward(request, response);
				  System.out.println("ssss");
			        return;
			    }
			 
			  if(session.getAttribute("currentUser") == null) {
				  request.getRequestDispatcher("Login.jsp").forward(request, response);
				  return;
			  }
			  
			
			  
			  
	      boolean isSamePsw = false;
	      UserRequestDTO updatedUser = new UserRequestDTO();
	      
		  updatedUser.setName(request.getParameter("name"));
		  updatedUser.setEmail(request.getParameter("email"));
		  updatedUser.setPassword(request.getParameter("password"));
		  updatedUser.setConfirmPassword(request.getParameter("confirmPassword"));
		  updatedUser.setUser_id(userId);
		  if(updatedUser.getName().equals("") || updatedUser.getEmail().equals("") || updatedUser.getPassword().equals("") || updatedUser.getConfirmPassword().equals("") ) {

			  request.setAttribute("blank", "Field cannot be blank");

			  request.getRequestDispatcher("UserUpdate.jsp").forward(request, response);
			  return;
		  }
		  
		  if(updatedUser.getPassword().equals(updatedUser.getConfirmPassword())) {
			  isSamePsw = true;
				  UserRequestDTO dto = new UserRequestDTO();
				  dto.setName(updatedUser.getName());
				  dto.setEmail(updatedUser.getEmail());
				  dto.setPassword(updatedUser.getPassword());
				  dto.setUser_id(userId);
				  int updatedResult =dao.updateUser(dto);
				  
				  if( updatedResult > 0) {
					  currentUser.setName(updatedUser.getName());
		                session.setAttribute("currentUser", currentUser);
					  response.sendRedirect("UserManagement.jsp");
				  }else {  
					  request.setAttribute("updateFailed", "Update Failed Try again");
					  request.setAttribute("updatedUser", updatedUser);
					  System.out.println("updateFail");
					  request.getRequestDispatcher("UserUpdate.jsp").forward(request, response);
				  }
			  }
			  
			  if(!isSamePsw) {
				  request.setAttribute("password", "Password doesn't match");
				  System.out.println("password");
				  request.setAttribute("UpdatedUser", updatedUser);
				  request.getRequestDispatcher("UserUpdate.jsp").forward(request, response);
			  }
		  }
	}


