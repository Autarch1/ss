package StudentRegistration.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import StudentRegistration.dao.StudentDAO;
import StudentRegistration.dto.StudentRequestDTO;


@WebServlet("/StudentRegisterServlet")
public class StudentRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	
  
	@Override
	public void init() throws ServletException {
		super.init();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		HttpSession session = request.getSession();
		  if (session.getAttribute("isLoggedIn") == null) {
		        request.getRequestDispatcher("Login.jsp").forward(request, response);
		        System.out.println("ssss");
		    }
		  
		  
		
		  StudentRequestDTO sb = new StudentRequestDTO();
			sb.setName(request.getParameter("studentName"));
			sb.setDOB(request.getParameter("studentDOB"));
			sb.setGender(request.getParameter("studentGender"));
			sb.setPhone(request.getParameter("studentPhone"));
			sb.setEducation(request.getParameter("studentEdu"));
			sb.setAttend(request.getParameterValues("studentAttend"));
			sb.setPhoto(request.getParameter("studentPhoto"));
		
		
		    
		StudentDAO sDao = new StudentDAO();
		
		try {
			if( sb.getName().equals("")|| sb.getDOB().equals("") || sb.getGender().equals("") || 
					   sb.getPhone().equals("") || sb.getEducation().equals("") || 
					   sb.getAttend() == null || sb.getAttend().length == 0) {
				request.setAttribute("NotEmpty", "You need to fill all the data");
				request.getRequestDispatcher("StudentRegistration.jsp").forward(request, response);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		StudentRequestDTO sdto = new StudentRequestDTO();
		
		sdto.setName(sb.getName());
		sdto.setDOB(sb.getDOB());
		sdto.setGender(sb.getGender());
		sdto.setPhone(sb.getPhone());
		sdto.setEducation(sb.getEducation());
		sdto.setAttend(sb.getAttend());
		sdto.setPhoto(sb.getPhoto());
		System.out.println(sdto.toString());
		int i = sDao.studentCreate(sdto);
		
		if(i > 0) {
			System.out.println("Asdasd");
			request.getRequestDispatcher("Welcome.jsp").forward(request, response);
		}else {
			System.out.println("Hoke kyay zu 2");

			request.setAttribute("insertError", "Error while inserting ");
			request.setAttribute("sb", sb);
			request.getRequestDispatcher("StudentRegistration.jsp").forward(request, response);
			
		}

			
		}
		
	}


