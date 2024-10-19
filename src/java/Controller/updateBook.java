package Controller;

import Model.Book;
import Model.Category;
import Model.Publisher;
import dal.BooksDAO;
import dal.CategoryDAO;
import dal.PublisherDAO;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;

public class updateBook extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private BooksDAO bookDAO;

    @Override
    public void init() throws ServletException {
        bookDAO = new BooksDAO(); // Initialize BooksDAO in the init() method of the Servlet
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");

        // Validate ID parameter
        if (idParam == null || idParam.isEmpty()) {
            response.getWriter().println("Invalid book ID.");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.getWriter().println("Invalid book ID format.");
            return;
        }

        Book book = bookDAO.getBookById(id);
        CategoryDAO categoryDAO = new CategoryDAO();
        PublisherDAO publisherDAO = new PublisherDAO();

        Map<Integer, Category> categories = categoryDAO.getAllCategories();
        Map<Integer, Publisher> publishers = publisherDAO.getAllPublishers();

        request.setAttribute("categories", categories);
        request.setAttribute("publishers", publishers);

        if (book != null) {
            request.setAttribute("book", book);
            request.getRequestDispatcher("updateBook.jsp").forward(request, response);
        } else {
            response.getWriter().println("Book with ID " + id + " not found.");
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get parameters from request
            String idParam = request.getParameter("id");
            String title = request.getParameter("title");
            String isbn = request.getParameter("isbn");
            String publisherIdStr = request.getParameter("publisherId");
            String publicationYearStr = request.getParameter("publicationYear");
            String categoryIdStr = request.getParameter("categoryId");
            String totalCopiesStr = request.getParameter("totalCopies");
            String availableCopiesStr = request.getParameter("availableCopies");

            // Validate ID parameter
            if (idParam == null || idParam.isEmpty()) {
                throw new IllegalArgumentException("Invalid input parameters: book ID is required.");
            }
            int id = Integer.parseInt(idParam);

            // Validate other parameters
            int publisherId = Integer.parseInt(publisherIdStr);
            int publicationYear = Integer.parseInt(publicationYearStr);
            int categoryId = Integer.parseInt(categoryIdStr);
            int totalCopies = Integer.parseInt(totalCopiesStr);
            int availableCopies = Integer.parseInt(availableCopiesStr);

            // Retrieve existing book record
            Book book = bookDAO.getBookById(id);

            if (book != null) {
                // Update book object with new data
                book.setTitle(title);
                book.setIsbn(isbn);
                book.setPublisherId(publisherId);
                book.setPublicationYear(publicationYear);
                book.setCategoryId(categoryId);
                book.setTotalCopies(totalCopies);
                book.setAvailableCopies(availableCopies);

                // Call DAO to update book record
                bookDAO.updateBook(book);

                // Redirect to listBooks.jsp after successful update
                response.sendRedirect(request.getContextPath() + "/listBooks");
            } else {
                response.getWriter().println("Book with ID " + id + " not found.");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error updating book: " + e.getMessage());
            request.getRequestDispatcher("/updateBook.jsp").forward(request, response);
        }
    }
}
