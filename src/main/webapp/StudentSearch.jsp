<%@page import="java.util.List"%>
<%@page import="java.util.stream.Collectors"%>
<%@page import="java.util.stream.Collector"%>
<%@page import="java.net.http.HttpRequest"%>
<%@page import="StudentRegistration.dto.StudentResponseDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="StudentRegistration.dao.CourseDAO"%>
<%@page import="StudentRegistration.dao.StudentDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
            <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
    
<!DOCTYPE html>
<html lang="en">

<head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="test.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
    
        <title>Course Registration</title>
</head>

<body>

  <div id="testheader">
    <div class="container">
        <div class=row>        
            <div class="col-md-5 ">
        <a href="Welcome.jsp"><h3>Student Registration</h3></a>
    </div>  
    <div class="col-md-6">
        <p>User : ${currentUser.name }</p>
<p class="my-2" style="color:#000000">Current Time : <span id="currentDateTime"></span></p>        </div>  
    </div>  
    <div class="col-md-1" >
        <input type="button" class="btn-basic" id="lgnout-button" value="Log Out" onclick="location.href='LogoutServlet'">
    </div>        
</div>
</div>

</div>
    <!-- <div id="testsidebar">Hello World </div> -->
    <div class="container">
    <div class="sidenav">
        
        <button class="dropdown-btn" > Class Management <i class="fa fa-caret-down"></i></button>
        
            <div class="dropdown-container">
			<c:if test="${isAdmin }">
          <a href="CourseRegistration.jsp">Course Registration </a>
          </c:if>         
           <a href="StudentRegisterServlet">Student Registration </a>
           
          <a href="StudentSearch.jsp">Student Search </a>
        </div>
        <a href="UserManagement.jsp">Users Management</a>
      </div>
      <div class="main_contents">
    <div id="sub_content">
      <form class="row g-3 mt-3 ms-2" action="StudentSearch.jsp" method="get">
        <div class="col-auto">
          <label for="staticEmail2" class="visually-hidden">studentID</label>
          <input type="text"  class="form-control" id="staticEmail2" name="studentID"  placeholder="student ID" >
        </div>
        <div class="col-auto">
          <label for="inputPassword2" class="visually-hidden">studentName</label>
          <input type="text" class="form-control" id="inputPassword2"  name="studentName" placeholder="Name">
        </div>
        <div class="col-auto">
            <label for="inputPassword2" class="visually-hidden">Course</label>
            <input type="text" class="form-control" id="inputPassword2" placeholder="Course" name="studentAttend">
          </div>
        <div class="col-auto">
          <button type="submit" class="btn btn-success mb-3">Search</button>
        </div>
        <div class="col-auto">
          <button type="reset" class="btn btn-secondary mb-3" onclick="location.herf='StudentSearch.jsp'">Reset</button>
        </div>
      </form>
<div>
      <table class="table table-striped" id="stduentTable">
        <thead>
          <tr>
            <th scope="col">#</th>
            <th scope="col">Student ID</th>
            <th scope="col">Name</th>
            <th scope="col">Course Name</th>
            <th scope="col">Details</th>
          </tr>
        </thead>
        <tbody>
        <% 
            String id = (String)request.getParameter("studentID");
            String name = (String) request.getParameter("studentName");
            String attendCourse = request.getParameter("studentAttend");

        	StudentDAO dao = new StudentDAO();
           	List<StudentResponseDTO> dto = dao.getAllStudents();
            if(id!=null || name!=null ||attendCourse != null){		
            	dto = dto.stream().filter(stu->stu.getStudId().equals(id) || stu.getName().equals(name) || java.util.Arrays.asList(stu.getAttend()).contains(attendCourse)  ).collect(Collectors.toList());
            }

            
              
                
            
            request.setAttribute("stuList", dto);
            CourseDAO cDao = new CourseDAO();
            request.setAttribute("cList", cDao.getAllCourses());
            		
        %>
        <c:forEach items="${stuList}" var="student" varStatus="s">
            
          <tr>
            <th scope="row">${s.index+1}</th>
             <td>${student.studId}</td>
                <td>${student.name}</td>
            <td>
			 <c:forEach items="${student.attend}" var="course" varStatus="c">
        	${course}
        	<c:if test="${not c.last}">, </c:if>
      </c:forEach>
</td>
<c:if test="${isAdmin }">
            <td>
              <a href="StudentSearchServlet?id=${student.studId }"><button type="submit" class="btn btn-secondary mb-2">See More</button></a> 
            </td>
            </c:if>
          </tr>
          
          </c:forEach>
        </tbody>
      </table>
                <p style="color:red;">${success}</p>
      			          <p style="color:red;">${fail}</p>
      			
    </div>
    </div>
</div>
</div>
        <div id="testfooter">
            <span>Copyright &#169; ACE Inspiration 2022</span>
        </div>
        <script>
            /* Loop through all dropdown buttons to toggle between hiding and showing its dropdown content - This allows the user to have multiple dropdowns without any conflict */
            var dropdown = document.getElementsByClassName("dropdown-btn");
            var i;
            
            for (i = 0; i < dropdown.length; i++) {
              dropdown[i].addEventListener("click", function() {
              this.classList.toggle("active");
              var dropdownContent = this.nextElementSibling;
              if (dropdownContent.style.display === "block") {
              dropdownContent.style.display = "none";
              } else {
              dropdownContent.style.display = "block";
              }
              });
            }
            
            function updateDateTime(){
                const currentDateTimeElement = document.getElementById("currentDateTime");
                const currentDateTime = new Date();
                currentDateTimeElement.innerHTML = currentDateTime.toLocaleString('en-US', {
                  weekday: "long",
                  year: "numeric",
                  month: "long",
                  day: "numeric",
                  hour: "2-digit",
                  minute: "2-digit",
                  second: "2-digit",
                });
              }
              updateDateTime();
              setInterval(updateDateTime, 1000);
            </script>
            
</body>

</html>



