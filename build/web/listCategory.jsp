<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>List Category</title>
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
        <h1 id="welcome">Category List</h1>
        <div class="form-container">
            <form action="addCategory.jsp" method="get">
                <button type="submit" class="btn btn-primary  addCategory" id="add">Add Category</button>
            </form>
        </div>
        <div class="message">
            <%-- Thông báo lỗi từ servlet DeleteCategory --%>
            <c:if test="${not empty requestScope.error}">
                ${requestScope.error}
            </c:if>
        </div>
        <div class="message">
            <%-- Thông thành công từ servlet DeleteCategory --%>
            <c:if test="${not empty requestScope.success}">
                ${requestScope.success}
            </c:if>
        </div>
        <div class="message">
            <%-- Thông báo thành công --%>
            <c:if test="${not empty param.message}">
                ${param.message}
            </c:if>
        </div>

        <table class="table table-hover">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Description</th>
                    <th style="text-align: center">Action</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty list}">
                        <c:forEach var="entry" items="${list}">
                            <tr>
                                <td>${entry.key}</td>
                                <td>${entry.value.name}</td>
                                <td>${entry.value.description}</td>
                                <td style="display: flex; justify-content: space-around;">
                                    <form action="updateCategory"  method="get" style="display:inline;">
                                        <input type="hidden" name="id" value="${entry.key}"/>
                                        <button type="submit" class="btn btn-success " value="Update">Update </button>
                                    </form>
                                    <form action="deleteCategory" method="get" style="display:inline;" onsubmit="return confirmDelete();">
                                        <input type="hidden" name="id" value="${entry.key}"/>
                                        <button type="submit" class="btn btn-danger " value="Delete">Delete</button>
                                    </form>

                                    <script>
                                        function confirmDelete() {
                                            return confirm("Are you sure you want to delete this category?");
                                        }
                                    </script>

                                    <form action="viewBook" method="get" style="display:inline;">
                                        <input type="hidden" name="id" value="${entry.key}"/>
                                        <button type="submit" value="View Book" class="btn btn-info action-btn">View Book </button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="3">No Category found.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>

        <!-- Add Category Button -->

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
