package Controller;

import Model.Book;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.CategoryDAO;
import dal.PublisherDAO;
import Model.Category;
import Model.Publisher;
import dal.BooksDAO;
import java.util.Map;

public class addBook extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CategoryDAO categoryDAO = new CategoryDAO();
        PublisherDAO publisherDAO = new PublisherDAO();

        Map<Integer, Category> categories = categoryDAO.getAllCategories();
        Map<Integer, Publisher> publishers = publisherDAO.getAllPublishers();

        request.setAttribute("categories", categories);
        request.setAttribute("publishers", publishers);

        request.getRequestDispatcher("AddBook.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Handle form submission for adding a new book
        String title = request.getParameter("title");
        String isbn = request.getParameter("isbn");
        int publisherId = Integer.parseInt(request.getParameter("publisherId"));
        int publicationYear = Integer.parseInt(request.getParameter("publicationYear"));
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        int totalCopies = Integer.parseInt(request.getParameter("totalCopies"));
        int availableCopies = Integer.parseInt(request.getParameter("availableCopies"));

        Book newBook = new Book();
        newBook.setTitle(title);
        newBook.setIsbn(isbn);
        newBook.setPublisherId(publisherId);
        newBook.setPublicationYear(publicationYear);
        newBook.setCategoryId(categoryId);
        newBook.setTotalCopies(totalCopies);
        newBook.setAvailableCopies(availableCopies);

        BooksDAO booksDAO = new BooksDAO();
        booksDAO.addBook(newBook);

        // Redirect or forward to a success page
        response.sendRedirect(request.getContextPath() + "/listBooks");
    }

}
