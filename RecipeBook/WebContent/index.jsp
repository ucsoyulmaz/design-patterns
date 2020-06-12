<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="config.session.AppSession"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Recipebook</title>
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
	<h3>Hi <%=userName %>, Login successful.</h3>
		<div class="menu">
			<ul>
				<form action="MyRecipesButton" >
					<button type="submit" class=""> My Recipes</button>
				</form>
				
				<form action="TimelineButton" >
					<button type="submit" class=""> Timeline</button>
				</form>
				
				<form action="RecipeCreationButton" >
					<button type="submit" class=""> Create a Recipe</button>
				</form>
			</ul>
		
		</div>
		<div>
			<form action="Logout" method="post">
				<input type="submit" value="Logout" >
			</form>
		</div>
	</body>
</html>