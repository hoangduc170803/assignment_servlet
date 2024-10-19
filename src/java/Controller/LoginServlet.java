package Controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import Model.Account;
import dal.AccountDAO;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String error;
        HttpSession session = request.getSession();

        AccountDAO accountDAO = new AccountDAO();
        Account account = accountDAO.getByUsernamePassword(username, password);
        if (account == null) {
            error = " Wrong username or password";
            request.setAttribute("error", error);
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            // Authentication successful
            session.setAttribute("account", account); // Lưu thông tin tài khoản vào session
            String role = account.getRole();
            if (role != null && !role.isEmpty()) {
                if (role.equalsIgnoreCase("Admin")) {
                    response.sendRedirect("admin.jsp");
                } else if (role.equalsIgnoreCase("Staff")) {
                    response.sendRedirect("staff.jsp");
                } else if (role.equalsIgnoreCase("Members")) {
                    session.setAttribute("role", role); // Lưu vai trò vào session
                    session.setAttribute("username", account.getUsername()); // Lưu username vào session
                    session.setAttribute("password", account.getPassword());
                    response.sendRedirect("member.jsp");
                }
            }
        }
    }
}
