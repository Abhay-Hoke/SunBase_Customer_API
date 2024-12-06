<%@page import="com.example.utils.JWTUtils"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

<link rel="stylesheet" href="CSS/customerdetails.css">
</head>

<%
//HttpSession sess = request.getSession(false);
//String token = (String) session.getAttribute("jwtToken");
//if (token == null) {
//    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized access");
//    return;
//}



//String role = JWTUtils.validateToken(token).get("role", String.class); %>

<body>
<%@include file="Header.jsp" %>
<%
if (!"ADMIN".equals(role)) {
    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
    return;
}
%>
<form action="AdminServlet" method="post" id="101">
<input type="hidden" name="action" value="add"> 
<div class="form-group">  <input class="input" type="text" id="first_name" name="first_name" required placeholder="First Name"> </div>
 <div class="form-group"> <input class="input" type="text" id="last_name" name="last_name" required placeholder="Last Name"> </div>
 <div class="form-group"><input class="input" type="text" id="street" name="street" required placeholder="Street"></div>

<div class="form-group"> <input class="input" type="text" id="address" name="address" required placeholder="Address"> </div>

<div class="form-group">  <input class="input" type="text" id="city" name="city" required placeholder="City"> </div> 

<div class="form-group"> <input class="input" type="text" id="state" name="state" required placeholder="State"> </div> 

<div class="form-group">  <input class="input" type="email" id="email" name="email" required placeholder="Email"> </div>

<div class="form-group"> <input class="input" type="text" id="phone" name="phone" required placeholder="Phone"> </div> 

<!--  <div class="form-group"><input type="hidden" name="createdBy" value="1"> -->

<button type="submit" class="btn btn-primary">Add Customer</button></div>

    </form>
<footer>
    &copy; 2024 Customer Management
</footer>
</body>
</html>