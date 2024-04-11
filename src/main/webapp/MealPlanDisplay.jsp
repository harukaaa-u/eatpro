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
            transform: translateY(-2px);
        }
    </style>
</head>

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
	<div class="button-container">
        <a href="LoggedInHomePage.jsp" class="button">Back to Home</a>
    </div>
</body>
</html>
