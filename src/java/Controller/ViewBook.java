package Controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.*;
import Model.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 *
 * @author Admin
 */
public class ViewBook extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");

        try {
            int categoryId = Integer.parseInt(idStr);

            try {
                BooksDAO bookDAO = new BooksDAO();
                CategoryDAO categoryDAO = new CategoryDAO();
                PublisherDAO publisherDAO = new PublisherDAO();

                List<Book> listB;
                if (categoryId == -1) {
                    listB = bookDAO.getAllBooks();
                } else {
                    listB = bookDAO.getAllBooksByCategory(categoryId);
                }
                
                Map<Integer, Category> listC = categoryDAO.getAllCategories();
                Map<Integer, String> publisherNames = new HashMap<>();

                // Lấy tên nhà xuất bản cho từng cuốn sách
                for (Book book : listB) {
                    String publisherName = publisherDAO.getPublisherNameByPublisherId(book.getPublisherId());
                    publisherNames.put(book.getPublisherId(), publisherName);
                }

                if (!listC.isEmpty()) {
                    request.setAttribute("listB", listB);
                    request.setAttribute("listC", listC);
                    request.setAttribute("publisherNames", publisherNames);

                    request.getRequestDispatcher("viewBook.jsp").forward(request, response);
                } else {
                    response.getWriter().println("No categories found.");
                }
            } catch (Exception e) {
                throw new ServletException("Database access error", e);
            }
        } catch (NumberFormatException e) {
            response.getWriter().println("Invalid category ID format.");
        }
    }
}