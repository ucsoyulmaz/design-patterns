package controller.bridge;

import java.sql.SQLException;

import datasource.datamapper.CommentDataMapper;
import datasource.datamapper.UserDataMapper;
import model.Comment;
import model.User;

//This class (a domain layer member) is responsible for the communication between UserDataMapper and related servlets
public class UserStrategy {

	private User user;
	
	//Constructor
	public UserStrategy() {
		
	}
	//Constructor
	public UserStrategy(User user) {
		this.user = user;
	}
	
	public String addUser(){
		
		UserDataMapper udm = new UserDataMapper();
		
		try {
			String userName = udm.insert(getUser());
			return userName;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		
	}
	
	public String editUser() {
		UserDataMapper udm = new UserDataMapper();
		
		try {
			 String update = udm.update(getUser());
			 return update;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	
	public void deleteUser() {
		
		UserDataMapper udm = new UserDataMapper();
		try {
			udm.delete(getUser());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public User readUser() {
		
		UserDataMapper udm = new UserDataMapper();
		User currentUser = udm.read(getUser());
		return currentUser;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
