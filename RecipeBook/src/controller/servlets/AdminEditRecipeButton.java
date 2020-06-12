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
import controller.domainmodel.RecipeStrategy;
import distributedsystems.dto.RecipeDTO;
import model.Category;
import model.Recipe;

/**
 * Servlet implementation class CommentServletController
 */

public class AdminEditRecipeButton extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminEditRecipeButton() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		int recipeId = Integer.parseInt(request.getParameter("recipeId"));
		String recipeOwner = request.getParameter("recipeOwner");
		
		if(AppSession.isAuthenticated()) {
			Recipe recipe = new Recipe(recipeId);
			RecipeStrategy strategy = new RecipeStrategy(recipe);
			String retrievedRecipe = strategy.readPost();
			
			RecipeDTO rdto = new RecipeDTO();
			rdto = rdto.deserialize(retrievedRecipe);
			
			System.out.println(rdto.getRecipeDescription());
			
			CategoryStrategy strategy2 = new CategoryStrategy();
			
			List<Category> allCategories = new ArrayList<Category>();
			allCategories = strategy2.readAllCategories();
			
			request.setAttribute("recipe", rdto);
			request.setAttribute("recipeOwner", recipeOwner);
			request.setAttribute("allCategories", allCategories);
			request.getRequestDispatcher("/recipeEdit.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
