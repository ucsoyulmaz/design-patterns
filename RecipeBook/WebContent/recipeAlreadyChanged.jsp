<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="config.session.AppSession"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>LOCK PROBLEM</title>
<%
	String user_type = "";
	String userName = "";
	//allow access only if session exists

		
	if(!AppSession.isAuthenticated() || AppSession.getUser() == null){
		response.sendRedirect("login.jsp");
	}
	else{
		userName = AppSession.getUser().getUserName();
		user_type = AppSession.getUser().getUserType();
	}
	
	%>
<h1>
This recipe has been changed by others, sorry! Please click on HOME button.
</h1>
<div>
	  		<a href="index.jsp" class="button">Home</a>
	  </div>
</head>
<body>

</body>
</html>