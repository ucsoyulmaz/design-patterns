<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="config.session.AppSession"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Get Recipe Details</title>
<style>
body {font-family: Arial, Helvetica, sans-serif;}
* {box-sizing: border-box;}

/* Button used to open the contact form - fixed at the bottom of the page */
.open-button {
  background-color: #555;
  color: white;
  padding: 16px 20px;
  border: none;
  cursor: pointer;
  opacity: 0.8;
  position: fixed;
  bottom: 23px;
  right: 28px;
  width: 280px;
}

/* The popup form - hidden by default */
.form-popup {
  display: none;
  position: fixed;
  bottom: 0;
  right: 15px;
  border: 3px solid #f1f1f1;
  z-index: 9;
}

/* Add styles to the form container */
.form-container {
  max-width: 300px;
  padding: 10px;
  background-color: white;
}

/* Full-width input fields */
.form-container input[type=text], .form-container input[type=password] {
  width: 100%;
  padding: 15px;
  margin: 5px 0 22px 0;
  border: none;
  background: #f1f1f1;
}

/* Add some hover effects to buttons */
.form-container .btn:hover, .open-button:hover {
  opacity: 1;
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
	  		<a href="index.jsp" class="button">Home</a>
	  </div>
<div>
	<c:if test="${not empty recipe}">
	 	<input type="hidden" name="recipeId" value="${recipe.getRecipeId()}" readonly>
	    <h3>Title : ${recipe.getRecipeTitle()}</h3>
	    <h3>Owner : <%=userName %></h3>
	    <h3>Cooking Time : ${recipe.getCookingTime()}</h3>
	    <h3>Rating : ${recipe.getRating()}</h3>
	    <h3>Cost : ${recipe.getCost()}</h3>
	    <h3>Category : ${recipe.getCategoryName()}</h3>
	    <h3>Description : ${recipe.getRecipeDescription()}</h3>
	</c:if>
</div>
<hr>
<hr>
 <hr>
<div>
	Ingredients : 
	<c:if test="${not empty recipeIngredients}">
	 	<c:forEach items="${recipeIngredients}" var="item">
	 		${item.getIngredientName()} &nbsp; 
   	    </c:forEach> 
	</c:if>
</div>
<hr>
<hr>
 <hr>
<div>
	<form action="EditMyRecipeButton">
		<input type="hidden" name="recipeId" value="${recipe.getRecipeId()}">
		<input type="hidden" name="recipeOwner" value="${recipe.getUserName()}">
		<input type="hidden" name="recipeTitle" value="${recipe.getRecipeTitle()}">
		<input type="hidden" name="cookingTime" value="${recipe.getCookingTime()}">
		<input type="hidden" name="rating" value="${recipe.getRating()}">
		<input type="hidden" name="cost" value="${recipe.getCost()}">
		<input type="hidden" name="recipeDescription" value="${recipe.getRecipeDescription()}">
		<input type="hidden" name="categoryName" value="${recipe.getCategoryName()}">
		<input type="hidden" name="version" value="${recipe.getVersion()}">
		<input type="hidden" name="modifiedBy" value="${recipe.getModifiedBy()}">
		<button type="submit" class="signupbtn" > Edit the Recipe</button>
	</form>
</div>
<div>
	<form action="DeleteMyRecipeButton">
		<input type="hidden" name="recipeId" value="${recipe.getRecipeId()}">
		<button type="submit" class="signupbtn" > Delete the Recipe</button>
	</form>
</div>
<div>
	<form method="post" action="WriteCommentButton">
		<input type="hidden" name="recipeId" value="${recipe.getRecipeId()}">
		<input type="hidden" name="recipeOwner" value="${recipe.getUserName()}">
		<button type="submit" class="signupbtn" > Write a Comment</button>
	</form>
</div>
<hr>
<hr>
 <hr>
<div>
	<table id="comment" style="width:100%">
		<c:if test="${not empty recipeComments}">
		 	<c:forEach items="${recipeComments}" var="item">
		 	
		 		<tr align="left">
				    <th>Comment Owner</th>
				    	<td>
				    		${item.getUserName()}
						</td>
				  </tr>
				  <tr align="left">  
				    <th>Comment </th> 
				    	<td>
				    		${item.getCommentContent()}
						</td> 
				  </tr> 
				  
				  <tr align="left">
				  	<th> Action <br><br><hr> </th>
				  		<td>
				  			<form method="get" action="EditCommentButton">
				  				<input type="hidden" name="recipeId" value="${recipe.getRecipeId()}">
				  				<input type="hidden" name="recipeOwner" value="${recipe.getUserName()}">
						  		<input type="hidden" name="commentId" value="${item.getCommentId()}">
						  		<input type="hidden" name="userName" value="${item.getUserName()}">
						  		<button type="submit" class="signupbtn" > Edit Comment </button>
						  	</form>
						  	<form method="get" action="DeleteCommentButton">
						  		<input type="hidden" name="recipeId" value="${recipe.getRecipeId()}">
						  		<input type="hidden" name="commentId" value="${item.getCommentId()}">
						  		<input type="hidden" name="recipeOwner" value="${recipe.getUserName()}">
						  		<button type="submit" class="signupbtn" > Delete Comment</button>
						  	</form>
				  			<hr>
				  		</td>	
				  </tr>
 
	   	    </c:forEach> 
		</c:if>
	</table>
</div>


</body>
</html>