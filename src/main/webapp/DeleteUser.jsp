<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<title>Delete Account</title>
	<style>
		body {
			font-family: Arial, sans-serif;
			background-color: #f0f0f0;
		}
		
		h1 {
			color: #333;
			text-align: center;
			margin-bottom: 20px;
		}
		
		form {
			width: 50%;
			margin: 40px auto;
			padding: 20px;
			border: 1px solid #ccc;
			border-radius: 10px;
			box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
		}
		
		label {
			display: block;
			margin-bottom: 10px;
		}
		
		input[type="text"] {
			width: 100%;
			padding: 10px;
			margin-bottom: 20px;
			border: 1px solid #ccc;
			border-radius: 5px;
		}
		
		input[type="submit"] {
			background-color: #4CAF50;
			color: #fff;
			padding: 10px 20px;
			border: none;
			border-radius: 5px;
			cursor: pointer;
		}
		
		input[type="submit"]:hover {
			background-color: #3e8e41;
		}
	</style>
</head>
<body>
	<h1>Delete Account</h1>
	
	<form action="DeleteUserDetailsServlet" method="post">
		<label for="accountNumber">Account Number:</label>
		<input type="text" id="accountNumber" name="accountNumber" required><br><br>
		<input type="submit" value="Search">
	</form>
</body>
</html>