package Controller;

import Model.Publisher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.PublisherDAO;

public class addPublisher extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Handle form submission for adding a new book
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String contact = request.getParameter("contact");
      
        Publisher newP = new Publisher();
        newP.setName(name);
        newP.setAddress(address);
        newP.setContact(contact);
        PublisherDAO booksDAO = new  PublisherDAO();
        booksDAO.addPublisher(newP);

        // Redirect or forward to a success page
        response.sendRedirect(request.getContextPath() + "/listPublisher");
    }

}
