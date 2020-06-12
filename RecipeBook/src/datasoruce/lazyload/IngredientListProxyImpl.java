package datasoruce.lazyload;

import java.util.List;

import model.Comment;
import model.Ingredient;

public class IngredientListProxyImpl implements IngredientList{
	
	private static IngredientList ingredientList;
	private static int recipeId;
	
	public IngredientListProxyImpl(int recipeId) {
		this.recipeId = recipeId;
		//Since we are using lazy load for only getting ingredients for viewing the details of recipe,
		//Each time we click on View button on the table, the ingredientList should be empty
		//In short words, when we show the table, we don't show ingredients
		// Once you click on the "View" button, the ingredients will be loaded.
		ingredientList = null;		
	}
	
	public List<Ingredient> getIngredients(){
		if (ingredientList == null) {
			ingredientList = new IngredientListImpl(getRecipeId());
		}
		
		return ingredientList.getIngredients();
	}

	public static int getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}
	
}