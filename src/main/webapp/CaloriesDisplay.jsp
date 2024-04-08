<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%@ page import="eatpro.model.Users" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Meal Plan Details</title>
    <style>
        body {
            font-family: monospace;
            background-color: #f4f4f4;
            padding: 20px;
            text-align: center; /* Center align everything in body */
        }
        table {
            border-collapse: collapse;
            width: 30%; /* Adjust table width as necessary */
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
            transform: translateY(-2px);
        }
    </style>
</head>
<body>
    <h1>🍗 Meal Plan for <%= ((Users) request.getSession().getAttribute("user")).getUserName() %></h1>
<%--     <% if(request.getAttribute("messages") != null) { %>
        <% Map<String, String> messages = (Map<String, String>) request.getAttribute("messages"); %>
        <% for(String key : messages.keySet()) { %>
            <p style="color: <%= key.equals("failure") ? "red" : "green" %>;"><%= messages.get(key) %></p>
        <% } %>
    <% } %> --%>

    <% if(request.getAttribute("totalCalories") != null) { %>
        <h2>Calories Distribution</h2>
        <table>
            <tr>
                <th>Meal</th>
                <th>Calories</th>
            </tr>
            <tr>
                <td>Breakfast</td>
                <td><%= request.getAttribute("breakfastCalories") %></td>
            </tr>
            <tr>
                <td>Lunch</td>
                <td><%= request.getAttribute("lunchCalories") %></td>
            </tr>
            <tr>
                <td>Dinner</td>
                <td><%= request.getAttribute("dinnerCalories") %></td>
            </tr>
            <tr>
                <td>Snack</td>
                <td><%= request.getAttribute("snackCalories") %></td>
            </tr>
        </table>

        <p>Total Daily Calorie Intake For Today: <%= request.getAttribute("totalCalories") %></p>
    <% } %>
    
    <!-- Optionally, link back to the meal planning or another page -->
    <a href="foodselection" class="button">View your Meal Plans!</a>
</body>
</html>


<%-- <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.HashMap" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Meal Plan Details</title>
</head>
<body>
    <h1>Meal Plan for <%= request.getAttribute("userName") %></h1>
    <h2>Calories Distribution</h2>
    <table border="1">
        <tr>
            <th>Meal</th>
            <th>Calories</th>
        </tr>
        <tr>
            <td>Breakfast</td>
            <td><%= request.getAttribute("breakfastCalories") %></td>
        </tr>
        <tr>
            <td>Lunch</td>
            <td><%= request.getAttribute("lunchCalories") %></td>
        </tr>
        <tr>
            <td>Dinner</td>
            <td><%= request.getAttribute("dinnerCalories") %></td>
        </tr>
        <tr>
            <td>Snack</td>
            <td><%= request.getAttribute("snackCalories") %></td>
        </tr>
    </table>

    <p>Total Daily Calorie Intake For Today: <%= request.getAttribute("totalCalories") %></p>
    
    <%
        HashMap<String, String> messages = (HashMap<String, String>) request.getAttribute("messages");
        boolean containsSuccess = false;
        if (messages != null) {
            for (String key : messages.keySet()) {
                out.println("<h4>" + messages.get(key) + "</h4>");
                if (messages.get(key).contains("Successfully")) {
                    containsSuccess = true;
                }
            }
        }
    %>
    
    <!-- Optionally, link back to the meal planning or another page -->
    <a href="foodselection">${userName}, View your Meal Plans!</a>
</body>
</html>
 --%>