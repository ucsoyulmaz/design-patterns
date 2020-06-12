package controller.bridge;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import datasource.datamapper.IngredientDataMapper;
import model.Ingredient;
import model.Recipe;

//This class (a domain layer member) is responsible for the communication between IngredientDataMapper and related servlets
public class IngredientStrategy {
	
	
	//Constructor
	public IngredientStrategy() {
		
	}
	
	//This function is for reading all the ingredients to display them during the recipe creation and update screens
	public List<Ingredient> readAllIngredients() {
		IngredientDataMapper idm = new IngredientDataMapper();
		List<Ingredient> allIngredients = new ArrayList<Ingredient>();
		allIngredients = idm.readAll();
		return allIngredients;
		
	}
	
	//This function is responsible for forwarding the data for inserting the chosen ingredient types into db for a particular recipe
	public void insert(String [] ingredients, int recipeIdInserted) {
		IngredientDataMapper idm = new IngredientDataMapper();
		try {
			idm.insert(ingredients, recipeIdInserted);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// This function is responsible for forwarding a delete request to data source layer for a particular recipe.
	public void delete(int recipeId) {
		IngredientDataMapper idm = new IngredientDataMapper();
		try {
			idm.delete(recipeId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
