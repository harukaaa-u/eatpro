<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Login</title>
    <style>
        body {
            font-family: monospace;
            background-color: #f2f2f7;
            color: #333;
            text-align: center;
            padding-top: 50px;
        }
        h2 {
            color: #000;
        }
        form {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            display: inline-block;
            box-shadow: 0 4px 6px rgba(50, 50, 93, 0.11), 0 1px 3px rgba(0, 0, 0, 0.08);
            margin: 20px;
        }
        input[type="text"],
        input[type="password"] {
            background-color: #f7f7f7;
            border: 1px solid #e5e5e5;
            border-radius: 4px;
            padding: 10px;
            margin-top: 5px;
            width: 200px;
            box-sizing: border-box;
        }
        button {
            background-color: #222222;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            margin-top: 10px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        button:hover {
            background-color: #242526;
        }
        p {
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <h2>User Login</h2>

    <%-- Display error message if it exists --%>
    <% if(request.getAttribute("error") != null) { %>
        <p style="color: red;"><%= request.getAttribute("error") %></p>
    <% } %>
    
    <%-- Display success message if login is successful --%>
    <% if(request.getSession().getAttribute("user") != null) { %>
        <p style="color: green;">Successfully logged in!</p>
    <% } else { %>
        <%-- Login form --%>
        <form action="userlogin" method="post">
            Username: <input type="text" name="username" required><br>
            Password: <input type="password" name="password" required><br>
            <button type="submit">Login</button>
        </form>
    <% } %>
</body>
</html>
