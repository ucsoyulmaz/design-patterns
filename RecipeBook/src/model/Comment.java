package model;

import datasoruce.lazyload.CommentList;

public class Comment {

	private int commentId;
	private int recipeId;
	private String userName;
	private String commentContent;
	
	public Comment() {
		
	}
	
	public Comment(int commentId) {
		this.commentId = commentId;
	}
	
	public Comment(int commentId, int recipeId, String userName, String commentContent) {
		this.commentId = commentId;
		this.recipeId = recipeId;
		this.userName = userName;
		this.commentContent = commentContent;
	}
	

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
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

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	
}
