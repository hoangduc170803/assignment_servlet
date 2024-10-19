package Controller;

import Model.Author;
import dal.AuthorDAO;
import java.io.IOException;
import java.sql.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class addAuthor extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Handle form submission for adding a new author
        String name = request.getParameter("name");
        String birthdateStr = request.getParameter("birthdate");
        String nationality = request.getParameter("nationality");
        
        // Convert birthdate from String to java.util.Date
        Date birthdate = null;
        try {
            birthdate = Date.valueOf(birthdateStr);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Invalid birthdate format. Please use yyyy-mm-dd.");
            request.getRequestDispatcher("addAuthor.jsp").forward(request, response);
            return;
        }
      
        Author newA = new Author();
        newA.setName(name);
        newA.setBirthdate(birthdate);
        newA.setNationality(nationality);
        
        AuthorDAO ADAO = new AuthorDAO();
        ADAO.addAuthor(newA);

        // Redirect or forward to a success page
        response.sendRedirect(request.getContextPath() + "/listAuthor");
    }

}
