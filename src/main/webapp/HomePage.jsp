<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home Page</title>
    <style>
        body {
            font-family: monospace;
        }
        .title {
            text-align: center;
            margin-top: 80px;
            margin-bottom: 30px;
        }
        .emoji {
        	text-align: center;
            margin-top: 10px;
            margin-bottom: 40px;
        }
        .intro {
            text-align: center;
            margin-top: 20px;
            margin-bottom: 40px; 
            width: 55%;
            margin-left: auto;
            margin-right: auto;
            font-size: 15px;
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
    </style>
</head>
<body>
    <h1 class="title">✨ Welcome to EatPro! ✨</h1>
    <h1 class="emoji">🍽️🍎🍗🥖🥦🥐🌽🍳🍇🥗🧀🍯🥨🍠🥕🥒🍕🌶️🥑🥩🍔🍜</h1>
    <p class="intro">
    	If you have a plan to adjust your weight, this is the application to use: We will record your goal, track your weight change, and adjust meal plans for you.
    </p>
     <p class="intro">
    	Tell us your goal, and Today will be Day1 of your change! Enjoy the journey! 🫡
    </p>
    <div class="button-container">
        <!-- User Registration Button -->
        <form action="userregistration" method="get">
            <button type="submit">User Registration</button>
        </form>
        
        <!-- User Login Button -->
        <form action="userlogin" method="get">
            <button type="submit">User Login</button>
        </form>
    </div>
</body>
</html>
