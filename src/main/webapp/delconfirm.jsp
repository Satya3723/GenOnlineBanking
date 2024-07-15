<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<title>Update User Details</title>
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
			background-color: #fff;
			border: 1px solid #ddd;
			box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
		}
		
		label {
			display: block;
			margin-bottom: 10px;
		}
		
		input[type="text"], input[type="email"], textarea {
			width: 100%;
			padding: 10px;
			margin-bottom: 20px;
			border: 1px solid #ccc;
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
		
		.goBack {
			background-color: #007bff;
			color: #fff;
			padding: 10px 20px;
			border: none;
			border-radius: 5px;
			cursor: pointer;
		}
		
		.goBack:hover {
			background-color: #0069d9;
		}
		
		.deleteUser {
			background-color: #FF0000;
			color: #fff;
			padding: 10px 20px;
			border: none;
			border-radius: 5px;
			cursor: pointer;
		}
		
		.deleteUser:hover {
			background-color: #CC0000;
		}
	</style>
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.all.min.js"></script>
</head>
<body>
	<h1>Delete Account</h1>

	<form action="DeleteUserDetailsServlet" method="post">
		<label for="name">Name:</label>
		<input type="text" id="name" name="name" value="${fullname}" required readonly><br><br> 
		<p>Balance is ${initialbalance} ask them, to withdraw first </p>
		<br><p>Proceed to close account here</p>
		<input type="hidden" name="accountNumber" value="${accountNumber}">
    <button type="submit">Close Account</button>
		
		<button class="goBack" onclick="location.href="admindsh.jsp">Go Back</button>
		
	</form>
	
</body>
</html>