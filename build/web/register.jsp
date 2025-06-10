<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.UserDTO"%>
<%@page import="util.AuthUtils" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Register</title>
        <link rel="stylesheet" href="assets/css/register_style.css">
    </head>
    <body>
        <%
            String UsernameAlert = (String) request.getAttribute("UserNameAlert");
            String EmailAlert = (String) request.getAttribute("EmailAlert");
            UserDTO user=AuthUtils.getCurrentUser(request);
            if (user!=null) response.sendRedirect("userinformation.jsp"); else {
        %>
        <div class="form-container">
            <h2 class="form-title">Register</h2>
            <form action="MainController" method="post">
                <input type="hidden" name="action" value="register">

                <div class="form-group">
                    <label for="Username">Username</label>
                    <input type="text" id="Username" name="username" 
                           value="<%=(UsernameAlert==null && request.getParameter("username")!=null)?request.getParameter("username"):""%>">
                    <span class="error"><%=UsernameAlert==null?"":UsernameAlert%></span>
                </div>

                <div class="form-group">
                    <label for="Password">Password</label>
                    <input type="password" id="Password" name="password">
                </div>

                <div class="form-group">
                    <label for="Email">Email</label>
                    <input type="email" id="Email" name="email"
                           value="<%=(EmailAlert==null && request.getParameter("email")!=null)?request.getParameter("email"):""%>">
                    <span class="error"><%=EmailAlert==null?"":EmailAlert%></span>
                </div>

                <div class="form-group">
                    <label for="Fullname">Fullname</label>
                    <input type="text" id="Fullname" name="fullname">
                </div>

                <input type="submit" value="Register" class="submit-btn">
            </form>

            <p class="form-note">Already have an account? <a href="login.jsp">Login here</a></p>
        </div>
        <%}%>
    </body>
</html>
