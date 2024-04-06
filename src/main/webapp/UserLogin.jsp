<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Login</title>
</head>
<body>
    <h2>User Login</h2>

    <%-- Display error message if it exists --%>
    <% if(request.getAttribute("error") != null) { %>
        <p style="color: red;"><%= request.getAttribute("error") %></p>
    <% } %>
    
    <%-- Display success message if login is successful --%>
    <% if(request.getSession().getAttribute("user") != null) { %>
        <p style="color: green;">Successfully logged in!</p>
    <% } else { %>
        <%-- Login form --%>
        <form action="userlogin" method="post">
            Username: <input type="text" name="username" required><br>
            Password: <input type="password" name="password" required><br>
            <button type="submit">Login</button>
        </form>
    <% } %>
</body>
</html>
