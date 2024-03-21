<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

    <p>Total Daily Calorie Intake: <%= request.getAttribute("totalCalories") %></p>
    
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
    <a href="mealplanning">Back to Meal Planning</a>
</body>
</html>
