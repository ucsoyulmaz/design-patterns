package datasource.datamapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.database.DBConnection;
import model.Ingredient;

public class IngredientDataMapper {

	public IngredientDataMapper() {

	}

	public List<Ingredient> readAll() {

		List<Ingredient> allIngredients = new ArrayList<Ingredient>();

		// if identity map can not return an object, connect to db and get data
		DBConnection DB = new DBConnection();
		String sql = "SELECT * FROM ingredients";

		try {
			PreparedStatement preparedStatement = DB.prepare(sql);
			ResultSet rset = preparedStatement.executeQuery();
			DB.getDbConnection().commit();
		
			int ingredientId = -1;
			String ingredientName = "";

			while (rset.next()) {
				ingredientId = rset.getInt("ingredient_id");
				ingredientName = rset.getString("ingredient_name");
				Ingredient ingredient = new Ingredient(ingredientId, ingredientName);
				allIngredients.add(ingredient);
			}

			// Add the retrieved recipe into identity map for the next requests.
			return allIngredients;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return allIngredients;
		}
	}
	
	public void insert(String [] ingredients, int recipeIdInserted) throws SQLException {
		
		DBConnection DB = new DBConnection();
		
		for(int i = 0; i < ingredients.length; i++) {
			
			String sql = "INSERT INTO ingredientsOfRecipes (recipe_id, ingredient_id) VALUES "
					+ "(?,?);";
			
			PreparedStatement preparedStatement = DB.prepare(sql);
			
			preparedStatement.setInt(1,recipeIdInserted);
			preparedStatement.setInt(2,Integer.parseInt(ingredients[i]));
			
			int insert = preparedStatement.executeUpdate();
			DB.getDbConnection().commit();	
		}
		
	}
	
	public void delete(int recipeId) throws SQLException {
		DBConnection DB = new DBConnection();
		String sql = "DELETE FROM ingredientsOfRecipes WHERE recipe_id = ?";
		PreparedStatement preparedStatement = DB.prepare(sql);
		preparedStatement.setInt(1,recipeId);
		
		int delete = preparedStatement.executeUpdate();
		DB.getDbConnection().commit();
	}
}
