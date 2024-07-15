<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%

if(session.getAttribute("username")==null){
	response.sendRedirect("login.jsp");
}

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Page</title>
<style>
    /* Embedded CSS */
    .box {
        display: flex;
        flex-direction: column;
        width: 300px;
        margin: 50px auto;
        padding: 20px;
        border: 1px solid #ddd;
        border-radius: 10px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        background-color: #fff;
        text-align: center;
    }

    .box h1 {
        margin-bottom: 20px;
        font-size: 24px;
        color: #333;
    }

    .box .btn,
    .box button {
        padding: 10px 20px;
        margin: 10px 0;
        background-color: #007bff;
        color: white;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        font-size: 16px;
    }

    .box .btn:hover,
    .box button:hover {
        background-color: #0056b3;
    }

    .box ul {
        list-style: none;
        padding: 0;
    }

    .box ul li {
        margin: 10px 0;
    }

    .box ul li button {
        width: 100%;
    }
    .button {
    display: inline-block;
    padding: 10px 20px;
    margin: 10px 0;
    background-color: #007bff;
    color: white;
    text-decoration: none;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 16px;
    text-align: center;
}

.button:hover {
    background-color: #0056b3;
}
    
</style>
</head>
<body>
    <div class="box">
        <h1>Welcome Admin <%=session.getAttribute("username") %> </h1>
        <a href="logout" class="button">Logout</a>
        <ul>
            <li>
                <button onclick="location.href='cusreg.jsp'">Register</button>
            </li>
            <li>
                <button onclick="location.href='UpdateUser.jsp'">Update</button>
            </li>
            <li>
                <button onclick="location.href='DeleteUser.jsp'" >Delete</button>
            </li>
        </ul>
    </div>
</body>
</html>