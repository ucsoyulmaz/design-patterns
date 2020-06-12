package datasoruce.lazyload;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.database.DBConnection;
import datasource.identity.RecipeRegistry;
import model.Ingredient;
import model.Recipe;

public class IngredientListImpl implements IngredientList{
	
	private static int recipeId;
	
	public IngredientListImpl(int recipeId) {
		this.recipeId = recipeId;
	}
	
	public List<Ingredient> getIngredients(){
		return getIngredientList();
	}
	
	public static List<Ingredient> getIngredientList(){
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		
		int recipeId = getRecipeId();
		
		//if identity map can not return an object, connect to db and get data
		DBConnection DB = new DBConnection();
		String sql = "SELECT * FROM ingredientsOfRecipes WHERE recipe_id = '" + recipeId + "'";
		try {
			PreparedStatement preparedStatement = DB.prepare(sql);
			ResultSet rset = preparedStatement.executeQuery();
			DB.getDbConnection().commit();
			
	        while(rset.next()) {   
	           int ingredientId = rset.getInt("ingredient_id");
	           String sql2 = "SELECT * FROM ingredients WHERE ingredient_id = '" + ingredientId + "'";
	           PreparedStatement preparedStatement2 = DB.prepare(sql2);
	           ResultSet rset2 = preparedStatement2.executeQuery();
	           rset2.next();
	           String ingredientName = rset2.getString("ingredient_name");
        	   ingredients.add(new Ingredient(ingredientId, ingredientName));
	           rset2.close();
	           preparedStatement2.close();
	        }
	        
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	
		}
		
		return ingredients;
	}

	public static int getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}
}
