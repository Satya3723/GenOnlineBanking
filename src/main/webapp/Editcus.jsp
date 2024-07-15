<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>

<!DOCTYPE html>
<html>
<head>
    <title>Customer Management</title>
</head>
<body>
    <h1>Customer Management</h1>

    <%
        // Database connection
        String url = "jdbc:mysql://localhost:3306/gen";
        String username = "root";
        String password = "system";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
            stmt = conn.createStatement();
        } catch (Exception e) {
            out.println("Error connecting to database: " + e.getMessage());
        }
    %>

    <!-- View Customers -->
    <h2>View Customers</h2>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Actions</th>
        </tr>
        <%
            String query = "SELECT accno, fullname, email FROM custdetails";
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("accno");
                String name = rs.getString("fullname");
                String email = rs.getString("email");

                out.println("<tr>");
                out.println("<td>" + id + "</td>");
                out.println("<td>" + name + "</td>");
                out.println("<td>" + email + "</td>");
                out.println("<td>");
                out.println("<a href='modifyCustomer.jsp?id=" + id + "'>Modify</a> | ");
                out.println("<a href='deleteCustomer.jsp?id=" + id + "'>Delete</a>");
                out.println("</td>");
                out.println("</tr>");
            }
        %>
    </table>

    <!-- Modify Customer -->
    <%
        if (request.getParameter("id")!= null) {
            int id = Integer.parseInt(request.getParameter("accno"));
            String query1 = "SELECT fullname, email FROM custdetails WHERE accno = " + id;
            rs = stmt.executeQuery(query);

            if (rs.next()) {
                String name = rs.getString("fullname");
                String email = rs.getString("email");

                out.println("<h2>Modify Customer</h2>");
                out.println("<form action='modifyCustomer.jsp' method='post'>");
                out.println("<input type='hidden' name='id' value='" + id + "'>");
                out.println("<label for='name'>Name:</label>");
                out.println("<input type='text' id='name' name='name' value='" + name + "'>");
                out.println("<br><br>");
                out.println("<label for='email'>Email:</label>");
                out.println("<input type='email' id='email' name='email' value='" + email + "'>");
                out.println("<br><br>");
                out.println("<input type='submit' value='Modify Customer'>");
                out.println("</form>");
            }
        }
    %>

    <!-- Delete Customer -->
    <%
        if (request.getParameter("id")!= null) {
            int id = Integer.parseInt(request.getParameter("accno"));
            String query2 = "DELETE FROM custdetails WHERE accno = " + id;
            stmt.executeUpdate(query);

            out.println("Customer deleted successfully!");
        }
    %>

</body>
</html>