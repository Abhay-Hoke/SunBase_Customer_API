package com.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.example.dao.CustomerDao;
import com.example.models.Customer;

/**
 * Servlet implementation class CustomerListServlet
 */
public class CustomerListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerListServlet() {
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		try {
	            CustomerDao customerdaO = new CustomerDao();
	            List<Customer> customers = customerdaO.getAllCustomers();

	            request.setAttribute("customers", customers);
	            request.getRequestDispatcher("customer-list.jsp").forward(request, response);
	        } catch (Exception e) {
	            e.printStackTrace();
	            response.sendRedirect("customer-list.jsp?error=An error occurred");
	        }
	}

	
	

}
