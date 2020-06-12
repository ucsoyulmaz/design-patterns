package controller.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import config.session.AppSession;

/**
 * Servlet implementation class CommentServletController
 */

public class WriteCommentButton extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WriteCommentButton() {
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
			String recipeOwner = request.getParameter("recipeOwner");
			request.setAttribute("recipeId", recipeId);
			request.setAttribute("recipeOwner", recipeOwner);
			request.getRequestDispatcher("/commentWriting.jsp").forward(request, response);
		}

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
