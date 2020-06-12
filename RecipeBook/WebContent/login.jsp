<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login Page</title>
</head>
<body>
<form action="Login" method="post" style="border:1px solid #ccc">
  <div class="">
    <h1>Login</h1>
    <hr>

    <label for="userName"><b>Username</b></label>
    <input type="text" name="userName" placeholder="Enter Username" required>

    <label for="passWord"><b>Password</b></label>
    <input type="password" name="passWord" placeholder="Enter Password" required>
	
	<div>
		${error}
	</div>
    <div>
      <button type="submit" class="signupbtn">Login</button>
    </div>
  </div>
</form>
<form action="signup.jsp">
	<button type="submit" class="signupbtn">Sign Up</button>
</form>
</body>
</html>