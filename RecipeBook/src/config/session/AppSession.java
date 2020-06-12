package config.session;

import org.apache.shiro.SecurityUtils;
import model.User;

//This class is responsible for handling the Session Managements

public class AppSession {
	
	// Attributes (roles)
	private static final String USER_ATTRIBUTE_NAME = "user";
	private static final String NORMAL_USER_ROLE = "Normal";
	private static final String ADMIN_USER_ROLE = "Admin";
	
	//This function checks whether the given user has a role or not
	public static boolean hasRole(String role) {
		return SecurityUtils.getSubject().hasRole(role);
	}
	
	//This function checks whether the given user is authenticated or not
	public static boolean isAuthenticated() {
		return SecurityUtils.getSubject().isAuthenticated();
	}
	
	//This function initiates the Session for a particular user
	
	public static void init(User user) {
		SecurityUtils.getSubject().getSession().setAttribute(USER_ATTRIBUTE_NAME, user);
	}
	
	//This function is for logout action to terminate the session of the given user
	public static void invalidate(User user) {
		SecurityUtils.getSubject().getSession().removeAttribute(USER_ATTRIBUTE_NAME);
	}
	
	//This function is for getting the details of the given User
	public static User getUser() {
		return (User) SecurityUtils.getSubject().getSession().getAttribute(USER_ATTRIBUTE_NAME);
	}

	public static String getUserAttributeName() {
		return USER_ATTRIBUTE_NAME;
	}

	public static String getNormalUserRole() {
		return NORMAL_USER_ROLE;
	}

	public static String getAdminUserRole() {
		return ADMIN_USER_ROLE;
	}
}
