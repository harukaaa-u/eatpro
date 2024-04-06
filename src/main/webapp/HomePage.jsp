<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home Page</title>
    <style>
        .button-container {
            text-align: center;
            margin-top: 20px;
        }
        .button-container form {
            display: inline-block; /* Align forms horizontally */
            margin: 0 10px; /* Space between buttons */
        }
        .button-container form button {
            padding: 10px 20px;
            cursor: pointer;
        }
    </style>
</head>
<body>
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
