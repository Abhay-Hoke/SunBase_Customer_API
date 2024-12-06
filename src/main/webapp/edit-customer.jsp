<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.example.models.Customer" %>
<%@ page import="com.example.dao.CustomerDao" %>



<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Edit Customer</title>
    <link rel="stylesheet" href="CSS/editcustomer.css">
</head>
<%@include file="Header.jsp" %>
<%
if (!"ADMIN".equals(role)) {
	    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
	    return;
	}
    // Check user role from session
   
 //  String t = (String) session.getAttribute("jwtToken");
//	String r = JWTUtils.validateToken(t).get("role", String.class);
//if("ADMIN".equals(r)){
	CustomerDao cdao = new CustomerDao();
	String uuid = request.getParameter("uuid");
	System.out.println(uuid);
	 Customer cus =cdao.getCustomerByUuid(uuid);
%>
<body>
    <h2>Edit Customer</h2>
    <form action="AdminServlet" method="POST" id="103">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="uuid" value="<%= cus.getUuid() %>">
        <div class="form-group">
        <label for="first_name">First Name:</label>
        <input class="input" type="text" id="first_name" name="first_name" value="<%= cus.getFirst_name() %>" required>
        </div>
        <div class="form-group">
        <label for="last_name">Last Name:</label>
        <input class="input" type="text" id="last_name" name="last_name" value="<%= cus.getLast_name() %>" required>
        </div>
        <div class="form-group">
        <label for="street">Street:</label>
        <input class="input" type="text" id="street" name="street" value="<%= cus.getStreet() %>" required>
        </div>
        <div class="form-group">
        <label for="address">Address:</label>
        <input class="input" type="text" id="address" name="address" value="<%= cus.getAddress() %>" required>
        </div>
        <div class="form-group">
        <label for="city">City:</label>
        <input class="input" type="text" id="city" name="city" value="<%= cus.getCity() %>" required>
        </div>
        <div class="form-group">
        <label for="state">State:</label>
        <input class="input" type="text" id="state" name="state" value="<%= cus.getState() %>" required>
        </div>
        <div class="form-group">
        <label for="email">Email:</label>
        <input class="input" type="email" id="email" name="email" value="<%= cus.getEmail() %>" required>
        </div>
        <div class="form-group">
        <label for="phone">Phone:</label>
        <input class="input" type="text" id="phone" name="phone" value="<%= cus.getPhone() %>" required>
        </div>
        <div class="form-group"><label></label>
        <button type="submit" class="btn btn-primary">Update</button>
        <a href="admin-dashboard.jsp" class="btn btn-secondary">Cancel</a>
        </div>
    </form>
    <%//} %>
    
    <footer>
    &copy; 2024 Customer Management
</footer>
</body>
</html>
