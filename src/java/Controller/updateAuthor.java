package Controller;

import Model.Author;
import Model.Publisher;
import dal.AuthorDAO;
import dal.PublisherDAO;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;

public class updateAuthor extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        AuthorDAO aDAO = new AuthorDAO();

        // Validate ID parameter
        if (idParam == null || idParam.isEmpty()) {
            response.getWriter().println("Invalid author ID.");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.getWriter().println("Invalid author ID format.");
            return;
        }

        Author author = aDAO.getAuthorById(id);

        if (author != null) {
            request.setAttribute("author", author);
            request.getRequestDispatcher("updateAuthor.jsp").forward(request, response);
        } else {
            response.getWriter().println("Author with ID " + id + " not found.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get parameters from request
            String idParam = request.getParameter("id");
            String name = request.getParameter("name");
            String birthdateStr = request.getParameter("birthdate");
            String nationality = request.getParameter("nationality");

            // Validate ID parameter
            if (idParam == null || idParam.isEmpty()) {
                throw new IllegalArgumentException("Invalid input parameters: Author ID is required.");
            }
            int id = Integer.parseInt(idParam);
            Date birthdate = null;
            try {
                birthdate = Date.valueOf(birthdateStr);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Invalid birthdate format. Please use yyyy-mm-dd.");
                request.getRequestDispatcher("addAuthor.jsp").forward(request, response);
                return;
            }
            // Retrieve existing book record
            AuthorDAO aDAO = new AuthorDAO();
            Author a = aDAO.getAuthorById(id);

            if (a != null) {
                // Update book object with new data
                a.setName(name);
                a.setBirthdate(birthdate);
                a.setNationality(nationality);

                // Call DAO to update book record
                aDAO.updateAuthor(a);

                // Redirect to listBooks.jsp after successful update
                response.sendRedirect(request.getContextPath() + "/listAuthor");
            } else {
                response.getWriter().println("Author with ID " + id + " not found.");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error updating book: " + e.getMessage());
            request.getRequestDispatcher("updateAuthor.jsp").forward(request, response);
        }
    }
}
