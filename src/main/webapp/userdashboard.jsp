<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.all.min.js"></script>
<title>Gen Banking User Dashboard</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" />
<style>
    /* Added font awesome icons */
    body {
        font-family: Arial, sans-serif;
        background-color: #f2f2f2; /* Genpact-inspired light gray background color */
        height: 100vh;
        margin: 0;
    }

    .dashboard {
        width: 80%;
        margin: 40px auto;
        background-color: #fff;
        padding: 20px;
        border: 1px solid #ddd;
        border-radius: 10px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }

    .dashboard-header {
        background-color: #333;
        color: #fff;
        padding: 10px;
        border-bottom: 1px solid #333;
    }

    .dashboard-header h2 {
        margin: 0;
        font-size: 24px;
    }

    .dashboard-header h2 i {
        margin-right: 10px;
    }

    .dashboard-content {
        padding: 20px;
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

    .dashboard-links {
        list-style: none;
        padding: 0;
        margin: 0;
    }

    .dashboard-links li {
        margin-bottom: 10px;
    }

    .dashboard-links a {
        color: #337ab7;
        text-decoration: none;
    }

    .dashboard-links a:hover {
        color: #23527c;
    }

    .dashboard-links i {
        margin-right: 10px;
        font-size: 18px;
    }
</style>
</head>
<body>
	<div class="dashboard">
		<div class="dashboard-header">
			<h2><i class="fas fa-bank"></i> Gen Banking User Dashboard</h2>
		</div>
		<div class="dashboard-content">
			<div class="account-info">
				<label>Account Number:</label>
				<span>${sessionScope.accountNumber}</span>
			</div>
			<div class="account-info">
				<label>Full Name:</label>
				<span>${sessionScope.fullName}</span>
			</div>
			
			<div class="account-info">
			<label>Balance:</label>
			<span>${sessionScope.initialbalance}</span>
			</div>
			
			<ul class="dashboard-links">
				<li><a href="deposit.jsp"><i class="fas fa-rupee-sign"></i>Deposit</a></li>
				<li><a href="withdrawl.jsp"><i class="fas fa-minus-circle"></i> Withdraw</a></li>
				<li><a href="${pageContext.request.contextPath}/transactionhis.jsp?accno=${sessionScope.accountNumber}"><i class="fas fa-exchange-alt"></i> Transaction History</a></li>
				<li><a href="passwordreset.jsp"><i class="fas fa-user-edit"></i> Change Password</a></li>
				<li><a href="overdraft.jsp"><i class="fas fa-chart-line"></i> Overdraft Options</a></li>
<li><a href="${pageContext.request.contextPath}/DeleteAccServlet?accno=${sessionScope.accountNumber}"><i class="fas fa-file-invoice-dollar"></i> Delete Account</a></li>
<li><a href="${pageContext.request.contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
			</ul>
		</div>
	</div>
	<%
        if (request.getParameter("deposit_success") != null) {
            double balance = (double) session.getAttribute("balance");
    %>
    <script>
        Swal.fire({
            title: 'Deposit Successful!',
            text: 'Your new balance is <%= balance %>',
            icon: 'success',
            confirmButtonText: 'OK'
        });
    </script>
    <%
        }
    %>
</body>
</html>