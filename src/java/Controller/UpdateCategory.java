package Controller;

import dal.CategoryDAO;
import Model.Category;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateCategory extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");

        // Validate ID parameter
        if (idParam == null || idParam.isEmpty()) {
            response.getWriter().println("Invalid category ID.");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.getWriter().println("Invalid category ID format.");
            return;
        }

        CategoryDAO categoryDAO = new CategoryDAO();
        Category category = categoryDAO.getCategoryById(id);

        if (category != null) {
            request.setAttribute("category", category);
            request.getRequestDispatcher("updateCategory.jsp").forward(request, response);
        } else {
            response.getWriter().println("Category with ID " + id + " not found.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String newName = request.getParameter("name");
        String description = request.getParameter("description");

        // Validate input parameters
        if (newName == null || newName.isEmpty() || description == null || description.isEmpty()) {
            request.setAttribute("error", "Name and Description are required!");
            request.getRequestDispatcher("updateCategory.jsp").forward(request, response);
            return;
        }

        CategoryDAO categoryDAO = new CategoryDAO();

        try {
            // Fetch the category being updated by its ID
            Category existingCategory = categoryDAO.getCategoryById(id);

            if (existingCategory == null) {
                request.setAttribute("error", "Category not found!");
                request.getRequestDispatcher("updateCategory.jsp").forward(request, response);
                return;
            }

            // Check if the new name is already used by another category
            Category categoryWithNewName = categoryDAO.getCategoryByName(newName);
            if (categoryWithNewName != null && categoryWithNewName.getId() != existingCategory.getId()) {
                request.setAttribute("error", "Category name already exists!");
                request.setAttribute("category", existingCategory); // Re-populate the form with existing data
                request.getRequestDispatcher("updateCategory.jsp").forward(request, response);
                return;
            }

            // Update category details
            existingCategory.setName(newName);
            existingCategory.setDescription(description);
            categoryDAO.updateCategory(existingCategory);

            // Redirect to listCategory page with success message
            response.sendRedirect("listCategory?message=Update%20successful");
        } catch (Exception e) {
            request.setAttribute("error", "An error occurred while updating the category!");
            e.printStackTrace();
            // Forward back to the update page
            request.getRequestDispatcher("updateCategory.jsp").forward(request, response);
        }
    }
}
