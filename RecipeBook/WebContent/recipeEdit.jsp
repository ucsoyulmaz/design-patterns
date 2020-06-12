<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="config.session.AppSession"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create Recipe</title>
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
</head>
<body>
<form action="EditRecipeChooseIngredientsButton" method="post">
  <div class="container">
  <div>
	  		<a href="index.jsp" class="button">Home</a>
	  </div>
    <h1>Edit Recipe</h1>
    <p>Please fill in this form to edit your recipe.</p>
    <hr>
	<div>
		<div>
			<input type="hidden" name="rating" value="${recipe.getRating()}">
			<input type="hidden" name="recipeOwner" value="${recipeOwner}">
			<input type="hidden" name="version" value="${recipe.getVersion()}">
			<input type="hidden" name="modifiedBy" value="${recipe.getModifiedBy()}">
			<input type="hidden" name="recipeId" value="${recipe.getRecipeId()}">
			
  		  	<label for="title"><b>Title</b></label>
    		<div>
				<input type="text" name="recipeTitle" value="${recipe.getRecipeTitle()}" required cols="100%" rows="5">
					${recipe.getRecipeTitle()}
				</input>	
			</div>
		</div>
		<hr>
		<div>
    		<label for="description" rows="6" cols="50"><b>Desc</b></label>
    		<div>
				<input type="text" name="recipeDescription" value="${recipe.getRecipeDescription()}" cols="100%" rows="5">
					
				</input>	
			</div>
		</div>
		<hr>
		<div>
		    <label for="estimated cooking time"><b>Est Cooking time in min</b></label>
    		<input type="number" value="${recipe.getCookingTime()}" name="cookingTime" required>
    	</div>
     	<hr>     
		<div>
    		<label for="cost"><b>Cost in AUD</b></label>
		    <input type="number" value="${recipe.getCost()}" name="cost" required>
		</div>
		<hr>
		<div>
    		<label for="category"><b>Category</b></label>
    		<select name="categoryName">
			    <c:forEach items="${allCategories}" var="item">
			    	<option value="${item.getCategoryName()}">${item.getCategoryName()}</option>
				</c:forEach>
			</select>
		</div>
		<hr>
    
	    <button type="submit" class=""> Next</button>
  </div>
  
</form>
</body>
</html>