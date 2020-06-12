package model;

public class User {

	private int userId;
	private String userName;
	private String password;
	private String userType;
	
	public User() {
		
	}
	
	public User(String userName) {
		this.userName = userName;
	}
	
	public User(int userId, String userName, String password, String userType) {
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.userType = userType;
	}
	
	public User(String userName, String password, String userType) {
		this.userName = userName;
		this.password = password;
		this.userType = userType;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
}
