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
import controller.domainmodel.RecipeStrategy;
import datasoruce.lazyload.IngredientList;
import datasoruce.lazyload.IngredientListProxyImpl;
import model.Recipe;

/**
 * Servlet implementation class RecipeServlet
 */

public class MyRecipesButton extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyRecipesButton() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
		if(AppSession.isAuthenticated()) {
			String userName = AppSession.getUser().getUserName();
			System.out.println(userName);
			RecipeStrategy strategy = new RecipeStrategy();
			List<Recipe> allRecipes = new ArrayList<Recipe>();
			allRecipes = strategy.readAllPostsOfAUser(userName);
			request.setAttribute("allRecipes", allRecipes);
			request.getRequestDispatcher("/displayRecipesUser.jsp").forward(request, response);
		}	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
}
