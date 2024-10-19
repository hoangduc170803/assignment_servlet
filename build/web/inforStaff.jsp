<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="Model.Staff" %>
<%@ page import="dal.StaffDAO" %>
<%
    // Check if session contains an account
    if (session == null || session.getAttribute("account") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    // Check if the account role is Staff
    Model.Account account = (Model.Account) session.getAttribute("account");
    if (!"Staff".equalsIgnoreCase(account.getRole())) {
        response.sendRedirect("login.jsp");
        return;
    }

    // Get staff information from session
    Staff staff = (Staff) session.getAttribute("staff");
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Edit Member Information</title>
        <link rel="stylesheet" href="styleInfoUpdate.css">
        <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
    </head>
    <body>
        <div class="wrapper">
            <nav class="nav">
                <div class="nav-logo">
                    <img src="Image/Logo.svg" alt="">
                </div>
                <div class="nav-menu" id="navMenu">
                    <ul>
                        <li><a href="staff.jsp" class="link">Home</a></li>
                            <c:choose>
                                <c:when test="${sessionScope.account == null}">
                                <li><a href="login.jsp" class="link">Login</a></li>
                                <li><a href="register.jsp" class="link">Register</a></li>
                                </c:when>
                                <c:otherwise>
                                <li><a href="StaffInfor?username=${sessionScope.account.username}" class="link">${sessionScope.account.username}</a></li>
                                <li><a href="logout" class="link">Logout</a></li>
                                </c:otherwise>
                            </c:choose>
                    </ul>
                </div>
            </nav>
            <div class="form-box">
                <div class="form-container">
                    <header>Your Information</header>
                        <c:if test="${not empty requestScope.errorMessage}">
                        <div class="error-message">
                            ${requestScope.errorMessage}
                        </div>
                    </c:if>
                    <c:if test="${not empty requestScope.successMessage}">
                        <div class="success-message">
                            ${requestScope.successMessage}
                        </div>
                    </c:if>
                    <div class="input-box">
                        <label for="username">Username:</label>
                        <span>${staff.getUsername()}</span>
                    </div>
                    <div class="input-box">
                        <label for="firstName">First Name:</label>
                        <span>${staff.getFirstName()}</span>
                    </div>
                    <div class="input-box">
                        <label for="lastName">Last Name:</label>
                        <span>${staff.getLastName()}</span>
                    </div>
                    <div class="input-box">
                        <label for="email">Email:</label>
                        <span>${staff.getEmail()}</span>
                    </div>
                    <div class="input-box">
                        <label for="phone">Phone:</label>
                        <span>${staff.getPhone()}</span>
                    </div>
                    <div class="input-box">
                        <label for="position">Position:</label>
                        <span>${staff.getPosition()}</span>
                    </div>
                    <form id="changePasswordForm" action="changeStaffPassword" method="post">
                        <input type="hidden" name="username" value="${sessionScope.account.username}">
                        <input type="button" value="Change Password" onclick="showPasswordFields()">

                        <!-- New Password and Confirm New Password fields -->
                        <div id="passwordFields" style="display: none;">
                            <label for="newPassword">New Password:</label>
                            <input type="password" id="newPassword" name="newPassword" required><br>

                            <label for="confirmNewPassword">Confirm New Password:</label>
                            <input type="password" id="confirmNewPassword" name="confirmNewPassword" required><br>
                        </div>

                        <!-- Submit button -->
                        <input type="submit" value="Submit" style="display: none;">
                    </form>


                    <script>
                        function showPasswordFields() {
                            var passwordFields = document.getElementById("passwordFields");
                            var submitButton = document.querySelector('input[type="submit"]');

                            // Show the password fields
                            passwordFields.style.display = "block";

                            // Show the submit button
                            submitButton.style.display = "block";

                            // Optionally, you can hide the "Change Password" button after showing the password fields
                            var changePasswordButton = document.querySelector('input[type="button"][value="Change Password"]');
                            changePasswordButton.style.display = "none";
                        }
                    </script>
                </div>
            </div>
        </div>
    </body>
</html>
