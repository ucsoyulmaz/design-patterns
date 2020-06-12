package config.security;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;

import config.session.AppSession;
import controller.bridge.UserStrategy;
import model.User;

//The config class of Shiro which communicates with shiro.ini under WebContent
public class AppRealm extends JdbcRealm{
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException{
		UsernamePasswordToken userPassToken = (UsernamePasswordToken) token;
		final String username = userPassToken.getUsername();
		
		User userTemp = new User(username);
		UserStrategy us = new UserStrategy(userTemp);
		User user = us.readUser();
	
		if(user == null) {
			return null;
		}
		return new SimpleAuthenticationInfo(username, user.getPassword(), getName());
	}
	
	@Override
	protected AuthorizationInfo getAuthorizationInfo(PrincipalCollection principals) {
		Set<String> roles = new HashSet<>();
		if(principals.isEmpty()) {
			System.out.println("Given principals to authorize are empty.");
			return null;
		}
		
		String userName = (String) principals.getPrimaryPrincipal();
		User userTemp = new User(userName);
		UserStrategy us = new UserStrategy(userTemp);
		final User user = us.readUser();
		
		if(user == null) {
			System.out.println("The user could not be found.");
			return null;
		}
		
		if(user.getUserType().equals(AppSession.getAdminUserRole())) {
			roles.add(AppSession.getAdminUserRole());
		}
		else if(user.getUserType().equals(AppSession.getNormalUserRole())) {
			roles.add(AppSession.getNormalUserRole());
		}
		
		return new SimpleAuthorizationInfo(roles);
	}
}
