package controller.domainmodel;
import java.sql.*;
import java.util.List;

import config.session.AppSession;
import config.session.Session;
import controller.lock.LockingMapper;
import model.Comment;
import model.Recipe;
import datasource.datamapper.CommentDataMapper;
import datasource.datamapper.RecipeDataMapper;

public class CommentStrategy implements PostStrategy{
	
private Comment comment;
private Session wrappedSession;
	
	public CommentStrategy(Comment comment) {
		this.comment = comment;
	}
	
	public CommentStrategy() {
		
	}
	
	//This function creates new comment object for a particular recipe
	
	public int addPost(){
		
		CommentDataMapper cdm = new CommentDataMapper();
		
		try {
			int commentId = cdm.insert(getComment());
			return commentId;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		
	}
	
	public int editPost() {
		
		CommentDataMapper cdm = new CommentDataMapper();
		
		try {
			 int update = cdm.update(getComment());
			 return update;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}
	
	public void deletePost() {
		CommentDataMapper cdm = new CommentDataMapper();
		cdm.delete(getComment());
	}
	
	public Comment readPost() {
		CommentDataMapper cdm = new CommentDataMapper();
		LockingMapper lm = new LockingMapper(cdm, AppSession.getUser());
		
		Comment currentComment;
		try {
			currentComment = lm.read(getComment().getCommentId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			currentComment = new Comment(-1, -1, "NOT AVAILABLE, Under use", "NOT AVAILABLE, Under use");
		}
		return currentComment;
	}
	
	public List<Comment> readAllCommentsForARecipe(int recipeId) {
		CommentDataMapper cdm = new CommentDataMapper();
		List<Comment> commentList = cdm.readAll(recipeId);
		
		for(int i = 0; i < commentList.size(); i++) {
			System.out.println(commentList.get(i).getCommentContent());
		}
		return commentList;
	}
	
	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}
}
