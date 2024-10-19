package Controller;

import dal.LoansDAO;
import Model.Loan;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class listLoan extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LoansDAO loansDAO = new LoansDAO();
        List<Loan> loans = loansDAO.getAllLoans(); // Lấy danh sách tất cả các khoản vay

        List<Map<String, Object>> loanDetails = new ArrayList<>();

        for (Loan loan : loans) {
            Map<String, Object> loanDetail = new HashMap<>();
            loanDetail.put("loanid", loan.getLoanId());
            loanDetail.put("loanDate", loan.getLoanDate());
            loanDetail.put("dueDate", loan.getDueDate());
            loanDetail.put("returnDate", loan.getReturnDate());
            loanDetail.put("bookTitle", loansDAO.getBookTitleById(loan.getBookId()));
            loanDetail.put("memberName", loansDAO.getMemberNameById(loan.getMemberId())); // Lấy tên thành viên từ memberId
            loanDetail.put("staffName", loansDAO.getStaffNameById(loan.getStaffId()));
            loanDetails.add(loanDetail);
        }

        request.setAttribute("loanDetails", loanDetails);
        request.getRequestDispatcher("listLoan.jsp").forward(request, response);
    }

}
