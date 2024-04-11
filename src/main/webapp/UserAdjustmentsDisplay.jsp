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
            text-align: center;
        }
        table {
            border-collapse: collapse;
            width: 80%;
            margin: 20px auto;
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
            justify-content: center;
            gap: 20px; 
            margin-top: 100px;
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
            text-decoration: none; 
            display: inline-block; 
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
            <th>Delete</th>
        </tr>
        <% for(UserAdjustments adjustment : adjustments) { %>
        <tr>
            <td><%= adjustment.getDateLogged().toString() %></td>
            <td><%= Math.round(adjustment.getWeight()/0.45392)%> lbs</td>
            <td><%= adjustment.getWorkoutToday() ? "Yes" : "No" %></td>
            <td><%= adjustment.getExpectedExerciseCalorie() %></td>
            <td>
                <form action="deleteAdjustment" method="post" onsubmit="return confirm('Are you sure you want to delete this record?');">
                    <input type="hidden" name="adjustmentId" value="<%= adjustment.getAdjustmentId() %>">
                    <button type="submit" style="border: none; background: transparent; cursor: pointer;">
                        🗑️ 
                    </button>
                </form>
            </td>
        </tr>
        <% } %>
    </table>
    <% } else { %>
    <p>No adjustments found for <%= user.getUserName() %>. Click the button below👇 and Add a record today!</p>
    <% } %>
    
    <div class="button-container">
        <a href="LoggedInHomePage.jsp" class="button">Back to Home</a>
        <a href="useradjustment" class="button">Add a new record</a>
    </div>
</body>
</html>
