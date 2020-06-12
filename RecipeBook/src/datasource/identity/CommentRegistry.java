package datasource.identity;

import java.util.HashMap;
import java.util.Map;
import model.Comment;


public class CommentRegistry {
	
	private static Map<Integer, Comment> CommentMap = new HashMap<>();
	
	//Check whether the given recipeId addresses an object in identity map or not
	public static Comment getComment(int id) {
		
		return CommentMap.get(id);
	}
	
	//Adds new Recipe objects into the identity map
	public static void addComment(Comment comment) {
		
		CommentRegistry.CommentMap.put(comment.getRecipeId(), comment);
	}
}
