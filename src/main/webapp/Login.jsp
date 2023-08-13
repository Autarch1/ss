<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="test.css">
<title> Student Registration LGN001 </title>
</head>

<body class="login-page-body"> 
  
    <div class="login-page">
      <div class="form">
        <div class="login">
          <div class="login-header">
            <h1>Welcome!</h1>
            <p>Please check your data again.</p>
          </div>
        </div>
        <form class="login-form" action="LoginServlet" method="post" >
          <input type="email" placeholder="Enter Your Email" name="email"/>
          <input type="password" placeholder="Enter Your Password" name="password"/>
          <button>login</button>
          <p class="text-danger">${Blank }</p>
          <p style="color:red;">${loginError }</p>
                    
          
          <p class="message">Not registered? <a href="Register.jsp">Create an account</a></p>
        </form>
      </div> 
    </div>
    
</body>

</html>
