package Controller;

import dal.LoansDAO;
import Model.Loan;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoansServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        if (username == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        LoansDAO loansDAO = new LoansDAO();
        Integer memberId = loansDAO.getMemberIdByUsername(username);

        if (memberId == null) {
            request.setAttribute("errorMessage", "User not found.");
            request.getRequestDispatcher("loans.jsp").forward(request, response);
            return;
        }

        List<Loan> loans = loansDAO.getAllLoanByMemberId(memberId);
        List<Map<String, Object>> loanDetails = new ArrayList<>();

        for (Loan loan : loans) {
            Map<String, Object> loanDetail = new HashMap<>();
            loanDetail.put("loanDate", loan.getLoanDate());
            loanDetail.put("dueDate", loan.getDueDate());
            loanDetail.put("returnDate", loan.getReturnDate());
            loanDetail.put("bookTitle", loansDAO.getBookTitleById(loan.getBookId()));
            loanDetail.put("staffName", loansDAO.getStaffNameById(loan.getStaffId()));
            loanDetails.add(loanDetail);
        }

        request.setAttribute("loanDetails", loanDetails);
        request.getRequestDispatcher("loans.jsp").forward(request, response);
    }
}
