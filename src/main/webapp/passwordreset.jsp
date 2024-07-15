<%
    session.setAttribute("password", request.getParameter("password"));
%>
<%
    String accountNumber = (String) session.getAttribute("accountNumber");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>Password Reset</title>

<!-- Font Icon -->
<link rel="stylesheet" href="fonts/material-icon/css/material-design-iconic-font.min.css">

<!-- Main css -->
<link rel="stylesheet" href="css/style.css">
</head>
<body>
<input type="hidden" id="status" value="<%= request.getAttribute("status") %>" >
    <div class="main">

        <!-- Sing in Form -->
        <section class="sign-in">
            <div class="container">
                <div class="signin-content">
                    <div class="signin-image">
                        <figure>
                            <img src="images/signin-image.jpg" alt="sing up image">
                        </figure>
                    </div>

                    <div class="signin-form">
                    <h1 class="form-title">Welcome to Gen Online Banking </h1>
                        <h2 class="form-title">User Password Reset</h2>
                       <form action="${pageContext.request.contextPath}/passwordresets" method="post" class="register-form" id="login-form">
                         <input type="hidden" name="username" value="<%= accountNumber %>">
                            <div class="form-group">
                                <label for="username"><i class="zmdi zmdi-lock"></i></label>
                                <input type="text"  id="username" placeholder="New Password" />
                            </div>
                            <div class="form-group">
                                <label for="password"><i class="zmdi zmdi-lock"></i></label>
                                <input type="password" name="newpassword" id="newpassword" placeholder="Confirm Password" />
                            </div>
                            <div class="form-group form-button">
                                <input type="submit" name="signin" id="signin" class="form-submit" value="Reset Password" />
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>

    </div>

    <!-- JS -->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="js/main.js"></script>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <link rel="stylesheet" href="alert/dist/sweetalert.css">

    <script type="text/javascript">
        var status = document.getElementById("status").value;
        if (status == "failed") {
            swal("Sorry", "Please Choose Another Password", "error");
        }
    </script>
</body>
</html>
