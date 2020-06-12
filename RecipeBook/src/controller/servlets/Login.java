package controller.servlets;

import config.database.DBConnection;
import config.session.AppSession;
import config.session.Session;
import controller.bridge.UserStrategy;
import controller.domainmodel.CommentStrategy;
import controller.domainmodel.RecipeStrategy;
import model.Comment;
import model.Recipe;
import model.User;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.Cookie;

/**
 * Servlet implementation class SimpleServlet
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String username = request.getParameter("userName");
		String password = request.getParameter("passWord");
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		token.setRememberMe(true);
		
		Subject currentUser = SecurityUtils.getSubject();
		String view = "index.jsp";
		try {
			User userTemp = new User(username);
			UserStrategy us = new UserStrategy(userTemp);
			User user = us.readUser();
			if(user.getUserId() < 1) {
				view = "login.jsp";
			}
			else {
				System.out.println(user.getUserName());
				currentUser.login(token);
				AppSession.init(user);
			}
		}catch(UnknownAccountException | IncorrectCredentialsException | NullPointerException e){
			view = "login.jsp";
		} finally {
			request.getRequestDispatcher(view).forward(request, response);
		}
		
		
		
	}

}
