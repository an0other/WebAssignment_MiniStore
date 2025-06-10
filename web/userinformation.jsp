<%-- 
    Document   : userinformation
    Created on : Jun 10, 2025, 11:14:53 AM
    Author     : an0other
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.UserDTO" %>
<%@page import="util.AuthUtils" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Your Information</title>
    </head>
    <body>
        <%
            UserDTO user=(UserDTO) session.getAttribute("user");
            if (user!=null) {
        %>
        <h1>Hello <%=user.getFullname()%></h1>
        <%} else {%>
        You haven't logged in yet please
        <a href="login.jsp">Login</a>
        <%}%>
        <a href="MainController?action=logout">Log out</a>
    </body>
</html>
