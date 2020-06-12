package controller.servlets;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import config.database.DBConnection;
import controller.bridge.UserStrategy;
import model.User;

/**
 * Servlet implementation class Signup
 */

public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Signup() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!request.getParameter("password").equals(request.getParameter("password-repeat"))) {
			request.setAttribute("error","Different Passwords");
    		request.getRequestDispatcher("/signup.jsp").forward(request, response);
		}
		else {
			DBConnection DB = new DBConnection();
			String sql = "SELECT * FROM users WHERE user_name = '" + request.getParameter("userName") + "'";
			try {
				PreparedStatement preparedStatement = DB.prepare(sql);
				ResultSet rset = preparedStatement.executeQuery();
				
				String password = null;
				String error = ""; 
				
				int rowCount = 0;
				
		        while(rset.next()) {   
		           ++rowCount;
		        }
		        
		        if(rowCount > 0) {
		        	request.setAttribute("error","Username already exists");
		    		request.getRequestDispatcher("/signup.jsp").forward(request, response);
		        }
		        else {
		        	User user = new User(request.getParameter("userName"),request.getParameter("password"), request.getParameter("userType"));
		        	UserStrategy us = new UserStrategy(user);
		        	String usernameInserted = us.addUser();
		        	System.out.println(usernameInserted);
		        	request.getRequestDispatcher("/login.jsp").forward(request, response);
		        }
		        
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
	}

}
