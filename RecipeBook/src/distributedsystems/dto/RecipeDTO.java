package distributedsystems.dto;

import com.google.gson.Gson;

import datasoruce.lazyload.CommentList;
import datasoruce.lazyload.IngredientList;

public class RecipeDTO {

	private static int recipeId;
	private static String userName;
	private static String recipeTitle;
	private static String recipeDescription;
	private static int cookingTime;
	private static int cost;
	private static float rating;
	private static String categoryName;
	private static IngredientList ingredientList;
	private static CommentList commentList;
	private static int version;
	private static String modifiedBy;
	
	public RecipeDTO(int recipeId, String userName, String recipeTitle, String recipeDescription, int cookingTime, int cost, float rating, String categoryName, int version, String modifiedBy, IngredientList ingredientList, CommentList commentList) {
		this.recipeId = recipeId;
		this.userName = userName;
		this.recipeTitle = recipeTitle;
		this.recipeDescription = recipeDescription;
		this.cookingTime = cookingTime;
		this.cost = cost;
		this.rating = rating;
		this.categoryName = categoryName;
		this.ingredientList = ingredientList;
		this.commentList = commentList;
		this.version = version;
		this.modifiedBy = modifiedBy;
	}
	
	public RecipeDTO() {
		
	}

	public static int getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}

	public static String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public static String getRecipeTitle() {
		return recipeTitle;
	}

	public void setRecipeTitle(String recipeTitle) {
		this.recipeTitle = recipeTitle;
	}

	public static String getRecipeDescription() {
		return recipeDescription;
	}

	public void setRecipeDescription(String recipeDescription) {
		this.recipeDescription = recipeDescription;
	}

	public static int getCookingTime() {
		return cookingTime;
	}

	public void setCookingTime(int cookingTime) {
		this.cookingTime = cookingTime;
	}

	public static int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public static float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public static String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public static IngredientList getIngredientList() {
		return ingredientList;
	}

	public void setIngredientList(IngredientList ingredientList) {
		this.ingredientList = ingredientList;
	}

	public static CommentList getCommentList() {
		return commentList;
	}

	public void setCommentList(CommentList commentList) {
		this.commentList = commentList;
	}

	public static int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public static String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	public static String serialize() { 
		RecipeDTO rdto = new RecipeDTO(getRecipeId(), getUserName(), getRecipeTitle(), 
				getRecipeDescription(), getCookingTime(), getCost(), getRating(),
				getCategoryName(), getVersion(), getModifiedBy(), 
				getIngredientList(), getCommentList());
		
		Gson gson = new Gson();
		return gson.toJson(rdto); 
	}
	
	public static RecipeDTO deserialize(String recipeStr) { 
		Gson gson = new Gson();
		return gson.fromJson(recipeStr, RecipeDTO.class); 
	}
}
