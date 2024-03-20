<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Registration</title>
</head>
<body>
    <h2>User Registration</h2>
    <%-- Display messages from the servlet --%>
    <h4><%= request.getAttribute("messages") != null ? request.getAttribute("messages") : "" %></h4>
    <form action="userregistration" method="post">
        Username: <input type="text" name="username" required><br>
        Password: <input type="password" name="password" required><br>
        Initial Weight: <input type="number" step="0.01" name="initialweight" required><br>
        Height: <input type="number" step="0.01" name="height" required><br>
        Goal: <select name="gainweight">
                <option value="true">Gain Weight</option>
                <option value="false">Lose Weight</option>
              </select><br>
        <input type="submit" value="Register">
    </form>
</body>
</html>
