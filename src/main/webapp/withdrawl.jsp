<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Withdraw Funds</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
        }
        .container {
            width: 80%;
            margin: 40px auto;
            background-color: #fff;
            padding: 20px;
            border: 1px solid #ddd;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .header {
            background-color: #333;
            color: #fff;
            padding: 10px;
            text-align: center;
        }
        .form {
            padding: 20px;
        }
        .label {
            display: block;
            margin-bottom: 10px;
        }
        .input {
            width: 100%;
            height: 30px;
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

    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.all.min.js"></script>
    <script>
        function withdrawFunds() {
            var withdrawalAmount = document.getElementById("withdrawalAmount").value;
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "loginbank/WithdrawServlet", true);
            xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhr.onload = function() {
                if (xhr.status === 200) {
                    var response = xhr.responseText;
                    if (response.indexOf("Withdrawal successful") !== -1) {
                        Swal.fire({
                            title: 'Withdrawal Successful!',
                            text: response,
                            icon: 'success',
                            confirmButtonText: 'OK'
                        }).then((result) => {
                            if (result.isConfirmed) {
                                window.location.href = "userdashboard.jsp"; // Redirect to dashboard
                            }
                        });
                    } else {
                        Swal.fire({
                            title: 'Error',
                            text: response,
                            icon: 'error',
                            confirmButtonText: 'OK'
                        });
                    }
                } else {
                    Swal.fire({
                        title: 'Error',
                        text: 'An error occurred while processing your request. Please try again.',
                        icon: 'error',
                        confirmButtonText: 'OK'
                    });
                }
            };
            xhr.send("withdrawalAmount=" + withdrawalAmount);
        }
    </script>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>Withdraw Funds</h1>
        </div>
        <div class="form">
            <form>
                <label for="withdrawalAmount" class="label">Enter Amount to Withdraw:</label>
                <input type="number" id="withdrawalAmount" class="input" min="1" required>
                <button type="button" class="button" onclick="withdrawFunds()">Withdraw</button>
            </form>
        </div>
        <a href="userdashboard.jsp">Go Back to Dashboard</a>
    </div>
</body>
</html>