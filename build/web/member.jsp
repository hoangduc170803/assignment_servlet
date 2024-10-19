<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Model.Category" %>
<%@ page import="dal.BooksDAO" %>
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
        <link rel="stylesheet" href="styleSearch.css">
        <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
        <title>Member Area - FPT Library</title>
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

            <h2 class="search-title">Search books</h2>

            <div class="form-box">
                <div class="search-container">
                    <label for="categoryDropdown" class="search-label">Category:</label>
                    <select id="categoryDropdown" class="input-field" autocomplete="off">
                        <option value="0">All Categories</option>
                        <% 
                            BooksDAO booksDAO = new BooksDAO();
                            List<Category> categories = booksDAO.getAllCategoriesByName();
                            for (Category category : categories) { 
                        %>
                        <option value="<%= category.getId() %>"><%= category.getName() %></option>
                        <% } %>
                    </select>
                    <div class="input-box">
                        <input type="text" id="searchBox" class="input-field" placeholder="Search..." autocomplete="off">
                        <button id="searchButton" class="search-button"><i class="fas fa-search"></i></button>
                    </div>
                </div>
                <ul id="suggestionList" class="suggestion-list"></ul>
            </div>
        </div>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
        <script>
            $(document).ready(function () {
                let currentIndex = -1; // Keep track of the current suggestion index

                function redirectToBookPage() {
                    var searchTerm = $("#searchBox").val().trim();
                    var categoryId = $("#categoryDropdown").val();

                    // Perform the redirection if search term or category is specified
                    if (searchTerm !== '' || categoryId !== '0') {
                        window.location.href = "book.jsp?title=" + encodeURIComponent(searchTerm) + "&category=" + categoryId;
                    }
                }

                function fetchSuggestions() {
                    var searchTerm = $("#searchBox").val().trim();
                    var categoryId = $("#categoryDropdown").val();
                    if (searchTerm === '' && categoryId === '0') {
                        $("#suggestionList").hide();
                        return;
                    }
                    $.ajax({
                        url: "bookSuggestion",
                        type: "GET",
                        data: {searchTerm: searchTerm, categoryId: categoryId},
                        success: function (data) {
                            $("#suggestionList").html(data);
                            if (searchTerm !== '' || categoryId !== '0') {
                                $("#suggestionList").show();
                            } else {
                                $("#suggestionList").hide();
                            }
                        }
                    });
                }

                $("#searchBox").on("input", function () {
                    currentIndex = -1; // Reset current index when typing
                    fetchSuggestions();
                });

                $("#searchBox").focus(function () {
                    fetchSuggestions();
                });

                $("#searchBox").blur(function () {
                    setTimeout(function () {
                        $("#suggestionList").hide();
                    }, 100);
                });

                $("#categoryDropdown").change(function () {
                    fetchSuggestions();
                });

                $("#suggestionList").on("click", "li", function () {
                    var selectedValue = $(this).text().trim();
                    $("#searchBox").val(selectedValue);
                    $("#suggestionList").hide();
                    redirectToBookPage();
                });

                $("#searchButton").click(function () {
                    redirectToBookPage();
                });

                $("#searchBox").keydown(function (e) {
                    var suggestions = $("#suggestionList li");
                    if (e.which === 13) { // Enter key
                        redirectToBookPage();
                    } else if (e.which === 38) { // Up arrow key
                        if (currentIndex > 0) {
                            currentIndex--;
                            var selectedValue = $(suggestions[currentIndex]).text().trim();
                            $("#searchBox").val(selectedValue);
                            suggestions.removeClass('selected');
                            $(suggestions[currentIndex]).addClass('selected');
                        }
                    } else if (e.which === 40) { // Down arrow key
                        if (currentIndex < suggestions.length - 1) {
                            currentIndex++;
                            var selectedValue = $(suggestions[currentIndex]).text().trim();
                            $("#searchBox").val(selectedValue);
                            suggestions.removeClass('selected');
                            $(suggestions[currentIndex]).addClass('selected');
                        }
                    }
                });

                $("<style>")
                        .prop("type", "text/css")
                        .html("\
                    .selected { \
                        background-color: #f0f0f0; \
                    }")
                        .appendTo("head");
            });
        </script>
    </body>
</html>
