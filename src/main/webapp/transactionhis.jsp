<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!DOCTYPE html>
<html>
<head>
    <title>Transaction History</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" />
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2; /* light gray background color */
        }
        
        .container {
            width: 80%;
            margin: 40px auto;
            background-color: #fff;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        
        .header {
            background-color: #333;
            color: #fff;
            padding: 10px;
            border-bottom: 1px solid #333;
        }
        
        .header h1 {
            margin: 0;
            font-size: 24px;
        }
        
        .header i {
            margin-right: 10px;
        }
        
        table {
            border-collapse: collapse;
            width: 100%;
        }
        
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        
        th {
            background-color: #f0f0f0; /* light gray background color */
        }
        
        .back-button {
            text-decoration: none;
            color: #337ab7;
            margin-top: 20px;
        }
        
        .back-button:hover {
            color: #23527c;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1><i class="fas fa-exchange-alt"></i> Transaction History</h1>
        </div>
        <div class="content">
            <c:set var="accno" value="${param.accno}"/>
            
            <sql:setDataSource var="dataSource" driver="com.mysql.cj.jdbc.Driver"
                               url="jdbc:mysql://localhost:3306/gen"
                               user="root" password="system"/>
            <sql:query dataSource="${dataSource}" var="result">
                SELECT * FROM transaction_history where accno =?;
                <sql:param value="${accno}"/>
            </sql:query>
            <table>
                <tr>
                    <th>Amount</th>
                    <th>Date</th>
                    <th>Type</th>
                    <th>Status</th>
                </tr>
                <c:forEach var="row" items="${result.rows}">
                    <tr>
                        
                        <td>${row.amount}</td>
                        <td>${row.trans_date}</td>
                        <td>${row.trans_type}</td>
                         <td>${row.status}</td>
                    </tr>
                </c:forEach>
            </table>
            <a href="userdashboard.jsp" class="back-button">Go Back</a>
        </div>
    </div>
</body>
</html>