package com.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.example.dao.UserDao;
import com.example.models.User;
import com.example.utils.JWTUtils;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
       
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 String username = request.getParameter("username");
	     String password = request.getParameter("password");

	        try {
	            UserDao userdaO = new UserDao();
	            User user = userdaO.validateLogin(username, password);

	            if (user != null) {
	                String token = JWTUtils.generateToken(user.getUsername(), user.getRole());
	               //System.out.println(token);
	                
	                HttpSession session = request.getSession();
	                session.setAttribute("jwtToken", token);
	                //response.setHeader("Authorization", "Bearer " + token);

	                if ("ADMIN".equals(user.getRole())) {
	                    response.sendRedirect("admin-dashboard.jsp");
	                } else {
	                    response.sendRedirect("customer-list.jsp");
	                }
	            } else {
	                response.sendRedirect("login.jsp?error=Invalid credentials");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            response.sendRedirect("login.jsp?error=An error occurred");
	        }
	}

}
