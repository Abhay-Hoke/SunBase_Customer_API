<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Registration</title>
<link rel="stylesheet" href="CSS/register.css">
</head>
<body>
 <h2>Register</h2>
    <form action="RegistrationServlet" method="post">
        <label>Username:</label>
        <input type="text" name="username" required><br>
        <label>Password:</label>
        <input type="password" name="password" required><br>
        <label>Role:</label>
        <select name="role" required>
            <option value="USER">User</option>
           <!--  <option value="ADMIN">Admin</option>--> 
        </select><br>
        <button type="submit">Register</button>
    </form>
<footer>
    &copy; 2024 Customer Management
</footer>
</body>
</html>