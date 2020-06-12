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

import config.session.AppSession;
import controller.domainmodel.CommentStrategy;
import controller.domainmodel.RecipeStrategy;
import datasoruce.lazyload.IngredientList;
import datasoruce.lazyload.IngredientListProxyImpl;
import distributedsystems.dto.RecipeDTO;
import model.Comment;
import model.Ingredient;
import model.Recipe;

/**
 * Servlet implementation class RecipeServlet
 */

public class ViewOneOfMyRecipeButton extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewOneOfMyRecipeButton() {
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
			
			System.out.println(rdto.getRecipeDescription());
			
			List<Ingredient> recipeIngredients = new ArrayList<Ingredient>();
			recipeIngredients = rdto.getIngredientList().getIngredients();
			
			List<Comment> recipeComments = new ArrayList<Comment>();
			recipeComments = rdto.getCommentList().getComments();
			
		
			request.setAttribute("recipe", rdto);
			request.setAttribute("recipeIngredients", recipeIngredients);
			request.setAttribute("recipeComments", recipeComments);
	        request.getRequestDispatcher("/recipeOnlyUser.jsp").forward(request, response);
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
	
}
