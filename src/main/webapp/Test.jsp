<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Goals</title>
</head>
<body>
<h2>Calorie Distribution for Meal Plan</h2>
    <table border="1">
        <tr>
            <th>Meal Type</th>
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
    <p>User Name: <%= request.getAttribute("userName") %></p>
</body>
</html>
