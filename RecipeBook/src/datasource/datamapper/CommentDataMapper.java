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
import datasource.identity.CommentRegistry;
import datasource.identity.RecipeRegistry;

public class CommentDataMapper {
	
	private int recipeId;
	
	public CommentDataMapper() {
		
	}
	
	public CommentDataMapper(int recipeId) {
		this.recipeId =recipeId;
	}
	
	public int insert(Comment comment) throws SQLException {
		
		DBConnection DB = new DBConnection();
		
		//timestamp and rating 0 for now.
		
		String sql = "INSERT INTO comments (user_name, recipe_id, comment_content) VALUES "
					+ "(?,?,?) RETURNING comment_id;";
		
		PreparedStatement preparedStatement = DB.prepare(sql);
		preparedStatement.setString(1,comment.getUserName());
		preparedStatement.setInt(2,comment.getRecipeId());
		preparedStatement.setString(3,comment.getCommentContent());
		
		preparedStatement.executeQuery();
		DB.getDbConnection().commit();
		
		ResultSet lastInsertedComment = preparedStatement.getResultSet();
		lastInsertedComment.next();
		int lastInsertedCommentId = lastInsertedComment.getInt(1);
		
		return lastInsertedCommentId;
	}
	
	public int update(Comment comment) throws SQLException {
		String sql = "UPDATE comments SET " 
				+ "recipe_id = ? , "
				+ "comment_content = ? "
				+ "WHERE comment_id = ? RETURNING comment_id;";
	
		
		DBConnection DB = new DBConnection();
		PreparedStatement preparedStatement = DB.prepare(sql);
		
		preparedStatement.setInt(1,comment.getRecipeId());
		preparedStatement.setString(2,comment.getCommentContent());
		preparedStatement.setInt(3,comment.getCommentId());
		
		preparedStatement.executeQuery();
		DB.getDbConnection().commit();
		
		ResultSet lastUpdatedComment = preparedStatement.getResultSet();
		lastUpdatedComment.next();
		int lastUdpatedCommentId = lastUpdatedComment.getInt(1);
		
		return lastUdpatedCommentId;
	}
	
	public void delete(Comment comment) {
		int commentId = comment.getCommentId();
		DBConnection DB = new DBConnection();
		
		String sql = "DELETE FROM comments WHERE comment_id = ?";
		try {
			PreparedStatement preparedStatement = DB.prepare(sql);
			preparedStatement.setInt(1,commentId);
			
			int rset = preparedStatement.executeUpdate();
			DB.getDbConnection().commit();
			
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	
		}
	}
	
	public Comment read(Comment comment) {
		int commentId = comment.getCommentId();
		
		//getRecipe function for determining whether the given id will return an object from identity map or not
		Comment result = CommentRegistry.getComment(commentId);
		
		//if identity map can give an object, just return the object
		//if(result != null) {
	        
			//return result;
		//}
		
		//if identity map can not return an object, connect to db and get data
		DBConnection DB = new DBConnection();
		String sql = "SELECT * FROM comments WHERE comment_id = ?";
		
		try {
			PreparedStatement preparedStatement = DB.prepare(sql);
			preparedStatement.setInt(1,commentId);
			
			ResultSet rset = preparedStatement.executeQuery();
			DB.getDbConnection().commit();
			
			int rowCount = 0;
			
			String userName = "";
			int recipeId = -1;
			String commentContent = "";
			
	        while(rset.next()) {   
	           userName = rset.getString("user_name");
	           recipeId = rset.getInt("recipe_id");
	           commentContent = rset.getString("comment_content");
	           ++rowCount;
	        }
	        
	        //the recipe object that will be returned
	        Comment currentCommentInDB;
	        
	        //TODO userId is the null indicator here but it should be changed for a better practice
	        //TODO recipeId = -1 is a null indicator, it should be changed.
	        if(!userName.equals("")) {
	     
	        	currentCommentInDB = new Comment(commentId, recipeId, userName, commentContent);
	        }
	        else {
	        	currentCommentInDB = new Comment(-1);
	        }
	        
	        //Add the retrieved recipe into identity map for the next requests.
	       // CommentRegistry.addComment(currentCommentInDB);
	        return currentCommentInDB;
	        
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			//TODO recipeId = -1 is a null indicator, it should be changed.
			Comment currentCommentInDB = new Comment(-1);
			return currentCommentInDB;
		}

	}
	
	public List<Comment> readAll(int recipeId) {
		
		List<Comment> commentsList = new ArrayList<Comment>();
		
		//if identity map can not return an object, connect to db and get data
		DBConnection DB = new DBConnection();
		String sql = "SELECT * FROM comments WHERE recipe_id = ?";

		try {
			PreparedStatement preparedStatement = DB.prepare(sql);
			preparedStatement.setInt(1,recipeId);
			
			ResultSet rset = preparedStatement.executeQuery();
			DB.getDbConnection().commit();
			
			
	        while(rset.next()) {   
	           String userName = rset.getString("user_name"); 
	           int commentId = rset.getInt("comment_id");
	           String commentContent = rset.getString("comment_content");
	           Comment currentComment = new Comment(commentId, recipeId, userName, commentContent);
	           
	           Comment result = CommentRegistry.getComment(commentId);
		   		
		   		//if identity map can give an object, just return the object
		   		if(result == null) {
		   			CommentRegistry.addComment(currentComment);
		   		}
	           
	           commentsList.add(currentComment);
	        }
	        
	       
	        //Add the retrieved recipe into identity map for the next requests
	        return commentsList;
	        
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			//TODO recipeId = -1 is a null indicator, it should be changed.
			return commentsList;
		}

	}

	public int getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}
}
