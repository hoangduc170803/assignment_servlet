<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    // Check if session contains an account
    if (session == null || session.getAttribute("account") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    // Check if the account role is Admin
    Model.Account account = (Model.Account) session.getAttribute("account");
    if (!"Members".equalsIgnoreCase(account.getRole())) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Change Password</title>
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
                    <li><a href="member.jsp" class="link">Home</a></li>
                    <c:choose>
                        <c:when test="${sessionScope.account == null}">
                            <li><a href="login.jsp" class="link">Login</a></li>
                            <li><a href="register.jsp" class="link">Register</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="infoMember.jsp" class="link">${sessionScope.account.username}</a></li>
                            <li><a href="logout" class="link">Logout</a></li>
                            <li><a href="loans" class="link">Loans</a></li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </nav>
        <div class="form-box">
            <div class="form-container">
                <header>Change Password</header>
                <c:if test="${not empty errorMessage}">
                    <div class="error-message">${errorMessage}</div>
                </c:if>
                <c:if test="${not empty successMessage}">
                    <div class="success-message ">${successMessage}</div>
                </c:if>
                <form id="changePasswordForm" action="changePassword" method="post">
                    <div class="input-box">
                        <label for="oldPassword">Old Password:</label>
                        <input type="password" id="oldPassword" name="oldPassword" required>
                    </div>
                    <div class="input-box">
                        <label for="newPassword">New Password:</label>
                        <input type="password" id="newPassword" name="newPassword" required>
                    </div>
                    <div class="input-box">
                        <label for="confirmNewPassword">Confirm New Password:</label>
                        <input type="password" id="confirmNewPassword" name="confirmNewPassword" required>
                    </div>
                    <div class="input-box">
                        <input type="submit" class="submit" value="Change Password">
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
