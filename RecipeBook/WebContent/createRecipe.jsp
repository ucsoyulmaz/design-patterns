<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="config.session.AppSession"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create Recipe</title>
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
<form action="CreateRecipeChooseIngredientsButton" method="post">
  <div class="container">
	  <div>
	  		<a href="index.jsp" class="button">Home</a>
	  </div>
    <h1>Create Recipe</h1>
    <p>Please fill in this form to create new recipe.</p>
    <hr>
	<div>
		<div>
  		  	<label for="title"><b>Title</b></label>
    		<div>
				<textarea type="text" name="recipeTitle" required cols="100%" rows="5">
					Type your recipe title here
				</textarea>	
			</div>
		</div>
		<hr>
		<div>
    		<label for="description" rows="6" cols="50"><b>Desc</b></label>
    		<div>
				<textarea type="text" name="recipeDescription" cols="100%" rows="5">
					Type your recipe description here
				</textarea>	
			</div>
		</div>
		<hr>
		<div>
		    <label for="estimated cooking time"><b>Est Cooking time in min</b></label>
    		<input type="number" placeholder="Enter Approximate Cooking Time in Minutes" name="cookingTime" required>
    	</div>
     	<hr>     
		<div>
    		<label for="cost"><b>Cost in AUD</b></label>
		    <input type="number" placeholder="Choose the approximate cost in AUD" name="cost" required>
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