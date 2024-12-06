<%@page import="com.example.utils.JWTUtils"%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.models.Customer" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<link rel="stylesheet" href="CSS/header.css">
<%
    HttpSession sess = request.getSession(false);
	String token = (String) session.getAttribute("jwtToken");
	if (token == null) {
	    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized access");
	    return;
	}

	
	String role = JWTUtils.validateToken(token).get("role", String.class);
   
    List<String> customerAttributes = List.of("First_Name","City", "Email", "Phone"); // Populate from DAO if needed
%>
<!--  
<script>
    async function Syncing(event) {
        event.preventDefault();

        try {
            //  Authenticate and fetch token
            const loginRequest = {
                login_id: "test@sunbasedata.com",
                password: "Test@123"
            };

            const tokenResponse = await fetch("https://qa.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(loginRequest)
            });

            if (!tokenResponse.ok) {
                throw new Error("Failed to authenticate: " + tokenResponse.statusText);
            }

            const tokenData = await tokenResponse.json();
            if (!tokenData.access_token) {
                throw new Error("Access token not provided in response.");
            }

            const bearerToken = `Bearer ${tokenData.access_token}`;
            localStorage.setItem("Btoken", bearerToken);

            //  Fetch customer data
            const customerResponse = await fetch("https://qa.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list", {
                method: "GET",
                headers: {
                    "Authorization": bearerToken
                }
            });

            if (!customerResponse.ok) {
                throw new Error("Failed to fetch customer data: " + customerResponse.statusText);
            }

            const customerData = await customerResponse.json();

            //  customer data to backend servlet
            await sendToServlet(customerData);

        } catch (error) {
            console.error("Error during synchronization:", error);
            alert("An error occurred. Please try again.");
        }
    }

    async function sendToServlet(data) {
        try {
            const response = await fetch("/AdminServlet", { // Adjust the servlet path as needed
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(data)
            });

            if (!response.ok) {
                throw new Error("Failed to send data to servlet: " + response.statusText);
            }

            const result = await response.json();
            console.log("Data stored successfully:", result);
            alert("Customer data has been synchronized!");
        } catch (error) {
            console.error("Error sending data to servlet:", error);
        }
    }
</script>-->

	 
<!-- 
<script>
	function Syncing(event){
		event.preventDefault();
		
		const request={
				login_id :test@sunbasedata.com,
			    password :Test@123
		}
		fetch("https://qa.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp",{
			method:'POST',
			headers:{
				'Content-type':'application/json'
			},
			body:JSON.stringify(request)
		})
		.then(response => {
            if (!response.ok) {
                throw new Error("Not able to fecth"+response.statusText);
            }
            return response.json();
        })
        .then(data=>{
        	if(data.access_token){
        		const Btoken = `Bearer ${data.access_token}`;
                localStorage.setItem('Btoken', Btoken);
        	}
        	return fetch("https://qa.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list",{
        		method:'GET',
        		headers:{
        			'Authorization': localStorage.getItem('Btoken')
        		}
        	}).then(response => response.json())
            .then(data => {
                storeCustomer(data);
            }).catch(error => console.error('Error:', error));
      })
        .catch(error => {
            console.error('Error during fetching data', error);
            alert('An error occurred during login. Please try again.');
        });
		
	}
	
	function storeCustomer(data){
		
		const [{
				first_name,
                lastName,
                street,
                address,
                city,
                state,
                email,
                phone
		}] = data;
		
	}
	
	

</script>-->

<div class="header">
<% if ("ADMIN".equals(role)) { %>

<!-- <button onclick="Syncing(event)" type="button" name="action" value="Syncing" class="btn btn-secondary">Fetch</button>
 -->
 <form action="AdminServlet" method="post" >
<button type="submit" name="action" value="Syncing" class="btn btn-secondary">Fetch</button>
</form>
        <a href="customer-details.jsp" class="btn btn-success">Add Customer</a>
    <% } %>
   <div> <form action="CustomerServlet" method="GET" class="search-form">
        <select name="searchAttribute" class="dropdown">
            <option value="">Search By</option>
            <% for (String attribute : customerAttributes) { %>
                <option value="<%= attribute %>"><%= attribute %></option>
            <% } %>
        </select>
        
        <input type="text" name="searchValue" placeholder="Enter value to search" class="search-box" required>
        
        <button type="submit" class="btn btn-primary">Search</button>
    </form>
 </div>
 <div>
    <form action="CustomerServlet" method="GET">
        <button type="submit" name="action" value="sync" class="btn btn-secondary" >Sync</button>
        
          </form>
    
    </div>
        <%if(sess !=null){ %>
    <a href="LogoutServlet" class="btn btn-danger" >Logout</a>
<%
} else { 
%>
<a href="login.jsp" class="btn btn-primary">Login</a>
<%
}
%>
  
    
    
    
</div>
