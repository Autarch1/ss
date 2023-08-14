<%@page import="java.util.stream.Collectors"%>
<%@page import="StudentRegistration.dto.UserResponseDTO"%>
<%@page import="StudentRegistration.dao.UserDAO"%>
<%@page import="java.util.List"%>

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
            <p>User: ${currentUser.name }</p>
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
          <a href="StudentRegistration.jsp">Student Registration </a>
          <a href="StudentSearch.jsp">Student Search </a>
        </div>
        <a href="UserManagement.jsp">Users Management</a>
      </div>
      <div class="main_contents">
    <div id="sub_content">
        <form class="row g-3 mt-3 ms-2" action="UserManagement.jsp" method="get">
            <div class="col-auto">
                <label for="userID" class="visually-hidden">User Id</label>
                <input type="text" class="form-control" id="userID" placeholder="User ID" name="user_id">
            </div>
            <div class="col-auto">
                <label for="userName" class="visually-hidden">User Name</label>
                <input type="text" class="form-control" id="userName" placeholder="User Name" name="name">
            </div>
    
            <div class="col-auto">
                <button type="submit" class="btn btn-primary mb-3" id="search-btn">Search</button>
            </div>
            <c:if test="${isAdmin }">
            <div class="col-auto">
                <button type="button" class="btn btn-secondary " onclick="location.href = 'Register.jsp';">
                    Add
                </button>
            </div>
            </c:if>
            <div class="col-auto">
                <button type=reset class="btn btn-danger mb-3" >Reset</button>
            </div>
        </form>
    
        <table class="table table-striped" id="stduentTable">
            <thead>
                <tr>
                    
                    <th scope="col">User ID</th>
                    <th scope="col">User Name</th>
                    <th scope="col">Details</th>
                    
                </tr>
            </thead>
            <tbody>
            
            <% 
            String idParam = request.getParameter("user_id");
            String nameParam = request.getParameter("name");
            UserDAO dao = new UserDAO();
            List<UserResponseDTO> userList = dao.getAllUsers();
            
            if (nameParam != null || (idParam != null && !idParam.isEmpty())) {
                userList = userList.stream()
                    .filter(user -> 
                        (nameParam == null || user.getName().equals(nameParam)) ||
                        (idParam == null || idParam.isEmpty() || user.getUser_id() == Integer.parseInt(idParam))
                    )
                    .collect(Collectors.toList());
            }


            	request.setAttribute("userList", userList);
            	
            %>
            <c:if test="${isUser }">
            <tr>
             
                    <td>USR0${currentUser.user_id}</td>
                    <td>${currentUser.name }</td>
                      <td>
                    <button type="button" class="btn btn-success  " onclick="location.href = 'UserProfile.jsp';">
                        Update
                    </button>
                </td>
                <td><button type="submit" class="btn btn-secondary mb-3" data-bs-toggle="modal"
                    data-bs-target="#exampleModal">Delete</button>
                    </td>
            </tr>
            </c:if> 
            
               <c:if test="${isAdmin }">
                 <c:forEach items="${userList }" var="s">
                <tr>
                    <td>${s.user_id}</td>
                    <td>${s.name}</td>  
                 
                 
                <td>
                    <button type="submit" class="btn btn-success  " onclick="location.href = 'UserUpdateServlet?id=${s.user_id}';">
                        Update
                    </button>
                </td>
                <td><button type="submit" class="btn btn-secondary mb-3" data-bs-toggle="modal"
                    data-bs-target="#exampleModal" onclick="location.href ='UserDeleteServlet?id=${s.user_id }';" >Delete</button>
                    </td>
    
                </tr>   
                </c:forEach>
                </c:if>
                    
            </tbody>
        </table>
    <p style="color:red;">${success }</p>
        <p style="color:red;">${fail }</p>
    
        <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Student Deletion</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        
                         <h5 style="color: rgb(127, 209, 131);">Are you sure want to delete !</h5>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-success col-md-2" data-bs-dismiss="modal">Ok</button>
    
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
        <div id="testfooter">
            <span>Copyright &#169; ACE Inspiration 2022</span>
        </div>
        <script>
		
        
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

    


    


    


