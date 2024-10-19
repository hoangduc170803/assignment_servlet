package Controller;

import dal.AccountDAO;
import dal.StaffDAO;
import Model.Staff;
import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ChangeStaffRole extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private StaffDAO staffDAO;
    private AccountDAO accountDAO;

    @Override
    public void init() throws ServletException {
        staffDAO = new StaffDAO(); // Initialize StaffDAO in the init() method of the Servlet
        accountDAO = new AccountDAO(); // Initialize AccountDAO
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String username = request.getParameter("username");

    // Retrieve staff details from DAO
    Staff staff = accountDAO.getStaffByUsername(username);

    if (staff != null) {
        // Set staff object in request attribute
        request.setAttribute("staff", staff);
        // Forward to ChangeStaffRole.jsp
        request.getRequestDispatcher("ChangeStaffRole.jsp").forward(request, response);
    } else {
        // Handle case where staff is not found
        response.getWriter().println("Staff not found for username: " + username);
    }
}


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String newRole = request.getParameter("newRole");

        try {
            // Update staff role
            staffDAO.updateRole(username, newRole);
            request.setAttribute("message", "Role updated successfully for " + username);
            
            // Delete staff if new role is "Admin" and username matches
            if (newRole.equalsIgnoreCase("Admin") && username.equals(request.getParameter("username"))) {
                staffDAO.deleteStaff(username);
                request.setAttribute("message", "Staff deleted after role change for " + username);
            }

            // Add staff to Admin table if new role is "Admin" and username matches
            if (newRole.equalsIgnoreCase("Admin") && username.equals(request.getParameter("username"))) {
                Staff staff = accountDAO.getStaffByUsername(username);
                staffDAO.addAdmin(staff.getUsername(), staff.getPassword(), "Admin");
                request.setAttribute("message", "Staff added to Admin table for " + username);
            }

            // Forward to listStaffs.jsp with the success message
            request.getRequestDispatcher("/listStaffs.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database error: " + e.getMessage());
        }
    }

    // Helper method to check if user has admin role
    private boolean isAdmin(HttpServletRequest request) {
        // Replace with your session attribute for role check
        String role = (String) request.getSession().getAttribute("role");
        return role != null && role.equalsIgnoreCase("Admin");
    }
}
