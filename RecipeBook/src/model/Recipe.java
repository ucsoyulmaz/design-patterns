package model;

import datasoruce.lazyload.CommentList;
import datasoruce.lazyload.IngredientList;

public class Recipe {
	
	private int recipeId;
	private String userName;
	private String recipeTitle;
	private String recipeDescription;
	private int cookingTime;
	private int cost;
	private float rating;
	private String categoryName;
	private IngredientList ingredientList;
	private CommentList commentList;
	private int version;
	private String modifiedBy;

	public Recipe(int recipeId) {
		this.recipeId = recipeId;
	}
	
	public Recipe(int recipeId, String userName, String recipeTitle, String recipeDescription, int cookingTime, int cost, float rating, String categoryName, int version, String modifiedBy, IngredientList ingredientList, CommentList commentList) {
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
	
	
	public Recipe(int recipeId, String userName, String recipeTitle, String recipeDescription, int cookingTime, int cost, float rating, String categoryName, int version, String modifiedBy) {
		this.recipeId = recipeId;
		this.userName = userName;
		this.recipeTitle = recipeTitle;
		this.recipeDescription = recipeDescription;
		this.cookingTime = cookingTime;
		this.cost = cost;
		this.rating = rating;
		this.categoryName = categoryName;
		this.version = version;
		this.modifiedBy = modifiedBy;
	}

	public int getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRecipeTitle() {
		return recipeTitle;
	}

	public void setRecipeTitle(String recipeTitle) {
		this.recipeTitle = recipeTitle;
	}

	public String getRecipeDescription() {
		return recipeDescription;
	}

	public void setRecipeDescription(String recipeDescription) {
		this.recipeDescription = recipeDescription;
	}

	public int getCookingTime() {
		return cookingTime;
	}

	public void setCookingTime(int cookingTime) {
		this.cookingTime = cookingTime;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public IngredientList getIngredientList() { 
		return ingredientList;
	}

	public void setIngredientList(IngredientList ingredientList) {
		this.ingredientList = ingredientList;
	}

	public CommentList getCommentList() {
		return commentList;
	}

	public void setCommentList(CommentList commentList) {
		this.commentList = commentList;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
}
