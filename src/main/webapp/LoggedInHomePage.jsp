<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="eatpro.model.Users" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Logged In Home Page</title>
    <style>
        body {
            font-family: monospace;
        }
        .title {
            text-align: center;
            margin-top: 80px;
            margin-bottom: 30px;
        }
        .button-container {
            text-align: center;
            margin-top: 20px;
        }
        .button-container form {
            display: inline-block;
            margin: 0 20px;
        }
  /* Style adapted from https://getcssscan.com/css-buttons-examples */
        .button-container form button {
            background-color: #347235;
            border: 1px solid #347235;
            border-radius: 4px;
            box-shadow: rgba(0, 0, 0, .1) 0 2px 4px 0;
            box-sizing: border-box;
            color: #fff;
            cursor: pointer;
            font-family: "Akzidenz Grotesk BQ Medium", -apple-system, BlinkMacSystemFont, sans-serif;
            font-size: 16px;
            font-weight: 400;
            outline: none;
            padding: 10px 25px;
            text-align: center;
            transform: translateY(0);
            transition: transform 150ms, box-shadow 150ms;
            user-select: none;
            -webkit-user-select: none;
            touch-action: manipulation;
        }
        .button-container form button:hover {
        	box-shadow: rgba(0, 0, 0, .15) 0 3px 9px 0;
            transform: translateY(-2px);
        }
        .logout-form {
            position: absolute;
            top: 50px;
            right: 50px;
        }
        .logout-form form button {
            background-color: #222222;
            border: 1px solid #222222;
            border-radius: 4px;
            box-shadow: rgba(0, 0, 0, .1) 0 2px 4px 0;
            box-sizing: border-box;
            color: #fff;
            cursor: pointer;
            font-family: "Akzidenz Grotesk BQ Medium", -apple-system, BlinkMacSystemFont, sans-serif;
            font-size: 16px;
            font-weight: 400;
            outline: none;
            padding: 10px 25px;
            text-align: center;
            transform: translateY(0);
            transition: transform 150ms, box-shadow 150ms;
            user-select: none;
            -webkit-user-select: none;
            touch-action: manipulation;
        }
    </style>
</head>
<body>
    <h1 class="title">Welcome, <%= ((Users) request.getSession().getAttribute("user")).getUserName() %>! ✨</h1>
    <div class="logout-form">
        <form action="logout" method="post">
            <button type="submit">Log Out</button>
        </form>
    </div>
    <div class="button-container">
	    <form action="userAdjustmentsDisplay" method="get">
	        <button type="submit">User Adjustments</button>
	    </form>
	    <form action="usergoals" method="get">
	        <button type="submit">User Goals</button>
	    </form>
	    <form action="mealplans" method="get">
	        <button type="submit">Meal Plans</button>
	    </form>
    </div>
</body>
</html>
