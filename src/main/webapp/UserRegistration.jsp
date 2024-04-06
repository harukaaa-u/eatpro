<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.HashMap" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Registration</title>
    <style>
        body {
            font-family:monospace;
            background-color: #f2f2f7;
            color: #333;
            text-align: center;
            padding-top: 50px;
        }
        form {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            display: inline-block;
            box-shadow: 0 4px 6px rgba(50, 50, 93, 0.11), 0 1px 3px rgba(0, 0, 0, 0.08);
            margin: 20px;
            text-align: left;
        }
        input, select {
            background-color: #f7f7f7;
            border: 1px solid #e5e5e5;
            border-radius: 4px;
            padding: 10px;
            margin-top: 5px;
            width: 150px;
            box-sizing: border-box;
        }
        .form-submit {
			text-align: center;
		}
        input[type="submit"] {
        	
            background-color: #222222;
            color: #fff;
            cursor: pointer;
            transition: background-color 0.3s ease;
            margin-top: 20px;
        }
        input[type="submit"]:hover {
            background-color: #242526;
        }
        h2 {
            color: #000;
        }
    </style>
</head>
<body>
    <h2>User Registration</h2>
    <form action="userregistration" method="post">
        <div>Username: <input type="text" name="username" required></div>
        <div>Password: <input type="password" name="password" required></div>
        <div>Initial Weight (lbs): <input type="number" step="0.01" name="initialweight" required></div>
        <div>Height:
            <input type="number" name="feet" min="0" step="1" required> feet
            <input type="number" name="inches" min="0" max="11" step="1" required> inches
        </div>
        <div>Goal: <select name="gainweight">
                <option value="true">Gain Weight</option>
                <option value="false">Lose Weight</option>
            </select>
        </div>
        <div class="form-submit">
            <input type="submit" value="Register">
        </div>
    </form>
    
    <%
        HashMap<String, String> messages = (HashMap<String, String>) request.getAttribute("messages");
        if (messages != null) {
            for (String key : messages.keySet()) {
                out.println("<h4>" + messages.get(key) + "</h4>");
            }
        }
    %>
</body>
</html>

<%-- <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.HashMap" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Registration</title>
</head>
<body>
    <h2>User Registration</h2>

    <!-- <h4><%= request.getAttribute("messages") != null ? request.getAttribute("messages") : "" %></h4>  -->
    <form action="userregistration" method="post">
        Username: <input type="text" name="username" required><br>
        Password: <input type="password" name="password" required><br>
        Initial Weight (lbs): <input type="number" step="0.01" name="initialweight" required><br>
        <!-- Height (in): <input type="number" step="0.01" name="height" required><br> -->
        Height: 
    	<input type="number" name="feet" min="0" step="1" required> feet
    	<input type="number" name="inches" min="0" max="11" step="1" required> inches<br>
        Goal: <select name="gainweight">
                <option value="true">Gain Weight</option>
                <option value="false">Lose Weight</option>
              </select><br>
        <input type="submit" value="Register">
    </form>
    
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
   <% 
    if (containsSuccess) {
	%>
    <form action="usergoal">
        <input type="submit" value="Tell Us Your Goal!" />
    </form>
	<% 
    } 
	%>
</body>
</html>
 --%>