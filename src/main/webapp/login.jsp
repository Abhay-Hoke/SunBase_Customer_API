<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
<link rel="stylesheet" href="CSS/login.css">
</head>
<body>
<div>
 <h2>Welcome to Customer Management</h2>

    <form action="LoginServlet" method="post">
        
       <label>Username:</label><input type="text" name="username" required>
       
        <label>Password:</label><input type="password" name="password" required>
        
        <button type="submit">Login</button>
         <a href="register.jsp">Register</a>
    <%
    String message = request.getParameter("message");
    if (message != null) {
%>
    <div class="alert success">
        <%= message %>
    </div>
<%
    }
%>
        
    </form>
    </div>
    <footer>
    &copy; 2024 Customer Management
</footer>
</body>
</html>