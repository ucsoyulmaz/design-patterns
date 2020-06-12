<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="config.session.AppSession"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
	${error_message}
</h1>
<div>
	  		<a href="index.jsp" class="button">Home</a>
	  </div>
</head>
<body>

</body>
</html>