<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="config.session.AppSession"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MY RECIPES</title>
<style>
table, th, td {
  border: 1px solid black;
  border-collapse: collapse;
}
th, td {
  padding: 15px;
  text-align: left;
}
table#t01 {
  width: 100%;    
  background-color: #f1f1c1;
}
</style>

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
<div>
<div>
	  		<a href="index.jsp" class="button">Home</a>
	  </div>
<table id="XX" style="width:100%">
  <tr>
  	<th> ID </th>
    <th>Owner</th>
    <th>Title</th> 
    <th>Cooking Time</th>
    <th>Action</th>
  </tr>  
  <c:forEach items="${allRecipes}" var="item">
  		<form action="ViewOneOfMyRecipeButton">
  			<tr>
  				<th><input type="number" name="recipeId" value="${item.getRecipeId()}" readonly></th> 
			    <th><%=userName %></th>
			    <th>${item.getRecipeTitle()}</th>
			    <th>${item.getCookingTime()} Minutes</th>
			    <th><button type="submit" class=""> View </button></th>
		  	</tr>
  		</form>
   </c:forEach> 
</table>
</div>
</body>
</html>
