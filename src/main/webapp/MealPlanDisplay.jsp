<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="java.util.List" %>
<%@ page import="eatpro.model.MealPlans" %>

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
<%-- <body>
    <h1>Meal Plan for <%= request.getAttribute("userName") %></h1>
    <% if(request.getAttribute("messages") != null) { %>
        <% Map<String, String> messages = (Map<String, String>) request.getAttribute("messages"); %>
        <% for(String key : messages.keySet()) { %>
            <p style="color: <%= key.equals("failure") ? "red" : "green" %>;"><%= messages.get(key) %></p>
        <% } %>
    <% } %>

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
 --%>


<body>
    <h1>Meal Plan Details</h1>
    
    <c:set var="mealsOrder" value="Breakfast,Lunch,Dinner"/>

    <c:forEach var="mealType" items="${mealsOrder}">
                <h2>${mealType}</h2>
                <c:set var="lowerMealType" value="${fn:toLowerCase(mealType)}"/>
                <c:choose>
                	
                    <c:when test="${mealDetailsMap[lowerMealType] != null}">
                        <table border="1">
                            <tr>
                                <th>Category</th>
                                <th>Food Name</th>
				                <th>Ingredients</th>
				                <th>Serving Size</th>
				                
                            </tr>
                            <c:forEach var="mealDetail" items="${mealDetailsMap[lowerMealType]}">
                                <tr>
                                    <td>${mealDetail.food.foodCategory}</td>
				                    <td>${mealDetail.food.foodName}</td>
				                    <td>${mealDetail.food.ingredients}</td>
				                    <td>${mealDetail.food.servingSize} ${mealDetail.food.servingUnit}</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <p>There is not suggested food for this meal currently.</p>
                    </c:otherwise>
                </c:choose>

    </c:forEach>

</body>
</html>
