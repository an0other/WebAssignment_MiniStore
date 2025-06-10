<%-- 
    Document   : login
    Created on : Jun 9, 2025, 10:07:24 PM
    Author     : an0other
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.UserDTO"%>
<%@page import="util.AuthUtils" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - MiniStore</title>
    <link rel="stylesheet" href="assets/css/login_style.css"> <!-- file css riêng -->
</head>
<body>
    <%
        UserDTO user=AuthUtils.getCurrentUser(request);
        if (user!=null) response.sendRedirect("userinformation.jsp"); else {
    %>
    <div class="login-container">
        <h2 class="title">LOGIN</h2>
        <form action="MainController" method="post" class="login-form">
            <input type="hidden" name="action" value="login">
            
            <label for="Username">Username</label>
            <input type="text" id="Username" name="username" placeholder="Enter your username" required>

            <label for="Password">Password</label>
            <input type="password" id="Password" name="password" placeholder="Enter your password" required>

            <input type="submit" value="Login" class="login-btn">
        </form>

        <div class="register-link">
            Don't have an account? <a href="register.jsp">Register here</a>
        </div>

        <div class="error-message">
            <%= (session.getAttribute("message") != null) ? session.getAttribute("message") : "" %>
        </div>
    </div><%}%>
</body>
</html>
