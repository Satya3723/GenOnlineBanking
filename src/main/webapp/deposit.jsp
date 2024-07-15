<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Deposit</title>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" />
	<style>
		body {
			font-family: Arial, sans-serif;
			background-color: #f0f0f0;
		}
		
		.container {
			width: 50%;
			margin: 40px auto;
			padding: 20px;
			background-color: #fff;
			border: 1px solid #ddd;
			box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
		}
		
		.header {
			background-color: #333;
			color: #fff;
			padding: 10px;
			text-align: center;
		}
		
		.form-group {
			margin-bottom: 20px;
		}
		
		.label {
			display: block;
			margin-bottom: 10px;
		}
		
		.input-field {
			width: 100%;
			padding: 10px;
			margin-bottom: 20px;
			border: 1px solid #ccc;
		}
		
		.button {
			background-color: #4CAF50;
			color: #fff;
			padding: 10px 20px;
			border: none;
			border-radius: 5px;
			cursor: pointer;
		}
		
		.button:hover {
			background-color: #3e8e41;
		}
	</style>
</head>
<body>
	<div class="container">
		<div class="header">
			<h2>Deposit</h2>
		</div>
		<form action="DepositServlet" method="post">
			<div class="form-group">
				<label class="label" for="amount">Amount:</label>
				<input type="number" id="amount" name="amount" class="input-field" required>
			</div>
			<div class="form-group">
				<label class="label" for="accountNumber">Account Number:</label>
				<input type="text" id="accountNumber" name="accountNumber" class="input-field" required>
			</div>
			<button type="submit" class="button">Deposit</button>
		</form>
	</div>
</body>
</html>