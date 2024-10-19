package Controller;

import Model.Publisher;
import dal.PublisherDAO;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class updatePublisher extends HttpServlet {

    private static final long serialVersionUID = 1L;
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String idParam = request.getParameter("id");
    PublisherDAO pDAO = new PublisherDAO();
    
    // Validate ID parameter
    if (idParam == null || idParam.isEmpty()) {
        response.getWriter().println("Invalid publisher ID.");
        return;
    }

    int id;
    try {
        id = Integer.parseInt(idParam);
    } catch (NumberFormatException e) {
        response.getWriter().println("Invalid publisher ID format.");
        return;
    }

    Publisher publisher = pDAO.getPublisherById(id);
    
    if (publisher != null) {
        request.setAttribute("publisher", publisher);
        request.getRequestDispatcher("updatePublisher.jsp").forward(request, response);
    } else {
        response.getWriter().println("Publisher with ID " + id + " not found.");
    }
}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get parameters from request
            String idParam = request.getParameter("id");
            String name = request.getParameter("name");
            String address = request.getParameter("address");
            String contact = request.getParameter("contact");
           

            // Validate ID parameter
            if (idParam == null || idParam.isEmpty()) {
                throw new IllegalArgumentException("Invalid input parameters: publisher ID is required.");
            }
            int id = Integer.parseInt(idParam);

            // Retrieve existing book record
             PublisherDAO pDAO = new PublisherDAO();
            Publisher p = pDAO.getPublisherById(id);

            if (p != null) {
                // Update book object with new data
                p.setName(name);
                p.setAddress(address);
                p.setContact(contact);
                

                // Call DAO to update book record
                pDAO.updatePublisher(p);

                // Redirect to listBooks.jsp after successful update
                response.sendRedirect(request.getContextPath() + "/listPublisher");
            } else {
                response.getWriter().println("Publisher with ID " + id + " not found.");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error updating book: " + e.getMessage());
            request.getRequestDispatcher("/updatePublisher.jsp").forward(request, response);
        }
    }
}
