<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>  
<%@ page import="Model.Book" %>      
<%@ page import="dal.BooksDAO" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    // Check if the user is logged in
    if (session.getAttribute("account") == null) {
        String loginMessage = "You have to log in to use this feature.";
        response.sendRedirect("login.jsp?message=" + java.net.URLEncoder.encode(loginMessage, "UTF-8"));
        return;
    }

    String bookTitle = request.getParameter("title");
    String bookIdParam = request.getParameter("id");
    int bookId = (bookIdParam != null && !bookIdParam.isEmpty()) ? Integer.parseInt(bookIdParam) : -1;

    BooksDAO booksDAO = new BooksDAO();
    List<Book> books = bookId != -1 ? booksDAO.getBooksById(bookId) : booksDAO.getBooksByTitle(bookTitle);

    Book book = null;
    if (!books.isEmpty()) {
        book = books.get(0); // Assuming only one book is found by title
    }
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Book Details - <%= (book != null) ? book.getTitle() : "Book Not Found" %></title>
        <link rel="stylesheet" href="styleBook.css">
        <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
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
            <div class="main-content">
                <div class="left-column">
                    <div class="search-container">
                        <div class="input-box">
                            <input type="text" id="searchBoxId" class="input-field" placeholder="ID" autocomplete="off">
                            <input type="text" id="searchBoxTitle" class="input-field" placeholder="Search by Title..." autocomplete="off">
                            <i class="fas fa-search" id="searchIcon"></i>
                        </div>
                        <ul id="suggestionList" class="suggestion-list"></ul>
                    </div>
                </div>
                <div class="right-column">
                    <% if (book != null) { %>
                    <h2><%= book.getTitle() %></h2>
                    <p>ISBN: <%= book.getIsbn() %></p>
                    <p>Publisher: <%= booksDAO.getPublisherNameById(book.getPublisherId()) %></p>
                    <p>Publication year: <%= book.getPublicationYear() %></p>
                    <p>Category: <%= booksDAO.getCategoryNameById(book.getCategoryId()) %></p>
                    <p>Total Copies: <%= book.getTotalCopies() %></p>
                    <p>Available Copies: <%= book.getAvailableCopies() %></p>
                    <!-- Additional details as needed -->
                    <% } else { %>
                    <p>Book not found.</p>
                    <% } %>
                </div>
            </div>
        </div>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
        <script>
            $(document).ready(function () {
                let currentIndex = -1; // Keep track of the current suggestion index

                function redirectToBookPage() {
                    var bookId = $("#searchBoxId").val().trim();
                    var bookTitle = $("#searchBoxTitle").val().trim();

                    // Perform the redirection if bookId or bookTitle is specified
                    if (bookId !== '' || bookTitle !== '') {
                        window.location.href = "book.jsp?id=" + encodeURIComponent(bookId) + "&title=" + encodeURIComponent(bookTitle);
                    }
                }

                function fetchSuggestions() {
                    var bookId = $("#searchBoxId").val().trim();
                    var bookTitle = $("#searchBoxTitle").val().trim();
                    if (bookId === '' && bookTitle === '') {
                        $("#suggestionList").hide();
                        return;
                    }
                    $.ajax({
                        url: "bookSuggestionById",
                        type: "GET",
                        data: {bookId: bookId, bookTitle: bookTitle},
                        success: function (data) {
                            $("#suggestionList").html(data);
                            if (bookId !== '' || bookTitle !== '') {
                                $("#suggestionList").show();
                            } else {
                                $("#suggestionList").hide();
                            }
                        }
                    });
                }

                $("#searchBoxId, #searchBoxTitle").on("input", function () {
                    currentIndex = -1; // Reset current index when typing
                    fetchSuggestions();
                });

                $("#searchBoxId, #searchBoxTitle").focus(function () {
                    fetchSuggestions();
                });

                $("#searchBoxId, #searchBoxTitle").blur(function () {
                    setTimeout(function () {
                        $("#suggestionList").hide();
                    }, 100);
                });

                // Change from dblclick to click
                $("#suggestionList").on("click", "li", function () {
                    var selectedValue = $(this).text().trim();
                    var parts = selectedValue.split("\t - \t");
                    if (parts.length === 2) {
                        $("#searchBoxId").val(parts[0]);
                        $("#searchBoxTitle").val(parts[1]);
                    }
                    $("#suggestionList").hide();
                    redirectToBookPage();
                });

                // Click event for the search icon
                $("#searchIcon").click(function () {
                    redirectToBookPage();
                });

                // Key events for the search input fields
                $("#searchBoxId, #searchBoxTitle").keydown(function (e) {
                    var suggestions = $("#suggestionList li");
                    if (e.which === 13) { // Enter key
                        redirectToBookPage();
                    } else if (e.which === 38) { // Up arrow key
                        if (currentIndex > 0) {
                            currentIndex--;
                            var selectedValue = $(suggestions[currentIndex]).text().trim();
                            var parts = selectedValue.split("\t - \t");
                            if (parts.length === 2) {
                                $("#searchBoxId").val(parts[0]);
                                $("#searchBoxTitle").val(parts[1]);
                            }
                            suggestions.removeClass('selected');
                            $(suggestions[currentIndex]).addClass('selected');
                        }
                    } else if (e.which === 40) { // Down arrow key
                        if (currentIndex < suggestions.length - 1) {
                            currentIndex++;
                            var selectedValue = $(suggestions[currentIndex]).text().trim();
                            var parts = selectedValue.split("\t - \t");
                            if (parts.length === 2) {
                                $("#searchBoxId").val(parts[0]);
                                $("#searchBoxTitle").val(parts[1]);
                            }
                            suggestions.removeClass('selected');
                            $(suggestions[currentIndex]).addClass('selected');
                        }
                    }
                });

                // Add CSS for selected suggestion
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
