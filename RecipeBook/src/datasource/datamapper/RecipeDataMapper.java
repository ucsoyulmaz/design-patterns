package datasource.datamapper;

import model.Comment;
import model.Ingredient;
import model.Recipe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import config.database.DBConnection;
import datasoruce.lazyload.CommentList;
import datasoruce.lazyload.CommentListProxyImpl;
import datasoruce.lazyload.IngredientList;
import datasoruce.lazyload.IngredientListProxyImpl;
import datasource.identity.RecipeRegistry;


//TODO this class has to communicate with identity map ---> needs improvement
public class RecipeDataMapper {

	public RecipeDataMapper() {
		
	}
	
	public int insert(Recipe recipe) throws SQLException {
		DBConnection DB = new DBConnection();
		
		//timestamp and rating 0 for now.
		
		String sql = "INSERT INTO recipes (user_name, recipe_title, recipe_description, cooking_time, cost, rating, category_name, version, modified_by) VALUES "
					+ "(?,?,?,?,?,?,?,?,?) RETURNING recipe_id;";
		
		PreparedStatement preparedStatement = DB.prepare(sql);
		
		preparedStatement.setString(1,recipe.getUserName());
		preparedStatement.setString(2,recipe.getRecipeTitle());
		preparedStatement.setString(3,recipe.getRecipeDescription());
		preparedStatement.setInt(4,recipe.getCookingTime());
		preparedStatement.setInt(5,recipe.getCost());
		preparedStatement.setFloat(6,recipe.getRating());
		preparedStatement.setString(7,recipe.getCategoryName());
		preparedStatement.setInt(8,recipe.getVersion());
		preparedStatement.setString(9,recipe.getModifiedBy());
		
		preparedStatement.executeQuery();
		DB.getDbConnection().commit();
		
		ResultSet lastInsertedRecipe = preparedStatement.getResultSet();
		lastInsertedRecipe.next();
		int lastInsertedRecipeId = lastInsertedRecipe.getInt(1);
		
		return lastInsertedRecipeId;
	}
	
	public int update(Recipe recipe) throws SQLException{
		
		DBConnection DB = new DBConnection();
		
		String sqlCheck = "SELECT version FROM recipes WHERE recipe_id = ?";
		PreparedStatement preparedStatementCheck = DB.prepare(sqlCheck);
		preparedStatementCheck.setInt(1,recipe.getRecipeId());
		preparedStatementCheck.executeQuery();
		
		ResultSet retrievedRecipe = preparedStatementCheck.getResultSet();
		retrievedRecipe.next();
		int currentRecipeVersion = retrievedRecipe.getInt("version");
		
		System.out.println("current: " + currentRecipeVersion + " , yours: " +recipe.getVersion());
		
		if(currentRecipeVersion > recipe.getVersion()) {
			System.out.println("This data has been changed!");
			return -1;
		}
		else {
			String sql = "UPDATE recipes SET " 
					+ "recipe_title = ? , "
					+ "recipe_description = ? , "
					+ "cooking_time = ? , "
					+ "cost = ? , "
					+ "rating = ? , "
					+ "category_name = ? , "
					+ "version = ? "
					+ "WHERE recipe_id = ? RETURNING recipe_id;";
			
			PreparedStatement preparedStatement = DB.prepare(sql);
			preparedStatement.setString(1,recipe.getRecipeTitle());
			preparedStatement.setString(2,recipe.getRecipeDescription());
			preparedStatement.setInt(3,recipe.getCookingTime());
			preparedStatement.setInt(4,recipe.getCost());
			preparedStatement.setFloat(5,recipe.getRating());
			preparedStatement.setString(6,recipe.getCategoryName());
			preparedStatement.setInt(7,recipe.getVersion());
			preparedStatement.setInt(8,recipe.getRecipeId());
	
			
			preparedStatement.executeQuery();
			DB.getDbConnection().commit();
			
			ResultSet lastUpdatedRecipe = preparedStatement.getResultSet();
			lastUpdatedRecipe.next();
			int lastUpdatedRecipeId = lastUpdatedRecipe.getInt(1);
			
			RecipeRegistry.changeAnItem(recipe);
			
			return lastUpdatedRecipeId;
		}
		
	}
	
	public void delete(Recipe recipe) {
		int recipeId = recipe.getRecipeId();
		DBConnection DB = new DBConnection();
		
		String sql = "DELETE FROM recipes WHERE recipe_id = ?";
		try {
			PreparedStatement preparedStatement = DB.prepare(sql);
			preparedStatement.setInt(1,recipe.getRecipeId());
			int rset = preparedStatement.executeUpdate();
			DB.getDbConnection().commit();
			
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	
		}
	}
	
	public Recipe read(Recipe recipe) {

		//Get recipeId to use it in RecipeRegistry Class
		int recipeId = recipe.getRecipeId();
		
		//getRecipe function for determining whether the given id will return an object from identity map or not
		Recipe result = RecipeRegistry.getRecipe(recipeId);
		
		//if identity map can give an object, just return the object
		if(result != null) {
						
			IngredientList ingredientList = new IngredientListProxyImpl(recipeId);
	        List<Ingredient> ingredientsListLoad = new ArrayList<Ingredient>();
	        ingredientsListLoad = result.getIngredientList().getIngredients();
	        
	        CommentList commentList = new CommentListProxyImpl(recipeId);
	        List<Comment> commentsListLoad = new ArrayList<Comment>();
	        commentsListLoad = result.getCommentList().getComments();
	        
			return result;
		}
		
		//if identity map can not return an object, connect to db and get data
		DBConnection DB = new DBConnection();
		String sql = "SELECT * FROM recipes WHERE recipe_id = ?";
		try {
			PreparedStatement preparedStatement = DB.prepare(sql);
			preparedStatement.setInt(1,recipe.getRecipeId());
			ResultSet rset = preparedStatement.executeQuery();
			DB.getDbConnection().commit();
			
			int rowCount = 0;
			
			String userName = "";
			String recipeTitle = "";
			String recipeDescription = "";
			int cookingTime = 0;
			int cost = 0;
			float rating = 0;
			String categoryName = "";
			int version = 0;
			String modifiedBy = "";
			
	        while(rset.next()) {   
	           userName = rset.getString("user_name");
	           recipeTitle = rset.getString("recipe_title");
	           recipeDescription = rset.getString("recipe_description");
	           cookingTime = rset.getInt("cooking_time");
	           cost = rset.getInt("cost");
	           rating = rset.getFloat("rating");
	           categoryName = rset.getString("category_name");
	           version = rset.getInt("version");
	           modifiedBy = rset.getString("modified_by");
	           ++rowCount;
	        }
	        
	        //the recipe object that will be returned
	        Recipe currentRecipeInDB;
	        
	        //TODO userId is the null indicator here but it should be changed for a better practice
	        //TODO recipeId = -1 is a null indicator, it should be changed.
	        if(!userName.equals("")) {
	        	
	        	IngredientList ingredientList = new IngredientListProxyImpl(recipeId);
	        	CommentList commentList = new CommentListProxyImpl(recipeId);
	        	
		        currentRecipeInDB = new Recipe(recipeId, userName, recipeTitle, recipeDescription, cookingTime, cost, rating, categoryName, version, modifiedBy, ingredientList, commentList);
		        
		        List<Ingredient> ingredientsListLoad = new ArrayList<Ingredient>();
		        ingredientsListLoad = currentRecipeInDB.getIngredientList().getIngredients();  
		        List<Comment> commentsListLoad = new ArrayList<Comment>();
		        commentsListLoad = result.getCommentList().getComments();
	        	
	        	//currentRecipeInDB = new Recipe(recipeId, userId, recipeTitle, recipeDescription, cookingTime, cost, rating, categoryId);
	        }
	        else {
	        	currentRecipeInDB = new Recipe(-1);
	        }
	        
	        //Add the retrieved recipe into identity map for the next requests.
	        RecipeRegistry.addRecipe(currentRecipeInDB);
	        return currentRecipeInDB;
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			//TODO recipeId = -1 is a null indicator, it should be changed.
			Recipe currentRecipeInDB = new Recipe(-1);
			return currentRecipeInDB;
		}
	}

	public List<Recipe> readAll() {
		
		List<Recipe> allRecipes = new ArrayList<Recipe>();
		
		//if identity map can not return an object, connect to db and get data
		DBConnection DB = new DBConnection();
		String sql = "SELECT * FROM recipes";
		
		try {
			PreparedStatement preparedStatement = DB.prepare(sql);
			ResultSet rset = preparedStatement.executeQuery();
			DB.getDbConnection().commit();
			
			int rowCount = 0;
			
			String userName = "";
			int recipeId = -1;
			String recipeTitle = "";
			String recipeDescription = "";
			int cookingTime = 0;
			int cost = 0;
			float rating = 0;
			String categoryName = "";
			int version = 0;
			String modifiedBy = "";
			
	        while(rset.next()) { 
	           recipeId = rset.getInt("recipe_id");
	           userName = rset.getString("user_name");
	           recipeTitle = rset.getString("recipe_title");
	           recipeDescription = rset.getString("recipe_description");
	           cookingTime = rset.getInt("cooking_time");
	           cost = rset.getInt("cost");
	           rating = rset.getFloat("rating");
	           categoryName = rset.getString("category_name");
	           version = rset.getInt("version");
	           modifiedBy = rset.getString("modified_by");
	           ++rowCount;
	           
	           IngredientList ingredientList = new IngredientListProxyImpl(recipeId);
	           CommentList commentList = new CommentListProxyImpl(recipeId);
	           
	           Recipe recipe = new Recipe(recipeId, userName, recipeTitle, recipeDescription, cookingTime, cost, rating, categoryName,version, modifiedBy, ingredientList, commentList);		 
	           //List<Ingredient> ingredientsListLoad = new ArrayList<Ingredient>();
	           //ingredientsListLoad = recipe.getIngredientList().getIngredients();
	           
	           Recipe result = RecipeRegistry.getRecipe(recipeId);
	   		
		   		//if identity map can give an object, just return the object
		   		if(result == null) {
		   			RecipeRegistry.addRecipe(recipe);
		   		}
	       
	           allRecipes.add(recipe);
	        }
	        
	        //Add the retrieved recipe into identity map for the next requests.
	        return allRecipes;
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return allRecipes;
		}
	}
	
public List<Recipe> readAllRecipesOfAUser(String userName) {
		
		List<Recipe> allRecipes = new ArrayList<Recipe>();
		
		//if identity map can not return an object, connect to db and get data
		DBConnection DB = new DBConnection();
		String sql = "SELECT * FROM recipes WHERE user_name = ?";
		
		try {
			PreparedStatement preparedStatement = DB.prepare(sql);
			preparedStatement.setString(1,userName);
			ResultSet rset = preparedStatement.executeQuery();
			DB.getDbConnection().commit();
			
			int rowCount = 0;
	
			int recipeId = -1;
			String recipeTitle = "";
			String recipeDescription = "";
			int cookingTime = 0;
			int cost = 0;
			float rating = 0;
			String categoryName = "";
			int version = 0;
			String modifiedBy = "";
			
	        while(rset.next()) { 
	           recipeId = rset.getInt("recipe_id");
	           recipeTitle = rset.getString("recipe_title");
	           recipeDescription = rset.getString("recipe_description");
	           cookingTime = rset.getInt("cooking_time");
	           cost = rset.getInt("cost");
	           rating = rset.getFloat("rating");
	           categoryName = rset.getString("category_name");
	           version = rset.getInt("version");
	           modifiedBy = rset.getString("modified_by");
	           ++rowCount;
	           
	           IngredientList ingredientList = new IngredientListProxyImpl(recipeId);
	           CommentList commentList = new CommentListProxyImpl(recipeId);
	           
	           Recipe recipe = new Recipe(recipeId, userName, recipeTitle, recipeDescription, cookingTime, cost, rating, categoryName, version, modifiedBy, ingredientList, commentList);		 
	           //List<Ingredient> ingredientsListLoad = new ArrayList<Ingredient>();
	           //ingredientsListLoad = recipe.getIngredientList().getIngredients();
	           
	           Recipe result = RecipeRegistry.getRecipe(recipeId);
	   		
		   		//if identity map can give an object, just return the object
		   		if(result == null) {
		   			RecipeRegistry.addRecipe(recipe);
		   		}
	       
	           allRecipes.add(recipe);
	        }
	        
	        //Add the retrieved recipe into identity map for the next requests.
	        return allRecipes;
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return allRecipes;
		}
	}


}
