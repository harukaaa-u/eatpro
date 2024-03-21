<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
<%-- <c:if test="${not empty messages.success}">
    <p>
        <span id="successMessage"><b>${messages.success}</b></span>
    </p>
</c:if> --%>

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
	<%-- <form action="dailyintakecalculation?username=<%= request.getAttribute("username") %>" method="get">
        <input type="submit" value="View your meal plan!">
    </form> --%>
<%
    }
%>
</body>
</html>
