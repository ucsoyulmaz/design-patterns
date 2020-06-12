package datasource.datamapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import config.database.DBConnection;
import datasource.identity.CommentRegistry;
import model.Comment;
import model.User;

public class UserDataMapper {

	public UserDataMapper() {
		
	}
	
	public String insert(User user) throws SQLException {
		
		DBConnection DB = new DBConnection();
		
		//timestamp and rating 0 for now.
		
		String sql = "INSERT INTO users (user_name, password, user_type) VALUES "
					+ "(?,?,?) RETURNING user_name;";
		
		PreparedStatement preparedStatement = DB.prepare(sql);
		
		preparedStatement.setString(1,user.getUserName());
		preparedStatement.setString(2,user.getPassword());
		preparedStatement.setString(3,user.getUserType());
		
		preparedStatement.executeQuery();
		DB.getDbConnection().commit();
		
		ResultSet lastInsertedUser = preparedStatement.getResultSet();
		lastInsertedUser.next();
		String lastInsertedUserName = lastInsertedUser.getString(1);
		
		return lastInsertedUserName;
	}
	
	public User read(User user){
		String userName = user.getUserName();
		
		DBConnection DB = new DBConnection();
		String sql = "SELECT * FROM users WHERE user_name = ?";
		
		try {
			PreparedStatement preparedStatement = DB.prepare(sql);
			preparedStatement.setString(1,user.getUserName());
			ResultSet rset = preparedStatement.executeQuery();
			DB.getDbConnection().commit();
			
			
			int userId = -1;
			String password = "";
			String userType = "";
			
	        while(rset.next()) {   
	           password = rset.getString("password");
	           userId = rset.getInt("user_id");
	           userType = rset.getString("user_type");
	        }
	        
	        User currentUserInDB = new User();
	        
	        if(!userType.equals("")) {
	     
	        	currentUserInDB = new User(userId, userName, password, userType);
	        }
	        else {
	        	currentUserInDB = new User("");
	        }
	    
	        
	        return currentUserInDB;
	        
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			//TODO recipeId = -1 is a null indicator, it should be changed.
			User currentUserInDB = new User("");
			return currentUserInDB;
		}

	}
	
	public String update(User user) throws SQLException{
		String sql = "UPDATE users SET " 
				+ "user_name = ?, "
				+ "password = ?, "
				+ "user_type = ? "
				+ "WHERE user_name = ? RETURNING user_name;";
	
		
		DBConnection DB = new DBConnection();
		PreparedStatement preparedStatement = DB.prepare(sql);
		
		preparedStatement.setString(1,user.getUserName());
		preparedStatement.setString(2,user.getPassword());
		preparedStatement.setString(3,user.getUserType());
		preparedStatement.setString(4,user.getUserName());
		
		preparedStatement.executeQuery();
		DB.getDbConnection().commit();
		
		ResultSet lastUpdatedUser = preparedStatement.getResultSet();
		lastUpdatedUser.next();
		String lastUpdatedUserId = lastUpdatedUser.getString(1);
		
		return lastUpdatedUserId;
	}
	
	public void delete(User user) throws SQLException{
		String userName = user.getUserName();
		DBConnection DB = new DBConnection();
		
		String sql = "DELETE FROM users WHERE user_name = ?";
		try {
			PreparedStatement preparedStatement = DB.prepare(sql);
			
			preparedStatement.setString(1,user.getUserName());
			
			int rset = preparedStatement.executeUpdate();
			DB.getDbConnection().commit();
			
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	
		}
	}
}
