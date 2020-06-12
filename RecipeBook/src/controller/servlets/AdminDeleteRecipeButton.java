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
import datasoruce.lazyload.CommentList;
import datasoruce.lazyload.CommentListProxyImpl;
import datasoruce.lazyload.IngredientList;
import datasoruce.lazyload.IngredientListProxyImpl;
import model.Ingredient;
import model.Recipe;

/**
 * Servlet implementation class RecipeServlet
 */

public class AdminDeleteRecipeButton extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminDeleteRecipeButton() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		int recipeId = Integer.parseInt(request.getParameter("recipeId"));
		String recipeOwner = request.getParameter("recipeOwner");
		
		
		if(AppSession.isAuthenticated()) {
			String userName = AppSession.getUser().getUserName();
			Recipe recipe = new Recipe(recipeId);
			RecipeStrategy rs = new RecipeStrategy(recipe);
			rs.deletePost();
			
			request.setAttribute("recipeId", recipeId);
			
			if(recipeOwner.equals(userName)) {
				request.getRequestDispatcher("/MyRecipesButton");
				MyRecipesButton servlet = new MyRecipesButton();
				servlet.doGet(request, response);
			}
			else {
				request.getRequestDispatcher("/TimelineButton");
				TimelineButton servlet = new TimelineButton();
				servlet.doGet(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
}
