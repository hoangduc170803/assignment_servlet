<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
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
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Your Loans</title>
    <link rel="stylesheet" href="styleLoans.css">
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
                        <c:when test="${sessionScope.username == null}">
                            <li><a href="login.jsp" class="link">Login</a></li>
                            <li><a href="register.jsp" class="link">Register</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="infoMember.jsp" class="link">${sessionScope.username}</a></li>
                            <li><a href="logout" class="link">Logout</a></li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </nav>
        <div class="main-content">
            <div class="loan-container">
                <header>Your Loans</header>
                <c:if test="${empty loanDetails}">
                    <p>You have no loans.</p>
                </c:if>
                <c:if test="${not empty loanDetails}">
                    <table>
                        <thead>
                            <tr>
                                <th>Book Title</th>
                                <th>Loan Date</th>
                                <th>Due Date</th>
                                <th>Return Date</th>
                                <th>Staff Name</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="loanDetail" items="${loanDetails}">
                                <tr>
                                    <td>${loanDetail.bookTitle}</td>
                                    <td>${loanDetail.loanDate}</td>
                                    <td>${loanDetail.dueDate}</td>
                                    <td>${loanDetail.returnDate}</td>
                                    <td>${loanDetail.staffName}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </div>
        </div>
    </div>
</body>
</html>
