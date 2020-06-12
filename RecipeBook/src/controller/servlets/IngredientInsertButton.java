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
import datasource.datamapper.IngredientDataMapper;
import model.Ingredient;
import model.Recipe;

/**
 * Servlet implementation class RecipeServlet
 */

public class IngredientInsertButton extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IngredientInsertButton() {
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
			
			String [] ingredientsArray = request.getParameterValues("ingredients");
			
			if(ingredientsArray != null) {
				int recipeIdInserted = Integer.parseInt(request.getParameter("recipeIdInserted"));
				
				IngredientStrategy strategy = new IngredientStrategy();
				
				strategy.insert(ingredientsArray, recipeIdInserted);
			}
			
			RecipeStrategy strategy = new RecipeStrategy();
				
			request.getRequestDispatcher("/MyRecipesButton");
			
			MyRecipesButton servlet = new MyRecipesButton();
			servlet.doGet(request, response);
		}
				
	}
	
}
