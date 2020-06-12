package datasource.uof;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import datasource.datamapper.RecipeDataMapper;
import model.Recipe;

public class UnitOfWorkRecipe {
	
	private static ThreadLocal thread = new ThreadLocal();
	
	private List<Recipe> newRecipes = new ArrayList<Recipe>();
	private List<Recipe> dirtyRecipes = new ArrayList<Recipe>();
	private List<Recipe> deletedRecipes = new ArrayList<Recipe>();
	
	
	public static void newThread() {
		setThread(new UnitOfWorkRecipe());
	}
	
	public static void setThread(UnitOfWorkRecipe uow) {
		thread.set(uow);
	}
	
	public static UnitOfWorkRecipe getThread() {
		return (UnitOfWorkRecipe) thread.get();
	}
	
	public void addNewRecipe(Recipe recipe) {
		newRecipes.add(recipe);
	}
	
	public void addDirtyRecipe(Recipe recipe) {
		if(!dirtyRecipes.contains(recipe) && !newRecipes.contains(recipe)) {
			dirtyRecipes.add(recipe);
		}
	}
	
	public void addDeletedRecipe(Recipe recipe) {
		if(newRecipes.remove(recipe) == true) {
			return;
		}
		dirtyRecipes.remove(recipe);
		if(deletedRecipes.remove(recipe) != true) {
			deletedRecipes.add(recipe);
		}
	}
	
	public void commit() throws SQLException {
		
		RecipeDataMapper rdm = new RecipeDataMapper();
		
		for(int i = 0; i < newRecipes.size(); i++) {
			int insert = rdm.insert(newRecipes.get(i));
		}
		
		for(int i = 0; i < newRecipes.size(); i++) {
			int update = rdm.update(newRecipes.get(i));
		}
		
		for(int i = 0; i < newRecipes.size(); i++) {
			rdm.delete(newRecipes.get(i));
		}
	}
}
