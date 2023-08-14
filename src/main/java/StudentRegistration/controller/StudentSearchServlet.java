package StudentRegistration.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import StudentRegistration.dao.StudentDAO;
import StudentRegistration.dto.StudentRequestDTO;
import StudentRegistration.dto.StudentResponseDTO;
import StudentRegistration.dto.UserResponseDTO;


/**
 * Servlet implementation class StudentSearchServlet
 */
@WebServlet("/StudentSearchServlet")
public class StudentSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
       HttpSession session;
    /**
     * @see HttpServlet#HttpServlet()
     */
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		  StudentDAO dao = new StudentDAO();
		if(  session.getAttribute("isAdmin") != null) {
			String id = request.getParameter("id");
			StudentResponseDTO oneStudent = dao.getOneStudent(id);
			System.out.println(oneStudent.getName());
			request.setAttribute("oneStudent", oneStudent);
			request.getRequestDispatcher("StudentUpdate.jsp").forward(request, response);

		}
		if (session.getAttribute("isUser") != null) {
			request.getRequestDispatcher("UserUpdate.jsp").forward(request, response);

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("asd");
		  StudentDAO dao = new StudentDAO();

		session = request.getSession();
		if (session.getAttribute("isLoggedIn") == null) {
			  request.getRequestDispatcher("Login.jsp").forward(request, response);
			  System.out.println("ssss");
		        return;
		    }
		
		if(session.getAttribute("isAdmin")==null) {
			request.getRequestDispatcher("Welcome.jsp").forward(request, response);
			return;
		}
		String id = request.getParameter("id");
		StudentResponseDTO oneStudent = dao.getOneStudent(id);
		
		 StudentRequestDTO sb = new StudentRequestDTO();
			sb.setName(request.getParameter("studentName"));
			sb.setDob(request.getParameter("studentDOB"));
			sb.setGender(request.getParameter("studentGender"));
			sb.setPhone(request.getParameter("studentPhone"));
			sb.setEducation(request.getParameter("studentEdu"));
			sb.setAttend(request.getParameterValues("studentAttend"));
			sb.setPhoto(request.getParameter("studentPhoto"));
			sb.setStudId(id);
			
			try {
				if( sb.getName().equals("")|| sb.getDob().equals("") || sb.getGender().equals("") || 
						   sb.getPhone().equals("") || sb.getEducation().equals("") || sb.getPhoto().equals("") ||
						   sb.getAttend() == null || sb.getAttend().length == 0) {
					request.setAttribute("oneStudent", oneStudent);
					request.setAttribute("NotEmpty", "You need to fill all the data");
					request.getRequestDispatcher("StudentUpdate.jsp").forward(request, response);
					return;
				}
				
			} catch (Exception e) {
				System.out.println(e);
			}
			
			StudentRequestDTO sdto = new StudentRequestDTO();
			
			sdto.setName(sb.getName());
			sdto.setDob(sb.getDob());
			sdto.setGender(sb.getGender());
			sdto.setPhone(sb.getPhone());
			sdto.setEducation(sb.getEducation());
			sdto.setAttend(sb.getAttend());
			sdto.setPhoto(sb.getPhoto());
			sdto.setStudId(sb.getStudId());
			
			int updateResult = dao.updatedStudent(sdto);
			if(updateResult > 0) {
				response.sendRedirect("StudentSearch.jsp");
			}else {
				request.setAttribute("updateFailed", "Update Failed Try again");
				  request.setAttribute("updatedUser", sb);
				  request.setAttribute("oneStudent", oneStudent);
				  request.getRequestDispatcher("StudentUpdate.jsp").forward(request, response);
			}
		}
	}


