package com.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.example.dao.UserDao;
import com.example.models.User;

/**
 * Servlet implementation class RegistrationServlet
 */
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationServlet() {
     
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 String username = request.getParameter("username");
	     String password = request.getParameter("password");
	     String role = request.getParameter("role");

	        User user = new User();
	        user.setUsername(username);
	        user.setPassword(password);
	        user.setRole(role);

	        try {
	            UserDao userdao = new UserDao();
	            if (userdao.registerUser(user)) {
	                response.sendRedirect("login.jsp?success=Registration successful");
	            } else {
	                response.sendRedirect("register.jsp?error=Registration failed");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            response.sendRedirect("register.jsp?error=An error occurred");
	        }
	}

}
