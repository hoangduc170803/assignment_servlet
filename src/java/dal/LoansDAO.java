package dal;

import Model.Loan;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class LoansDAO extends DBContext {

    // Method to get member ID by username
    public Integer getMemberIdByUsername(String username) {
        Integer memberId = null;
        String sql = "SELECT member_id FROM Members WHERE username = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                memberId = rs.getInt("member_id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoansDAO.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return memberId;
    }

    public List<Loan> getAllLoans() {
        List<Loan> loans = new ArrayList<>();

        String query = "SELECT * FROM Loans";
        try (
                PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int loanId = resultSet.getInt("loan_id");
                int bookId = resultSet.getInt("book_id");
                int memberId = resultSet.getInt("member_id");
                Date loanDate = resultSet.getDate("loan_date");
                Date dueDate = resultSet.getDate("due_date");
                Date returnDate = resultSet.getDate("return_date");
                int staffId = resultSet.getInt("staff_id");

                Loan loan = new Loan(loanId, bookId, memberId, loanDate, dueDate, returnDate, staffId);
                loans.add(loan);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Optionally, log the exception or rethrow it
        }

        return loans;
    }

    public Integer getStaffIdByUsername(String username) {
        Integer memberId = null;
        String sql = "SELECT staff_id FROM Staff WHERE username = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                memberId = rs.getInt("staff_id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoansDAO.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return memberId;

    }

    // Method to get all loans by member ID
    public List<Loan> getAllLoanByMemberId(int memberId) {
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM Loans WHERE member_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, memberId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                loans.add(new Loan(rs.getInt("loan_id"), rs.getInt("book_id"), rs.getInt("member_id"), rs.getDate("loan_date"), rs.getDate("due_date"), rs.getDate("return_date"), rs.getInt("staff_id")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoansDAO.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return loans;
    }

    public String getBookTitleById(int bookId) {
        String title = null;
        String sql = "SELECT title FROM Books WHERE book_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                title = rs.getString("title");
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoansDAO.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return title;
    }

    public String getStaffNameById(int staffId) {
        String name = null;
        String sql = "SELECT username FROM Staff WHERE staff_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, staffId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                name = rs.getString("username");
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoansDAO.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return name;
    }

    public String getMemberNameById(int memberId) {
        String name = null;
        String sql = "SELECT username FROM Members WHERE member_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, memberId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                name = rs.getString("username");
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoansDAO.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return name;
    }

    public boolean createLoan(Loan loan) {
        String query = "INSERT INTO Loans (book_id, member_id, loan_date, due_date, staff_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query);) {
            stmt.setInt(1, loan.getBookId());
            stmt.setInt(2, loan.getMemberId());
            stmt.setDate(3, new java.sql.Date(loan.getLoanDate().getTime()));
            stmt.setDate(4, new java.sql.Date(loan.getDueDate().getTime()));
            stmt.setInt(5, loan.getStaffId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean returnBook(int loanId) {
        String sql = "UPDATE Loans SET return_date = GETDATE() WHERE loan_id = ?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, loanId);
            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

}
