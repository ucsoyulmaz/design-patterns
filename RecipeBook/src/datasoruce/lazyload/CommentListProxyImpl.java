package datasoruce.lazyload;

import java.util.List;

import model.Comment;

public class CommentListProxyImpl implements CommentList{
	
	private static CommentList commentList;
	private static int recipeId;
	
	public CommentListProxyImpl(int recipeId) {
		this.recipeId = recipeId;
		commentList = null;		
	}
	
	public List<Comment> getComments(){
		if (commentList == null) {
			commentList = new CommentListImpl(getRecipeId());
		}
		
		return commentList.getComments();
	}

	public static int getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}
	
}