<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="dal.AccountDAO" %>
<%@ page import="Model.Members" %>
<%@ page import="java.io.*" %>
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
                    <header>Edit Member Information</header>
                    <c:if test="${not empty requestScope.message}">
                        <div class="message">
                            ${requestScope.message}
                        </div>
                    </c:if>
                    <c:set var="username" value="${sessionScope.account.username}" />
                    <%
                        String username = (String) session.getAttribute("username");
                        AccountDAO dao = new AccountDAO();
                        Members member = dao.getMembersByUsername(username);

                        if (member == null) {
                            response.sendRedirect("login.jsp");
                            return;
                        }
                    %>
                    <form id="updateForm" action="updateInfo" method="post">
                        <input type="hidden" name="memberId" value="<%= member.getMemberId() %>">
                        <div class="input-box">
                            <label for="username">Username:</label>
                            <span id="username"><%= member.getUsername() %></span>
                        </div>
                        <div class="input-box">
                            <label for="password">Password:</label>
                            <a href="changePassword.jsp">Change Password</a>
                        </div>
                        <div class="input-box">
                            <label for="firstName">First Name:</label>
                            <input type="text" id="firstName" name="firstName" value="<%= member.getFirstName() %>">
                        </div>
                        <div class="input-box">
                            <label for="lastName">Last Name:</label>
                            <input type="text" id="lastName" name="lastName" value="<%= member.getLastName() %>">
                        </div>
                        <div class="input-box">
                            <label for="email">Email:</label>
                            <input type="email" id="email" name="email" value="<%= member.getEmail() %>">
                        </div>
                        <div class="input-box">
                            <label for="phone">Phone:</label>
                            <input type="text" id="phone" name="phone" value="<%= member.getPhone() %>">
                        </div>
                        <div class="input-box">
                            <label for="address">Address:</label>
                            <input type="text" id="address" name="address" value="<%= member.getAddress() %>">
                        </div>
                        <div class="input-box">
                            <input type="submit" class="submit" value="Update Information">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
