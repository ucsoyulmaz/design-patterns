package config.session;

import javax.servlet.http.HttpSession;

import controller.bridge.UserStrategy;
import model.User;


//This class was used before the security integration. It is still here just in case if there is a problem with shiro.
// IT IS NOT ACTIVELY BEING USED NOW

public class Session {
	private static final String USER_ATTRIBUTE_NAME = "user";
	private HttpSession httpSession;
	
	public Session(HttpSession httpSession) {
		this.httpSession = httpSession;
	}
	
	public static Session refreshSession(HttpSession httpSession) {
		if(httpSession.getAttribute(USER_ATTRIBUTE_NAME) == null) {
			User userTemp = new User("");
			UserStrategy us = new UserStrategy(userTemp);
			User user = us.readUser();
			httpSession.setAttribute(USER_ATTRIBUTE_NAME, user);
			httpSession.setMaxInactiveInterval(24 * 60 * 60);
		}
		return new Session(httpSession);
	}
	
	public User getUser() {
		return (User) httpSession.getAttribute(USER_ATTRIBUTE_NAME);
	}
}
