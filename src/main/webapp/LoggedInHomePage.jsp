<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="eatpro.model.Users" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Logged In Home Page</title>
</head>
<body>
    <h1>Welcome, <%= ((Users) request.getSession().getAttribute("user")).getUserName() %></h1>
    
    <form action="useradjustment" method="get">
        <button type="submit">User Adjustments</button>
    </form>
    <form action="usergoals" method="get">
        <button type="submit">User Goals</button>
    </form>
    <form action="mealplans" method="get">
        <button type="submit">Meal Plans</button>
    </form>
</body>
</html>
