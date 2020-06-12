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
import controller.lock.ExclusiveReadLockManager;
import model.Comment;
import model.Recipe;

/**
 * Servlet implementation class CommentServlet
 */

public class CommentEditSendButton extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentEditSendButton() {
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
			Comment comment = new Comment(Integer.parseInt(request.getParameter("commentId")),Integer.parseInt(request.getParameter("recipeId")),request.getParameter("userName"),request.getParameter("commentContent"));
			CommentStrategy cs = new CommentStrategy(comment);
			cs.editPost();
			
			String recipeOwner = request.getParameter("recipeOwner");
			int recipeId = Integer.parseInt(request.getParameter("recipeId"));
			request.setAttribute("recipeId", recipeId);
			
			String userName = AppSession.getUser().getUserName();
			
			ExclusiveReadLockManager erlm = new ExclusiveReadLockManager();
			erlm.releaseLock(comment.getCommentId(), userName);
			
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
