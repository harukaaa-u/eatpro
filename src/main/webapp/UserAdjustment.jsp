<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.HashMap" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Adjustment</title>
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
            width: calc(100% - 22px);
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
        label {
            display: block;
            margin-top: 10px;
        }
    </style>
</head>
<body>
    <h2> How are you doing today? </h2>
    <form action="useradjustment" method="post">
<!--         <label for="username">Username:</label>
        <input id="username" name="username" type="text" required> -->

        <label for="datelogged">Date Logged (yyyy-mm-dd):</label>
        <input id="datelogged" name="datelogged" type="date" required>

        <label for="weight">Weight (lbs):</label>
        <input id="weight" name="weight" type="number" step="0.01" required>

        <label for="workouttoday">Workout Today:</label>
        <select id="workouttoday" name="workouttoday">
            <option value="true">Yes</option>
            <option value="false">No</option>
        </select>

        <label for="expectedexercisecalorie">Expected Exercise Calorie:</label>
        <input id="expectedexercisecalorie" name="expectedexercisecalorie" type="number" required>

        <div class="form-submit">
            <input type="submit" value="Submit">
        </div>
    </form>
    
    <%--  <% String goalMessage = (String) session.getAttribute("goalMessage");
   		session.removeAttribute("goalMessage"); // Remove the attribute after using it
   		if (goalMessage != null) {
	%>
    <script>
        alert('<%= goalMessage %>');
        // Redirect if needed
        <% if (goalMessage.contains("Congratulations") || goalMessage.contains("Take a break")) { %>
            window.location.href = "<%=request.getContextPath()%>/UserGoal.jsp";
        <% } %>
    </script>
<% } %> --%>

		<%@ page import="java.util.Objects" %>
		<% if (session.getAttribute("goalMessage") != null && session.getAttribute("goalActionRedirect") != null) { %>
		<script>
		    window.onload = function() {
		        var message = "<%= session.getAttribute("goalMessage") %>";
		        var redirectURL = "<%= session.getAttribute("goalActionRedirect") %>";
		        
		        alert(message);
		        
		        window.location.href = redirectURL;
		
		        <% 
		        session.removeAttribute("goalMessage");
		        session.removeAttribute("goalActionRedirect");
		        %>
		    }
		</script>
		<% } %>

</body>
</html>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.HashMap" %>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>User Adjustment</title>
</head> -->
<!DOCTYPE html> <!-- Have to use HTML5 otherwise number and date unrecognized  -->
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>User Adjustment</title>
</head>
<body>
<body>
<h1>User Adjustment</h1>
<form action="useradjustment" method="post">
    <p>
        <label for="username">Username:</label>
        <input id="username" name="username" value="">
    </p>
    <p>
        <label for="datelogged">Date Logged (yyyy-mm-dd):</label>
        <input id="datelogged" name="datelogged" type="date" value="">
    </p>
    <p>
        <label for="weight">Weight (lbs):</label>
        <input id="weight" name="weight" type="number" step="0.01" value="">
    </p>
    <p>
        <label for="workouttoday">Workout Today:</label>
        <select id="workouttoday" name="workouttoday">
            <option value="true">Yes</option>
            <option value="false">No</option>
        </select>
    </p>
    <p>
        <label for="expectedexercisecalorie">Expected Exercise Calorie:</label>
        <input id="expectedexercisecalorie" name="expectedexercisecalorie" type="number" value="">
    </p>
    <p>
        <input type="submit" value="Submit">
    </p>
</form>
<br/><br/>
<c:if test="${not empty messages.success}">
    <p>
        <span id="successMessage"><b>${messages.success}</b></span>
    </p>
</c:if>

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

	<a href="dailyintakecalculation?username=${username}">${username}, View your calories for today!</a>
	<form action="dailyintakecalculation?username=<%= request.getAttribute("username") %>" method="get">
        <input type="submit" value="View your meal plan!">
    </form>
<%
    }
%>
</body>
</html>
 --%>