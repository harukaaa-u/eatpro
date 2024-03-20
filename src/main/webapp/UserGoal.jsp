<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Goals</title>
</head>
<body>
    <h2>User Goals</h2>
    <%-- Display messages from the servlet --%>
    <h4><%= request.getAttribute("messages") != null ? request.getAttribute("messages") : "" %></h4>

    <h3>Create New Goal</h3>
    <form action="usergoal" method="post">
        <input type="hidden" name="action" value="create">
        Username: <input type="text" name="username" required><br>
        Goal Type: <select name="goaltype">
                     <option value="Weight Loss">Weight Loss</option>
                     <option value="Gain Weight">Gain Weight</option>
                   </select><br>
        Target Weight: <input type="number" step="0.01" name="targetvalue" required><br>
        Target Date: <input type="date" name="targetdate" required><br>
        <input type="submit" value="Submit Goal">
    </form>

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
