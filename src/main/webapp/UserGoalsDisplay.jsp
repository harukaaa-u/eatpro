<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="eatpro.model.UserGoals"%>
<%@ page import="eatpro.model.Users"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Goals</title>
    <style>
        body {
            font-family: monospace;
            background-color: #f4f4f4;
            padding: 20px;
            text-align: center; /* Center align everything in body */
        }
        table {
            border-collapse: collapse;
            width: 80%; /* Adjust table width as necessary */
            margin: 20px auto; /* Center the table and add spacing at the top */
            box-shadow: 0 2px 3px rgba(0,0,0,0.1);
            border-radius: 4px;
            overflow: hidden;
        }
        th, td {
            padding: 12px 15px;
            text-align: left;
            font-size: 14px;
        }
        th {
            background-color: #222222;
            color: #ffffff;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        .button-container {
            display: flex;
            justify-content: center; /* Center the buttons horizontally */
            gap: 20px; /* Add some space between buttons */
            margin-top: 100px; /* Add space between table and buttons */
        }
        .button {
            background-color: #222222;
            border: none;
            border-radius: 4px;
            box-shadow: rgba(0, 0, 0, .1) 0 2px 4px 0;
            color: #fff;
            cursor: pointer;
            font-size: 16px;
            padding: 10px 25px;
            text-decoration: none; /* Remove underline from links */
            display: inline-block; /* Allow width and padding */
            transition: transform 150ms, box-shadow 150ms;
        }
        .button:hover {
            background-color: #242526;
        }
    </style>
</head>
<body>
    <h2>Your Goals 🌟 Keep pushing forward!</h2>
    <% Users user = (Users)session.getAttribute("user");
       List<UserGoals> goals = (List<UserGoals>)request.getAttribute("goals");
       if(user != null && goals != null && !goals.isEmpty()) {
    %>
    <table>
        <tr>
            <th>Goal Type</th>
            <th>Target Weight</th>
            <th>Target Date</th>
            <th>Status</th>
        </tr>
        <% for(UserGoals goal : goals) { %>
        <tr>
            <td><%= goal.getGoalType() %></td>
            <td><%= Math.round(goal.getTargetValue()/0.45392)%> lbs</td>
            <td><%= goal.getTargetDate().toString() %></td>
            <td><%= goal.getStatus() %></td>
        </tr>
        <% } %>
    </table>
    <% } else { %>
    <p>No goals found for <%= user.getUserName() %>. It's a great time to start!</p>
    <% } %>
    
    <div class="button-container">
        <a href="LoggedInHomePage.jsp" class="button">Back to Home</a>
        <a href="usergoal?action=create" class="button">Set a New Goal</a>
    </div>
</body>
</html>
