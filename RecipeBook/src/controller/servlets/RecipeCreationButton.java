package controller.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import config.session.AppSession;
import controller.bridge.CategoryStrategy;
import model.Category;

/**
 * Servlet implementation class RecipeServlet
 */

public class RecipeCreationButton extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecipeCreationButton() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		if(AppSession.isAuthenticated()) {
			String userName = AppSession.getUser().getUserName();
			CategoryStrategy strategy = new CategoryStrategy();
			
			List<Category> allCategories = new ArrayList<Category>();
			allCategories = strategy.readAllCategories();

			
			request.setAttribute("allCategories", allCategories);
			request.getRequestDispatcher("/createRecipe.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}
	
}
