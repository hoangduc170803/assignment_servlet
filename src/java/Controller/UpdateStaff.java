package Controller;

import Model.Staff;
import dal.StaffDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UpdateStaff extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private StaffDAO staffDAO;

    @Override
    public void init() throws ServletException {
        staffDAO = new StaffDAO(); // Initialize StaffDAO in the init() method of the Servlet
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("staff_id");

        // Validate ID parameter
        if (idParam == null || idParam.isEmpty()) {
            response.getWriter().println("Invalid staff ID.");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.getWriter().println("Invalid staff ID format.");
            return;
        }

        Staff staff = staffDAO.getStaffById(id);

        if (staff != null) {
            request.setAttribute("staff", staff);
            request.getRequestDispatcher("UpdateStaff.jsp").forward(request, response);
        } else {
            response.getWriter().println("Staff with ID " + id + " not found.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get parameters from request
            String staffIdStr = request.getParameter("staff_id");
            String firstName = request.getParameter("first_name");
            String lastName = request.getParameter("last_name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String position = request.getParameter("position");

            // Validate ID parameter
            if (staffIdStr == null || staffIdStr.isEmpty()) {
                throw new IllegalArgumentException("Invalid input parameters: staff_id is required.");
            }
            int staffId = Integer.parseInt(staffIdStr);

            // Retrieve existing staff record
            Staff staff = staffDAO.getStaffById(staffId);

            if (staff != null) {
                // Update staff object with new data
                staff.setFirstName(firstName);
                staff.setLastName(lastName);
                staff.setEmail(email);
                staff.setPhone(phone);
                staff.setPosition(position);

                // Call DAO to update staff record
                staffDAO.updateStaff(staff);

                // Redirect to listStaff.jsp after successful update
                response.sendRedirect(request.getContextPath() + "/listStaffs");
            } else {
                response.getWriter().println("Staff with ID " + staffId + " not found.");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error updating staff: " + e.getMessage());
            request.getRequestDispatcher("/UpdateStaff.jsp").forward(request, response);
        }
    }
}
