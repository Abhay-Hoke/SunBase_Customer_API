<%@page import="com.example.models.Customer"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>customer-list</title>
<link rel="stylesheet" href="CSS/customerlist.css"> 
</head>
<body>
<%@include file="Header.jsp"%>
<% HttpSession se = request.getSession(false);
String t = (String) se.getAttribute("jwtToken");
if (t == null) {
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized access");
    return;
}


String r = JWTUtils.validateToken(token).get("role", String.class); %>
<h2>Customer List</h2>
    <table border="1">
        <tr>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Street</th>
            <th>Address</th>
            <th>City</th>
            <th>State</th>
            <th>Email</th>
            <th>Phone</th>
        </tr>
        <% 
            List<Customer> customers = (List<Customer>) request.getAttribute("customers"); // Retrieved from Servlet
            if (customers != null) {
                for (Customer customer : customers) {
        %>
        <tr>
                <td><%= customer.getFirst_name() %></td>
                <td><%= customer.getLast_name() %></td>
                <td><%=customer.getStreet() %></td>
                <td><%= customer.getAddress() %></td>
                <td><%= customer.getCity() %></td>
                <td><%= customer.getState() %></td>
                <td><%= customer.getEmail() %></td>
                <td><%= customer.getPhone() %></td>
         </tr>
         <%}} %>
    </table>
<footer>
    &copy; 2024 Customer Management
</footer>
</body>
</html>