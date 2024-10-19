package Controller;

import Model.Staff;
import dal.AccountDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

/**
 * CreateStaff Servlet
 */
public class CreateStaff extends HttpServlet {

    private AccountDAO accountDAO;

    @Override
    public void init() throws ServletException {
        // Initialize AccountDAO
        accountDAO = new AccountDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String first_name = request.getParameter("first_name");
        String last_name = request.getParameter("last_name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String position = request.getParameter("position");
        String hireDateStr = request.getParameter("hire_date");

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date hire_date = null;

        // Validate and parse hireDate
        try {
            hire_date = new Date(dateFormat.parse(hireDateStr).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Invalid hire date format. Please use MM-dd-yyyy.");
            request.getRequestDispatcher("createStaff.jsp").forward(request, response);
            return;
        }

        try {
            if (isUsernameTaken(username)) {
                request.setAttribute("errorMessage", "Username is already taken.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("createStaff.jsp");
                dispatcher.forward(request, response);
                return;
            }

            if (accountDAO.createStaff(username, password, first_name, last_name, email, phone, position, hire_date)) {
                request.setAttribute("successMessage", "Staff member created successfully!");
            } else {
                request.setAttribute("errorMessage", "Failed to create staff. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Server error. Please try again later.");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("createStaff.jsp");
        dispatcher.forward(request, response);
    }

    private boolean isUsernameTaken(String username) throws SQLException {
        Staff staff = accountDAO.getStaffByUsername(username);
        return staff != null;
    }
}
