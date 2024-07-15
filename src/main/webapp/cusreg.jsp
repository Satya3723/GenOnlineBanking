<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    session.setAttribute("mobileNumber", request.getParameter("mobileno"));
%>

<!DOCTYPE html>
<html>
<head>
	<title>Customer Details Form</title>
	<style>
  body {
    font-family: Arial, sans-serif;
    background-color: #f0f0f0;
  }

 .center {
    margin: 0 auto;
    width: 80%;
    background-color: #fff;
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 10px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  }

  h1 {
    color: #333;
    margin-bottom: 20px;
  }

  table {
    width: 100%;
    border-collapse: collapse;
  }

  th, td {
    padding: 10px;
    border: 1px solid #ddd;
  }

  th {
    background-color: #f0f0f0;
    color: #333;
  }

  td input[type="text"], td input[type="email"], td input[type="number"], td input[type="password"], td select {
    width: 100%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    font-size: 16px;
    height: 40px;
  }

  td button[type="button"] {
    background-color: #4CAF50;
    color: #fff;
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    height: 40px;
  }

  td button[type="button"]:hover {
    background-color: #3e8e41;
  }

  td input[type="submit"] {
    background-color: #4CAF50;
    color: #fff;
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    height: 40px;
  }

  td input[type="submit"]:hover {
    background-color: #3e8e41;
  }

  /* Add some space between rows */
  tr {
    margin-bottom: 10px;
  }
</style>
	<script>
		function generateCustomerId() {
			var customerId = Math.floor(Math.random() * 100000) + 1;
			document.getElementsByName("customerid")[0].value = customerId;
		}
		
		function generateAccountNo() {
			var accountNo = Math.floor(Math.random() * 100000000000) + 1;
			document.getElementsByName("accno")[0].value = accountNo;
		}
		
		function generatePassword() {
			var password = Math.random().toString(36).substr(2, 8);
			document.getElementsByName("password")[0].value = password;
		}
	</script>
</head>
<body>
<center>
	<h1>Customer Details Form</h1>
	<form action="cusdet" method="post">
	<center>
		<table>
			<tr>
				<td>Customer Id:</td>
				<td><input type="text" name="customerid" required/></td>
				<td><button onclick="generateCustomerId()">Generate Id</button></td>
			</tr>
			<tr>
				<td>Full Name:</td>
				<td><input type="text" name="fullname" required/></td>
			</tr>
			<tr>
				<td>Address:</td>
				<td><input type="text" name="address" required/></td>
			</tr>
			<tr>
				<td>Mobile No:</td>
				<td><input type="text" name="mobileno" required/></td>
			</tr>
			<tr>
				<td>Email ID:</td>
				<td><input type="email" name="emailid" required/></td>
			</tr>
			<tr>
				<td>Account Type:</td>
				<td>
					<select name="acctype" required>
						<option value="">Select</option>
						<option value="Savings">Savings</option>
						<option value="Current">Current</option>
						<option value="Fixed Deposit">Fixed Deposit</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>Initial Balance:</td>
				<td><input type="number" name="initialbalance" required/></td>
			</tr>
			<tr>
				<td>ID Proof:</td>
				<td><input type="text" name="idproof" required/></td>
			</tr>
			<tr>
				<td>Account No:</td>
				<td><input type="text" name="accno" required/></td>
				<td><button onclick="generateAccountNo()">Generate Acc Num</button></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type="password" name="password" required/></td>
				<td><button onclick="generatePassword()">Generate Password</button></td>
			</tr>
			<tr>
				<td>Status:</td>
				<td>
					<select name="status" required>
						<option value="">Select</option>
						<option value="active">Active</option>
						<option value="close">Close</option>
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="2"> <center>
					<input type="submit" value="Submit"/>
			</center>
				</td>
			</tr>
			
		</table>
		</center>
	</form>
</body>
</html>