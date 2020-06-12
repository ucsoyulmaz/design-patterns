package controller.domainmodel;
import model.Recipe;
import datasource.datamapper.RecipeDataMapper;
import distributedsystems.dto.RecipeDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class RecipeStrategy implements PostStrategy{
	
	private Recipe recipe;
	
	public RecipeStrategy() {
		
	}
	
	public RecipeStrategy(Recipe recipe) {
		this.recipe = recipe;
	}
	
	public int addPost() {
		RecipeDataMapper rdm = new RecipeDataMapper();
		
		try {
			int recipeId = rdm.insert(getRecipe());
			return recipeId;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}
	
	public int editPost() {
		
		//Read the current one first ---> it can be changed (not very efficient)
		RecipeDataMapper rdm = new RecipeDataMapper();
		
		try {
			int updatedId = rdm.update(getRecipe());
			return updatedId;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			int updatedId = -1;
			return updatedId;
		}
	}
	
	public void deletePost() {
		RecipeDataMapper rdm = new RecipeDataMapper();
		rdm.delete(getRecipe());
	}
	
	public String readPost() {
		RecipeDataMapper rdm = new RecipeDataMapper();
		Recipe currentRecipe = rdm.read(getRecipe());	
		
		RecipeDTO rdto = new RecipeDTO(currentRecipe.getRecipeId(), currentRecipe.getUserName(), currentRecipe.getRecipeTitle(), 
				currentRecipe.getRecipeDescription(), currentRecipe.getCookingTime(), currentRecipe.getCost(), currentRecipe.getRating(),
				currentRecipe.getCategoryName(), currentRecipe.getVersion(), currentRecipe.getModifiedBy(), 
				currentRecipe.getIngredientList(), currentRecipe.getCommentList());
	
		
		return rdto.serialize();
		
	}
	
	public List<Recipe> readAllPosts() {
		RecipeDataMapper rdm = new RecipeDataMapper();
		
		List<Recipe> allRecipes = new ArrayList<Recipe>();
		allRecipes = rdm.readAll();
		return allRecipes;
		
	}
	
	public List<Recipe> readAllPostsOfAUser(String userName) {
		RecipeDataMapper rdm = new RecipeDataMapper();
		
		List<Recipe> allRecipes = new ArrayList<Recipe>();
		allRecipes = rdm.readAllRecipesOfAUser(userName);
		return allRecipes;
		
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
}
