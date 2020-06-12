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

public class EditMyRecipeButton extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditMyRecipeButton() {
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
			
			Recipe recipe = new Recipe(recipeId);
			RecipeStrategy strategy = new RecipeStrategy(recipe);
			String retrievedRecipe = strategy.readPost();
			
			RecipeDTO rdto = new RecipeDTO();
			rdto = rdto.deserialize(retrievedRecipe);
			
			CategoryStrategy strategy2 = new CategoryStrategy();
			
			List<Category> allCategories = new ArrayList<Category>();
			allCategories = strategy2.readAllCategories();
			
			String recipeOwner = request.getParameter("recipeOwner");
			
			request.setAttribute("recipeOwner", recipeOwner);
			request.setAttribute("recipe", rdto);
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
