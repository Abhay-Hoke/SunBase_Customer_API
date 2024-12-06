<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin Dashboard</title>
<link rel="stylesheet" href="CSS/admin-dashboard.css">
</head>
<body>
    <h2>Dashboard</h2>
<%@ include file="Header.jsp"%>
<% 	
String t = (String) session.getAttribute("jwtToken");
	String r = JWTUtils.validateToken(t).get("role", String.class);
	if (!"ADMIN".equals(r)) {
	    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
	    return;
	}
          
        %>
  
  
    <h3>Customer List</h3>
    <table class="table table-bordered">
    <thead>
        <tr>
             <th>First Name</th>
            <th>Last Name</th>
            <th>Street</th>
            <th>Address</th>
            <th>City</th>
            <th>State</th>
            <th>Email</th>
            <th>Phone</th>
            <% if ("ADMIN".equals(role)) { %>
                <th>Actions</th>
            <% } %>
        </tr>
    </thead>
    <%  List<Customer> customers = (List<Customer>) request.getAttribute("customers"); // Retrieved from Servlet
    
    if (customers != null) {
       for (Customer customer : customers) { %>
    <tbody>
        
            <tr>
                <td><%= customer.getFirst_name() %></td>
                <td><%= customer.getLast_name() %></td>
                <td><%=customer.getStreet() %></td>
                <td><%= customer.getAddress() %></td>
                <td><%= customer.getCity() %></td>
                <td><%= customer.getState() %></td>
                <td><%= customer.getEmail() %></td>
                <td><%= customer.getPhone() %></td>
                <% if ("ADMIN".equals(role)) { %>
                    <td>
                        <a href="edit-customer.jsp?uuid=<%= customer.getUuid() %>" class="btn btn-warning">Edit</a>
                        <form action="AdminServlet" method="POST" style="display:inline;">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="uuid" value="<%= customer.getUuid() %>">
                            <button type="submit" class="btn btn-danger" onclick="return confirm('Are you sure?')">Delete</button>
                        </form>
                    </td>
                <% } %>
            </tr>
        <% 
                }
            } else { 
        %>
            <tr>
                <td colspan="<%= "ADMIN".equals(r) ? 9 : 8 %>" class="text-center">No customers found.</td>
            </tr>
        <% } %>
    </tbody>
    <div class="pagination"></div>
</table>
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
<!-- Pagination container -->

<script>
    // Example: Fetch pagination data from server-side attributes
    const totalPages = parseInt('<%= request.getAttribute("totalPages") %>', 10) || 1;
    const currentPage = parseInt('<%= request.getAttribute("currentPage") %>', 10) || 1;

    const paginationContainer = document.querySelector('.pagination');
    function renderPagination(totalPages, currentPage) {
        if (!paginationContainer) return;

        paginationContainer.innerHTML = ''; // Clear existing pagination links

        for (let pageNum = 1; pageNum <= totalPages; pageNum++) {
            const link = document.createElement('a');
            link.href = `CustomerServlet?action=list&page=${pageNum}&itemsPerPage=10`;
            link.textContent = pageNum;
            link.className = pageNum === currentPage ? 'active' : '';
            paginationContainer.appendChild(link);
        }
    }

    // Render pagination on page load
    renderPagination(totalPages, currentPage);
</script>

<style>
/* Basic styling for pagination */
.pagination a {
    display: inline-block;
    margin: 0 5px;
    padding: 5px 10px;
    text-decoration: none;
    border: 1px solid #ccc;
    color: #333;
}

.pagination a.active {
    background-color: #007bff;
    color: white;
    border-color: #007bff;
}
</style>
</body>
</html>
<footer>
    &copy; 2024 Customer Management
</footer>
    
</body>
</html>

