package StudentRegistration.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import StudentRegistration.dao.UserDAO;

/**
 * Servlet implementation class UserDeleteServlet
 */
@WebServlet("/UserDeleteServlet")
public class UserDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userId = Integer.parseInt(request.getParameter("id")); // Assuming you have a parameter named "userId" in your URL
        UserDAO userDAO = new UserDAO();
        int deleteResult = userDAO.deleteUser(userId);
        
        if (deleteResult > 0) {
            request.setAttribute("success", "successfuly deleted");
            request.getRequestDispatcher("UserManagement.jsp").forward(request, response);
        } else {
        	request.setAttribute("fail", "Failed");
            System.out.println("Failed");
            request.getRequestDispatcher("UserManagement.jsp").forward(request, response);

        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
		
	}

}
