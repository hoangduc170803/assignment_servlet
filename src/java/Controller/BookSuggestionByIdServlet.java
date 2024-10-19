package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import dal.BooksDAO;
import Model.Book;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BookSuggestionByIdServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookIdParam = request.getParameter("bookId");
        String bookTitle = request.getParameter("bookTitle");
        BooksDAO booksDAO = new BooksDAO();
        List<Book> books = null;

        if (bookIdParam != null && !bookIdParam.isEmpty()) {
            books = booksDAO.getBooksByPartialId(bookIdParam);
        } else if (bookTitle != null && !bookTitle.isEmpty()) {
            books = booksDAO.getBooksByTitle(bookTitle);
        }

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        if (books != null) {
            for (Book book : books) {
                out.println("<li>" + book.getId() + "\t - \t" + book.getTitle() + "</li>");
            }
        }
    }
}
