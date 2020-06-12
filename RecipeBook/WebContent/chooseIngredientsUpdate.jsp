<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="config.session.AppSession"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ingredients Page</title>
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
<form action="IngredientUpdateButton" method="post" style="border:1px solid #ccc">
  <div class="">
  	<div>
	  		<a href="index.jsp" class="button">Home</a>
	  </div>
    <h1>Ingredient Selection</h1>
    <p>Please select recipe ingredients from scratch.</p>
    <hr>
  </div>
  <div>
  	Recipe ID : 
  	<input type="number" name="recipeIdUpdated" value="${recipeIdUpdated}" readonly> 
  	<input type="hidden" name="recipeOwner" value="${recipeOwner}">	
  </div>
  
  
  <div> 
	  <c:forEach items="${allIngredients}" var="item">
	  	<input name="ingredients" type="checkbox" value="${item.getIngredientId()}">${item.getIngredientName()}</input><br>
	  </c:forEach> 
  </div>
  <input type="submit" value="Submit">
</form>
</body>
</html>