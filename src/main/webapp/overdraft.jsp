<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>

<!DOCTYPE html>
<html>
<head>
	<title>Overdraft Facility</title>
	<style>
		body {
			font-family: Arial, sans-serif;
			background-color: #f0f0f0;
		}
		
		.dashboard-content {
			width: 80%;
			margin: 40px auto;
			padding: 20px;
			background-color: #fff;
			border: 1px solid #ddd;
			border-radius: 10px;
			box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
		}
		
		.account-info {
			margin-bottom: 20px;
		}
		
		.account-info label {
			font-weight: bold;
			margin-right: 10px;
		}
		
		.account-info span {
			font-size: 18px;
			color: #337ab7;
		}
		
		.result {
			font-size: 24px;
			font-weight: bold;
			color: #337ab7;
			margin-top: 20px;
		}
		
		.result.eligible {
			color: #2ecc71;
		}
		
		.result.not-eligible {
			color: #e74c3c;
		}
	</style>
</head>
<body>
	<h1>Overdraft Facility</h1>
	<div class="dashboard-content">
		<div class="account-info">
			<label>Account Number:</label>
			<span>${sessionScope.accountNumber}</span>
		</div>
		<div class="account-info">
			<label>Full Name:</label>
			<span>${sessionScope.fullName}</span>
		</div>
		
	<%
	String url = "jdbc:mysql://localhost:3306/gen";
	String username = "root";
	String password = "system";
	
	Class.forName("com.mysql.cj.jdbc.Driver");
	Connection con = DriverManager.getConnection(url, username, password);
	
	String accountNumber = (String) session.getAttribute("accountNumber");
	
	PreparedStatement pstmt = con.prepareStatement("SELECT idproof, initialbalance FROM custdetails WHERE accno = ?");
	pstmt.setString(1, accountNumber);
	
	ResultSet rs = pstmt.executeQuery();
	
	if (rs.next()) {
		String idProof = rs.getString("idproof");
		double initialBalance = rs.getDouble("initialbalance");
		
		if (idProof != null) {
			if (!idProof.equalsIgnoreCase("Aadhar") && idProof.matches(".*\\d.*")) {
				if (initialBalance > 1000) {
					out.println("You are eligible for overdraft facility Contact Bank for Overdraft.");
				} else {
					out.println("You are not eligible for overdraft facility.");
				}
			} else {
				out.println("You are not eligible for overdraft facility.");
			}
		} else {
			out.println("ID proof not found in database.");
		}
	} else {
		out.println("Account not found in database.");
	}
	
	con.close();
	%>
	<a href="userdashboard.jsp">Go Back</a>
</body>
</html>