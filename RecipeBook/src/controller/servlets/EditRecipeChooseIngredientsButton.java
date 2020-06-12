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
import controller.bridge.IngredientStrategy;
import controller.domainmodel.RecipeStrategy;
import datasoruce.lazyload.CommentList;
import datasoruce.lazyload.CommentListProxyImpl;
import datasoruce.lazyload.IngredientList;
import datasoruce.lazyload.IngredientListProxyImpl;
import model.Ingredient;
import model.Recipe;

/**
 * Servlet implementation class RecipeServlet
 */

public class EditRecipeChooseIngredientsButton extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditRecipeChooseIngredientsButton() {
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
			
			IngredientList ingredientList = new IngredientListProxyImpl(Integer.parseInt(request.getParameter("recipeId")));
			CommentList commentList = new CommentListProxyImpl(Integer.parseInt(request.getParameter("recipeId")));
			
			Recipe recipe = new Recipe(Integer.parseInt(request.getParameter("recipeId")),userName, request.getParameter("recipeTitle"), request.getParameter("recipeDescription"), Integer.parseInt(request.getParameter("cookingTime")), Integer.parseInt(request.getParameter("cost")), Float.parseFloat(request.getParameter("rating")), request.getParameter("categoryName"), Integer.parseInt(request.getParameter("version")),request.getParameter("modifiedBy"),ingredientList,commentList);
			RecipeStrategy rs = new RecipeStrategy(recipe);
			int recipeIdUpdated = rs.editPost();

			if(recipeIdUpdated == -1) {
				request.getRequestDispatcher("/recipeAlreadyChanged.jsp").forward(request, response);
			}
			else {
				IngredientStrategy strategy = new IngredientStrategy();
				List<Ingredient> allIngredients = new ArrayList<Ingredient>();
				allIngredients = strategy.readAllIngredients();
				
				String recipeOwner = request.getParameter("recipeOwner");
				
				request.setAttribute("recipeIdUpdated", recipeIdUpdated);
				request.setAttribute("recipeOwner", recipeOwner);
				request.setAttribute("allIngredients", allIngredients);
				request.getRequestDispatcher("/chooseIngredientsUpdate.jsp").forward(request, response);
			}
		}
		
	}
	
}
