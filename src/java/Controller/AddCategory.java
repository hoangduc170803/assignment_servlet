package Controller;

import Model.Category;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import dal.CategoryDAO;
import java.util.Map;

public class AddCategory extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CategoryDAO categoryDAO;

    @Override
    public void init() {
        categoryDAO = new CategoryDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check for a success message and set it as a request attribute if present
        String message = request.getParameter("message");
        if (message != null) {
            request.setAttribute("success", message);
        }

        Map<Integer, Category> list = categoryDAO.getAllCategories();
        request.setAttribute("list", list);
        request.getRequestDispatcher("addCategory.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        if (name != null && !name.isEmpty() && description != null && !description.isEmpty()) {
            try {
                Category existingCategory = categoryDAO.getCategoryByName(name);

                if (existingCategory != null) {
                    request.setAttribute("error", "Category name already exists!");
                    // Forward back to the addCategory.jsp with error message
                    request.getRequestDispatcher("addCategory.jsp").forward(request, response);
                } else {
                    categoryDAO.addCategory(name, description);
                    // Redirect to listCategory page with success message
                    response.sendRedirect("listCategory?message=Category%20added%20successfully");
                }
            } catch (Exception e) {
                request.setAttribute("error", "An error occurred while adding the category!");
                e.printStackTrace();
                // Forward back to the addCategory.jsp with error message
                request.getRequestDispatcher("addCategory.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "Name and Description are required!");
            // Forward back to the addCategory.jsp with error message
            request.getRequestDispatcher("addCategory.jsp").forward(request, response);
        }
    }
}
