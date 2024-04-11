<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Goals</title>
    <style>
        body {
            font-family:monospace;
            background-color: #f2f2f7;
            color: #333;
            text-align: center;
            padding-top: 50px;
        }
        h4 {
        	text-align: center;
        }
        form {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            display: inline-block;
            box-shadow: 0 4px 6px rgba(50, 50, 93, 0.11), 0 1px 3px rgba(0, 0, 0, 0.08);
            margin: 20px;
            text-align: left;
        }
        input, select {
            background-color: #f7f7f7;
            border: 1px solid #e5e5e5;
            border-radius: 4px;
            padding: 10px;
            margin-top: 5px;
            width: calc(100% - 22px);
            box-sizing: border-box;
        }
        .form-submit, .back-to-home {
            text-align: center;
            margin-top: 20px;
        }
        input[type="submit"], .back-to-home a {
            background-color: #222222;
            color: #fff;
            cursor: pointer;
            transition: background-color 0.3s ease;
            padding: 10px 25px;
            text-decoration: none;
            display: inline-block;
            border: none;
            border-radius: 4px;
        }
        input[type="submit"]:hover, .back-to-home a:hover {
            background-color: #242526;
        }
    </style>
</head>
<body>
    <h2>Create a New Goal</h2>
    <form action="usergoal" method="post" style="text-align: center;">
        <input type="hidden" name="action" value="create">
        <div>Goal Type: <select name="goaltype">
            <option value="WEIGHTLOSS">Weight Loss</option>
            <option value="GAINWEIGHT">Gain Weight</option>
        </select></div>
        <div>Target Weight (lbs): <input type="number" step="0.01" name="targetvalue" required></div>
        <div>Target Date (yyyy-mm-dd): <input type="date" name="targetdate" required></div>
        <div class="form-submit"><input type="submit" value="Submit Goal"></div>
    </form>
    <h4> ** We will change your previous "Active" goals to "On Hold" Status when you submit a new goal. Keep trying! Don't give up 💪</h4>

    <% if (session.getAttribute("goalMessage") != null) { %>
<script>alert('<%= session.getAttribute("goalMessage") %>');</script>
<% session.removeAttribute("goalMessage"); %>
<% } %>

    <div class="back-to-home">
        <a href="LoggedInHomePage.jsp">Back to Home</a>
    </div>
</body>
</html>

