package Controller;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Model.Staff;
import dal.StaffDAO;

public class StaffInfor extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        if (username == null || username.isEmpty()) {
            // If username is not provided in query parameter, handle the error
            response.sendRedirect("login.jsp");
            return;
        }

        // Fetch staff information from DAO
        StaffDAO staffDAO = new StaffDAO();
        Staff staff = staffDAO.getStaffByUsername(username);

        if (staff == null) {
            // If staff information is not found, handle the error appropriately
            response.sendRedirect("login.jsp");
            return;
        }

        // Set staff object in request attribute to be accessed in JSP
        request.setAttribute("staff", staff);

        // Forward the request to inforStaff.jsp to display staff information
        request.getRequestDispatcher("inforStaff.jsp").forward(request, response);
    }
}
