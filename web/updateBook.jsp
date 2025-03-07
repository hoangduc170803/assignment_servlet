<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="Model.Book"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Update Book</title>
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
        <c:if test="${empty sessionScope.account || not (sessionScope.account.role.equalsIgnoreCase('Admin') or sessionScope.account.role.equalsIgnoreCase('Staff'))}">
            <c:redirect url="login.jsp"/>
        </c:if>
        <h1 id="welcome">Update Book</h1>
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

        <form action="updateBook" method="post">
            <div class="form-group row" style="margin-bottom: 20px">
                <label for="title" class="col-sm-2 col-form-label">Title:</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control"id="title" name="title" value="${book.title}" required>
                </div>
            </div>

            <div class="form-group row" style="margin-bottom: 20px">
                <label for="isbn" class="col-sm-2 col-form-label">ISBN:</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control"id="isbn" name="isbn" value="${book.isbn}"  required>
                </div>
            </div>
            <div class="form-group row" style="margin-bottom: 20px">
                <label class="col-sm-2 col-form-label"for="publisherId">Publisher:</label>
                <select class="col-sm-10"  id="publisherId" name="publisherId"  required>
                    <c:forEach items="${publishers}" var="publisher">
                        <option value="${publisher.value.publisher_id}">${publisher.value.name}</option>
                    </c:forEach>
                </select>
            </div>


            <div class="form-group row" style="margin-bottom: 20px">
                <label  for="publicationYear" class="col-sm-2 col-form-label">Publication Year:</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control"id="publicationYear" name="publicationYear" value="${book.publicationYear}"  required>
                </div>
            </div>

            <div class="form-group row" style="margin-bottom: 20px">
                <label class="col-sm-2 col-form-label"for="categoryId">Category:</label>
                <select class="col-sm-10" id="categoryId" name="categoryId" required>
                    <c:forEach items="${categories}" var="category">
                        <option value="${category.value.id}">${category.value.name}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group row" style="margin-bottom: 20px">
                <label for="totalCopies" class="col-sm-2 col-form-label">Total Copies:</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control"id="totalCopies" name="totalCopies" value="${book.totalCopies}"  required>
                </div>
            </div>
            <div class="form-group row" style="margin-bottom: 20px">
                <label for="availableCopies" class="col-sm-2 col-form-label">Available Copies:</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="availableCopies" name="availableCopies" value="${book.availableCopies}" required>
                </div>
            </div>

            <div class="form-container">

                <input type="hidden" name="id" value="${book.id}">
                <input type="submit" class="btn btn-primary " value="Update Book">
            </div>
        </form>
        <div class="form-container">
            <form action="deleteBook" method="get">
                <input type="hidden" name="id" value="${book.id}">
                <input type="submit" style="margin: 30px" class="btn btn-danger  " value="Delete Book">
            </form>
        </div>
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
