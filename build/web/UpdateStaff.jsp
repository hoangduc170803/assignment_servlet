<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="Model.Staff"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Update Staff</title>
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
        <c:if test="${empty sessionScope.account || !sessionScope.account.role.equalsIgnoreCase('Admin')}">
            <c:redirect url="login.jsp"/>
        </c:if>
        <h1 id="welcome">Update Staff</h1>
        <c:if test="${not empty errorMessage}">
            <div class="error">
                ${errorMessage}
            </div>
        </c:if>

        <c:if test="${not empty successMessage}">
            <div class="success">
                ${successMessage}
            </div>
        </c:if>

        <form action="updatestaff" method="post">
            <div class="form-group row" style="margin-bottom: 20px">
                <label for="username" class="col-sm-2 col-form-label">Username:</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control"id="username" name="username" value="${staff.username}" readonly >
                </div>
            </div>
            <div class="form-group row" style="margin-bottom: 20px">
                <label for="first_name" class="col-sm-2 col-form-label">First Name:</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control"id="first_name" name="first_name" value="${staff.firstName}" required >
                </div>
            </div>
            <div class="form-group row" style="margin-bottom: 20px">
                <label for="last_name" class="col-sm-2 col-form-label">Last Name:</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control"id="last_name" name="last_name" value="${staff.lastName}" required >
                </div>
            </div>

            <div class="form-group row" style="margin-bottom: 20px">
                <label for="email" class="col-sm-2 col-form-label">Email:</label>
                <div class="col-sm-10">
                    <input type="email" class="form-control"id="email" name="email" value="${staff.email}" required >
                </div>
            </div>



            <div class="form-group row" style="margin-bottom: 20px">
                <label for="phone" class="col-sm-2 col-form-label">Phone:</label>
                <div class="col-sm-10">
                    <input type="tel" class="form-control"id="phone" name="phone" value="${staff.phone}" required >
                </div>
            </div>
            <div class="form-group row" style="margin-bottom: 20px">
                <label for="phone" class="col-sm-2 col-form-label">Phone:</label>
                <div class="col-sm-10">
                    <input type="tel" class="form-control"id="phone" name="phone" value="${staff.phone}" required >
                </div>
            </div>
            <div class="form-group row" style="margin-bottom: 20px">
                <label for="position" class="col-sm-2 col-form-label">Position:</label>
                <div class="col-sm-10">
                    <input  class="form-control" type="text" id="position" name="position" value="${staff.position}"  required >
                </div>
            </div>

            <div class="form-container">
                <input type="hidden" name="staff_id" value="${staff.staffId}">
                <input type="submit" class="btn btn-primary btn-lg" value="Update Staff">
            </div>
        </form>
        <div class="form-container">
            <form action="changeRole" method="get">
                <c:forEach var="entry" items="${staffList}">
                    <c:if test="${entry.key eq staff.staffId}">
                        <a href="changeRole?username=${entry.value.username}">${entry.value.username}</a>
                    </c:if>
                </c:forEach>

                <input type="hidden" name="username" value="${staff.username}">
                <input type="submit" class="btn btn-success btn-lg" style="margin: 30px" value="Change Role">
            </form>
        </div>
        <div class="form-container">
            <form action="deleteStaff" method="get">
                <input type="hidden" name="staff_id" value="${staff.staffId}">
                <input type="submit" class="btn btn-danger btn-lg" value="Delete Staff">
            </form>

        </div>
    </body>
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
</html>
