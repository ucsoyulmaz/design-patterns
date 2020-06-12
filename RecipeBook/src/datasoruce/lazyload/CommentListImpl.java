package datasoruce.lazyload;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.database.DBConnection;
import datasource.datamapper.CommentDataMapper;
import datasource.identity.RecipeRegistry;
import model.Comment;
import model.Recipe;

public class CommentListImpl implements CommentList{
	
	private static int recipeId;
	
	public CommentListImpl(int recipeId) {
		this.recipeId = recipeId;
	}
	
	public List<Comment> getComments(){
		return getCommentList();
	}
	
	public static List<Comment> getCommentList(){
		List<Comment> comments = new ArrayList<Comment>();
		
		int recipeId = getRecipeId();
		
		CommentDataMapper cdm = new CommentDataMapper();
		comments = cdm.readAll(getRecipeId());
		
		return comments;
	}

	public static int getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}
}
