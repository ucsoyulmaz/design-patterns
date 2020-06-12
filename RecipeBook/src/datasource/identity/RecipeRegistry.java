package datasource.identity;

import java.util.HashMap;
import java.util.Map;

import model.Recipe;

public class RecipeRegistry {

	private static Map<Integer, Recipe> RecipeMap = new HashMap<>();
	
	//Check whether the given recipeId addresses an object in identity map or not
	public static Recipe getRecipe(int id) {
		return RecipeMap.get(id);
	}
	
	//Adds new Recipe objects into the identity map
	public static void addRecipe(Recipe recipe) {
		RecipeRegistry.RecipeMap.put(recipe.getRecipeId(), recipe);
	}
	
	public static void changeAnItem(Recipe recipe) {
		int recipeId = recipe.getRecipeId();
		RecipeRegistry.RecipeMap.remove(recipeId);
		
	}
	
}

