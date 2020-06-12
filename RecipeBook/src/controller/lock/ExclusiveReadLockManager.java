package controller.lock;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import config.database.DBConnection;
import model.Category;

public class ExclusiveReadLockManager {

	public ExclusiveReadLockManager() {
		
	}
	
	public int acquireLock(int commentId, String session_userName) throws SQLException {
		DBConnection DB = new DBConnection();
		if(!hasLock(commentId, session_userName)) {
			String sql = "INSERT INTO lock (comment_id, user_name) VALUES (" + commentId + ", '" + session_userName + "') RETURNING comment_id;";
			PreparedStatement preparedStatement = DB.prepare(sql);
			preparedStatement.executeQuery();
			DB.getDbConnection().commit();
			
			ResultSet lastInsertedComment = preparedStatement.getResultSet();
			lastInsertedComment.next();
			int lastInsertedCommentId = lastInsertedComment.getInt(1);
			return lastInsertedCommentId;
		}
		else {
			return -1;
		}
	}
	
	public void releaseLock(int commentId, String session_userName) {
		DBConnection DB = new DBConnection();
		
		String sql = "DELETE FROM lock WHERE comment_id = '" + commentId + "'";
		try {
			PreparedStatement preparedStatement = DB.prepare(sql);
			int rset = preparedStatement.executeUpdate();
			DB.getDbConnection().commit();
			
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	
		}
	}
	
	public boolean hasLock(int commentId, String session_userName) {
		DBConnection DB = new DBConnection();
		String sql = "SELECT * FROM lock WHERE comment_id= " + commentId;
		
		int comment_id = -1;
		
		try {
			PreparedStatement preparedStatement = DB.prepare(sql);
			ResultSet rset = preparedStatement.executeQuery();
			DB.getDbConnection().commit();
		

			while (rset.next()) {
				comment_id = rset.getInt("comment_id");
			}

			if(comment_id == commentId) {
				return true;
			}
			else {
				return false;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return false;
		}
	}
}
