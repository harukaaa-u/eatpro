<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Goals</title>
    <style>
        body {
            font-family:monospace;
            background-color: #f2f2f7;
            color: #333;
            text-align: center;
            padding-top: 50px;
        }
        h4 {
        	text-align: center;
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
            width: calc(100% - 22px); /* Adjust input width considering padding and border */
            box-sizing: border-box;
        }
        .form-submit, .back-to-home {
            text-align: center;
            margin-top: 20px;
        }
        input[type="submit"], .back-to-home a {
            background-color: #222222;
            color: #fff;
            cursor: pointer;
            transition: background-color 0.3s ease;
            padding: 10px 25px;
            text-decoration: none; /* Removes underline from link */
            display: inline-block; /* Aligns with other inline elements */
            border: none;
            border-radius: 4px;
        }
        input[type="submit"]:hover, .back-to-home a:hover {
            background-color: #242526;
        }
    </style>
</head>
<body>
    <h2>Create a New Goal</h2>
    <form action="usergoal" method="post" style="text-align: center;">
        <input type="hidden" name="action" value="create">
        <div>Goal Type: <select name="goaltype">
            <option value="WEIGHTLOSS">Weight Loss</option>
            <option value="GAINWEIGHT">Gain Weight</option>
        </select></div>
        <div>Target Weight (lbs): <input type="number" step="0.01" name="targetvalue" required></div>
        <div>Target Date (yyyy-mm-dd): <input type="date" name="targetdate" required></div>
        <div class="form-submit"><input type="submit" value="Submit Goal"></div>
    </form>
    <h4> ** We will change your previous "Active" goals to "On Hold" Status when you submit a new goal. Keep trying! Don't give up 💪</h4>

    <%-- Optional: Display success or error messages --%>
<%--     <%
        HashMap<String, String> messages = (HashMap<String, String>) request.getAttribute("messages");
        if (messages != null) {
            for (String key : messages.keySet()) {
                out.println("<div><h4>" + messages.get(key) + "</h4></div>");
            }
        }
    %> --%>
    <% if (session.getAttribute("goalMessage") != null) { %>
<script>alert('<%= session.getAttribute("goalMessage") %>');</script>
<% session.removeAttribute("goalMessage"); %>
<% } %>
<%--     
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
		<% } %> --%>
    <div class="back-to-home">
        <a href="LoggedInHomePage.jsp">Back to Home</a>
    </div>
</body>
</html>

<%-- <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Goals</title>
</head>
<body>
    <h2>User Goals</h2>
    Display messages from the servlet
    <h4><%= request.getAttribute("messages") != null ? request.getAttribute("messages") : "" %></h4>

    <h3>Create New Goal</h3>
    <form action="usergoal" method="post">
        <input type="hidden" name="action" value="create">
<!--         Username: <input type="text" name="username" required><br> -->
        Goal Type: <select name="goaltype">
                     <option value="WEIGHTLOSS">Weight Loss</option>
                     <option value="GAINWEIGHT">Gain Weight</option>
                   </select><br>
        Target Weight (lbs): <input type="number" step="0.01" name="targetvalue" required><br>
        Target Date (yyyy-mm-dd): <input type="date" name="targetdate" required><br>
        <input type="submit" value="Submit Goal">
    </form>
	
	<h4><%= request.getAttribute("messages") != null ? request.getAttribute("messages") : "" %></h4>
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
    <form action="useradjustment">
        <input type="submit" value="Check in your progress today!" />
    </form>
 <% 
    } 
 %> --%>
	
   <%--  <h3>Existing Goals</h3>
    <table border="1">
        <tr>
            <th>Goal ID</th>
            <th>Goal Type</th>
            <th>Target Weight</th>
            <th>Target Date</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        <% List<UserGoals> goals = (List<UserGoals>) request.getAttribute("goals");
           if (goals != null) {
               for (UserGoals goal : goals) { %>
               <tr>
                   <td><%= goal.getGoalId() %></td>
                   <td><%= goal.getGoalType() %></td>
                   <td><%= goal.getTargetValue() %></td>
                   <td><%= goal.getTargetDate() %></td>
                   <td><%= goal.getStatus() %></td>
                   <td>
                       <form action="usergoal" method="post">
                           <input type="hidden" name="action" value="delete">
                           <input type="hidden" name="goalid" value="<%= goal.getGoalId() %>">
                           <input type="submit" value="Delete">
                       </form>
                   </td>
               </tr>
        <%      }
           } %>
    </table> --%>
</body>
</html>
