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
import datasource.datamapper.IngredientDataMapper;
import model.Ingredient;

/**
 * Servlet implementation class RecipeServlet
 */

public class IngredientUpdateButton extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IngredientUpdateButton() {
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
			int recipeIdUpdated = Integer.parseInt(request.getParameter("recipeIdUpdated"));
			IngredientStrategy strategy = new IngredientStrategy();
			
			strategy.delete(recipeIdUpdated);
			
			
			String [] ingredientsArray = request.getParameterValues("ingredients");
			
			if(ingredientsArray != null) {
				
				strategy.insert(ingredientsArray, recipeIdUpdated);
			}
			
			String recipeOwner = request.getParameter("recipeOwner");
			
			request.setAttribute("recipeId", recipeIdUpdated);
			
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
	
}
