package com.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



import com.example.dao.CustomerDao;
import com.example.models.Customer;
import com.example.utils.JWTUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public AdminServlet() {
    }

	
    
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        HttpSession session = request.getSession(false);
//        if (session == null) {
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Session expired. Please log in again.");
//            return;
//        }
//
//        String token = (String) session.getAttribute("jwtToken");
//        if (token == null) {
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized access");
//            return;
//        }
//
//        try {
//            String role = JWTUtils.validateToken(token).get("role", String.class);
//            if (!"ADMIN".equals(role)) {
//                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
//                return;
//            }
//
//            // Pagination parameters
//            int currentPage = 1;
//            int itemsPerPage = 10;
//
//            String pageParam = request.getParameter("page");
//            String itemsParam = request.getParameter("itemsPerPage");
//
//            if (pageParam != null) {
//                currentPage = Integer.parseInt(pageParam);
//            }
//            if (itemsParam != null) {
//                itemsPerPage = Integer.parseInt(itemsParam);
//            }
//
//            int offset = (currentPage - 1) * itemsPerPage;
//
//            CustomerDao customerDao = new CustomerDao();
//            List<Customer> customers = customerDao.getCustomersWithPagination(offset, itemsPerPage);
//            int totalCustomers = customerDao.getTotalCustomers();
//            int totalPages = (int) Math.ceil((double) totalCustomers / itemsPerPage);
//
//            // Set attributes for JSP
//            request.setAttribute("customers", customers);
//            request.setAttribute("totalPages", totalPages);
//            request.setAttribute("currentPage", currentPage);
//
//            // Forward to JSP
//            request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.sendRedirect("admin-dashboard.jsp?error=An error occurred");
//        }
//    }    
    

   
    
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		
		
		HttpSession session = request.getSession(false);
		if (session == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Session expired. Please log in again.");
            return;
        }
		 String token = (String) session.getAttribute("jwtToken");
	        if (token == null) {
	            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized access");
	            return;
	        }
	
        try {
            String role = JWTUtils.validateToken(token).get("role", String.class);
            if (!"ADMIN".equals(role)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
                return;
            }
            
            String action = request.getParameter("action");
            CustomerDao customerdaO = new CustomerDao();

            if ("add".equals(action)) {
                Customer customer = new Customer();
                customer.setFirst_name(request.getParameter("first_name"));
                customer.setLast_name(request.getParameter("last_name"));
                customer.setStreet(request.getParameter("street"));
                customer.setAddress(request.getParameter("address"));
                customer.setCity(request.getParameter("city"));
                customer.setState(request.getParameter("state"));
                customer.setEmail(request.getParameter("email"));
                customer.setPhone(request.getParameter("phone"));
                
                customerdaO.addCustomer(customer);
                response.sendRedirect("admin-dashboard.jsp?success=Customer added");
            
            }else if ("update".equals(action)) {
               
            	String uuid = request.getParameter("uuid");
                String first_name = request.getParameter("first_name");
                String last_name = request.getParameter("last_name");
                String street = request.getParameter("street");
                String address = request.getParameter("address");
                String city = request.getParameter("city");
                String state = request.getParameter("state");
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");

                Customer customer = new Customer();
                customer.setUuid(uuid);
                customer.setFirst_name(first_name);
                customer.setLast_name(last_name);
                customer.setStreet(street);
                customer.setAddress(address);
                customer.setCity(city);
                customer.setState(state);
                customer.setEmail(email);
                customer.setPhone(phone);

                if (customerdaO.updateCustomer(customer)) {
                    response.sendRedirect("admin-dashboard.jsp?success=Customer updated");
                } else {
                    response.sendRedirect("admin-dashboard.jsp?error=Failed to update customer");
                }
            } else if ("delete".equals(action)) {
               
                String uuid = request.getParameter("uuid");

                if (customerdaO.deleteCustomer(uuid)) {
                    response.sendRedirect("admin-dashboard.jsp?success=Customer deleted");
                } else {
                    response.sendRedirect("admin-dashboard.jsp?error=Failed to delete customer");
                }
            
            }else if("Syncing".equals(action)) {
            	 syncCustomerData(response, customerdaO);
         	
//            	 // t5ry with javascript
//                BufferedReader reader = request.getReader();
//                String jsonData = reader.lines().collect(Collectors.joining());
//                reader.close();
//
//                //Gson gson = new Gson();
//                ObjectMapper objmap = new ObjectMapper();
//                Customer[] customers = objmap.readValue(jsonData, Customer[].class);
//                
//                //Customer[] customers = gson.fromJson(jsonData, Customer[].class);
//
//                CustomerDao customerDao = new CustomerDao();
//                int addedCount = 0;
//
//                try {
//                    for (Customer customer : customers) {
//                        // Check if customer already exists by email
//                        if (!customerDao.existsByEmail(customer.getEmail())) {
//                            customerDao.addCustomer(customer); // Save only unique customers
//                            addedCount++;
//                        }
//                    }
//                    // Send success response
//                    response.setContentType("application/json");
//                    response.getWriter().write("{\"status\":\"success\",\"addedCount\":" + addedCount + "}");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//                    response.getWriter().write("{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
//                }
            }
            } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("admin-dashboard.jsp?error=An error occurred");
        }
	}
	
	
	private void syncCustomerData(HttpServletResponse response, CustomerDao customerDao) {
        try {
        	System.out.println("Im syncCustomerData");
            //  Login and Retrieve Token
            String loginUrl = "https://qa.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";
            Map<String, String> loginPayload = new HashMap<>();
            loginPayload.put("login_id", "test@sunbasedata.com");
            loginPayload.put("password", "Test@123");

            String loginResponse = sendPostRequest(loginUrl, new ObjectMapper().writeValueAsString(loginPayload));
            JsonNode loginJson = new ObjectMapper().readTree(loginResponse);
            String accessToken = loginJson.get("access_token").asText();

            //  Fetch Customer Data
            String getCustomersUrl = "https://qa.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list";
            String customersResponse = sendGetRequest(getCustomersUrl, "Bearer " + accessToken);

            Customer[] customers = new ObjectMapper().readValue(customersResponse, Customer[].class);

            //  Save Customers to Database
            for (Customer customer : customers) {
                if (!customerDao.existsByEmail(customer.getEmail())) {
                    customerDao.addCustomer(customer);
                }
            }

            response.sendRedirect("admin-dashboard.jsp?success=Data synchronized successfully");
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.sendRedirect("admin-dashboard.jsp?error=An error occurred during synchronization");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private String sendPostRequest(String urlString, String payload) throws IOException {
    	
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        System.out.println("Im sendPostRequest");

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            os.write(payload.getBytes());
            os.flush();
        }

        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new IOException("POST request failed with response code " + connection.getResponseCode());
        }

        return readResponse(connection);
    }

    private String sendGetRequest(String urlString, String authorization) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", authorization);

        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new IOException("GET request failed with response code " + connection.getResponseCode());
        }

        return readResponse(connection);
    }
    
    

    private String readResponse(HttpURLConnection connection) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }
	
	
	
	
	
	
	
	
	
//		WebClient 	webClient = WebClient.create();
//	 private void syncCustomerData(HttpServletResponse response, CustomerDao customerDao) {
//	        try {
//	            // Step 1: Perform Login to Get Token
//	            String loginUrl = "https://qa.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";
//
//	            Map<String, String> loginPayload = Map.of(
//	                    "login_id", "test@sunbasedata.com",
//	                    "password", "Test@123"
//	            );
//
//	            String accessToken = webClient.post()
//	                    .uri(loginUrl)
//	                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//	                    .bodyValue(loginPayload)
//	                    .retrieve()
//	                    .bodyToMono(JsonNode.class)
//	                    .block()
//	                    .get("access_token")
//	                    .asText();
//
//	            // Step 2: Fetch Customer Data
//	            String getCustomersUrl = "https://qa.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list";
//
//	            Customer[] customers = webClient.get()
//	                    .uri(getCustomersUrl)
//	                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
//	                    .retrieve()
//	                    .bodyToMono(Customer[].class)
//	                    .block();
//
//	            // Step 3: Save Customers to Database
//	            for (Customer customer : customers) {
//	                if (!customerDao.exists(customer.getUuid())) {
//	                    customerDao.addCustomer(customer);
//	                }
//	            }
//
//	            response.sendRedirect("admin-dashboard.jsp?success=Data synchronized successfully");
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	            try {
//	                response.sendRedirect("admin-dashboard.jsp?error=An error occurred during synchronization");
//	            } catch (IOException ioException) {
//	                ioException.printStackTrace();
//	            }
//	        }
//	    }
//	

}
