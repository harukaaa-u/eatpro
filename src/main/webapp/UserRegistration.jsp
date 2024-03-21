<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
