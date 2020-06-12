<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="config.session.AppSession"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Write Comment</title>
</head>
<body>
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
<form action="CommentSendButton" method="post">
  <div class="container">
  <div>
	  		<a href="index.jsp" class="button">Home</a>
	  </div>
    <h1>Create Comment</h1>
    <hr>
	<div>
		<hr>
		<div>
			
    		<label for="description" rows="6" cols="50"><b>Comment Content</b></label>
    		<div>
    			<input type="hidden" name="recipeId" value="${recipeId}" readonly>
    			<input type="hidden" name="recipeOwner" value="${recipeOwner}">	
				<textarea type="text" name="commentContent" cols="100%" rows="5">
					Type your comment here
				</textarea>	
			</div>
		</div>
	    <button type="submit" class=""> Post </button>
  </div>
  
</form>
</body>
</html>