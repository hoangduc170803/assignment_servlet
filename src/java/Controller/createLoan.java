package Controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dal.LoansDAO;
import Model.Loan;

public class createLoan extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward the request to createLoan.jsp or handle the GET request as needed
        request.getRequestDispatcher("createLoan.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get form data
        String bookIdStr = request.getParameter("book_id");
        String memberUsername = request.getParameter("username");
        String loanDateStr = request.getParameter("loan_date");
        String staffUsername = request.getParameter("staff_username");

        if (bookIdStr == null || memberUsername == null || loanDateStr == null || staffUsername == null) {
            response.sendRedirect("createLoan.jsp");
            return;
        }

        int bookId = Integer.parseInt(bookIdStr);

        // Calculate due date (loan_date + 60 days)
        Date loanDate;
        Date dueDate;
        try {
            loanDate = new SimpleDateFormat("yyyy-MM-dd").parse(loanDateStr);
            dueDate = addDays(loanDate, 60);
        } catch (ParseException e) {
            throw new ServletException("Error parsing loan date", e);
        }

        // Get member_id from username
        LoansDAO loanDAO = new LoansDAO();
        int memberId = loanDAO.getMemberIdByUsername(memberUsername);

        // Assuming LoansDAO has a method to get staff ID by username
        int staffId = loanDAO.getStaffIdByUsername(staffUsername);

        // Create Loan object
        Loan loan = new Loan();
        loan.setBookId(bookId);
        loan.setMemberId(memberId);
        loan.setLoanDate(new java.sql.Date(loanDate.getTime()));
        loan.setDueDate(new java.sql.Date(dueDate.getTime()));
        loan.setStaffId(staffId);

        // Save to database using DAO
        boolean success = loanDAO.createLoan(loan);

        if (success) {
            response.sendRedirect("listLoan");
        } else {
            response.sendRedirect("createLoan.jsp?error=Failed+to+create+loan");
        }
    }

    private Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }
}
