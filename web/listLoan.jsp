<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Loan List</title>
        <link rel="stylesheet" href="styleList.css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css"/>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css"/>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"/>
    </head>
    <body id="body-pd">
        <header class="header" id="header">
            <div class="header_toggle"> <i class='bx bx-menu' id="header-toggle"></i> </div>

        </header>

        <div class="nav-menu l-navbar" id="nav-bar">
            <nav class="nav">
                <div> <a href="staff.jsp" class="nav_logo"> <i class='bx bx-layer nav_logo-icon'></i> <span class="nav_logo-name">Home</span> </a>
                    <div class="nav_list"> <a href="StaffInfor?username=${sessionScope.account.username}" class="nav_link active"> <i class='bx bx-grid-alt nav_icon'></i> <span class="nav_name">${sessionScope.account.username}</span> </a> </div>
                </div> <a href="logout" class="nav_link"> <i class='bx bx-log-out nav_icon'></i> <span class="nav_name">Logout</span> </a>
            </nav>
        </div>
        <c:choose>
            <c:when test="${empty sessionScope.account || (sessionScope.account.role != 'Admin' && sessionScope.account.role != 'Staff')}">
                <c:redirect url="login.jsp"/>
            </c:when>
            <c:otherwise>
                <h1 id="welcome">Loan List</h1>
                <c:if test="${sessionScope.account.role == 'Staff'}">
                    <div class="form-container">
                        <form action="createLoan" method="get">
                            <button type="submit"  class="btn btn-primary ">Create Loan</button>
                        </form>
                    </div> 
                </c:if>
                <div class="message">
                    <c:if test="${not empty requestScope.error}">
                        ${requestScope.error}
                    </c:if>
                </div>
                <div class="message">
                    <c:if test="${not empty requestScope.success}">
                        ${requestScope.success}
                    </c:if>
                </div>
                <div class="message">
                    <c:if test="${not empty param.message}">
                        ${param.message}
                    </c:if>
                </div>

                <c:if test="${not empty loanDetails}">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Book</th>
                                <th>Member</th>
                                <th>Loan Date</th>
                                <th>Due Date</th>
                                <th>Return Date</th>
                                    <c:if test="${sessionScope.account.role == 'Admin'}">
                                    <th>Staff</th>
                                    </c:if>
                                <th>Return Book</th> <!-- New column for action button -->
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="loanDetail" items="${loanDetails}">
                                <tr>
                                    <td>${loanDetail.loanid}</td>
                                    <td>${loanDetail.bookTitle}</td>
                                    <td>${loanDetail.memberName}</td>
                                    <td>${loanDetail.loanDate}</td>
                                    <td>${loanDetail.dueDate}</td>
                                    <td>${loanDetail.returnDate}</td>
                                    <c:if test="${sessionScope.account.role == 'Admin'}">
                                        <td>${loanDetail.staffName}</td>
                                    </c:if>
                                    <td> <!-- Form to submit loanId to returns servlet -->
                                        <form action="returnBook">
                                            <input type="hidden" name="loanId" value="${loanDetail.loanid}">
                                            <button type="submit">Return</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>

                <c:if test="${empty loanDetails}">
                    <p style="color: red;">No loans found.</p>
                </c:if>

                <!-- Create Loan Button -->


            </c:otherwise>
        </c:choose>
        <script>
            document.addEventListener("DOMContentLoaded", function (event) {

                const showNavbar = (toggleId, navId, bodyId, headerId) => {
                    const toggle = document.getElementById(toggleId),
                            nav = document.getElementById(navId),
                            bodypd = document.getElementById(bodyId),
                            headerpd = document.getElementById(headerId)

// Validate that all variables exist
                    if (toggle && nav && bodypd && headerpd) {
                        toggle.addEventListener('click', () => {
// show navbar
                            nav.classList.toggle('show')
// change icon
                            toggle.classList.toggle('bx-x')
// add padding to body
                            bodypd.classList.toggle('body-pd')
// add padding to header
                            headerpd.classList.toggle('body-pd')
                        })
                    }
                }

                showNavbar('header-toggle', 'nav-bar', 'body-pd', 'header')

                /*===== LINK ACTIVE =====*/
                const linkColor = document.querySelectorAll('.nav_link')

                function colorLink() {
                    if (linkColor) {
                        linkColor.forEach(l => l.classList.remove('active'))
                        this.classList.add('active')
                    }
                }
                linkColor.forEach(l => l.addEventListener('click', colorLink))

                // Your code to run since DOM is loaded and ready
            });
        </script>
    </body>
</html>