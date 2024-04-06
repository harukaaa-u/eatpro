<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="eatpro.model.UserAdjustments"%>
<%@ page import="eatpro.model.Users"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Adjustments</title>
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
    <h2>Your Progress💪 You are doing great!</h2>
    <% Users user = (Users)session.getAttribute("user");
       List<UserAdjustments> adjustments = (List<UserAdjustments>)request.getAttribute("adjustments");
       if(user != null && adjustments != null) {
    %>
    <table>
        <tr>
            <th>Date Logged</th>
            <th>Weight</th>
            <th>Workout Today</th>
            <th>Expected Exercise Calorie</th>
        </tr>
        <% for(UserAdjustments adjustment : adjustments) { %>
        <tr>
            <td><%= adjustment.getDateLogged().toString() %></td>
            <td><%= adjustment.getWeight() %></td>
            <td><%= adjustment.getWorkoutToday() ? "Yes" : "No" %></td>
            <td><%= adjustment.getExpectedExerciseCalorie() %></td>
        </tr>
        <% } %>
    </table>
    <% } else { %>
    <p>No adjustments found for <%= user.getUserName() %>.</p>
    <% } %>

    <div class="button-container">
        <a href="LoggedInHomePage.jsp" class="button">Back to Home</a>
        <a href="useradjustment" class="button">Add a new record</a>
    </div>
</body>
</html>

<%-- <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="eatpro.model.UserAdjustments"%>
<%@ page import="eatpro.model.Users"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Adjustments</title>
    <style>
        body {
            font-family: monospace;
            background-color: #f0f0f0;
            padding: 20px;
        }
        .adjustments-table {
            border-collapse: collapse;
            width: 100%;
            margin-top: 20px;
        }
        .adjustments-table th, .adjustments-table td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        .adjustments-table tr:nth-child(even){background-color: #f2f2f2;}
        .adjustments-table th {
            padding-top: 12px;
            padding-bottom: 12px;
            background-color: #4CAF50;
            color: white;
        }
        .button {
            background-color: #4CAF50; /* Green */
            border: none;
            color: white;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            margin: 4px 2px;
            cursor: pointer;
        }
        .button-back {background-color: #f44336;} /* Red */
    </style>
</head>
<body>
    <h2>User Adjustments</h2>
    
    <% Users user = (Users)session.getAttribute("user");
       List<UserAdjustments> adjustments = (List<UserAdjustments>)request.getAttribute("adjustments");
       if(user != null && adjustments != null) {
    %>
    <table class="adjustments-table">
        <tr>
            <th>Date Logged</th>
            <th>Weight</th>
            <th>Workout</th>
            <th>Expected Exercise Calorie</th>
        </tr>
        <% for(UserAdjustments adjustment : adjustments) { %>
        <tr>
            <td><%= adjustment.getDateLogged().toString() %></td>
            <td><%= adjustment.getWeight() %></td>
            <td><%= adjustment.getWorkoutToday() ? "Yes" : "No" %></td>
            <td><%= adjustment.getExpectedExerciseCalorie() %></td>
        </tr>
        <% } %>
    </table>
    <% } else { %>
    <p>No adjustments found for <%= user.getUserName() %>.</p>
    <% } %>

    <div>
        <a href="LoggedInHomePage.jsp" class="button button-back">Back to Home</a>
        <a href="useradjustment" class="button">Create New Adjustment</a>
    </div>
</body>
</html>
 --%>