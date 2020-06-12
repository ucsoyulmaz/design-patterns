package controller.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import config.session.AppSession;
import config.session.Session;
import controller.domainmodel.CommentStrategy;
import controller.domainmodel.RecipeStrategy;
import model.Comment;
import model.Recipe;

/**
 * Servlet implementation class CommentServlet
 */

public class CommentSendButton extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentSendButton() {
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
		
		if(AppSession.isAuthenticated()) {
			String userName = AppSession.getUser().getUserName();
			
			Comment comment = new Comment(0,Integer.parseInt(request.getParameter("recipeId")),userName,request.getParameter("commentContent"));
			CommentStrategy cs = new CommentStrategy(comment);
			cs.addPost();
			
			String recipeId = request.getParameter("recipeId");
			String recipeOwner = request.getParameter("recipeOwner");
		
			if(recipeOwner.equals(userName)) {
				request.getRequestDispatcher("/ViewOneOfMyRecipeButton");
		        ViewOneOfMyRecipeButton is = new ViewOneOfMyRecipeButton();
				is.doGet(request, response);
			}
			else {
				request.getRequestDispatcher("/ViewARecipeFromTimelineButton");
				ViewARecipeFromTimelineButton is = new ViewARecipeFromTimelineButton();
				is.doGet(request, response);
			}
		}
		
	}

}
