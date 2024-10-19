package Controller;

import dal.AccountDAO;
import Model.Members;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class ChangePasswordServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmNewPassword = request.getParameter("confirmNewPassword");

        AccountDAO dao = new AccountDAO();
        Members member = dao.getMembersByUsername(username);

        if (member == null || !member.getPassword().equals(oldPassword)) {
            request.setAttribute("errorMessage", "Your old password is incorrect");
            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            return;
        }

        if (oldPassword.equals(newPassword)) {
            request.setAttribute("errorMessage", "The new password cannot be the same as the old password");
            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            return;
        }

        if (!newPassword.equals(confirmNewPassword)) {
            request.setAttribute("errorMessage", "The new passwords do not match");
            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            return;
        }

        member.setPassword(newPassword);
        boolean isUpdated = dao.updateMemberPassword(member);

        if (isUpdated) {
            request.setAttribute("successMessage", "Password changed successfully!");
            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", "Failed to update password");
            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
        }
    }
}
