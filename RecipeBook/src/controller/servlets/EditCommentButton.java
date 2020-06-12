package controller.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import config.session.AppSession;
import config.session.Session;
import controller.bridge.CategoryStrategy;
import controller.domainmodel.CommentStrategy;
import controller.domainmodel.RecipeStrategy;
import model.Category;
import model.Comment;
import model.Recipe;

/**
 * Servlet implementation class CommentServletController
 */

public class EditCommentButton extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditCommentButton() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		if(AppSession.isAuthenticated()) {
			String userName = AppSession.getUser().getUserName();
			int recipeId = Integer.parseInt(request.getParameter("recipeId"));
			int commentId = Integer.parseInt(request.getParameter("commentId"));
			String recipeOwner = request.getParameter("recipeOwner");
			
			Comment comment = new Comment(commentId);
			CommentStrategy strategy = new CommentStrategy(comment);
			Comment retrievedComment = strategy.readPost();
			
			if(retrievedComment.getRecipeId() == -1) {
				request.setAttribute("error_message", "This comment is being used by another user, please click on HOME button.");
				request.getRequestDispatcher("/commentInUse.jsp").forward(request, response);
			}
			else {
				request.setAttribute("comment", retrievedComment);
				request.setAttribute("recipeOwner", recipeOwner);
				request.getRequestDispatcher("/commentEdit.jsp").forward(request, response);
			}
		}		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
