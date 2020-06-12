package controller.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import config.session.AppSession;
import config.session.Session;
import controller.bridge.IngredientStrategy;
import controller.domainmodel.CommentStrategy;
import controller.domainmodel.RecipeStrategy;
import datasoruce.lazyload.IngredientList;
import datasoruce.lazyload.IngredientListProxyImpl;
import model.Comment;
import model.Ingredient;
import model.Recipe;

/**
 * Servlet implementation class RecipeServlet
 */

public class CreateRecipeChooseIngredientsButton extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateRecipeChooseIngredientsButton() {
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
			Recipe recipe = new Recipe(0,userName, request.getParameter("recipeTitle"), request.getParameter("recipeDescription"), Integer.parseInt(request.getParameter("cookingTime")), Integer.parseInt(request.getParameter("cost")), (float) 0.0, request.getParameter("categoryName"),1, userName);
			RecipeStrategy rs = new RecipeStrategy(recipe);
			int recipeIdInserted = rs.addPost();
			
			IngredientStrategy strategy = new IngredientStrategy();
			List<Ingredient> allIngredients = new ArrayList<Ingredient>();
			allIngredients = strategy.readAllIngredients();
			
			request.setAttribute("recipeIdInserted", recipeIdInserted);
			request.setAttribute("allIngredients", allIngredients);
			request.setAttribute("userName", userName);
			request.getRequestDispatcher("/chooseIngredients.jsp").forward(request, response);
		}
		
		
	}
	
}
