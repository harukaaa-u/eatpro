<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="eatpro.model.MealPlans" %>
<html>
<head>
    <title>Meal Plan Display</title>
    <!-- Add link to CSS stylesheet if needed -->
</head>
<body>
<h2>Meal Plan for <%= request.getParameter("username") %></h2>

<%-- Display success or failure messages --%>
<h3><%= request.getAttribute("messages") %></h3>

<%-- Display each meal plan detail --%>
<div>
    <% List<MealPlans> mealPlans = (List<MealPlans>) request.getAttribute("mealPlans");
        if(mealPlans != null && !mealPlans.isEmpty()) {
            for(MealPlans mealPlan : mealPlans) { %>
    <div class="meal-plan">
        <h4>Meal Plan ID: <%= mealPlan.getMealPlanId() %></h4>
        <p>Total Calories for Today: <%= mealPlan.getTotalCalorieForToday() %></p>
        <p>Adjustment ID: <%= mealPlan.getAdjustment().getAdjustmentId() %></p>
        <!-- Add more details as necessary -->

        <%-- Form to update total calories --%>
        <form action="mealplandisplay" method="post">
            <input type="hidden" name="mealPlanId" value="<%= mealPlan.getMealPlanId() %>" />
            <label for="totalCalorieForToday">Update Total Calories:</label>
            <input type="number" id="totalCalorieForToday" name="totalCalorieForToday" required />
            <button type="submit">Update</button>
        </form>
    </div>
    <% }
    } else { %>
    <p>No meal plans found.</p>
    <% } %>
</div>

</body>
</html>
