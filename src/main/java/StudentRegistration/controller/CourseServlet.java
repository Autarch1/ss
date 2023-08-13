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

import StudentRegistration.dao.CourseDAO;
import StudentRegistration.dao.UserDAO;
import StudentRegistration.dto.CourseRequestDTO;
import StudentRegistration.dto.CourseResponseDTO;
import StudentRegistration.dto.UserResponseDTO;
import StudentRegistration.model.UserBean;
import StudentRegistration.model.courseBean;


@WebServlet("/CourseServlet")
public class CourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

    public CourseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		  if (session.getAttribute("isLoggedIn") == null) {
			  request.getRequestDispatcher("Login.jsp").forward(request, response);
			  System.out.println("ssss");
		        return;
		    }
		  
		 UserResponseDTO currentUser = (UserResponseDTO) session.getAttribute("currentUser");
		    CourseDAO cdao = new CourseDAO();
		   
		  
		    if(currentUser.getRole().equals("2")) {
		    	request.setAttribute("admin", "admin");
		    	courseBean cb = new courseBean();
				cb.setCourseName(request.getParameter("courseName"));
				
				
			    
			    ArrayList<CourseResponseDTO> cList = cdao.getAllCourses();
			    
			    for(CourseResponseDTO crd : cList) {
			    	if(cb.getCourseName().equals(crd.getCourseName())) {
			    		request.setAttribute("dup", "This course already exist");
			    		request.getRequestDispatcher("CourseRegistration.jsp").forward(request, response);
			    	}
			    }
			    if(cb.getCourseName().equals("")) {
			    	request.setAttribute("error", "Field Cannot Be Blank");
			    	request.setAttribute("cb", cb);
			    	request.getRequestDispatcher("CourseRegistration.jsp").forward(request, response);
			    }else {
			    	CourseRequestDTO cdto = new CourseRequestDTO();
			    	cdto.setCourseName(cb.getCourseName());
			    	int i = cdao.courseAdd(cdto);
			    	if(i > 0) {
			    		request.setAttribute("cList", cdao.getAllCourses());
			    		request.getRequestDispatcher("StudentRegistration.jsp").forward(request, response);
			    	}else {
			    		request.setAttribute("insertError", "Faile while creating Course");
			    		request.setAttribute("cb", cb);
			    		request.getRequestDispatcher("CourseRegistration.jsp").forward(request, response);
			    	}
			    }

		    }else {
		    	System.out.println("User cant ");
		    	request.getRequestDispatcher("Welcome.jsp").forward(request, response);
		    }
			

		
		
	}
}
			
		
	 


