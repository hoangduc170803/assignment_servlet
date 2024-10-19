package Controller;

import Model.Staff;
import dal.StaffDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class changeStaffPassword extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String newPassword = request.getParameter("newPassword");
        String confirmNewPassword = request.getParameter("confirmNewPassword");

        StaffDAO staffDAO = new StaffDAO();
        Staff staff = staffDAO.getStaffByUsername(username);

        if (staff == null) {
            request.setAttribute("errorMessage", "User not found");
            request.getRequestDispatcher("inforStaff.jsp").forward(request, response);
            return;
        }

        if (!newPassword.equals(confirmNewPassword)) {
            request.setAttribute("errorMessage", "The new passwords do not match");
            request.getRequestDispatcher("inforStaff.jsp").forward(request, response);
            return;
        }

        staff.setPassword(newPassword);
        boolean isUpdated = staffDAO.updateStaffPassword(staff);

        if (isUpdated) {
            request.setAttribute("successMessage", "Password changed successfully!");
            response.sendRedirect("login.jsp"); // Redirect to login page after successful password change
        } else {
            request.setAttribute("errorMessage", "Failed to update password");
            request.getRequestDispatcher("inforStaff.jsp").forward(request, response);
        }
    }
}
